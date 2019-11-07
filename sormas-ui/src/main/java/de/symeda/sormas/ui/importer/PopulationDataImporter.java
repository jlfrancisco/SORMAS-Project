package de.symeda.sormas.ui.importer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.vaadin.ui.UI;

import de.symeda.sormas.api.AgeGroup;
import de.symeda.sormas.api.FacadeProvider;
import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.api.i18n.Validations;
import de.symeda.sormas.api.importexport.InvalidColumnException;
import de.symeda.sormas.api.infrastructure.PopulationDataCriteria;
import de.symeda.sormas.api.infrastructure.PopulationDataDto;
import de.symeda.sormas.api.person.Sex;
import de.symeda.sormas.api.region.DistrictDto;
import de.symeda.sormas.api.region.DistrictReferenceDto;
import de.symeda.sormas.api.region.RegionDto;
import de.symeda.sormas.api.region.RegionReferenceDto;
import de.symeda.sormas.api.user.UserReferenceDto;
import de.symeda.sormas.api.utils.DataHelper;
import de.symeda.sormas.api.utils.ValidationRuntimeException;

public class PopulationDataImporter extends DataImporter {

	private static final String HEADER_PATTERN = "[A-Z]+_[A-Z]{3}_\\d+_(\\d+|PLUS)";
	private static final String TOTAL_HEADER_PATTERN = "[A-Z]+_TOTAL";
	private final Date collectionDate;

	public PopulationDataImporter(File inputFile, UserReferenceDto currentUser, UI currentUI, Date collectionDate) throws IOException {
		this(inputFile, null, currentUser, currentUI, collectionDate);
	}

	public PopulationDataImporter(File inputFile, OutputStreamWriter errorReportWriter, UserReferenceDto currentUser, UI currentUI, Date collectionDate) throws IOException {
		super(inputFile, false, errorReportWriter, currentUser, currentUI);
		this.collectionDate = collectionDate;
	}

	@Override
	protected void importDataFromCsvLine(String[] values, String[] entityClasses, String[] entityProperties, String[][] entityPropertyPaths) throws IOException, InvalidColumnException, InterruptedException {
		// Check whether the new line has the same length as the header line
		if (values.length > entityProperties.length) {
			hasImportError = true;
			writeImportError(values, I18nProperties.getValidationError(Validations.importLineTooLong));
			importedCallback.accept(ImportResult.ERROR);
			return;
		}

		// Reference population data that contains the region and district for this line
		RegionReferenceDto region = null;
		DistrictReferenceDto district = null;

		for (int i=0; i<entityProperties.length; i++) {
			if (PopulationDataDto.REGION.equalsIgnoreCase(entityProperties[i])) {
				List<RegionReferenceDto> regions = FacadeProvider.getRegionFacade().getByName(values[i]);
				if (regions.size() != 1) {
					hasImportError = true;
					writeImportError(values, new ImportErrorException(values[i], entityProperties[i]).getMessage());
					importedCallback.accept(ImportResult.ERROR);
					return;
				}
				region = regions.get(0);
			} 
			if (PopulationDataDto.DISTRICT.equalsIgnoreCase(entityProperties[i])) {
				if (DataHelper.isNullOrEmpty(values[i])) {
					district = null;
				} else {
					List<DistrictReferenceDto> districts = FacadeProvider.getDistrictFacade().getByName(values[i], region);
					if (districts.size() != 1) {
						hasImportError = true;
						writeImportError(values, new ImportErrorException(values[i], entityProperties[i]).getMessage());
						importedCallback.accept(ImportResult.ERROR);
						return;
					}
					district = districts.get(0);
				}
			} 
		}

		final RegionReferenceDto finalRegion = region;
		final DistrictReferenceDto finalDistrict = district;

		PopulationDataCriteria criteria = new PopulationDataCriteria().region(finalRegion);
		if (district == null) {
			criteria.districtIsNull(true);
		} else {
			criteria.district(finalDistrict);
		}
		List<PopulationDataDto> existingPopulationDatas = FacadeProvider.getPopulationDataFacade().getPopulationData(criteria);
		List<PopulationDataDto> modifiedPopulationDatas = new ArrayList<PopulationDataDto>();
		
		boolean populationDataHasImportError = insertRowIntoData(values, entityClasses, entityPropertyPaths, false, new Function<ImportCellData, Exception>() {
			@Override
			public Exception apply(ImportCellData cellData) {
				try {
					if (PopulationDataDto.REGION.equalsIgnoreCase(cellData.getEntityPropertyPath()[0])
							|| PopulationDataDto.DISTRICT.equalsIgnoreCase(cellData.getEntityPropertyPath()[0])) {
						// do nothing
					} else if (RegionDto.GROWTH_RATE.equalsIgnoreCase(cellData.getEntityPropertyPath()[0])) {
						if (!DataHelper.isNullOrEmpty(cellData.value)) {
							Float growthRate = Float.parseFloat(cellData.value);
							if (finalDistrict != null) {
								DistrictDto districtDto = FacadeProvider.getDistrictFacade().getDistrictByUuid(finalDistrict.getUuid());
								districtDto.setGrowthRate(growthRate);
								FacadeProvider.getDistrictFacade().saveDistrict(districtDto);
							} else {
								RegionDto regionDto = FacadeProvider.getRegionFacade().getRegionByUuid(finalRegion.getUuid());
								regionDto.setGrowthRate(growthRate);
								FacadeProvider.getRegionFacade().saveRegion(regionDto);
							}
						}
					} else {
						PopulationDataDto newPopulationData = PopulationDataDto.build(collectionDate);
						insertCellValueIntoData(newPopulationData, cellData.getValue(), cellData.getEntityPropertyPath());

						Optional<PopulationDataDto> existingPopulationData = existingPopulationDatas.stream().filter(
								populationData -> populationData.getAgeGroup() == newPopulationData.getAgeGroup()
								&& populationData.getSex() == newPopulationData.getSex()).findFirst();
						
						// Check whether this population data set already exists in the database; if yes, override it
						if (existingPopulationData.isPresent()) {
							existingPopulationData.get().setPopulation(newPopulationData.getPopulation());
							existingPopulationData.get().setCollectionDate(collectionDate);
							modifiedPopulationDatas.add(existingPopulationData.get());
						} else {
							newPopulationData.setRegion(finalRegion);
							newPopulationData.setDistrict(finalDistrict);
							modifiedPopulationDatas.add(newPopulationData);
						}
					}
				} catch (ImportErrorException | InvalidColumnException | NumberFormatException e) {
					return e;
				}

				return null;
			}
		});

		if (!populationDataHasImportError) {
			try {
				FacadeProvider.getPopulationDataFacade().savePopulationData(modifiedPopulationDatas);
				importedCallback.accept(ImportResult.SUCCESS);
			} catch (ValidationRuntimeException e) {
				hasImportError = true;
				writeImportError(values, e.getMessage());
				importedCallback.accept(ImportResult.ERROR);
			}
		} else {
			hasImportError = true;
			importedCallback.accept(ImportResult.ERROR);
		}
	}

	private void insertCellValueIntoData(PopulationDataDto populationData, String value, String[] entityPropertyPaths) throws InvalidColumnException, ImportErrorException {

		String entityProperty = buildEntityProperty(entityPropertyPaths);
		
		if (entityPropertyPaths.length != 1) {
			throw new UnsupportedOperationException(I18nProperties.getValidationError(Validations.importPropertyTypeNotAllowed, buildEntityProperty(entityPropertyPaths)));
		}

		String entityPropertyPath = entityPropertyPaths[0];

		try {
			if (entityPropertyPath.equalsIgnoreCase("TOTAL")) {
				insertPopulationIntoPopulationData(populationData, value);						
			} 
			else if (entityPropertyPath.matches(TOTAL_HEADER_PATTERN)) {
				try {
					populationData.setSex(Sex.valueOf(entityPropertyPaths[0].substring(0, entityPropertyPaths[0].indexOf("_"))));
				} catch (IllegalArgumentException e) {
					throw new InvalidColumnException(entityProperty);
				}
				insertPopulationIntoPopulationData(populationData, value);
			} 
			else if (entityPropertyPath.matches(HEADER_PATTERN)) {
				// Sex
				String sexString = entityPropertyPath.substring(0, entityPropertyPaths[0].indexOf("_"));
				if (!sexString.equals("TOTAL")) {
					try {
						populationData.setSex(Sex.valueOf(sexString));
					} catch (IllegalArgumentException e) {
						throw new InvalidColumnException(entityProperty);
					}
				}

				// Age group
				String ageGroupString = entityPropertyPath.substring(entityPropertyPath.indexOf("_") + 1, entityPropertyPaths[0].length());
				try {
					populationData.setAgeGroup(AgeGroup.valueOf(ageGroupString));
				} catch (IllegalArgumentException e) {
					throw new InvalidColumnException(entityProperty);
				}

				insertPopulationIntoPopulationData(populationData, value);
			}
			else {
				throw new ImportErrorException (I18nProperties.getValidationError(Validations.importPropertyTypeNotAllowed, entityPropertyPath));
			}
		} catch (IllegalArgumentException e) {
			throw new ImportErrorException(value, entityProperty);
		} catch (ImportErrorException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected error when trying to import population data: " + e.getMessage());
			throw new ImportErrorException(I18nProperties.getValidationError(Validations.importUnexpectedError));
		}
	}
	
	private void insertPopulationIntoPopulationData(PopulationDataDto populationData, String entry) throws ImportErrorException {
		try {
			populationData.setPopulation(Integer.parseInt(entry));
		} catch (NumberFormatException e) {
			throw new ImportErrorException(e.getMessage());
		}
	}

}

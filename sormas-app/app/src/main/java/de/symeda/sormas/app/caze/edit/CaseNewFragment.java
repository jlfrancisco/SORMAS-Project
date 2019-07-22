/*
 * SORMAS® - Surveillance Outbreak Response Management & Analysis System
 * Copyright © 2016-2018 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.symeda.sormas.app.caze.edit;

import android.view.View;

import java.util.List;

import de.symeda.sormas.api.Disease;
import de.symeda.sormas.api.DiseaseHelper;
import de.symeda.sormas.api.caze.CaseOrigin;
import de.symeda.sormas.api.caze.DengueFeverType;
import de.symeda.sormas.api.caze.PlagueType;
import de.symeda.sormas.api.user.UserRole;
import de.symeda.sormas.app.BaseEditFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.caze.Case;
import de.symeda.sormas.app.backend.config.ConfigProvider;
import de.symeda.sormas.app.backend.user.User;
import de.symeda.sormas.app.component.Item;
import de.symeda.sormas.app.databinding.FragmentCaseNewLayoutBinding;
import de.symeda.sormas.app.util.Bundler;
import de.symeda.sormas.app.util.DataUtils;
import de.symeda.sormas.app.util.DiseaseConfigurationHelper;
import de.symeda.sormas.app.util.InfrastructureHelper;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class CaseNewFragment extends BaseEditFragment<FragmentCaseNewLayoutBinding, Case, Case> {

    public static final String TAG = CaseNewFragment.class.getSimpleName();

    private Case record;

    private List<Item> diseaseList;
    private List<Item> plagueTypeList;
    private List<Item> dengueFeverTypeList;
    private List<Item> initialRegions;
    private List<Item> initialDistricts;
    private List<Item> initialCommunities;
    private List<Item> initialFacilities;
    private List<Item> initialPointsOfEntry;

    public static CaseNewFragment newInstance(Case activityRootData) {
        return newInstance(CaseNewFragment.class, CaseNewActivity.buildBundle().get(), activityRootData);
    }

    public static CaseNewFragment newInstanceFromContact(Case activityRootData, String contactUuid) {
        return newInstance(CaseNewFragment.class, CaseNewActivity.buildBundleWithContact(contactUuid).get(), activityRootData);
    }

    public static CaseNewFragment newInstanceFromEventParticipant(Case activityRootData, String eventParticipantUuid) {
        return newInstance(CaseNewFragment.class, CaseNewActivity.buildBundleWithEventParticipant(eventParticipantUuid).get(), activityRootData);
    }

    @Override
    protected String getSubHeadingTitle() {
        return getResources().getString(R.string.caption_new_case);
    }

    @Override
    public Case getPrimaryData() {
        return record;
    }

    @Override
    protected void prepareFragmentData() {
        record = getActivityRootData();

        List<Disease> diseases = DiseaseConfigurationHelper.getInstance().getAllActivePrimaryDiseases();
        diseaseList = DataUtils.toItems(diseases);
        if (record.getDisease() != null && !diseases.contains(record.getDisease())) {
            diseaseList.add(DataUtils.toItem(record.getDisease()));
        }
        plagueTypeList = DataUtils.getEnumItems(PlagueType.class, true);
        dengueFeverTypeList = DataUtils.getEnumItems(DengueFeverType.class, true);

        initialRegions = InfrastructureHelper.loadRegions();
        initialDistricts = InfrastructureHelper.loadDistricts(record.getRegion());
        initialCommunities = InfrastructureHelper.loadCommunities(record.getDistrict());
        initialFacilities = InfrastructureHelper.loadFacilities(record.getDistrict(), record.getCommunity());
        initialPointsOfEntry = InfrastructureHelper.loadPointsOfEntry(record.getDistrict());
    }

    @Override
    public void onLayoutBinding(FragmentCaseNewLayoutBinding contentBinding) {
        contentBinding.setData(record);
        contentBinding.setCaseOriginClass(CaseOrigin.class);

        InfrastructureHelper.initializeFacilityFields(contentBinding.caseDataRegion, initialRegions,
                contentBinding.caseDataDistrict, initialDistricts,
                contentBinding.caseDataCommunity, initialCommunities,
                contentBinding.caseDataHealthFacility, initialFacilities,
                contentBinding.caseDataPointOfEntry, initialPointsOfEntry);

        contentBinding.caseDataDisease.initializeSpinner(diseaseList);
        contentBinding.caseDataPlagueType.initializeSpinner(plagueTypeList);
        contentBinding.caseDataDengueFeverType.initializeSpinner(dengueFeverTypeList);
        contentBinding.caseDataReportDate.initializeDateField(getFragmentManager());
    }

    @Override
    public void onAfterLayoutBinding(final FragmentCaseNewLayoutBinding contentBinding) {
        InfrastructureHelper.initializeHealthFacilityDetailsFieldVisibility(contentBinding.caseDataHealthFacility, contentBinding.caseDataHealthFacilityDetails);
        InfrastructureHelper.initializePointOfEntryDetailsFieldVisibility(contentBinding.caseDataPointOfEntry, contentBinding.caseDataPointOfEntryDetails);

        contentBinding.caseDataRegion.setEnabled(false);
        contentBinding.caseDataRegion.setRequired(false);
        contentBinding.caseDataDistrict.setEnabled(false);
        contentBinding.caseDataDistrict.setRequired(false);

        User user = ConfigProvider.getUser();
        if (user.hasUserRole(UserRole.HOSPITAL_INFORMANT) && user.getHealthFacility() != null) {
            // Hospital Informants are not allowed to create cases in another health facility
            contentBinding.caseDataCommunity.setEnabled(false);
            contentBinding.caseDataCommunity.setRequired(false);
            contentBinding.caseDataHealthFacility.setEnabled(false);
            contentBinding.caseDataHealthFacility.setRequired(false);
        }

        if (user.hasUserRole(UserRole.POE_INFORMANT) && user.getPointOfEntry() != null) {
            contentBinding.caseDataPointOfEntry.setEnabled(false);
            contentBinding.caseDataPointOfEntry.setRequired(false);
        }

        if (user.hasUserRole(UserRole.COMMUNITY_INFORMANT) && user.getCommunity() != null) {
            // Community Informants are not allowed to create cases in another community
            contentBinding.caseDataCommunity.setEnabled(false);
            contentBinding.caseDataCommunity.setRequired(false);
        }

        // Disable first and last name and disease fields when case is created from contact
        // or event person
        Bundler bundler = new Bundler(getArguments());
        if (bundler.getContactUuid() != null || bundler.getEventParticipantUuid() != null) {
            contentBinding.caseDataFirstName.setEnabled(false);
            contentBinding.caseDataLastName.setEnabled(false);
            contentBinding.caseDataDisease.setEnabled(false);
            contentBinding.caseDataDiseaseDetails.setEnabled(false);
            contentBinding.caseDataPlagueType.setEnabled(false);
            contentBinding.caseDataDengueFeverType.setEnabled(false);
        }

        // Set up port health visibilities
        if (UserRole.isPortHealthUser(ConfigProvider.getUser().getUserRoles())) {
            contentBinding.caseDataCaseOrigin.setVisibility(GONE);
            contentBinding.caseDataDisease.setVisibility(GONE);
            contentBinding.healthFacilityFieldsLayout.setVisibility(GONE);
            contentBinding.caseDataHealthFacility.setRequired(false);
            contentBinding.caseDataHealthFacilityDetails.setRequired(false);
        } else {
            if (record.getCaseOrigin() == CaseOrigin.IN_COUNTRY) {
                contentBinding.caseDataPointOfEntry.setRequired(false);
                contentBinding.caseDataPointOfEntry.setVisibility(GONE);
            } else {
                contentBinding.caseDataHealthFacility.setRequired(false);
                contentBinding.healthFacilityFieldsLayout.setVisibility(GONE);
            }
            contentBinding.caseDataCaseOrigin.addValueChangedListener(e -> {
                if (e.getValue() == CaseOrigin.IN_COUNTRY) {
                    contentBinding.caseDataPointOfEntry.setVisibility(GONE);
                    contentBinding.healthFacilityFieldsLayout.setVisibility(VISIBLE);
                    InfrastructureHelper.setHealthFacilityDetailsFieldVisibility(contentBinding.caseDataHealthFacility, contentBinding.caseDataHealthFacilityDetails);
                    contentBinding.caseDataPointOfEntry.setRequired(false);
                    contentBinding.caseDataPointOfEntry.setValue(null);
                    contentBinding.caseDataHealthFacility.setRequired(true);
                } else {
                    contentBinding.healthFacilityFieldsLayout.setVisibility(GONE);
                    contentBinding.caseDataPointOfEntry.setVisibility(VISIBLE);
                    InfrastructureHelper.setPointOfEntryDetailsFieldVisibility(contentBinding.caseDataPointOfEntry, contentBinding.caseDataPointOfEntryDetails);
                    contentBinding.caseDataHealthFacility.setRequired(false);
                    contentBinding.caseDataHealthFacility.setValue(null);
                    contentBinding.caseDataPointOfEntry.setRequired(true);
                }
            });
        }
    }

    @Override
    public int getEditLayout() {
        return R.layout.fragment_case_new_layout;
    }
}

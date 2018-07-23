package de.symeda.sormas.app.shared;

import android.content.Context;

import de.symeda.sormas.api.caze.CaseClassification;
import de.symeda.sormas.api.caze.InvestigationStatus;
import de.symeda.sormas.app.backend.caze.Case;
import de.symeda.sormas.app.core.BaseFormNavigationCapsule;
import de.symeda.sormas.app.core.SearchBy;
import de.symeda.sormas.app.core.enumeration.IStatusElaborator;

public class CaseFormNavigationCapsule extends BaseFormNavigationCapsule<Case, CaseFormNavigationCapsule>  {

    public CaseFormNavigationCapsule(Context context) {
        super(context, null, null);
    }

    public CaseFormNavigationCapsule(Context context, String recordUuid, CaseClassification pageStatus) {
        super(context, recordUuid, pageStatus);
    }
}
package de.symeda.sormas.app.symptom;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import de.symeda.sormas.api.Disease;
import de.symeda.sormas.api.symptoms.SymptomState;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.symptoms.Symptoms;
import de.symeda.sormas.app.core.ReadOnly;

/**
 * Created by Orson on 03/01/2018.
 */

//TODO: Load from XML configuration
@ReadOnly
public abstract class Symptom<TChildViewModel extends ISymptomViewModel> {

    private final static String TAG = Symptom.class.getSimpleName();

    private final int value;
    private final String name;
    private boolean hasDetail;
    private Integer detailTemplateResId;

    private SymptomState state;
    private int mLastCheckedId = -1;


    /*private SymptomState state;
    private boolean hasDetail;
    private String detail;
    private int mLastCheckedId = -1;*/

    //private TViewModel mViewModel;

    // TODO: Every child should maintain thier own state
    // TODO: Getters and setters should be abstract
    protected TChildViewModel mChildViewModel;


    // <editor-fold defaultstate="collapsed" desc="Constants">

    public static final Symptom<NoViewModel> FEVER = new Fever<>();
    public static final Symptom<NoViewModel> VOMITING = new Vomiting<>();
    public static final Symptom<NoViewModel> DIARRHEA = new Diarrhea<>();
    public static final Symptom<NoViewModel> BLOOD_IN_STOOL = new BloodInStool<>();
    public static final Symptom<NoViewModel> NAUSEA = new Nausea<>();
    public static final Symptom<NoViewModel> ABDOMINAL_PAIN = new AbdominalPain<>();
    public static final Symptom<NoViewModel> HEAD_ACHE = new Headache<>();
    public static final Symptom<NoViewModel> MUSCLE_PAIN = new MusclePain<>();
    public static final Symptom<NoViewModel> FATIGUE_GENERAL_WEAKNESS = new FatigueGeneralWeakness<>();
    public static final Symptom<NoViewModel> UNEXPLAINED_BLEEDING_BRUISING = new UnexplainedBleedingBruising<>();
    public static final Symptom<NoViewModel> BLEEDING_GUM = new BleedingGum<>();
    public static final Symptom<NoViewModel> BLEEDING_FROM_INJECTION_SITE = new BleedingFromInjectionSite<>();
    public static final Symptom<NoViewModel> NOSE_BLEED = new NoseBleed<>();
    public static final Symptom<NoViewModel> BLOODY_BLACK_STOOL = new BloodyBlackStool<>();
    public static final Symptom<NoViewModel> BLOOD_IN_VOMIT = new BloodInVomit<>();
    public static final Symptom<NoViewModel> DIGESTED_BLOOD_IN_VOMIT = new DigestedBloodInVomit<>();
    public static final Symptom<NoViewModel> COUGHING_BLOOD = new CoughingBlood<>();
    public static final Symptom<NoViewModel> BLEEDING_FROM_VAGINA = new BleedingFromVagina<>();
    public static final Symptom<NoViewModel> BRUISED_SKIN = new BruisedSkin<>();
    public static final Symptom<NoViewModel> BLOOD_IN_URINE = new BloodInUrine<>();
    public static final Symptom<DetailsViewModel> OTHER_HEMORRHAGIC = new OtherHemorrhagic<>(DetailsViewModel.class);
    public static final Symptom<NoViewModel> SKIN_RASH = new SkinRash<>();
    public static final Symptom<NoViewModel> STIFF_NECK = new StiffNeck<>();
    public static final Symptom<NoViewModel> SORE_THROAT = new SoreThroat<>();
    public static final Symptom<NoViewModel> COUGH = new Cough<>();
    public static final Symptom<NoViewModel> RUNNY_NOSE = new RunnyNose<>();
    public static final Symptom<NoViewModel> DIFFICULTY_BREATHING = new DifficultyBreathing<>();
    public static final Symptom<NoViewModel> CHEST_PAIN = new ChestPain<>();
    public static final Symptom<NoViewModel> CONFUSED_OR_DISORIENTED = new ConfusedOrDisoriented<>();
    public static final Symptom<NoViewModel> CONVULSION_OR_SEIZURES = new ConvulsionsOrSeizures<>();
    public static final Symptom<NoViewModel> ALTERED_CONSCIOUSNESS = new AlteredConsciousness<>();
    public static final Symptom<NoViewModel> CONJUNCTIVITIS = new Conjunctivitis<>();
    public static final Symptom<NoViewModel> PAIN_BEHIND_EYES = new PainBehindEyes<>();
    public static final Symptom<NoViewModel> KOPLIK_SPOTS = new KoplikSpots<>();
    public static final Symptom<NoViewModel> THROMBOCYTOPENIA = new Thrombocytopenia<>();
    public static final Symptom<NoViewModel> MIDDLE_EAR_INFLAMMATION = new MiddleEarInflammation<>();
    public static final Symptom<NoViewModel> ACUTE_HEARING_LOSS = new AcuteHearingLoss<>();
    public static final Symptom<NoViewModel> DEHYDRATION = new Dehydration();
    public static final Symptom<NoViewModel> LOSS_OF_APPETITE = new LossOfAppetite<>();
    public static final Symptom<NoViewModel> REFUSAL_TO_FEED = new RefusalToFeed<>();
    public static final Symptom<NoViewModel> JOINT_PAIN = new JointPain<>();
    public static final Symptom<NoViewModel> SHOCK = new Shock<>();
    public static final Symptom<NoViewModel> HICCUPS = new Hiccups<>();
    public static final Symptom<DetailsViewModel> OTHER_NON_HEMORRHAGIC = new OtherNonHemorrhagic<>(DetailsViewModel.class);
    public static final Symptom<NoViewModel> BACKACHE = new Backache<>();
    public static final Symptom<NoViewModel> BLEEDING_FROM_EYES = new BleedingFromEyes<>();
    public static final Symptom<NoViewModel> JAUNDICE = new Jaundice<>();
    public static final Symptom<NoViewModel> DARK_URINE = new DarkUrine<>();
    public static final Symptom<NoViewModel> BLEEDING_FROM_STOMACH = new BleedingFromStomach<>();
    public static final Symptom<NoViewModel> RAPID_BREATHING = new RapidBreathing<>();
    public static final Symptom<NoViewModel> SWOLLEN_GLANDS = new SwollenGlands<>();
    public static final Symptom<NoViewModel> CUTANEOUS_ERUPTION = new CutaneousEruption<>();
    public static final Symptom<NoViewModel> CHILLS_OR_SWEAT = new ChillsOrSweat<>();
    public static final Symptom<NoViewModel> BEDRIDDEN = new Bedridden<>();
    public static final Symptom<NoViewModel> ORAL_ULCERS = new OralUlcers<>();
    public static final Symptom<NoViewModel> PAINFUL_LYMPHADENITIS = new PainfulLymphadenitis<>();
    public static final Symptom<NoViewModel> BLACKENING_DEATH_OF_TISSUE = new BlackeningDeathOfTissue<>();
    public static final Symptom<NoViewModel> BUBOES_GROIN_ARMPIT_NECK = new BuboesGroinArmpitNeck<>();
    public static final Symptom<NoViewModel> BULGING_FONTANELLE = new BulgingFontanelle<>();


    //NEW
    public static final Symptom<NoViewModel> DIFFICULTY_SWALLOWING = new DifficultySwallowing<>();

    public static final Symptom<NoViewModel> LESIONS_THAT_ITCH = new LesionsThatItch<>();
    public static final Symptom<NoViewModel> LESIONS_SAME_STATE = new LesionsSameState<>();
    public static final Symptom<NoViewModel> LESIONS_SAME_SIZE = new LesionsSameSize<>();
    public static final Symptom<NoViewModel> LESIONS_SAME_PROFOUND = new LesionsDeepProfound<>();
    public static final Symptom<NoViewModel> LESIONS_LIKE_PIC1 = new LesionsLikePic1<>();
    public static final Symptom<NoViewModel> LESIONS_LIKE_PIC2 = new LesionsLikePic2<>();
    public static final Symptom<NoViewModel> LESIONS_LIKE_PIC3 = new LesionsLikePic3<>();
    public static final Symptom<NoViewModel> LESIONS_LIKE_PIC4 = new LesionsLikePic4<>();
    public static final Symptom<LesionChildViewModel> LESIONS = new Lesions<>(LesionChildViewModel.class);



    public static final Symptom<NoViewModel> LYMPHADENOPATHY_INGUINAL = new LymphadenopathyInguinal<>();
    public static final Symptom<NoViewModel> LYMPHADENOPATHY_AXILLARY = new LymphadenopathyAxillary<>();
    public static final Symptom<NoViewModel> LYMPHADENOPATHY_CERVICAL = new LymphadenopathyCervical<>();


    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Contructor">

    /*protected Symptom(int value, String name, SymptomState state) {
        this(value, name, state, false, "");
    }*/
    private Symptom(Symptom s) {
        this.value = s.getValue();
        this.name = s.getName();
        this.state = s.getState();
        this.hasDetail = s.hasDetail();
        this.detailTemplateResId = s.getDetailTemplateResId();
        this.mChildViewModel = (TChildViewModel)s.getChildViewModel();
        /*
        this.hasDetail = s.hasDetail();
        this.state = s.getState();
        this.detail = s.getDetail();
        this.detailTemplateResId = s.getDetailTemplateResId()*/;
    }

    protected Symptom(int value, String name) { //TViewModel viewModel,
        this(value, name, null, null);
    }

    protected Symptom(int value, String name, TChildViewModel childViewModel) { //TViewModel viewModel,
        this(value, name, null, childViewModel);
    }

    protected Symptom(int value, String name, int detailTemplateResId) { //TViewModel viewModel,
        this(value, name, detailTemplateResId, null);
    }

    protected Symptom(int value, String name, Integer detailTemplateResId, TChildViewModel childViewModel) { //, TViewModel viewModel
        this.value = value;
        this.name = name;
        this.state = SymptomState.UNKNOWN;
        this.hasDetail = childViewModel != null || detailTemplateResId != null;
        this.detailTemplateResId = hasDetail ? detailTemplateResId : null;
        //this.mViewModel = viewModel;
        this.mChildViewModel = childViewModel;
        /*this.state = SymptomState.UNKNOWN;
        this.detailTemplateResId = hasDetail ? detailTemplateResId : -1;
        this.detail = "";*/
    }

    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return value;
    }

    public boolean hasDetail() {
        return hasDetail;
    }

    public void setHasDetail(boolean hasDetail) {
        this.hasDetail = hasDetail;
    }

    public Integer getDetailTemplateResId() {
        return detailTemplateResId;
    }

    public void setDetailTemplateResId(Integer detailTemplateResId) {
        this.detailTemplateResId = detailTemplateResId;
    }

    public SymptomState getState() {
        return this.state;
    }

    public void setState(SymptomState state) {
        if (state == null)
            state = SymptomState.UNKNOWN;

        this.state = state;
    }

    public int getLastCheckedId() {
        return mLastCheckedId;
    }

    public void setLastCheckedId(int mLastCheckedId) {
        this.mLastCheckedId = mLastCheckedId;
    }

    /*public TViewModel getViewModel() {
        return mViewModel;
    }*/

    public TChildViewModel getChildViewModel() {
        return mChildViewModel;
    }

    /*public void setViewModel(TViewModel viewModel) {
        this.mViewModel = viewModel;
    }*/

    public void setChildViewModel(TChildViewModel childViewModel) {
        this.mChildViewModel = childViewModel;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Enumurations">

    private static class Fever<T extends ISymptomViewModel> extends Symptom<T> {
        public Fever() {
            this(null);
        }

        public Fever(Class<T> cls) {
            super(0, "Fever");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.MEASLES);
                add(Disease.YELLOW_FEVER);
                add(Disease.DENGUE);
                add(Disease.MONKEYPOX);
            }};
        }
    }

    private static class Vomiting<T extends ISymptomViewModel> extends Symptom<T> {
        public Vomiting() {
            this(null);
        }

        public Vomiting(Class<T> cls) {
            super(1, "Vomiting");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.MEASLES);
                add(Disease.YELLOW_FEVER);
                add(Disease.DENGUE);
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Diarrhea<T extends ISymptomViewModel> extends Symptom<T> {
        public Diarrhea() {
            this(null);
        }

        public Diarrhea(Class<T> cls) {
            super(2, "Diarrhea");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.MEASLES);
                add(Disease.OTHER);
            }};
        }
    }

    private static class BloodInStool<T extends ISymptomViewModel> extends Symptom<T> {
        public BloodInStool() {
            this(null);
        }

        public BloodInStool(Class<T> cls) {
            super(3, "Blood in Stool");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.CHOLERA);
                add(Disease.YELLOW_FEVER);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Nausea<T extends ISymptomViewModel> extends Symptom<T> {
        public Nausea() {
            this(null);
        }

        public Nausea(Class<T> cls) {
            super(4, "Nausea");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.YELLOW_FEVER);
                add(Disease.DENGUE);
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class AbdominalPain<T extends ISymptomViewModel> extends Symptom<T> {
        public AbdominalPain() {
            this(null);
        }

        public AbdominalPain(Class<T> cls) {
            super(5, "Abdominal pain");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CHOLERA);
                add(Disease.YELLOW_FEVER);
                add(Disease.DENGUE);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Headache<T extends ISymptomViewModel> extends Symptom<T> {
        public Headache() {
            this(null);
        }

        public Headache(Class<T> cls) {
            super(6, "Headache");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.YELLOW_FEVER);
                add(Disease.DENGUE);
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class MusclePain<T extends ISymptomViewModel> extends Symptom<T> {
        public MusclePain() {
            this(null);
        }

        public MusclePain(Class<T> cls) {
            super(7, "Muscle pain");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.MEASLES);
                add(Disease.YELLOW_FEVER);
                add(Disease.DENGUE);
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class FatigueGeneralWeakness<T extends ISymptomViewModel> extends Symptom<T> {
        public FatigueGeneralWeakness() {
            this(null);
        }

        public FatigueGeneralWeakness(Class<T> cls) {
            super(8, "Fatigue/general weakness");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.MEASLES);
                add(Disease.YELLOW_FEVER);
                add(Disease.DENGUE);
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class UnexplainedBleedingBruising<T extends ISymptomViewModel> extends Symptom<T> {
        public UnexplainedBleedingBruising() {
            this(null);
        }

        public UnexplainedBleedingBruising(Class<T> cls) {
            super(9, "Unexplained bleeding or bruising");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class BleedingGum<T extends ISymptomViewModel> extends Symptom<T> {
        public BleedingGum() {
            this(null);
        }

        public BleedingGum(Class<T> cls) {
            super(10, "Bleeding of the gums");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.YELLOW_FEVER);
                add(Disease.DENGUE);
                add(Disease.OTHER);
            }};
        }
    }
    //Other
    private static class BleedingFromInjectionSite<T extends ISymptomViewModel> extends Symptom<T> {
        public BleedingFromInjectionSite() {
            this(null);
        }

        public BleedingFromInjectionSite(Class<T> cls) {
            super(11, "Bleeding from injection site");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class NoseBleed<T extends ISymptomViewModel> extends Symptom<T> {
        public NoseBleed() {
            this(null);
        }

        public NoseBleed(Class<T> cls) {
            super(12, "Nose bleed (epistaxis)");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.YELLOW_FEVER);
                add(Disease.OTHER);
            }};
        }
    }

    private static class BloodyBlackStool<T extends ISymptomViewModel> extends Symptom<T> {
        public BloodyBlackStool() {
            this(null);
        }

        public BloodyBlackStool(Class<T> cls) {
            super(13, "Bloody or black stools (melena)");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class BloodInVomit<T extends ISymptomViewModel> extends Symptom<T> {
        public BloodInVomit() {
            this(null);
        }

        public BloodInVomit(Class<T> cls) {
            super(14, "Fresh/red blood in vomit (hematemesis)");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.DENGUE);
                add(Disease.OTHER);
            }};
        }
    }

    private static class DigestedBloodInVomit<T extends ISymptomViewModel> extends Symptom<T> {
        public DigestedBloodInVomit() {
            this(null);
        }

        public DigestedBloodInVomit(Class<T> cls) {
            super(15, "Digested blood\"coffee grounds\" in vomit");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class CoughingBlood<T extends ISymptomViewModel> extends Symptom<T> {
        public CoughingBlood() {
            this(null);
        }

        public CoughingBlood(Class<T> cls) {
            super(16, "Coughing up blood (haemoptysis)");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class BleedingFromVagina<T extends ISymptomViewModel> extends Symptom<T> {
        public BleedingFromVagina() {
            this(null);
        }

        public BleedingFromVagina(Class<T> cls) {
            super(17, "Bleeding from vagina, other than menstruation");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class BruisedSkin<T extends ISymptomViewModel> extends Symptom<T> {
        public BruisedSkin() {
            this(null);
        }

        public BruisedSkin(Class<T> cls) {
            super(18, "Bruising of the skin (petechiae/ecchymosis)");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class BloodInUrine<T extends ISymptomViewModel> extends Symptom<T> {
        public BloodInUrine() {
            this(null);
        }

        public BloodInUrine(Class<T> cls) {
            super(19, "Blood in urine (hematuria)");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class OtherHemorrhagic<T extends ISymptomViewModel> extends Symptom<T> {
        public OtherHemorrhagic() {
            this(null);
        }

        public OtherHemorrhagic(Class<T> cls) {
            super(20, "Other hemorrhagic symptoms");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class SkinRash<T extends ISymptomViewModel> extends Symptom<T> {
        public SkinRash() {
            this(null);
        }

        public SkinRash(Class<T> cls) {
            super(21, "Skin rash");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.MEASLES);
                add(Disease.DENGUE);
                add(Disease.OTHER);
            }};
        }
    }

    private static class StiffNeck<T extends ISymptomViewModel> extends Symptom<T> {
        public StiffNeck() {
            this(null);
        }

        public StiffNeck(Class<T> cls) {
            super(22, "Stiff neck");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.CSM);
                add(Disease.OTHER);
            }};
        }
    }

    private static class SoreThroat<T extends ISymptomViewModel> extends Symptom<T> {
        public SoreThroat() {
            this(null);
        }

        public SoreThroat(Class<T> cls) {
            super(23, "Sore throat/pharyngitis");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.MEASLES);
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Cough<T extends ISymptomViewModel> extends Symptom<T> {
        public Cough() {
            this(null);
        }

        public Cough(Class<T> cls) {
            super(24, "Cough");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.MEASLES);
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class RunnyNose<T extends ISymptomViewModel> extends Symptom<T> {
        public RunnyNose() {
            this(null);
        }

        public RunnyNose(Class<T> cls) {
            super(25, "Runny nose");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.MEASLES);
                add(Disease.OTHER);
            }};
        }
    }

    private static class DifficultyBreathing<T extends ISymptomViewModel> extends Symptom<T> {
        public DifficultyBreathing() {
            this(null);
        }

        public DifficultyBreathing(Class<T> cls) {
            super(26, "Difficulty breathing");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.MEASLES);
                add(Disease.OTHER);
            }};
        }
    }

    private static class ChestPain<T extends ISymptomViewModel> extends Symptom<T> {
        public ChestPain() {
            this(null);
        }

        public ChestPain(Class<T> cls) {
            super(27, "Chest pain");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class ConfusedOrDisoriented<T extends ISymptomViewModel> extends Symptom<T> {
        public ConfusedOrDisoriented() {
            this(null);
        }

        public ConfusedOrDisoriented(Class<T> cls) {
            super(28, "Confused or disoriented");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.MEASLES);
                add(Disease.OTHER);
            }};
        }
    }

    private static class ConvulsionsOrSeizures<T extends ISymptomViewModel> extends Symptom<T> {
        public ConvulsionsOrSeizures() {
            this(null);
        }

        public ConvulsionsOrSeizures(Class<T> cls) {
            super(29, "Convulsions or Seizures");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.MEASLES);
                add(Disease.OTHER);
            }};
        }
    }

    private static class AlteredConsciousness<T extends ISymptomViewModel> extends Symptom<T> {
        public AlteredConsciousness() {
            this(null);
        }

        public AlteredConsciousness(Class<T> cls) {
            super(30, "Altered level of consciousness");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.MEASLES);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Conjunctivitis<T extends ISymptomViewModel> extends Symptom<T> {
        public Conjunctivitis() {
            this(null);
        }

        public Conjunctivitis(Class<T> cls) {
            super(31, "Conjunctivitis (red eyes)");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.MEASLES);
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class PainBehindEyes<T extends ISymptomViewModel> extends Symptom<T> {
        public PainBehindEyes() {
            this(null);
        }

        public PainBehindEyes(Class<T> cls) {
            super(32, "Pain behind eyes/Sensitivity to light");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.MEASLES);
                add(Disease.DENGUE);
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class KoplikSpots<T extends ISymptomViewModel> extends Symptom<T> {
        public KoplikSpots() {
            this(null);
        }

        public KoplikSpots(Class<T> cls) {
            super(33, "Koplik's Spots");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MEASLES);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Thrombocytopenia<T extends ISymptomViewModel> extends Symptom<T> {
        public Thrombocytopenia() {
            this(null);
        }

        public Thrombocytopenia(Class<T> cls) {
            super(34, "Thrombocytopenia");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class MiddleEarInflammation<T extends ISymptomViewModel> extends Symptom<T> {
        public MiddleEarInflammation() {
            this(null);
        }

        public MiddleEarInflammation(Class<T> cls) {
            super(35, "Middle ear inflammation (otitis media)");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.MEASLES);
                add(Disease.OTHER);
            }};
        }
    }

    private static class AcuteHearingLoss<T extends ISymptomViewModel> extends Symptom<T> {
        public AcuteHearingLoss() {
            this(null);
        }

        public AcuteHearingLoss(Class<T> cls) {
            super(36, "Acute hearing loss");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Dehydration<T extends ISymptomViewModel> extends Symptom<T> {
        public Dehydration() {
            this(null);
        }

        public Dehydration(Class<T> cls) {
            super(37, "Dehydration");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.CHOLERA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class LossOfAppetite<T extends ISymptomViewModel> extends Symptom<T> {
        public LossOfAppetite() {
            this(null);
        }

        public LossOfAppetite(Class<T> cls) {
            super(38, "Anorexia/loss of appetite");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.YELLOW_FEVER);
                add(Disease.OTHER);
            }};
        }
    }

    private static class RefusalToFeed<T extends ISymptomViewModel> extends Symptom<T> {
        public RefusalToFeed() {
            this(null);
        }

        public RefusalToFeed(Class<T> cls) {
            super(39, "Refusal to feed or drink");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class JointPain<T extends ISymptomViewModel> extends Symptom<T> {
        public JointPain() {
            this(null);
        }

        public JointPain(Class<T> cls) {
            super(40, "Joint pain or arthritis");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.MEASLES);
                add(Disease.DENGUE);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Shock<T extends ISymptomViewModel> extends Symptom<T> {
        public Shock() {
            this(null);
        }

        public Shock(Class<T> cls) {
            super(41, "Shock (Systolic bp <90)");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.MEASLES);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Hiccups<T extends ISymptomViewModel> extends Symptom<T> {
        public Hiccups() {
            this(null);
        }

        public Hiccups(Class<T> cls) {
            super(42, "Hiccups");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.OTHER);
            }};
        }
    }

    private static class OtherNonHemorrhagic<T extends ISymptomViewModel> extends Symptom<T> {
        public OtherNonHemorrhagic() {
            this(null);
        }

        public OtherNonHemorrhagic(Class<T> cls) {
            super(43, "Other clinical symptom");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.CSM);
                add(Disease.CHOLERA);
                add(Disease.MEASLES);
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Backache<T extends ISymptomViewModel> extends Symptom<T> {
        public Backache() {
            this(null);
        }

        public Backache(Class<T> cls) {
            super(44, "Backache");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.YELLOW_FEVER);
                add(Disease.OTHER);
            }};
        }
    }

    private static class BleedingFromEyes<T extends ISymptomViewModel> extends Symptom<T> {
        public BleedingFromEyes() {
            this(null);
        }

        public BleedingFromEyes(Class<T> cls) {
            super(45, "Bleeding from the eyes");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.YELLOW_FEVER);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Jaundice<T extends ISymptomViewModel> extends Symptom<T> {
        public Jaundice() {
            this(null);
        }

        public Jaundice(Class<T> cls) {
            super(46, "Jaundice");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.YELLOW_FEVER);
                add(Disease.OTHER);
            }};
        }
    }

    private static class DarkUrine<T extends ISymptomViewModel> extends Symptom<T> {
        public DarkUrine() {
            this(null);
        }

        public DarkUrine(Class<T> cls) {
            super(47, "Dark Urine");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.YELLOW_FEVER);
                add(Disease.OTHER);
            }};
        }
    }

    private static class BleedingFromStomach<T extends ISymptomViewModel> extends Symptom<T> {
        public BleedingFromStomach() {
            this(null);
        }

        public BleedingFromStomach(Class<T> cls) {
            super(48, "Bleeding from the stomach");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.YELLOW_FEVER);
                add(Disease.OTHER);
            }};
        }
    }

    private static class RapidBreathing<T extends ISymptomViewModel> extends Symptom<T> {
        public RapidBreathing() {
            this(null);
        }

        public RapidBreathing(Class<T> cls) {
            super(49, "Rapid breathing");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.DENGUE);
                add(Disease.OTHER);
            }};
        }
    }

    private static class SwollenGlands<T extends ISymptomViewModel> extends Symptom<T> {
        public SwollenGlands() {
            this(null);
        }

        public SwollenGlands(Class<T> cls) {
            super(50, "Swollen glands");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.DENGUE);
                add(Disease.OTHER);
            }};
        }
    }

    private static class CutaneousEruption<T extends ISymptomViewModel> extends Symptom<T> {
        public CutaneousEruption() {
            this(null);
        }

        public CutaneousEruption(Class<T> cls) {
            super(51, "Cutaneous eruption");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class ChillsOrSweat<T extends ISymptomViewModel> extends Symptom<T> {
        public ChillsOrSweat() {
            this(null);
        }

        public ChillsOrSweat(Class<T> cls) {
            super(52, "Chills or sweats");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Bedridden<T extends ISymptomViewModel> extends Symptom<T> {
        public Bedridden() {
            this(null);
        }

        public Bedridden(Class<T> cls) {
            super(54, "Is the patient bedridden?");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class OralUlcers<T extends ISymptomViewModel> extends Symptom<T> {
        public OralUlcers() {
            this(null);
        }

        public OralUlcers(Class<T> cls) {
            super(55, "Oral ulcers");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class PainfulLymphadenitis<T extends ISymptomViewModel> extends Symptom<T> {
        public PainfulLymphadenitis() {
            this(null);
        }

        public PainfulLymphadenitis(Class<T> cls) {
            super(56, "Painful lymphadenitis");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class BlackeningDeathOfTissue<T extends ISymptomViewModel> extends Symptom<T> {
        public BlackeningDeathOfTissue() {
            this(null);
        }

        public BlackeningDeathOfTissue(Class<T> cls) {
            super(57, "Blackening and death of tissue in extremities");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{

            }};
        }
    }

    private static class BuboesGroinArmpitNeck<T extends ISymptomViewModel> extends Symptom<T> {
        public BuboesGroinArmpitNeck() {
            this(null);
        }

        public BuboesGroinArmpitNeck(Class<T> cls) {
            super(58, "Buboes in the groin, armpit or neck");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{

            }};
        }
    }

    private static class BulgingFontanelle<T extends ISymptomViewModel> extends Symptom<T> {
        public BulgingFontanelle() {
            this(null);
        }

        public BulgingFontanelle(Class<T> cls) {
            super(59, "Bulging fontanelle");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.CSM);
            }};
        }
    }

    //NEW
    private static class DifficultySwallowing<T extends ISymptomViewModel> extends Symptom<T> {
        public DifficultySwallowing() {
            this(null);
        }

        public DifficultySwallowing(Class<T> cls) {
            super(60, "Difficulty Swallowing");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.EVD);
                add(Disease.LASSA);
                add(Disease.AVIAN_INFLUENCA);
                add(Disease.OTHER);
            }};
        }
    }

    private static class Lesions<T extends LesionChildViewModel & ISymptomViewModel> extends Symptom<T> {
        public Lesions() {
            this(null);
        }

        public Lesions(Class<T> cls) {
            super(61, "Does the patient have lesions?",  R.layout.row_symptom_details_lesions_child_layout);

            if (cls == null)
                return;

            try {
                Constructor<T> ctor = cls.getConstructor(Symptom.class);
                mChildViewModel = ctor.newInstance(this);

                /*mChildViewModel.setLesionsThatItches(Symptom.LESIONS_THAT_ITCH);
                mChildViewModel.setLesionsInSameState(Symptom.LESIONS_SAME_STATE);
                mChildViewModel.setLesionsSameSize(Symptom.LESIONS_SAME_SIZE);
                mChildViewModel.setLesionsDeepAndProfound(Symptom.LESIONS_SAME_PROFOUND);
                mChildViewModel.setLesionsResemblePic1(Symptom.LESIONS_LIKE_PIC1);
                mChildViewModel.setLesionsResemblePic2(Symptom.LESIONS_LIKE_PIC2);
                mChildViewModel.setLesionsResemblePic3(Symptom.LESIONS_LIKE_PIC3);
                mChildViewModel.setLesionsResemblePic4(Symptom.LESIONS_LIKE_PIC4);*/

            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (NoSuchMethodException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }

        @Override
        public T getChildViewModel() {
            return mChildViewModel;
        }
    }

    private static class LymphadenopathyInguinal<T extends ISymptomViewModel> extends Symptom<T> {
        public LymphadenopathyInguinal() {
            this(null);
        }

        public LymphadenopathyInguinal(Class<T> cls) {
            super(62, "Lymphadenopathy, inguinal");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class LymphadenopathyAxillary<T extends ISymptomViewModel> extends Symptom<T> {
        public LymphadenopathyAxillary() {
            this(null);
        }

        public LymphadenopathyAxillary(Class<T> cls) {
            super(63, "Lymphadenopathy, axillary");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class LymphadenopathyCervical<T extends ISymptomViewModel> extends Symptom<T> {
        public LymphadenopathyCervical() {
            this(null);
        }

        public LymphadenopathyCervical(Class<T> cls) {
            super(64, "Lymphadenopathy, cervical");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }



    private static class LesionsThatItch<T extends ISymptomViewModel> extends Symptom<T> {
        public LesionsThatItch() {
            this(null);
        }

        public LesionsThatItch(Class<T> cls) {
            super(65, "Lesions that itch");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class LesionsSameState<T extends ISymptomViewModel> extends Symptom<T> {
        public LesionsSameState() {
            this(null);
        }

        public LesionsSameState(Class<T> cls) {
            super(66, "Are the lesions in the same state of development on the body?");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class LesionsSameSize<T extends ISymptomViewModel> extends Symptom<T> {
        public LesionsSameSize() {
            this(null);
        }

        public LesionsSameSize(Class<T> cls) {
            super(67, "Are all of the lesions the same size and state of development?");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class LesionsDeepProfound<T extends ISymptomViewModel> extends Symptom<T> {
        public LesionsDeepProfound() {
            this(null);
        }

        public LesionsDeepProfound(Class<T> cls) {
            super(68, "Are the lesions deep and profound?");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class LesionsLikePic1<T extends ISymptomViewModel> extends Symptom<T> {
        public LesionsLikePic1() {
            this(null);
        }

        public LesionsLikePic1(Class<T> cls) {
            super(69, "Does the rash resemble the picture below?");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class LesionsLikePic2<T extends ISymptomViewModel> extends Symptom<T> {
        public LesionsLikePic2() {
            this(null);
        }

        public LesionsLikePic2(Class<T> cls) {
            super(70, "Does the rash resemble the picture below?");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class LesionsLikePic3<T extends ISymptomViewModel> extends Symptom<T> {
        public LesionsLikePic3() {
            this(null);
        }

        public LesionsLikePic3(Class<T> cls) {
            super(71, "Does the rash resemble the picture below?");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    private static class LesionsLikePic4<T extends ISymptomViewModel> extends Symptom<T> {
        public LesionsLikePic4() {
            this(null);
        }

        public LesionsLikePic4(Class<T> cls) {
            super(72, "Does the rash resemble the picture below?");

            if (cls == null)
                return;

            try {
                mChildViewModel = cls.newInstance();
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public List<Disease> getSupportDisease() {
            return new ArrayList<Disease>() {{
                add(Disease.MONKEYPOX);
                add(Disease.OTHER);
            }};
        }
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    // TODO: Make async
    public static ISymptomValueLoader makeSymptoms(Disease disease) {
        List<Symptom> database = getSymptomDatabase();
        List<Symptom> newList = new ArrayList<>();

        for (Symptom s : database) {
            if (s.getSupportDisease().contains(disease)) {
                newList.add(newSymptom(s));
            }
        }

        return new ValueLoader(newList);
    }

    public abstract List<Disease> getSupportDisease();

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    private static Symptom newSymptom(Symptom s) {
        Symptom newSymptom = new Symptom(s.getValue(), s.getName(), s.getDetailTemplateResId(), s.getChildViewModel()) {
            private Symptom _s;
            private boolean _copiedSupportedDiseases;
            private List<Disease> _originalList;
            private List<Disease> _copy;

            @Override
            public List<Disease> getSupportDisease() {
                if (!_copiedSupportedDiseases) {
                    _originalList = _s.getSupportDisease();
                    _copy = new ArrayList<Disease>(_originalList.size());

                    for (Disease d: _originalList) {
                        _copy.add(d);
                    }
                }

                _copiedSupportedDiseases = true;

                return _copy;
            }

            private Symptom init(Symptom symptom) {
                _s = symptom;
                _copiedSupportedDiseases = false;

                //this.setViewModel(symptom.getViewModel());
                this.setChildViewModel(symptom.getChildViewModel());
                return this;
            }

        }.init(s);

        return newSymptom;
    }

    private static List<Symptom> getSymptomDatabase() {
        return new ArrayList<Symptom>() {{
            add(Symptom.FEVER);
            add(Symptom.VOMITING);
            add(Symptom.DIARRHEA);
            add(Symptom.BLOOD_IN_STOOL);
            add(Symptom.NAUSEA);
            add(Symptom.ABDOMINAL_PAIN);
            add(Symptom.HEAD_ACHE);
            add(Symptom.MUSCLE_PAIN);
            add(Symptom.FATIGUE_GENERAL_WEAKNESS);
            add(Symptom.UNEXPLAINED_BLEEDING_BRUISING);
            add(Symptom.BLEEDING_GUM);
            add(Symptom.BLEEDING_FROM_INJECTION_SITE);
            add(Symptom.NOSE_BLEED);
            add(Symptom.BLOODY_BLACK_STOOL);
            add(Symptom.BLOOD_IN_VOMIT);
            add(Symptom.DIGESTED_BLOOD_IN_VOMIT);
            add(Symptom.COUGHING_BLOOD);
            add(Symptom.BLEEDING_FROM_VAGINA);
            add(Symptom.BRUISED_SKIN);
            add(Symptom.BLOOD_IN_URINE);
            add(Symptom.OTHER_HEMORRHAGIC);
            add(Symptom.SKIN_RASH);
            add(Symptom.STIFF_NECK);
            add(Symptom.SORE_THROAT);
            add(Symptom.COUGH);
            add(Symptom.RUNNY_NOSE);
            add(Symptom.DIFFICULTY_BREATHING);
            add(Symptom.CHEST_PAIN);
            add(Symptom.CONFUSED_OR_DISORIENTED);
            add(Symptom.CONVULSION_OR_SEIZURES);
            add(Symptom.ALTERED_CONSCIOUSNESS);
            add(Symptom.CONJUNCTIVITIS);
            add(Symptom.PAIN_BEHIND_EYES);
            add(Symptom.KOPLIK_SPOTS);
            add(Symptom.THROMBOCYTOPENIA);
            add(Symptom.MIDDLE_EAR_INFLAMMATION);
            add(Symptom.ACUTE_HEARING_LOSS);
            add(Symptom.DEHYDRATION);
            add(Symptom.LOSS_OF_APPETITE);
            add(Symptom.REFUSAL_TO_FEED);
            add(Symptom.JOINT_PAIN);
            add(Symptom.SHOCK);
            add(Symptom.HICCUPS);
            add(Symptom.OTHER_NON_HEMORRHAGIC);
            add(Symptom.BACKACHE);
            add(Symptom.BLEEDING_FROM_EYES);
            add(Symptom.JAUNDICE);
            add(Symptom.DARK_URINE);
            add(Symptom.BLEEDING_FROM_STOMACH);
            add(Symptom.RAPID_BREATHING);
            add(Symptom.SWOLLEN_GLANDS);
            add(Symptom.CUTANEOUS_ERUPTION);
            add(Symptom.CHILLS_OR_SWEAT);
            add(Symptom.BEDRIDDEN);
            add(Symptom.ORAL_ULCERS);
            add(Symptom.PAINFUL_LYMPHADENITIS);
            add(Symptom.BLACKENING_DEATH_OF_TISSUE);
            add(Symptom.BUBOES_GROIN_ARMPIT_NECK);
            add(Symptom.BULGING_FONTANELLE);

            //NEW
            add(Symptom.DIFFICULTY_SWALLOWING);
            add(Symptom.LESIONS);
            add(Symptom.LYMPHADENOPATHY_INGUINAL);
            add(Symptom.LYMPHADENOPATHY_AXILLARY);
            add(Symptom.LYMPHADENOPATHY_CERVICAL);


            //add(Symptom.LESIONS_THAT_ITCH);
            //add(Symptom.LESIONS_SAME_STATE);
            //add(Symptom.LESIONS_SAME_SIZE);
            //add(Symptom.LESIONS_SAME_PROFOUND);
        }};
    }

    private static class ValueLoader implements ISymptomValueLoader {

        private List<Symptom> list;

        public ValueLoader(List<Symptom> list) {
            this.list = list;
        }

        @Override
        public List<Symptom> unloaded() {
            return list;
        }

        @Override
        public List<Symptom> loadState(Symptoms record) {
            return SymptomFacade.loadState(list, record);
        }
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Overrides">

    @Override
    public int hashCode() {
        return value + 37 * value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Symptom)) {
            return false;
        }
        Symptom other = (Symptom) obj;
        return value == other.value;
    }

    @Override
    public String toString() {
        return getName();
    }

    // </editor-fold>
}
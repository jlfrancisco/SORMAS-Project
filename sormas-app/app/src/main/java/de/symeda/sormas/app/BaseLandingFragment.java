package de.symeda.sormas.app;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.symeda.sormas.app.component.menu.PageMenuControl;
import de.symeda.sormas.app.core.NotImplementedException;
import de.symeda.sormas.app.core.adapter.multiview.EnumMapDataBinderAdapter;

public abstract class BaseLandingFragment<E extends Enum<E>, TAdapter extends EnumMapDataBinderAdapter<E>> extends BaseFragment {

    private BaseLandingActivity baseLandingActivity;
    private RecyclerView.LayoutManager layoutManager;
    private TAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(this.getRootListLayout(), container, false);

        if (getActivity() instanceof BaseLandingActivity) {
            this.baseLandingActivity = (BaseLandingActivity) this.getActivity();
        } else {
            throw new NotImplementedException("The landing activity for fragment must implement BaseLandingActivity");
        }

        this.recyclerView = createRecyclerView(view);
        this.layoutManager = createLayoutManager();
        this.adapter = createLandingAdapter();

        return view;
    }

    public int getRootListLayout() {
        return R.layout.fragment_root_landing_layout;
    }

    public PageMenuControl createMenuControl(View view) {
        return (PageMenuControl) view.findViewById(R.id.landingPageMenuControl);
    }

    public RecyclerView createRecyclerView(View view) {
        return (RecyclerView) view.findViewById(R.id.recyclerview_main);
    }

    public abstract TAdapter createLandingAdapter();

    public abstract RecyclerView.LayoutManager createLayoutManager();

    public TAdapter getLandingAdapter() {
        return this.adapter;
    }

    public RecyclerView.LayoutManager getLandingLayoutManager() {
        return this.layoutManager;
    }

    public BaseLandingActivity getBaseLandingActivity() {
        return this.baseLandingActivity;
    }

    public RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    public void configure() {
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(this.layoutManager);
    }

    protected static <TFragment extends BaseLandingFragment, TActivity extends BaseLandingActivity> TFragment newInstance(Class<TFragment> f) throws IllegalAccessException, java.lang.InstantiationException {
        TFragment fragment = f.newInstance();
        /*Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }


        fragment.setArguments(bundle);*/
        return fragment;
    }

    public boolean showNewAction() {
        return false;
    }
}
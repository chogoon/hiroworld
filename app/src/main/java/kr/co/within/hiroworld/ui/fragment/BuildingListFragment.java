package kr.co.within.hiroworld.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.data.model.ReviewData;
import kr.co.within.hiroworld.ui.adapter.BuildingsAdapter;
import kr.co.within.hiroworld.ui.adapter.ReviewAdapter;
import kr.co.within.hiroworld.ui.view.BuildingListContract;
import kr.co.within.hiroworld.util.MyLog;
import kr.co.within.hiroworld.util.MyToast;
import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chogoon on 2017-07-06.
 */

public class BuildingListFragment extends Fragment implements BuildingListContract.View {

    private ProgressDialog progressDialog;

    @BindView(R.id.building_list_)
    RecyclerView buildingRecyclerView;

    private BuildingsAdapter buildingsAdapter;
    private BuildingListContract.Presenter presenter;

    public BuildingListFragment() {

    }

    public static BuildingListFragment newInstance() {
        return new BuildingListFragment();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        DaggerBuildingListComponent.builder()
//                .buildingListModule(new BuildingListModule(getActivity(), this))
//                .appComponent(MyApplication.get(getActivity()).component())
//                .build().inject((BuildingListActivity) getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyLog.e("onCreateView");
        View view = inflater.inflate(R.layout.fragment_watch_review, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        buildingsAdapter = new BuildingsAdapter(BuildingsAdapter.ViewHolder.class, presenter.getPicasso());
        buildingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        buildingRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        buildingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        buildingRecyclerView.setAdapter(buildingsAdapter);

        presenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.unsubscribe();
        super.onDestroyView();
    }

    public Observable<View> getItemViewClickObservable(){
        return  buildingsAdapter.getItemViewClickObservable();
    }

    @Override
    public void setPresenter(BuildingListContract.Presenter presenter) {
        MyLog.e("setPresenter");
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void showLoading(boolean flag){
        if(flag){
            progressDialog.show();
        }else{
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String message){
        MyToast.show(getContext(), message);
    }

    @Override
    public void showBuildings(List items){
        buildingsAdapter.setItems(items);
    }

    @Override
    public void addBuilding(Object item){
        buildingsAdapter.addItem(item);
    }

    @Override
    public void swapBuildingData(Object item) {
        buildingsAdapter.swapData(item);
    }

    @Override
    public void removeBuildingData(Object item) {
        buildingsAdapter.removeData(item);
    }


}

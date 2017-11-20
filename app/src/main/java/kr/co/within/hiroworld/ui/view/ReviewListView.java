package kr.co.within.hiroworld.ui.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.ui.adapter.ReviewAdapter;
import kr.co.within.hiroworld.util.MyLog;
import rx.Observable;

/**
 * Created by chogoon on 2017-07-03.
 */

@SuppressLint("ViewConstructor")
public class ReviewListView extends FrameLayout implements ReviewListContract.View{

    @BindView(R.id.recycler_view_)
    RecyclerView recyclerView;

    @BindView(R.id.write_)
    Button writeButton;

    private final ProgressDialog progressDialog = new ProgressDialog(getContext());
    private ReviewAdapter reviewAdapter;

    public ReviewListView(Activity activity, Picasso picasso, LinearLayoutManager linearLayoutManager) {
        super(activity);
               inflate(getContext(), R.layout.activity_review_list, this);
        ButterKnife.bind(this);
        progressDialog.setMessage("Loading...");
        reviewAdapter = new ReviewAdapter(ReviewAdapter.ViewHolder.class, picasso);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reviewAdapter);

    }

    @Override
    public void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Observable<Void> writeButton(){
        return RxView.clicks(writeButton);
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
    public void setPresenter(ReviewListContract.Presenter presenter) {

    }

    @Override
    public void showReviews(List items) {
        MyLog.e("----------------------------" + items.size() + "----------------------------");
        reviewAdapter.getItems().clear();
        reviewAdapter.setItems(items);
    }

    @Override
    public void addData(Object item) {
        reviewAdapter.addItem(item);
    }

    @Override
    public void swapData(Object item) {
        MyLog.e("----------------------------swapData----------------------------");
        reviewAdapter.swapData(item);
    }
    @Override
    public void removeData(Object item) {
        reviewAdapter.removeData(item);
    }

}

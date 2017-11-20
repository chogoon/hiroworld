package kr.co.within.hiroworld.ui.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.RxViewGroup;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.data.model.RecommendData;
import kr.co.within.hiroworld.util.MyLog;
import rx.Observable;

/**
 * Created by chogoon on 2017-07-03.
 */

@SuppressLint("ViewConstructor")
public class ReviewWriteView extends FrameLayout implements ReviewWriteContract.View{

    @BindView(R.id.main_layout_)
    LinearLayout mainLayout;

    @BindView(R.id.review_content_)
    EditText reviewEdit;

    @BindView(R.id.recommend_layout_)
    LinearLayout recommendLayout;

    @BindView(R.id.footer_layout_)
    LinearLayout footerLayout;

    @BindView(R.id.send_)
    Button sendButton;

    private final ProgressDialog progressDialog = new ProgressDialog(getContext());

    private final List<RecommendData> recommendDataList = new ArrayList<>();
    public ReviewWriteView(Activity activity) {
        super(activity);
               inflate(getContext(), R.layout.activity_review_write, this);
        ButterKnife.bind(this);
        progressDialog.setMessage("Loading...");
        recommendLayout.removeAllViewsInLayout();
    }

    public void setToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public Observable<Void> sendButton(){
        return RxView.clicks(sendButton);
    }

    public Observable<String> sendEditRealTime(){
        return RxTextView.textChanges(reviewEdit).map(CharSequence::toString).doOnNext(s -> setSendButton(!s.isEmpty()));
    }

    public void setRecomendItem(RecommendData data){
        if(data == null){
            recommendLayout.removeAllViewsInLayout();
        }else{
            if(recommendDataList.size() > 2){
                recommendDataList.remove(0);
            }
            recommendDataList.add(data);
            recommendLayout.removeAllViewsInLayout();
            for (RecommendData item : recommendDataList) {
                final TextView view = new TextView(getContext());
                view.setText(item.content);
                view.setTextSize(24f);
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                view.setBackgroundColor(color);
                view.setTextColor(Color.WHITE);
                view.setPadding(24,24,24,24);
                view.setGravity(Gravity.CENTER);
                recommendLayout.addView(view, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                RxView.clicks(view).filter(aVoid -> view.getText().length() > 0).subscribe(aVoid -> {
                    reviewEdit.setText(reviewEdit.getText() + " " + view.getText());
                    reviewEdit.setSelection(reviewEdit.getText().toString().length());
                });
            }
        }
    }

    public void setReviewEdit(String str){
        reviewEdit.setText(str);
    }

    public String getReviewEdit(){
        return reviewEdit.getText().toString().trim();
    }

    private void setSendButton(boolean flag){
        sendButton.setEnabled(flag);
    }

    @Override
    public void showLoading(boolean flag){
        MyLog.e(flag);
        if(flag){
            progressDialog.show();
        }else{
            progressDialog.dismiss();
        }
    }

    @Override
    public void setPresenter(ReviewWriteContract.Presenter presenter) {

    }

    @Override
    public void setMessages(List list) {

    }

    @Override
    public void swapData(Object item) {

    }

    @Override
    public void removeData(Object item) {

    }

    @Override
    public void showToast(String message) {

    }
}

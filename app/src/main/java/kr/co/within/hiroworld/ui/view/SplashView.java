package kr.co.within.hiroworld.ui.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.ui.adapter.MenuAdapter;
import kr.co.within.hiroworld.ui.model.SplashModel;
import kr.co.within.hiroworld.util.CircleTransform;
import kr.co.within.hiroworld.util.MyLog;
import rx.Observable;

/**
 * Created by chogoon on 2017-07-03.
 */

@SuppressLint("ViewConstructor")
public class SplashView extends FrameLayout {

    private final Picasso picasso;

    @BindView(R.id.head_layout_)
    LinearLayout headLayout;

    @BindView(R.id.email_)
    TextView emailText;

    @BindView(R.id.photo_)
    ImageView photoImage;

    @BindView(R.id.profile_layout_)
    LinearLayout profileLayout;

    @BindView(R.id.sign_layout_)
    LinearLayout signLayout;

    @BindView(R.id.sign_in_)
    Button signButton;

    @BindView(R.id.main_layout_)
    LinearLayout mainLayout;

    @BindView(R.id.recycler_view_)
    RecyclerView recyclerView;

    private MenuAdapter menuAdapter;

    private final LinearLayoutManager linearLayoutManager;
    private final DividerItemDecoration dividerItemDecoration;
    private final ProgressDialog progressDialog = new ProgressDialog(getContext());

    public SplashView(Activity activity, Picasso picasso) {
        super(activity);
        this.picasso = picasso;
        inflate(getContext(), R.layout.activity_splash, this);
        ButterKnife.bind(this);
        progressDialog.setMessage("Loading...");
        linearLayoutManager = new LinearLayoutManager(activity);
        dividerItemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        menuAdapter = new MenuAdapter(MenuAdapter.MenuViewHolder.class);
    }

    public void setToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public Observable<Void> signInButton(){
        return RxView.clicks(signButton);
    }

    public void showLoading(boolean flag){
        if(flag){
            progressDialog.show();
        }else{
            progressDialog.dismiss();
        }
    }

    public void showSignIn(final FirebaseUser user){
        if(user == null){
            signLayout.setVisibility(VISIBLE);
            profileLayout.setVisibility(GONE);
            mainLayout.setVisibility(GONE);
        }else{
            signLayout.setVisibility(GONE);
            profileLayout.setVisibility(VISIBLE);
            emailText.setText(user.getEmail() + " / " + user.getDisplayName());
            picasso.load(user.getPhotoUrl())
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.googleg_standard_color_18)
                    .error(R.drawable.googleg_standard_color_18)
                    .into(photoImage);

//            Animation animation = new TranslateAnimation(0, 0, 0, -600);
//            animation.setDuration(1000);
//            RxAnimation.events(animation, headLayout).subscribe(animationEvent ->{
//            });

            mainLayout.setVisibility(VISIBLE);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(menuAdapter);
            menuAdapter.getItems().clear();
        }
    }

    public Observable<View> getItemViewClickObservable(){
        return  menuAdapter.getItemViewClickObservable();
    }

    public void setMenuAdapterItem(SplashModel.SplashMenus item){
        MyLog.e(item);
        menuAdapter.addItem(item);
    }

}

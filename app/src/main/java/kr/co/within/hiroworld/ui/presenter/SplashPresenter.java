package kr.co.within.hiroworld.ui.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import kr.co.within.hiroworld.ui.BasePresenter;
import kr.co.within.hiroworld.ui.model.SplashModel;
import kr.co.within.hiroworld.ui.view.SplashView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chogoon on 2017-07-03.
 */

public class SplashPresenter implements BasePresenter {

    private final SplashView view;
    private final SplashModel model;
    private final FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private FirebaseAuth.AuthStateListener authListener;
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    public SplashPresenter(SplashView view, SplashModel model) {
        this.view = view;
        this.model = model;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = firebaseAuth.getCurrentUser();
    }

    @Override
    public void subscribe() {
        if(firebaseUser == null){
            authListener = firebaseAuth -> setUser(firebaseAuth.getCurrentUser());
        }else{
            setUser(firebaseUser);
        }
        compositeSubscription.add(signInButton());
        compositeSubscription.add(menuItemClick());
//

    }

    private void setUser(final FirebaseUser user){
        view.showSignIn(user);
        model.createUserComponent(user);
        compositeSubscription.add(loadMenuList());
    }

    private Subscription menuItemClick(){
        return view.getItemViewClickObservable()
                .doOnNext(itemView -> view.showLoading(true))
                .subscribe(itemView -> {
                    final SplashModel.SplashMenus menu = (SplashModel.SplashMenus) itemView.getTag();
                    switch (menu) {
                        case LOGOUT:
                            firebaseAuth.signOut();
                            view.showSignIn(null);
                            break;
                        default:
                            model.startActivity(itemView);
                            break;
                    }
                    view.showLoading(false);
                });
    }

    public Subscription signInButton(){
        return view.signInButton()
                .doOnNext(__ -> view.showLoading(true))
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> model.loginWithGoogle());
    }

    public void getAuthWithGoogle(GoogleSignInResult result) {
        view.showLoading(false);
        if(result.isSuccess()) {
            final GoogleSignInAccount account = result.getSignInAccount();
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(model.getActivity(), task -> {
                        if (task.isSuccessful()) {
                            setUser(task.getResult().getUser());
                        } else {
                            view.setToast( "Authentication failed.");
                        }
                    });
        } else {
            view.setToast("Sign In Failed");
        }
    }

    private Subscription loadMenuList(){
        return model.loadMenuList().subscribe(view::setMenuAdapterItem);
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
    }
}

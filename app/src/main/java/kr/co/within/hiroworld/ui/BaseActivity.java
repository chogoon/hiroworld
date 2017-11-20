package kr.co.within.hiroworld.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chogoon on 2017-07-03.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent(); //MyApplication.get(this).component()
    }

    protected abstract void setupActivityComponent();

}

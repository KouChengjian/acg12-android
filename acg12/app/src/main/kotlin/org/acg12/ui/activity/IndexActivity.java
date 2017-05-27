package org.acg12.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.acg12.R;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.view.IndexView;

public class IndexActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }

    //    @Override
//    public void create(Bundle savedInstance) {
//        super.create(savedInstance);
//    }
}

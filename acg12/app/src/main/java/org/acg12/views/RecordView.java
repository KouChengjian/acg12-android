package org.acg12.views;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.acg12.R;
import org.acg12.ui.ViewImpl;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class RecordView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ecord;
    }

    @Override
    public void created() {
        super.created();
        ((AppCompatActivity) getContext()).setTitle(getContext().getString(R.string.record));
        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getContext()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
    }
}

package org.acg12.views;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.acg12.R;
import org.acg12.ui.ViewImpl;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class SearchView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void created() {
        super.created();
        ((AppCompatActivity) getContext()).setTitle(getContext().getString(R.string.search));
        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getContext()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
    }
}

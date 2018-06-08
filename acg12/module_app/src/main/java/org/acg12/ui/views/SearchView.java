package org.acg12.ui.views;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.acg12.lib.listener.ParameCallBack;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.widget.CommonRecycleview;
import com.acg12.lib.widget.DeletableEditText;

import org.acg12.R;
import org.acg12.ui.adapter.SearchAdapter;
import org.acg12.widget.FlowLayout;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/16.
 */

public class SearchView extends ViewImpl {

    @BindView(R.id.edt_search)
    DeletableEditText edt_search;
    @BindView(R.id.tv_search_finish)
    TextView tv_search_finish;

    @BindView(R.id.layout_search_tag)
    protected LinearLayout layout_search_tag;
    @BindView(R.id.history_flowlayout)
    FlowLayout mHistoryFlowlayout;

    @BindView(R.id.common_recycleview)
    CommonRecycleview commonRecycleview;
    @BindView(R.id.progress_loading)
    ProgressBar progress_loading;

    SearchAdapter searchAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void created() {
        super.created();
        commonRecycleview.setLinearLayoutManager();
        commonRecycleview.setLoadingEnabled(false);
        commonRecycleview.setRefreshEnabled(false);
        searchAdapter = new SearchAdapter(getContext());
        commonRecycleview.setAdapter(searchAdapter);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, tv_search_finish);
        edt_search.addTextChangedListener((TextWatcher)mPresenter);
        edt_search.setOnEditorActionListener((TextView.OnEditorActionListener)mPresenter);
    }

    public void startLoading(){
        progress_loading.setVisibility(View.VISIBLE);
    }

    public void stopLoading(){
        progress_loading.setVisibility(View.GONE);
    }

    public void showSearchList(){
        layout_search_tag.setVisibility(View.GONE);
        commonRecycleview.setVisibility(View.VISIBLE);
        searchAdapter.getList().clear();
        searchAdapter.notifyDataSetChanged();
    }

    public void hideSearchList(){
        layout_search_tag.setVisibility(View.VISIBLE);
        commonRecycleview.setVisibility(View.GONE);
    }

    public String getSearch() {
        return edt_search.getText().toString();
    }

    public void bindData(List result , boolean refresh){
        if (refresh) {
            searchAdapter.setList(result);
            commonRecycleview.notifyChanged();
        } else {
            searchAdapter.addAll(result);
            commonRecycleview.notifyChanged(searchAdapter.getList().size() - result.size() , searchAdapter.getList().size());
        }
    }

    public void bindHistorySearch(final List<String> historyList) {
        if(historyList ==  null || historyList.size() == 0){
            layout_search_tag.setVisibility(View.GONE);
        } else {
            layout_search_tag.setVisibility(View.VISIBLE);
        }
        mHistoryFlowlayout.removeAllViews();
        for (int i = 0; i < historyList.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.include_search_history_item, mHistoryFlowlayout, false);
            TextView tv_search_history = (TextView) view.findViewById(R.id.tv_search_history);
            ImageView iv_search_history = (ImageView) view.findViewById(R.id.iv_search_history);
            iv_search_history.setVisibility(View.GONE);
            tv_search_history.setText(historyList.get(i));
            final String str = tv_search_history.getText().toString();
            //点击事件
            tv_search_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPresenter != null) {
                        ((ParameCallBack) mPresenter).onCall(str);
                    }
                }
            });
            tv_search_history.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showTagDelDialog(historyList,str);
                    return true;
                }
            });
            mHistoryFlowlayout.addView(view);
        }
    }

    /** ------------------------------------内部函数---------------------------------------------*/
    private void showTagDelDialog(final List<String> historyList , final String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setMessage("是否删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delTag(historyList , title);
                    }
                })
                .setNegativeButton("取消", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void delTag(List<String> historyList ,String title){
        Iterator<String> stringIterator = historyList.iterator();
        while (stringIterator.hasNext()) {
            String str1 = stringIterator.next();
            if (str1.equals(title)) {
                stringIterator.remove();
            }
        }
        mHistoryFlowlayout.removeAllViews();
        bindHistorySearch(historyList);
        if (mPresenter != null) {
            ((ParameCallBack) mPresenter).onCall(historyList);
        }
    }
}

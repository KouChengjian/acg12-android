package org.acg12.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.listener.ParameCallBack;
import com.acg12.lib.utils.LogUtil;

import org.acg12.R;
import org.acg12.entity.Search;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.base.SkinBaseActivity;
import org.acg12.ui.views.SearchView;
import org.acg12.utlis.cache.Cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SearchActivity extends SkinBaseActivity<SearchView> implements View.OnClickListener , ParameCallBack ,TextWatcher , TextView.OnEditorActionListener
    ,ItemClickSupport.OnItemClickListener{

    private LinkedList<String> historyList = new LinkedList<>();
    private int histroyTotal = 8;
    private String searchKey = "";

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        List<String> tags = Cache.getInstance().getHistoryTags();
        if(tags != null && tags.size() != 0){
            historyList = new LinkedList<>(tags);
        }
        mView.bindHistorySearch(new ArrayList<>(historyList));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_search_finish){
            aminFinish();
        }
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Search search = mView.getList().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", search.getSearchId());
        bundle.putInt("type", search.getType());
        bundle.putString("title", search.getTitle());
        startAnimActivity(SearchInfoActivity.class, bundle);
        finish();
    }

    @Override
    public void onCall(Object object) {
        if(object instanceof List){
            historyList = new LinkedList<>((List<String>) object);
            Cache.getInstance().savaHistoryTags(new ArrayList<>(historyList));
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            searchKey = mView.getSearch();
            refresh(searchKey);
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        searchKey = s.toString();
        if(searchKey.isEmpty()){
            mView.hideSearchList();
            mView.bindHistorySearch(new ArrayList<>(historyList));
        } else {
            mView.showSearchList();

            Iterator<String> stringIterator = historyList.iterator();
            while (stringIterator.hasNext()){
                String str = stringIterator.next();
                if(str.equals(searchKey)){
                    stringIterator.remove();
                }
            }

            historyList.addFirst(searchKey);
            if (historyList.size() > histroyTotal) {
                historyList.removeLast();
            }
            Cache.getInstance().savaHistoryTags(new ArrayList<>(historyList));

            refresh(searchKey);
        }
    }

    private void refresh(String key){
        mView.startLoading();
        HttpRequestImpl.getInstance().searchSubjectList(currentUser(), key, new HttpRequestListener<List<Search>>() {

            @Override
            public void onSuccess(List<Search> result) {
                mView.bindData(result , true);
                mView.stopLoading();
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                ShowToast(msg);
                LogUtil.e(msg);
                mView.stopLoading();
            }
        });
    }

}

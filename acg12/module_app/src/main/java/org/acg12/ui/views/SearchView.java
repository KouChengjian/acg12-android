package org.acg12.ui.views;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.kk.listener.ParameCallBack;
import com.acg12.kk.ui.ViewImpl;
import com.acg12.kk.widget.DeletableEditText;

import org.acg12.R;
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
    @BindView(R.id.history_flowlayout)
    FlowLayout mHistoryFlowlayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void created() {
        super.created();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
    }

    public void bindHistorySearch(final List<String> historyList){
        for (int i = 0; i < historyList.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.include_search_history_item, mHistoryFlowlayout , false);
            TextView tv_search_history = (TextView) view.findViewById(R.id.tv_search_history) ;
            ImageView iv_search_history = (ImageView) view.findViewById(R.id.iv_search_history) ;
            tv_search_history.setText(historyList.get(i));
            final String str = tv_search_history.getText().toString();
            //点击事件
            tv_search_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPresenter != null){
                        ((ParameCallBack) mPresenter).onCall(str);
                    }
                }
            });
            iv_search_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Iterator<String> stringIterator = historyList.iterator();
                    while (stringIterator.hasNext()){
                        String str1 = stringIterator.next();
                        if(str1.equals(str)){
                            stringIterator.remove();
                        }
                    }
                    mHistoryFlowlayout.removeAllViews();
                    bindHistorySearch(historyList);
                    if(mPresenter != null){
                        ((ParameCallBack) mPresenter).onCall(historyList);
                    }
                }
            });
            mHistoryFlowlayout.addView(view);
        }
    }
}

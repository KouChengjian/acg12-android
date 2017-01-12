package org.acg12.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.acg12.bean.Album;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.views.PreviewAlbumView;

import java.util.List;

public class PreviewAlbumActivity extends PresenterActivityImpl<PreviewAlbumView> {

    int position;
    int originLeft;
    int originTop;
    int originHeight;
    int originWidth;
    List<Album> albumList ;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        //replace = false;
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        originLeft = intent.getExtras().getInt("originLeft");
        originTop = intent.getExtras().getInt("originTop");
        originHeight = intent.getExtras().getInt("originHeight");
        originWidth = intent.getExtras().getInt("originWidth");
        position = intent.getExtras().getInt("position");
        albumList = (List<Album>)intent.getSerializableExtra("albumList");
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mView.bindData(position, originLeft ,originTop ,originHeight ,originWidth ,albumList );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}

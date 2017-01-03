package org.acg12.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import org.acg12.R;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.views.DownloadView;

public class DownloadActivity extends PresenterActivityImpl<DownloadView> {


    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

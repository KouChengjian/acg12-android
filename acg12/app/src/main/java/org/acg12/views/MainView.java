package org.acg12.views;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.ui.fragment.HomeFragment;
import org.acg12.utlis.SystemBarUtlis;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class MainView extends ViewImpl {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    Fragment[] fragments;
    HomeFragment homeFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void created() {
        super.created();
        homeFragment = new HomeFragment();
        fragments = new Fragment[]{homeFragment};
        ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().
                add(R.id.main_fragment_container, fragments[0]).show(fragments[0]).commit();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener)mPresenter);
    }

    public void toggleDrawer() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void closeDrawers() {
        drawerLayout.closeDrawers();
    }
}

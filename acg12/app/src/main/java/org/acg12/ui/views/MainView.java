package org.acg12.ui.views;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.fragment.HomeFragment;
import org.acg12.ui.fragment.SkinFragment;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class MainView extends ViewImpl {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    int currentTabIndex;
    MenuItem[] mTabs;
    Fragment[] fragments;
    HomeFragment homeFragment;
//    SkinFragment skinFragment;

    View headerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void created() {
        super.created();
        headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        mTabs = new MenuItem[menu.size()];
        mTabs[0] = menu.findItem(R.id.nav_home);
        mTabs[1] = menu.findItem(R.id.nav_color_lens);

        homeFragment = new HomeFragment();
//        skinFragment = new SkinFragment();
        fragments = new Fragment[]{homeFragment};
        ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().
                add(R.id.main_fragment_container, fragments[0]).show(fragments[0]).commit();

    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener)mPresenter);
    }

    public void onTabSelect(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.main_fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setChecked(false);
        //把当前tab设为选中状态
        mTabs[index].setChecked(true);
        currentTabIndex = index;
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

    public DrawerLayout getDrawerLayout(){
        return drawerLayout;
    }

    public NavigationView getNavigationView(){
        return navigationView;
    }
}

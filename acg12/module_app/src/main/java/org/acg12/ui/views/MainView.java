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
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.common.dao.DaoBaseImpl;
import com.acg12.common.entity.User;
import com.acg12.kk.ui.ViewImpl;
import com.acg12.kk.ui.base.PresenterHelper;
import com.acg12.kk.utils.ViewUtil;
import com.acg12.kk.utils.loadimage.ImageLoadUtils;

import org.acg12.R;
import org.acg12.ui.fragment.HomeFragment;

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

    View headerView;
    ImageView iv_nav_avatar;
    TextView tv_nav_nick;
    ImageView iv_nav_sex;
    TextView tv_nav_signature;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void created() {
        super.created();
        headerView = navigationView.getHeaderView(0);
        iv_nav_avatar = (ImageView) headerView.findViewById(R.id.iv_nav_avatar);
        tv_nav_nick = (TextView) headerView.findViewById(R.id.tv_nav_nick);
        iv_nav_sex = (ImageView) headerView.findViewById(R.id.iv_nav_sex);
        tv_nav_signature = (TextView) headerView.findViewById(R.id.tv_nav_signature);

        Menu menu = navigationView.getMenu();
        mTabs = new MenuItem[menu.size()];
        mTabs[0] = menu.findItem(R.id.nav_home);
        mTabs[1] = menu.findItem(R.id.nav_color_lens);

        homeFragment = new HomeFragment();
        fragments = new Fragment[]{homeFragment};
        ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().
                add(R.id.main_fragment_container, fragments[0]).show(fragments[0]).commit();

        paddingDate(DaoBaseImpl.getInstance(getContext()).getCurrentUser());
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, iv_nav_avatar, tv_nav_nick, tv_nav_signature);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) mPresenter);
    }

    public void paddingDate(User user) {
        if (user == null || user.getUsername() == null) {
            iv_nav_avatar.setImageResource(R.mipmap.bg_avatar_default);
            iv_nav_sex.setVisibility(View.GONE);
            ViewUtil.setText(tv_nav_nick, "点击头像登录");
            ViewUtil.setText(tv_nav_signature, "");
        } else {
            String avatar = user.getAvatar();
            if (avatar != null) {
                ImageLoadUtils.glideCircleLoading(avatar, iv_nav_avatar);
                iv_nav_sex.setVisibility(View.VISIBLE);
                if (user.getSex() == 0) {
                    iv_nav_sex.setSelected(false);
                } else {
                    iv_nav_sex.setSelected(true);
                }
                ViewUtil.setText(tv_nav_nick, user.getNick());
                ViewUtil.setText(tv_nav_signature, user.getSignature());
            }
        }
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

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }
}

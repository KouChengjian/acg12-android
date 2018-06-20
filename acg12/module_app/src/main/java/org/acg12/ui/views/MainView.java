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

import org.acg12.dao.DaoBaseImpl;
import com.acg12.lib.entity.User;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;

import org.acg12.R;
import org.acg12.ui.fragment.FindFragemnt;
import org.acg12.ui.fragment.HomeFragment;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class MainView extends ViewImpl {

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    protected NavigationView navigationView;

    private int currentTabIndex;
    private MenuItem[] mTabs;
    private Fragment[] fragments;
    private HomeFragment homeFragment;
    private FindFragemnt findFragemnt;

    private View headerView;
    private ImageView iv_nav_avatar;
    private TextView tv_nav_nick;
    private ImageView iv_nav_sex;
    private TextView tv_nav_signature;

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
        mTabs[1] = menu.findItem(R.id.nav_find);

        homeFragment = new HomeFragment();
        findFragemnt = new FindFragemnt();
        fragments = new Fragment[]{homeFragment ,findFragemnt};
        ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment_container, fragments[0]).add(R.id.main_fragment_container, fragments[1]).show(fragments[0]).hide(fragments[1]).commit();

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

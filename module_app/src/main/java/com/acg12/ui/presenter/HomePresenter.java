package com.acg12.ui.presenter;

import com.acg12.di.scope.PerFragment;
import com.acg12.entity.po.HomeEntity;
import com.acg12.lib.net.result.ResultObserver;
import com.acg12.lib.utils.JsonParse;
import com.acg12.net.services.HomeApiService;
import com.acg12.net.transformer.LifeAndDialog;
import com.acg12.ui.contract.HomeContract;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created with Android Studio.
 * UserEntity kcj
 * Date 2019/06/20
 * Description: 自动生成
 */
@PerFragment
public class HomePresenter implements HomeContract.Presenter{

    @Inject
    HomeApiService homeApiService;

    private HomeContract.View view;

    @Inject
    HomePresenter() {
    }

    @Override
    public void take(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(HomeContract.View view) {

    }

    @Override
    public void requestIndex() {
        homeApiService.index()
                .flatMap(data -> {
                    HomeEntity home = JsonParse.fromJson(data.toString(), HomeEntity.class);
                    return Single.just(home);
                })
                .compose(new LifeAndDialog<>(view))
                .subscribe(new ResultObserver<HomeEntity>() {
                    @Override
                    public void onSuccess(HomeEntity homeEntity) {
                        view.requestIndexSuccess(homeEntity);
                    }
                });
    }
}

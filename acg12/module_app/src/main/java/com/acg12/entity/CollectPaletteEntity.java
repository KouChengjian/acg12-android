package com.acg12.entity;

import com.litesuits.orm.db.annotation.Column;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/3/2 16:38
 * Description:
 */
public class CollectPaletteEntity  {

    private Long id;

    private String boardId;

    private String title;

    private String sign;

    private Integer num;

    private String cover;

    private String thumImage1;

    private String thumImage2;

    private String thumImage3;

    /** 是否收藏 */
    private Integer isCollect;
}

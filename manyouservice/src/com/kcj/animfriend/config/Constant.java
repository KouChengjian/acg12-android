package com.kcj.animfriend.config;

import com.kcj.animfriend.bean.RequestStatus;


/**
 * @ClassName: Constant
 * @Description: 常量
 * @author: KCJ
 * @date: 2016-1-3
 */
public class Constant {
	
	public static final RequestStatus SUCCESS = 
			new RequestStatus(ErrorCode.DATA_REQUEST_SUCCESS, ErrorMsg.DATA_REQUEST_SUCCESS_MSG);
	public static final RequestStatus ERROR   = 
			new RequestStatus(ErrorCode.DATA_REQUEST_FAILURE, ErrorMsg.DATA_REQUEST_FAILURE_MSG);

	// 获取画集
	public static final String URL_ALBUM = "http://huaban.com/favorite/anime/?ieks59gh&limit=20&wfl=1";//&max=474845357
	// 获取画板
	public static final String URL_PALETTE = "http://huaban.com/boards/favorite/anime/?iemf5hf8&limit=20&wfl=1"; //&max=24465404
	// 获取画板中的画集
	public static final String URL_PALETTE_ALBUM = "http://huaban.com/boards/";

	// 主页-横幅
	public static final String URL_HOME_BRAND = "http://www.bilibili.com/index/slideshow/13.json"; 
	// 主页-内容
	public static final String URL_HOME_CONTENT = "http://www.bilibili.com/index/ding.json"; 
    // 主页-更多内容 - 排行榜 (7天)
	public static final String URL_RANK_BANGUMI = "http://www.bilibili.com/index/rank/all-7-33.json";  // 新番（7天）
	public static final String URL_RANK_DOUGA   = "http://www.bilibili.com/index/rank/all-7-1.json";   // 动画（7天）
	public static final String URL_RANK_MUSIC   = "http://www.bilibili.com/index/rank/all-7-3.json";   // 音乐（7天）
	public static final String URL_RANK_ENT     = "http://www.bilibili.com/index/rank/all-7-5.json";   // 娱乐（7天）
	public static final String URL_RANK_KICHIKU = "http://www.bilibili.com/index/rank/all-7-119.json"; // 鬼畜（7天）
	// 主页-更多内容 - 番剧 
	public static final String URL_BANKUN_SERIALIZE = "http://www.bilibili.com/list/default-33-";  // 连载动画  
	public static final String URL_BANKUN_END       = "http://www.bilibili.com/list/default-32-";  // 完结动画  
	public static final String URL_BANKUN_MESSAGE   = "http://www.bilibili.com/list/default-51-";  // 资讯 
	public static final String URL_BANKUN_OFFICIAL  = "http://www.bilibili.com/list/default-152-"; // 官方延伸   
	public static final String URL_BANKUN_DOMESTIC  = "http://www.bilibili.com/list/default-153-"; // 国产动画
	// 主页-更多内容 - 动漫 
	public static final String URL_DONGMAN_MAD_AMV    = "http://www.bilibili.com/list/default-24-"; // MAD·AMV
	public static final String URL_DONGMAN_MMD_3D     = "http://www.bilibili.com/list/default-25-"; // MMD·3D
	public static final String URL_DONGMAN_SHORT_FILM = "http://www.bilibili.com/list/default-47-"; // 动画短片
	public static final String URL_DONGMAN_SYNTHESIZE = "http://www.bilibili.com/list/default-27-"; // 综合
    
	// 发现 - 所有番剧
	public static final String URL_FIND_BANKUN = "http://www.bilibili.com/api_proxy?app=bangumi&indexType=0&pagesize=30&action=site_season_index&page=";//所有的动画资源

	// 搜索 - 图片
	public static final String URL_SEARCH_ALBUM   = "http://huaban.com/search/?category=anime&q=";
	// 搜索 - 画集
	public static final String URL_SEARCH_PALETTE = "http://huaban.com/search/boards/?q=";
	// 搜索 - 视频
	public static final String URL_SEARCH_VIDEO   = "http://search.bilibili.com/video?";
	// 搜索 - 番剧
	public static final String URL_SEARCH_SERIES  = "http://search.bilibili.com/bangumi?";
}

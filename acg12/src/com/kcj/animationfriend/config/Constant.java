package com.kcj.animationfriend.config;

import io.vov.vitamio.MediaPlayer;
import android.graphics.Typeface;
import android.os.Environment;

/**
 * @ClassName: Constant
 * @Description: 一些常量
 * @author: KCJ
 * @date:  
 */
public class Constant {
	public static final String BMOB_APP_ID = "92b8ccc3c129b72251e817155d955485"; 

	public static final int PUBLISH_COMMENT = 1;
	public static final int NUMBERS_PER_PAGE = 20;//每次请求返回评论条数20
	public static final int SAVE_FAVOURITE = 2;
	public static final int GET_FAVOURITE = 3;
	public static final int GO_SETTINGS = 4;
	
	public static final String SEX_MALE = "male";
	public static final String SEX_FEMALE = "female";
	
	public static final int STARE_OK = 1000; // ok返回
	public static final int STARE_COMMENT_FOR_RESULT = 1001; // 打开
	public static final int TAKE_PICTURE = 0x000001;
	public static final int REQUEST_CODE_CAMERA = 2;
	public static final int REQUEST_CODE_PALETTE = 15;
	public static final int REQUEST_CODE_LOGIN = 16;
	public static final int REQUEST_CODE_EXIT = 17;
	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//拍照
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//本地图片
	public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;//位置
	
	public static final String Filter = "action.updata.refresh.comment";
	public static final String Filter_LOVE = "action.updata.refresh.love";
	
	public static final String DB_NAME = "caches.db";
	
	/**
	 * 存放发送图片的目录
	 */
	public static String BMOB_PICTURE_PATH = Environment.getExternalStorageDirectory()	+ "/FriendAnimal/image/";
	
	/**
	 * 管理员id
	 */
	public static String ADMIN_ID = "JGDI7WWa";
	
	
	
	/** ------------------贴图start-----------------------------  */
	public static final String APP_DIR = Environment
			.getExternalStorageDirectory() + "/StickerCamera";
	public static final String APP_TEMP = APP_DIR + "/temp";
	public static final String APP_IMAGE = APP_DIR + "/image";

	public static final int POST_TYPE_POI = 1;
	public static final int POST_TYPE_TAG = 0;
	public static final int POST_TYPE_DEFAULT = 0;

	public static final float DEFAULT_PIXEL = 1242; // 按iphone6设置
	public static final String PARAM_MAX_SIZE = "PARAM_MAX_SIZE";
	public static final String PARAM_EDIT_TEXT = "PARAM_EDIT_TEXT";
	public static final int ACTION_EDIT_LABEL = 8080;
	public static final int ACTION_EDIT_LABEL_POI = 9090;

	public static final String FEED_INFO = "FEED_INFO";

	public static final int REQUEST_CROP = 6709;
	public static final int REQUEST_PICK = 9162;
	public static final int RESULT_ERROR = 404;
	/** ------------------贴图end-----------------------------  */
	
	
	/** ------------------播放start-----------------------------  */
	
	public static final String SNAP_SHOT_PATH = "/Player";
	public static final String SESSION_LAST_POSITION_SUFIX = ".last";
	
	// key
	public static final String SUB_SHADOW_COLOR = "vplayer_sub_shadow_color";
	public static final String SUB_POSITION = "vplayer_sub_position";
	public static final String SUB_SIZE = "vplayer_sub_size";
	public static final String SUB_SHADOW_RADIUS = "vplayer_sub_shadow_radius";
	public static final String SUB_ENABLED = "vplayer_sub_enabled";
	public static final String SUB_SHADOW_ENABLED = "vplayer_sub_shadow_enabled";
	public static final String SUB_TEXT_KEY = "sub_text";
	public static final String SUB_PIXELS_KEY = "sub_pixels";
	public static final String SUB_WIDTH_KEY = "sub_width";
	public static final String SUB_HEIGHT_KEY = "sub_height";
	
	// default value 1024
	public static final int DEFAULT_BUF_SIZE = 512 * 1024;
	public static final int DEFAULT_VIDEO_QUALITY = MediaPlayer.VIDEOQUALITY_MEDIUM;
	public static final boolean DEFAULT_DEINTERLACE = false;
	public static final float DEFAULT_ASPECT_RATIO = 0f;
	public static final float DEFAULT_STEREO_VOLUME = 1.0f;
	public static final String DEFAULT_META_ENCODING = "auto";
	public static final String DEFAULT_SUB_ENCODING = "auto";
	public static final int DEFAULT_SUB_STYLE = Typeface.BOLD;
	public static final int DEFAULT_SUB_COLOR = 0xffffffff;
	public static final int DEFAULT_SUB_SHADOWCOLOR = 0xff000000;
	public static final float DEFAULT_SUB_SHADOWRADIUS = 2.0f;
	public static final float DEFAULT_SUB_SIZE = 18.0f;
	public static final float DEFAULT_SUB_POS = 10.0f;
	public static final int DEFAULT_TYPEFACE_INT = 0;
	public static final boolean DEFAULT_SUB_SHOWN = true;
	public static final boolean DEFAULT_SUB_SHADOW = true;
	public static final Typeface DEFAULT_TYPEFACE = Typeface.DEFAULT;
	
	
	public static Typeface getTypeface(int type) {
		switch (type) {
		case 0:
			return Typeface.DEFAULT;
		case 1:
			return Typeface.SANS_SERIF;
		case 2:
			return Typeface.SERIF;
		case 3:
			return Typeface.MONOSPACE;
		default:
			return DEFAULT_TYPEFACE;
		}
	}
	/** ------------------播放end-----------------------------  */
	
	public static Boolean debug = true;
	
	// URL-域名
	public static final String URL = debug ? "http://192.168.1.100:8080/acg12/":"http://120.25.97.142:8080/";
	// 主页-内容
	public static final String URL_HOME_CONTENT = URL + "home";
	// 主页-更多内容 - 图片
	public static final String URL_HOME_MORE_ALBUM = URL + "home/more/album";
	// 主页-更多内容 - 画集
	public static final String URL_HOME_MORE_PALETTE = URL + "home/more/palette";
	// 主页-更多内容 - 画集 - 图片
	public static final String URL_HOME_MORE_PALETTE_ALBUM = URL + "home/more/palette/album";
	// 主页-更多内容 - 番剧
	public static final String URL_BANKUN_SERIALIZE = URL + "home/more/vedio?type=default-33&page=";  // 连载动画
	public static final String URL_BANKUN_END       = URL + "home/more/vedio?type=default-32&page=";  // 完结动画
	public static final String URL_BANKUN_MESSAGE   = URL + "home/more/vedio?type=default-51&page=";  // 资讯
	public static final String URL_BANKUN_OFFICIAL  = URL + "home/more/vedio?type=default-152&page="; // 官方延伸
	public static final String URL_BANKUN_DOMESTIC  = URL + "home/more/vedio?type=default-153&page="; // 国产动画
	// 主页-更多内容 - 动漫
	public static final String URL_DONGMAN_MAD_AMV    = URL + "home/more/vedio?type=default-24&page="; // MAD·AMV
	public static final String URL_DONGMAN_MMD_3D     = URL + "home/more/vedio?type=default-25&page="; // MMD·3D
	public static final String URL_DONGMAN_SHORT_FILM = URL + "home/more/vedio?type=default-47&page="; // 动画短片
	public static final String URL_DONGMAN_SYNTHESIZE = URL + "home/more/vedio?type=default-27&page="; // 综合

	// 发现 - 所有番剧
	public static final String URL_FIND_BANKUN = URL + "find?&page=";//所有的动画资源

	// 搜索 - 图片
	public static final String URL_SEARCH_ALBUM   = URL + "search?action=album&key=";
	// 搜索 - 画集
	public static final String URL_SEARCH_PALETTE = URL + "search?action=palette&key=";
	// 搜索 - 视频
	public static final String URL_SEARCH_VIDEO   = URL + "search?action=video&key=";
	// 搜索 - 番剧
	public static final String URL_SEARCH_SERIES  = URL + "search?action=bangumi&key=";

	// 播放获取视频信息
	public static final String URL_GET_VIDEO_INFO = "http://www.bilibili.com/mobile/video/av";
	public static final String URL_NEW_BANKUN_INFO = "http://www.bilibili.com";//详细信息
	public static final String URL_NEW_BANKUN_RE = "http://comment.bilibili.com/recommend,";
}

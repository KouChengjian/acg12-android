package com.liteutil.image;

import com.liteutil.http.request.ParamsBuilder;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.widget.ImageView;

public class ImageOptions {

	public final static ImageOptions DEFAULT = new ImageOptions();
	
	// region ###################### decode options (equals & hashcode prop) ################
    private int maxWidth = 0;
    private int maxHeight = 0;
    private int width = 0; // С��0ʱ������ѹ��. ����0ʱ�Զ�ʶ��ImageView�Ŀ�ߺ�maxWidth.
    private int height = 0; // С��0ʱ������ѹ��. ����0ʱ�Զ�ʶ��ImageView�Ŀ�ߺ�maxHeight.
    private boolean crop = false; // crop to (width, height)

    private int radius = 0;
    private boolean square = false;
    private boolean circular = false;
    private boolean autoRotate = false;
    private boolean compress = true;
    private Bitmap.Config config = Bitmap.Config.RGB_565;
    
    // gif option
    private boolean ignoreGif = true;
    
    // region ############# display options
    private int loadingDrawableId = 0;
    private int failureDrawableId = 0;
    private Drawable loadingDrawable = null;
    private Drawable failureDrawable = null;
    private boolean forceLoadingDrawable = true;

    private ImageView.ScaleType placeholderScaleType = ImageView.ScaleType.CENTER_INSIDE;
    private ImageView.ScaleType imageScaleType = ImageView.ScaleType.CENTER_CROP;

    private boolean fadeIn = false;
    private Animation animation = null;
    
    // extends
    private boolean useMemCache = true;
    private ParamsBuilder paramsBuilder;
	
	protected ImageOptions() {}
	
	
}

package debug;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.acg12.comics.R;

/**
 * <p>组件开发模式下，用于传递数据的启动Activity，集成模式下无效</p>
 * @name LauncherActivity
 */
public class LauncherActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ARouter.getInstance().build(BaseConstant.ROUTER_DEVICE_BOLOOD_FAT).navigation();
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ARouter.getInstance().build(BaseConstant.ROUTER_DEVICE_BOLOOD_GLUCOSE).navigation();
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ARouter.getInstance().build(BaseConstant.ROUTER_DEVICE_BOLOOD_PRESSURE).navigation();
            }
        });
//        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ARouter.getInstance().build(BaseConstant.ROUTER_DEVICE_THERMOMETER).navigation();
//            }
//        });
//        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ARouter.getInstance().build(BaseConstant.ROUTER_DEVICE_WEIGHT).navigation();
//            }
//        });
//        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                bundle.putString("week","7");
//                bundle.putString("day","12");
//                ARouter.getInstance().build(BaseConstant.ROUTER_DEVICE_FETALHEART).with(bundle).navigation();
//            }
//        });
//        findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ARouter.getInstance().build(BaseConstant.ROUTER_DEVICE_HEALTH_RECORD).navigation();
//            }
//        });
    }
}

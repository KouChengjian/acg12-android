package org.acg12.config;


/**
 * Created by DELL on 2016/11/25.
 */
public class Config {

//    Context mContext ;
//    // 环信全局监听
//    private EMConnectionListener connectionListener;
//    // 更新用户数据
//    static EventBus eventbusUser;
//
//    static {
//        eventbusUser = EventBus.builder().build();
//    }
//
//    public Config(Context mContext){
//        this.mContext = mContext;
//        setEMConnectionListener();
//    }
//
//    public static EventBus getUserEventBus(){
//        return eventbusUser;
//    }
//
//    public void setEMConnectionListener(){
//        connectionListener = new EMConnectionListener() {
//
//            @Override
//            public void onConnected() {
//
//            }
//
//            @Override
//            public void onDisconnected(int error) {
//                if (error == EMError.USER_REMOVED) {
//                    onCurrentAccountRemoved();
//                }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
//                    onConnectionConflict();
//                }
//            }
//        };
//        EMClient.getInstance().addConnectionListener(connectionListener);
//    }
//
//    /**
//     * account is removed
//     */
//    protected void onCurrentAccountRemoved(){
//        Intent intent = new Intent(mContext, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(Constant.ACCOUNT_REMOVED, true);
//        mContext.startActivity(intent);
//    }
//
//    protected void onConnectionConflict(){
//        Intent intent = new Intent(mContext, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(Constant.ACCOUNT_CONFLICT, true);
//        mContext.startActivity(intent);
//    }





}

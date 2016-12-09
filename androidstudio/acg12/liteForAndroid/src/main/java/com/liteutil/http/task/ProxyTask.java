package com.liteutil.http.task;

import java.util.concurrent.Executor;

import com.liteutil.http.listener.Callback;
import com.liteutil.util.Log;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * @ClassName: ProxyTask<ResultType>
 * @Description: 异步任务的代理类(仅在task包内可用)
 */
public class ProxyTask<ResultType> extends AbsTask<ResultType> {

	public static final InternalHandler sHandler = new InternalHandler();
	public static final PriorityExecutor sDefaultExecutor = new PriorityExecutor();

    private final AbsTask<ResultType> task;
    private final Executor executor;
    private volatile boolean callOnCanceled = false;
    private volatile boolean callOnFinished = false;

    public ProxyTask(AbsTask<ResultType> task) {
        super(task);
        this.task = task;
        this.task.setTaskProxy(this);
        Executor taskExecutor = task.getExecutor();
        if (taskExecutor == null) {
            taskExecutor = sDefaultExecutor;
        }
        this.executor = taskExecutor;
        this.setTaskProxy(null);
    }

    /**
     * 执行
     */
    @Override
    public final ResultType doBackground() throws Throwable {
        this.onWaiting();
        PriorityRunnable runnable = new PriorityRunnable( task.getPriority(),new Runnable() {
        	@Override
            public void run() {
                try {
            	    // 等待过程中取消
                    if (callOnCanceled || ProxyTask.this.isCancelled()) {
                	    throw new Callback.CancelledException("");
                    }
                    // start running
                    ProxyTask.this.onStarted();
                    if (ProxyTask.this.isCancelled()) { 
                    	// 开始时取消
                        throw new Callback.CancelledException("");
                    }
                    // 执行task, 得到结果.
                    task.setResult(task.doBackground());
                    ProxyTask.this.setResult(task.getResult());
                    // 未在doBackground过程中取消成功
                    if (ProxyTask.this.isCancelled()) {
                    	throw new Callback.CancelledException("");
                    }
                    // 执行成功
                    ProxyTask.this.onSuccess(task.getResult());
                } catch (Callback.CancelledException cex) {
                	ProxyTask.this.onCancelled(cex);
                } catch (Throwable ex) {
                	ProxyTask.this.onError(ex, false);
                }
                // finished
                ProxyTask.this.onFinished();
            }
        });
        this.executor.execute(runnable);
        return null;
    }
    
    /**
     * 成功
     */
    @Override
    public void onSuccess(ResultType result) {
        this.setState(State.SUCCESS);
        sHandler.obtainMessage(MSG_WHAT_ON_SUCCESS, this).sendToTarget();
    }

    /**
     * 失败
     */
    @Override
    public void onError(Throwable ex, boolean isCallbackError) {
        this.setState(State.ERROR);
        sHandler.obtainMessage(MSG_WHAT_ON_ERROR, new ArgsObj(this, ex)).sendToTarget();
    }

    /**
     * 等待
     */
    @Override
    public void onWaiting() {
        this.setState(State.WAITING);
        sHandler.obtainMessage(MSG_WHAT_ON_WAITING, this).sendToTarget();
    }

    /**
     * 开始
     */
    @Override
    public void onStarted() {
        this.setState(State.STARTED);
        sHandler.obtainMessage(MSG_WHAT_ON_START, this).sendToTarget();
    }

    /**
     * 更新
     */
    @Override
    public void onUpdate(int flag, Object... args) {
        // obtainMessage(int what, int arg1, int arg2, Object obj), arg2 not be used.
        sHandler.obtainMessage(MSG_WHAT_ON_UPDATE, flag, flag, new ArgsObj(this, args)).sendToTarget();
    }

    /**
     * 取消
     */
    @Override
    public void onCancelled(Callback.CancelledException cex) {
        this.setState(State.CANCELLED);
        sHandler.obtainMessage(MSG_WHAT_ON_CANCEL, new ArgsObj(this, cex)).sendToTarget();
    }

    /**
     * 完成
     */
    @Override
    public void onFinished() {
        sHandler.obtainMessage(MSG_WHAT_ON_FINISHED, this).sendToTarget();
    }

    @Override
    public final void setState(State state) {
        super.setState(state);
        this.task.setState(state);
    }

    @Override
    public final Priority getPriority() {
        return task.getPriority();
    }

    @Override
    public final Executor getExecutor() {
        return this.executor;
    }

    // ########################### inner type #############################
    private static class ArgsObj {
        @SuppressWarnings("rawtypes")
		final ProxyTask taskProxy;
        final Object[] args;

        @SuppressWarnings("rawtypes")
		public ArgsObj(ProxyTask taskProxy, Object... args) {
            this.taskProxy = taskProxy;
            this.args = args;
        }
    }

    private final static int MSG_WHAT_BASE = 1000000000;
    private final static int MSG_WHAT_ON_WAITING = MSG_WHAT_BASE + 1;
    private final static int MSG_WHAT_ON_START = MSG_WHAT_BASE + 2;
    private final static int MSG_WHAT_ON_SUCCESS = MSG_WHAT_BASE + 3;
    private final static int MSG_WHAT_ON_ERROR = MSG_WHAT_BASE + 4;
    private final static int MSG_WHAT_ON_UPDATE = MSG_WHAT_BASE + 5;
    private final static int MSG_WHAT_ON_CANCEL = MSG_WHAT_BASE + 6;
    private final static int MSG_WHAT_ON_FINISHED = MSG_WHAT_BASE + 7;

    public final static class InternalHandler extends Handler {

        private InternalHandler() {
            super(Looper.getMainLooper());
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj == null) {
                throw new IllegalArgumentException("msg must not be null");
            }
            ProxyTask taskProxy = null;
            Object[] args = null;
            if (msg.obj instanceof ProxyTask) {
                taskProxy = (ProxyTask) msg.obj;
            } else if (msg.obj instanceof ArgsObj) {
                ArgsObj argsObj = (ArgsObj) msg.obj;
                taskProxy = argsObj.taskProxy;
                args = argsObj.args;
            }
            if (taskProxy == null) {
                throw new RuntimeException("msg.obj not instanceof TaskProxy");
            }

            try {
                switch (msg.what) {
                    case MSG_WHAT_ON_WAITING: {
                        taskProxy.task.onWaiting();
                        break;
                    }
                    case MSG_WHAT_ON_START: {
                        taskProxy.task.onStarted();
                        break;
                    }
                    case MSG_WHAT_ON_SUCCESS: {
                        taskProxy.task.onSuccess(taskProxy.getResult());
                        break;
                    }
                    case MSG_WHAT_ON_ERROR: {
                        assert args != null;
                        Throwable throwable = (Throwable) args[0];
                        Log.d(throwable.getMessage(), throwable);
                        taskProxy.task.onError(throwable, false);
                        break;
                    }
                    case MSG_WHAT_ON_UPDATE: {
                        taskProxy.task.onUpdate(msg.arg1, args);
                        break;
                    }
                    case MSG_WHAT_ON_CANCEL: {
                        if (taskProxy.callOnCanceled) return;
                        taskProxy.callOnCanceled = true;
                        assert args != null;
                        taskProxy.task.onCancelled((com.liteutil.http.listener.Callback.CancelledException) args[0]);
                        break;
                    }
                    case MSG_WHAT_ON_FINISHED: {
                        if (taskProxy.callOnFinished) return;
                        taskProxy.callOnFinished = true;
                        taskProxy.task.onFinished();
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } catch (Throwable ex) {
                taskProxy.setState(State.ERROR);
                if (msg.what != MSG_WHAT_ON_ERROR) {
                    taskProxy.task.onError(ex, true);
                } else  {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}

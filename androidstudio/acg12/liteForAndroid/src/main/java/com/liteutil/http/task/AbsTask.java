package com.liteutil.http.task;

import com.liteutil.http.listener.Callback;

import java.util.concurrent.Executor;

/**
 * @ClassName: AbsTask<ResultType>
 * @Description: 异步任务基类
 */
public abstract class AbsTask<ResultType> implements Callback.Cancelable {

    @SuppressWarnings("rawtypes")
	protected ProxyTask taskProxy = null;
    protected final Callback.Cancelable cancelHandler;
    protected volatile boolean isCancelled = false;
    protected volatile State state = State.IDLE; // 空闲
    protected ResultType result;

    public AbsTask() {
        this(null);
    }

    public AbsTask(Callback.Cancelable cancelHandler) {
        this.cancelHandler = cancelHandler;
    }

    /**
     * 执行
     */
    public abstract ResultType doBackground() throws Throwable;
    /**
     * 成功
     */
    public abstract void onSuccess(ResultType result);
    /**
     * 失败
     */
    public abstract void onError(Throwable ex, boolean isCallbackError);
    /**
     * 等待
     */
    public void onWaiting() {}
    /**
     * 开始
     */
    public void onStarted() {}
    /**
     * 更新
     */
    public void onUpdate(int flag, Object... args) {}
    /**
     * 取消
     */
    protected void onCancelled(Callback.CancelledException cex) {}
    /**
     * 完成
     */
    protected void onFinished() {}

    public Priority getPriority() {
        return null;
    }

    public Executor getExecutor() {
        return null;
    }

    protected final void update(int flag, Object... args) {
        if (taskProxy != null) {
            taskProxy.onUpdate(flag, args);
        }
    }

    /**
     * invoked via cancel()
     */
    protected void cancelWorks() {}

    @Override
    public final synchronized void cancel() {
        if (!this.isCancelled) {
            cancelWorks();
            if (cancelHandler != null && !cancelHandler.isCancelled()) {
                cancelHandler.cancel();
            }
            if (this.state == State.WAITING) {
                if (taskProxy != null) {
                    taskProxy.onCancelled(new Callback.CancelledException("cancelled by user"));
                    taskProxy.onFinished();
                } else if (this instanceof ProxyTask) {
                    this.onCancelled(new Callback.CancelledException("cancelled by user"));
                    this.onFinished();
                }
            }
            this.isCancelled = true;
        }
    }

    @Override
    public final boolean isCancelled() {
        return isCancelled || state == State.CANCELLED ||
                (cancelHandler != null && cancelHandler.isCancelled());
    }

    public final boolean isFinished() {
        return this.state.value() > State.STARTED.value();
    }

    public final State getState() {
        return state;
    }

    public final ResultType getResult() {
        return result;
    }

    public void setState(State state) {
        this.state = state;
    }

    @SuppressWarnings("rawtypes")
	public final void setTaskProxy(ProxyTask taskProxy) {
        this.taskProxy = taskProxy;
    }

    public final void setResult(ResultType result) {
        this.result = result;
    }

    public enum State {
        IDLE(0), WAITING(1), STARTED(2), SUCCESS(3), CANCELLED(4), ERROR(5);
        private final int value;

        private State(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }
}

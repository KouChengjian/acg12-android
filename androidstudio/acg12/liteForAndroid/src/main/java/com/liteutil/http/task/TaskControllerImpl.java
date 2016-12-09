package com.liteutil.http.task;

import java.util.concurrent.atomic.AtomicInteger;

import com.liteutil.http.listener.Callback;
import com.liteutil.util.Log;

import android.os.Looper;

/**
 * Created by wyouflf on 15/6/5.
 * 异步任务的管理类
 */
public final class TaskControllerImpl implements TaskController {

    private TaskControllerImpl() {}

    private static TaskControllerImpl instance;

	public static TaskControllerImpl registerInstance() {
		if (instance == null) {
			synchronized (TaskController.class) {
				if (instance == null) {
					instance = new TaskControllerImpl();
				}
			}
		}
		return instance;
	}

    /**
     * 开始一个异步任务
     */
    @Override
    public <T> AbsTask<T> start(AbsTask<T> task) {
        ProxyTask<T> proxy = null;
        if (task instanceof ProxyTask) {
            proxy = (ProxyTask<T>) task;
        } else {
            proxy = new ProxyTask<T>(task);
        }
        try {
            proxy.doBackground();
        } catch (Throwable ex) {
            Log.e(ex.getMessage(), ex);
        }
        return proxy;
    }

    /**
     * 同步执行一个任务
     */
    @Override
    public <T> T startSync(AbsTask<T> task) throws Throwable {
        T result = null;
        try {
            task.onWaiting();
            task.onStarted();
            result = task.doBackground();
            task.onSuccess(result);
        } catch (Callback.CancelledException cex) {
            task.onCancelled(cex);
        } catch (Throwable ex) {
            task.onError(ex, false);
            throw ex;
        } finally {
            task.onFinished();
        }
        return result;
    }

    /**
     * 批量执行异步任务
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbsTask<?>> Callback.Cancelable startTasks(
            final Callback.GroupCallback<T> groupCallback, final T... tasks) {

        if (tasks == null) {
            throw new IllegalArgumentException("task must not be null");
        }

        final Runnable callIfOnAllFinished = new Runnable() {
            private final int total = tasks.length;
            private final AtomicInteger count = new AtomicInteger(0);

            @Override
            public void run() {
                if (count.incrementAndGet() == total) {
                    if (groupCallback != null) {
                        groupCallback.onAllFinished();
                    }
                }
            }
        };

        for (final T task : tasks) {
            start(new ProxyTask(task) {
                @Override
                public void onSuccess(Object result) {
                    super.onSuccess(result);
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (groupCallback != null) {
                                groupCallback.onSuccess(task);
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(final Callback.CancelledException cex) {
                    super.onCancelled(cex);
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (groupCallback != null) {
                                groupCallback.onCancelled(task, cex);
                            }
                        }
                    });
                }

                @Override
                public void onError(final Throwable ex, final boolean isCallbackError) {
                    super.onError(ex, isCallbackError);
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (groupCallback != null) {
                                groupCallback.onError(task, ex, isCallbackError);
                            }
                        }
                    });
                }

                @Override
                public void onFinished() {
                    super.onFinished();
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (groupCallback != null) {
                                groupCallback.onFinished(task);
                            }
                            callIfOnAllFinished.run();
                        }
                    });
                }
            });
        }

        return new Callback.Cancelable() {

            @Override
            public void cancel() {
                for (T task : tasks) {
                    task.cancel();
                }
            }

            @Override
            public boolean isCancelled() {
                boolean isCancelled = true;
                for (T task : tasks) {
                    if (!task.isCancelled()) {
                        isCancelled = false;
                    }
                }
                return isCancelled;
            }
        };
    }

    /**
     * 在UI线程执行runnable.
     * 如果已在UI线程, 则直接执行.
     */
    @Override
    public void autoPost(Runnable runnable) {
        if (runnable == null) return;
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            ProxyTask.sHandler.post(runnable);
        }
    }

    /**
     * 在UI线程执行runnable.
     * post到msg queue.
     */
    @Override
    public void post(Runnable runnable) {
        if (runnable == null) return;
        ProxyTask.sHandler.post(runnable);
    }

    /**
     * 在UI线程执行runnable.
     * @param delayMillis 延迟时间(单位毫秒)
     */
    @Override
    public void postDelayed(Runnable runnable, long delayMillis) {
        if (runnable == null) return;
        ProxyTask.sHandler.postDelayed(runnable, delayMillis);
    }

    /**
     * 在后台线程执行runnable
     */
    @Override
    public void run(Runnable runnable) {
        if (!ProxyTask.sDefaultExecutor.isBusy()) {
            ProxyTask.sDefaultExecutor.execute(runnable);
        } else {
            new Thread(runnable).start();
        }
    }

    /**
     * 移除未执行的runnable
     */
    @Override
    public void removeCallbacks(Runnable runnable) {
        ProxyTask.sHandler.removeCallbacks(runnable);
    }
}

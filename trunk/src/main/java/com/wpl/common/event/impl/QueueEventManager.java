package com.wpl.common.event.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.wpl.common.event.IEventArgs;
import com.wpl.common.event.IEventListener;
import com.wpl.common.event.IEventManager;
import com.wpl.common.event.IEventSender;

public final class QueueEventManager implements IEventManager {

    private final IEventListener mInvoker;

    private final List<EventListenerProxy> mListeners = new ArrayList<EventListenerProxy>();

    private final ExecutorService mEventDispatchService;

    /**
     * 
     */
    public QueueEventManager() {

        mInvoker = new IEventListener() {

            @Override
            public void onEvent(final IEventSender sender, final IEventArgs args) {

                mEventDispatchService.submit(new Runnable() {

                    @Override
                    public void run() {
                        for (EventListenerProxy invoker : mListeners) {
                            invoker.onEvent(sender, args);
                        }
                    }
                });
            }
        };

        mEventDispatchService = Executors.newSingleThreadExecutor();
    }

    /*
     * (non-Javadoc)
     * @see com.sicpa.standard.common.event.IListenerManager#addListener(java.lang.Object)
     */
    @Override
    public void addListener(IEventListener listener) {
        synchronized (mListeners) {
            mListeners.add(new EventListenerProxy(listener));
        }
    }

    /*
     * (non-Javadoc)
     * @see com.sicpa.standard.common.event.IListenerManager#invoker()
     */
    @Override
    public IEventListener invoker() {
        return mInvoker;
    }

    /*
     * (non-Javadoc)
     * @see com.sicpa.standard.common.event.IListenerManager#removeListener(java.lang.Object)
     */
    @Override
    public void removeListener(IEventListener listener) {
        synchronized (mListeners) {

            for (EventListenerProxy invoker : mListeners) {
                if (invoker.getListener() == listener) {
                    mListeners.remove(invoker);
                    break;
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.sicpa.standard.common.event.IEventManager#dispose()
     */
    @Override
    public void dispose() {

        mEventDispatchService.shutdown();

        synchronized (mListeners) {
            mListeners.clear();
        }

    }
}

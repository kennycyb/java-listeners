package com.wpl.common.event.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.wpl.common.event.IEventListener;
import com.wpl.common.event.IEventManager;

public final class QueueEventManager implements IEventManager {

	private final IEventListener mInvoker;

	private final List<EventListenerProxy> mListeners = new ArrayList<EventListenerProxy>();

	private final ExecutorService mEventDispatchService;

	/**
     * 
     */
	public QueueEventManager() {
		this(null);
	}

	public QueueEventManager(final ExecutorService executor) {

		mEventDispatchService = new PassiveExecutorService(executor, null, null);

		mInvoker = new IEventListener() {

			@Override
			public void onEvent(final Object sender, final Serializable args) {

				mEventDispatchService.submit(new Runnable() {

					@Override
					public void run() {
						for (final EventListenerProxy invoker : mListeners) {
							invoker.onEvent(sender, args);
						}
					}
				});
			}
		};
	}

	@Override
	public void addListener(final IEventListener listener) {

		if (listener == null) {
			return;
		}

		synchronized (mListeners) {
			mListeners.add(new EventListenerProxy(listener));
		}
	}

	@Override
	public void invoke(final Object sender, final Serializable args) {
		mInvoker.onEvent(sender, args);
	}

	@Override
	public void removeListener(final IEventListener listener) {

		if (listener == null) {
			return;
		}

		synchronized (mListeners) {

			for (final EventListenerProxy invoker : mListeners) {
				if (invoker.getListener() == listener) {
					mListeners.remove(invoker);
					break;
				}
			}
		}
	}

	@Override
	public void dispose() {

		mEventDispatchService.shutdown();

		synchronized (mListeners) {
			mListeners.clear();
		}

	}
}

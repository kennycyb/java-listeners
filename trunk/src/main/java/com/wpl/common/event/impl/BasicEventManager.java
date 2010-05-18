package com.wpl.common.event.impl;

import java.util.ArrayList;
import java.util.List;

import com.wpl.common.event.IEventArgs;
import com.wpl.common.event.IEventListener;
import com.wpl.common.event.IEventManager;
import com.wpl.common.event.IEventSender;

public final class BasicEventManager implements IEventManager {

	private final IEventListener mInvoker;

	private final List<EventListenerProxy> mListeners = new ArrayList<EventListenerProxy>();

	/**
     * 
     */
	public BasicEventManager() {
		mInvoker = new IEventListener() {

			@Override
			public void onEvent(IEventSender sender, IEventArgs args) {
				synchronized (mListeners) {
					for (IEventListener listener : mListeners) {
						listener.onEvent(sender, args);
					}
				}
			}
		};
	}

	@Override
	public void addListener(IEventListener listener) {

		if (listener == null) {
			return;
		}

		synchronized (mListeners) {
			mListeners.add(new EventListenerProxy(listener));
		}
	}

	@Override
	public IEventListener invoker() {
		return mInvoker;
	}

	@Override
	public void removeListener(IEventListener listener) {

		if (listener == null) {
			return;
		}

		synchronized (mListeners) {

			for (EventListenerProxy invoker : mListeners) {
				if (invoker.getListener() == listener) {
					mListeners.remove(invoker);
					break;
				}
			}
		}
	}

	@Override
	public void dispose() {
		synchronized (mListeners) {
			mListeners.clear();
		}
	}
}

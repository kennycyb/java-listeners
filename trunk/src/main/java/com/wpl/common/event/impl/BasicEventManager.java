package com.wpl.common.event.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wpl.common.event.IEventListener;
import com.wpl.common.event.IEventManager;

public final class BasicEventManager implements IEventManager {

	private final IEventListener mInvoker;

	private final List<EventListenerProxy> mListeners = new ArrayList<EventListenerProxy>();

	/**
     * 
     */
	public BasicEventManager() {
		mInvoker = new IEventListener() {

			@Override
			public void onEvent(final Object sender, final Serializable args) {
				synchronized (mListeners) {
					for (final IEventListener listener : mListeners) {
						listener.onEvent(sender, args);
					}
				}
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
		synchronized (mListeners) {
			mListeners.clear();
		}
	}
}

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

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.sicpa.standard.common.event.IEventListener#onEvent(com.sicpa
			 * .standard.common. event.IEventSender,
			 * com.sicpa.standard.common.event.IEventArgs)
			 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sicpa.standard.common.event.IListenerManager#addListener(java.lang
	 * .Object)
	 */
	@Override
	public void addListener(IEventListener listener) {

		if (listener == null) {
			return;
		}

		synchronized (mListeners) {
			mListeners.add(new EventListenerProxy(listener));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sicpa.standard.common.event.IListenerManager#invoker()
	 */
	@Override
	public IEventListener invoker() {
		return mInvoker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sicpa.standard.common.event.IListenerManager#removeListener(java.
	 * lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sicpa.standard.common.event.IEventManager#dispose()
	 */
	@Override
	public void dispose() {
		synchronized (mListeners) {
			mListeners.clear();
		}
	}
}

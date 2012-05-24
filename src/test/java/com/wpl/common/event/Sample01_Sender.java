package com.wpl.common.event;

import com.wpl.common.event.impl.BasicEventManager;

public class Sample01_Sender {

	private final IEventManager mEventManager = new BasicEventManager();

	public void addListener(final IEventListener listener) {
		mEventManager.addListener(listener);
	}

	public void removeListener(final IEventListener listener) {
		mEventManager.removeListener(listener);
	}

	public void run() {

		// Some event happened, notify the listeners
		mEventManager.invoke(this, new TestEventArgs(1));

		// this line will only executed after all listeners has processed the
		// above event.
		mEventManager.invoke(this, new TestEventArgs(2));
	}
}

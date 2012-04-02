package com.wpl.common.event;

import java.io.Serializable;

public class EventListener implements IEventListener {

	private boolean mTestEventCalled = false;

	// Default listener
	@Override
	public void onEvent(final Object sender, final Serializable args) {
	}

	@EventHandler(TestEventArgs.class)
	public void onTestEvent(final Object sender, final TestEventArgs args) {
		mTestEventCalled = true;
	}

	public boolean isTestEventCalled() {
		return mTestEventCalled;
	}
}

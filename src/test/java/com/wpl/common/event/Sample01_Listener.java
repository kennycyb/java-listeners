package com.wpl.common.event;

import java.io.Serializable;

public class Sample01_Listener implements IEventListener {

	// Default event listener
	@Override
	public void onEvent(final Object sender, final Serializable args) {
	}

	@EventHandler(TestEventArgs.class)
	public void onTestEvent(final Object sender, final TestEventArgs args) {
		// method that will be called when "TestEventArgs" is raised by the
		// sender
	}

}

package com.wpl.common.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import com.wpl.common.event.impl.QueueEventExecutor;
import com.wpl.common.event.impl.QueueListenerManager;

public class Sample02_Sender {

	private final QueueListenerManager<ActionListener> mActionListeners;
	private final ICustomEventManager<MouseListener> mMouseListeners;
	private final IEventManager mEventListeners;

	public Sample02_Sender() {

		final QueueEventExecutor executor = new QueueEventExecutor();

		mActionListeners = executor.createListenerManager(ActionListener.class);
		mMouseListeners = executor.createListenerManager(MouseListener.class);
		mEventListeners = executor.createEventManager();
	}

	public void addListener(final ActionListener listener) {
		mActionListeners.addListener(listener);
	}

	public void addListener(final MouseListener listener) {
		mMouseListeners.addListener(listener);
	}

	public void addListener(final IEventListener listener) {
		mEventListeners.addListener(listener);
	}

	public void run() {

		mActionListeners.invoker().actionPerformed(
				new ActionEvent(this, 1, "test"));

		mMouseListeners.invoker().mouseClicked(null);

		mEventListeners.invoke(this, new TestEventArgs(2));
	}
}

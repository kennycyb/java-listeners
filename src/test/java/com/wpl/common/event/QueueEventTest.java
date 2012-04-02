package com.wpl.common.event;

import org.junit.Assert;
import org.junit.Test;

import com.wpl.common.event.impl.QueueEventManager;

public class QueueEventTest {

	@Test
	public void eventTest() {

		final QueueEventManager em = new QueueEventManager();

		final EventListener listener = new EventListener();

		em.addListener(listener);

		em.invoke(this, new TestEventArgs(1));

		try {
			Thread.sleep(2000);
		} catch (final InterruptedException e) {
		}

		Assert.assertTrue(listener.isTestEventCalled());

	}
}

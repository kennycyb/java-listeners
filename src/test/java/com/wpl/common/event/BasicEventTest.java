package com.wpl.common.event;

import org.junit.Assert;
import org.junit.Test;

import com.wpl.common.event.impl.BasicEventManager;

public class BasicEventTest {

	@Test
	public void eventTest() {

		final BasicEventManager em = new BasicEventManager();

		final EventListener listener = new EventListener();

		em.addListener(listener);

		em.invoke(this, new TestEventArgs(1));

		Assert.assertTrue(listener.isTestEventCalled());
	}
}

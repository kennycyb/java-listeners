package com.wpl.common.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.wpl.common.event.impl.BasicListenerManager;
import com.wpl.common.event.impl.QueueListenerManager;

public class ActionPerformedTest {

	Mockery context = new Mockery();

	@Test
	public void customEventTest() {

		final ActionListener listener = context.mock(ActionListener.class);

		final BasicListenerManager<ActionListener> lm = new BasicListenerManager<ActionListener>(
				ActionListener.class);

		final ActionEvent event = new ActionEvent(lm, 0, "test");

		context.checking(new Expectations() {
			{
				oneOf(listener).actionPerformed(with(event));
			}
		});

		lm.addListener(listener);

		lm.invoker().actionPerformed(event);
	}

	@Test
	public void queueCustomEventTest() throws InterruptedException {
		final ActionListener listener = context.mock(ActionListener.class);

		final QueueListenerManager<ActionListener> lm = new QueueListenerManager<ActionListener>(
				ActionListener.class);

		final ActionEvent event = new ActionEvent(lm, 0, "test");

		context.checking(new Expectations() {
			{
				oneOf(listener).actionPerformed(with(event));
			}
		});

		lm.addListener(listener);

		lm.invoker().actionPerformed(event);

		Thread.sleep(2000);

	}
}

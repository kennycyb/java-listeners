package com.wpl.common.event;

import com.wpl.common.event.impl.BasicEventManager;
import com.wpl.common.event.impl.BasicListenerManager;
import com.wpl.common.event.impl.QueueEventManager;
import com.wpl.common.event.impl.QueueListenerManager;

public final class ListenerManager {

	public static <T> IListenerManager<T> createBasicListener(
			Class<T> listenerClass) {
		return new BasicListenerManager<T>(listenerClass);
	}

	public static <T> IListenerManager<T> createQueueListener(
			Class<T> listenerClass) {
		return new QueueListenerManager<T>(listenerClass);
	}

	public static IEventManager createBasicEvent() {
		return new BasicEventManager();
	}

	public static IEventManager createQueueEvent() {
		return new QueueEventManager();
	}
}

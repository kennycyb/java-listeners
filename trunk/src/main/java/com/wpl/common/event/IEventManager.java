package com.wpl.common.event;

import java.io.Serializable;

/**
 * The event manager.
 */
public interface IEventManager extends IListenerManager<IEventListener> {

	public void invoke(final Object sender, final Serializable args);
}

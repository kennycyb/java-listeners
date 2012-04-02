package com.wpl.common.event;

import java.io.Serializable;

/**
 * Define the minimum method that required for a event listener.
 */
public interface IEventListener {

	/**
	 * Default event handler. If no handler was declared, this will be called.
	 * 
	 * @param sender
	 *            The sender of this event.
	 * @param args
	 */
	public void onEvent(final Object sender, final Serializable args);
}

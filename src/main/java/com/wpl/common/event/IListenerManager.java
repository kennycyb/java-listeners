package com.wpl.common.event;

/**
 * The Listener Manager.
 * 
 * @since 2.0
 */
public interface IListenerManager<E> {

	/**
	 * Add a listener
	 * 
	 * @param listener
	 * @since 2.0
	 */
	public void addListener(E listener);

	/**
	 * Remove a listener
	 * 
	 * @param listener
	 * @since 2.0
	 */
	public void removeListener(E listener);

	/**
	 * Dispose this event manager. Once it's disposed, event will not be
	 * dispatched. It will be useless after "dispose" has been called. This
	 * method allows the event manager to release any resources, e.g. TCP
	 * connection, database connection, etc.
	 */
	public void dispose();

	/**
	 * Capture the results.
	 * 
	 * @param <T>
	 * @param result
	 * @return
	 * @since 2.1
	 */
	// public <T> List<Future<T>> captureResults(T result);
}

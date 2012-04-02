package com.wpl.common.event;

public interface ICustomEventManager<E> extends IListenerManager<E> {

	/**
	 * Get the invoker.
	 * 
	 * @return
	 */
	public E invoker();

}

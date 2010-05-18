package com.wpl.common.event;

/**
 * Define the minimum method that required for a event listener.
 */
public interface IEventListener {

    /**
     * Default event handler. If no handler was declared, this will be called.
     * 
     * @param sender
     * @param args
     */
    public void onEvent(final IEventSender sender, final IEventArgs args);
}

package com.wpl.common.event.args;

import com.wpl.common.event.IEventArgs;

public class EventArgs<T extends Object> implements IEventArgs {

    /**
     * 
     */
    private static final long serialVersionUID = 6532258190144286134L;
    private final T mEvent;

    public EventArgs(T event) {
        this.mEvent = event;
    }

    public T getData() {
        return mEvent;
    }

    public Class<?> getEventType() {
        if (mEvent == null) {
            return null;
        }

        return mEvent.getClass();
    }
}

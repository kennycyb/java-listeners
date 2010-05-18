package com.wpl.common.event.args;

public class EventIdArgs<ID, Type> extends EventArgs<Type> {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private final ID mId;

	/**
     * 
     */
	public EventIdArgs(ID id, Type data) {
		super(data);
		this.mId = id;
	}

	public ID getId() {
		return mId;
	}
}

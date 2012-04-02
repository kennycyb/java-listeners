package com.wpl.common.event;

import java.io.Serializable;

public class TestEventArgs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -189290324792266171L;

	private final int mValue;

	public TestEventArgs(final int value) {
		mValue = value;
	}

	public int getValue() {
		return mValue;
	}
}

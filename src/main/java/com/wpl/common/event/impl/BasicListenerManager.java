package com.wpl.common.event.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.wpl.common.event.ICustomEventManager;

public final class BasicListenerManager<E> implements ICustomEventManager<E> {

	private final List<E> mListeners = new ArrayList<E>();
	private final Interceptor mInterceptor = new Interceptor();
	private final E mInvoker;

	// private final ThreadLocal<List<Object>> mInvokeResults = new
	// ThreadLocal<List<Object>>();

	private class Interceptor implements MethodInterceptor {

		@Override
		public Object intercept(final Object object, final Method method,
				final Object[] args, final MethodProxy proxy) throws Throwable {

			for (final E listener : mListeners) {
				try {
					method.invoke(listener, args);
				} catch (final Throwable t) {
				}
			}

			return null;
		}
	}

	/**
     * 
     */
	public BasicListenerManager(final Class<E> listenerClass) {
		final Enhancer e = new Enhancer();
		e.setSuperclass(listenerClass);
		e.setCallback(mInterceptor);
		mInvoker = listenerClass.cast(e.create());
	}

	@Override
	public void addListener(final E listener) {

		if (listener == null) {
			return;
		}

		synchronized (mListeners) {
			mListeners.add(listener);
		}
	}

	@Override
	public E invoker() {
		return mInvoker;
	}

	@Override
	public void dispose() {
		synchronized (mListeners) {
			mListeners.clear();
		}
	}

	@Override
	public void removeListener(final E listener) {

		if (listener == null) {
			return;
		}

		synchronized (mListeners) {
			mListeners.remove(listener);
		}
	}

}

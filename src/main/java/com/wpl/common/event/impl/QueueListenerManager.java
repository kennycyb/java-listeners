package com.wpl.common.event.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.common.event.IListenerManager;
import com.wpl.common.event.annotations.FutureResultList;

public final class QueueListenerManager<E> implements IListenerManager<E> {

	private static Logger LOGGER = LoggerFactory
			.getLogger(QueueListenerManager.class);

	private final List<E> mListeners = new ArrayList<E>();
	private final Interceptor mInterceptor = new Interceptor();
	private final E mInvoker;
	private final ExecutorService mEventDispatchService;

	private final ThreadLocal<List<Future<Object>>> mResults = new ThreadLocal<List<Future<Object>>>();

	private class Interceptor implements MethodInterceptor {

		public Object intercept(final Object object, final Method method,
				final Object[] args, final MethodProxy proxy) throws Throwable {

			List<Future<Object>> results = null;

			if (method.getAnnotation(FutureResultList.class) != null) {
				results = new ArrayList<Future<Object>>();
				mResults.set(results);
			} else {
				mResults.set(null);
			}

			for (final E listener : mListeners) {

				Future<Object> future =

				mEventDispatchService.submit(new Callable<Object>() {

					@Override
					public Object call() throws Exception {

						LOGGER.debug("Invoke: {0}.{1}",
								new Object[] { listener.getClass().getName(),
										method.getName() });

						return method.invoke(listener, args);
					}
				});

				if (results != null) {
					results.add(future);
				}
			}

			if (method.getReturnType().equals(List.class)
					&& method.getAnnotation(FutureResultList.class) != null) {
				return results;
			}

			return null;
		}
	}

	public List<Future<Object>> getResults() {
		return mResults.get();
	}

	/**
     * 
     */
	public QueueListenerManager(Class<E> listenerClass) {
		Enhancer e = new Enhancer();
		e.setSuperclass(listenerClass);
		e.setCallback(mInterceptor);
		mInvoker = listenerClass.cast(e.create());
		mEventDispatchService = Executors.newSingleThreadExecutor();
	}

	@Override
	public void addListener(E listener) {

		// Do not allow NULL listener

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

		mEventDispatchService.shutdown();

		synchronized (mListeners) {
			mListeners.clear();
		}
	}

	@Override
	public void removeListener(E listener) {

		if (listener == null) {
			return;
		}

		synchronized (mListeners) {
			mListeners.remove(listener);
		}
	}

}

package com.wpl.common.event.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.common.event.EventHandler;
import com.wpl.common.event.IEventListener;

/**
 * The dispatcher that look for "EventHandler" declared on a method of the
 * listener. Dispatch the event based on event type that mapped to a method.
 */
public final class EventListenerProxy implements IEventListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EventListenerProxy.class);

	private final Map<Class<?>, Method> mHandlerMap = new HashMap<Class<?>, Method>();

	private final IEventListener mListener;

	/**
	 * @return the listener
	 */
	public IEventListener getListener() {
		return mListener;
	}

	/**
	 * Create the standard dispatcher for a specified listener.
	 * 
	 * @param listener
	 */
	public EventListenerProxy(final IEventListener listener) {

		mListener = listener;

		final Method[] methods = listener.getClass().getMethods();

		for (final Method method : methods) {

			final EventHandler handler = method
					.getAnnotation(EventHandler.class);

			if (handler == null) {
				continue;
			}

			if (mHandlerMap.get(handler.value()) != null) {
				LOGGER.warn("Multiple handler for the same class type is not supported, in method"
						+ method.getName());
				continue;
			}
			mHandlerMap.put(handler.value(), method);
		}

		final Class<?>[] interfaces = listener.getClass().getInterfaces();

		for (final Class<?> clazz : interfaces) {
			final Method[] intMethods = clazz.getMethods();

			for (final Method method : intMethods) {
				final EventHandler handler = method
						.getAnnotation(EventHandler.class);

				if (handler == null) {
					continue;
				}

				final Method m = mHandlerMap.get(handler.value());

				if (m != null) {

					if (!m.getName().equals(method.getName())) {
						LOGGER.warn("Multiple handler for the same class type is not supported, in method"
								+ method.getName());
					}
					continue;
				}
				mHandlerMap.put(handler.value(), method);
			}
		}
	}

	@Override
	public void onEvent(final Object sender, final Serializable args) {

		if (args == null) {
			mListener.onEvent(sender, null);
			return;
		}

		Class<?> clazz = null;

		clazz = args.getClass();

		if (clazz == null) {
			mListener.onEvent(sender, args);
			return;
		}

		final Method handler = mHandlerMap.get(clazz);

		if (handler == null) {

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No handler defined for: "
						+ args.getClass().getName()
						+ ", calling default handler");
			}

			// Call the default handler
			mListener.onEvent(sender, args);
			return;
		}

		try {
			handler.invoke(mListener, sender, args);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("event dispatched to: "
						+ mListener.getClass().getName() + "."
						+ handler.getName() + ", event: "
						+ args.getClass().getName());
			}

		} catch (final IllegalArgumentException e) {
			LOGGER.warn("Failed to dispatch event @ "
					+ mListener.getClass().getName() + "." + handler.getName(),
					e);
		} catch (final IllegalAccessException e) {
			LOGGER.warn("Failed to dispatch event @ "
					+ mListener.getClass().getName() + "." + handler.getName(),
					e);
		} catch (final InvocationTargetException e) {
			LOGGER.warn("Failed to dispatch event @ "
					+ mListener.getClass().getName() + "." + handler.getName(),
					e);
		}
	}
}

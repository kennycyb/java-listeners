package com.wpl.common.event.impl;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class QueueEventExecutor implements ExecutorService {

	private final ExecutorService mExecutorService;

	public QueueEventExecutor() {
		mExecutorService = Executors.newSingleThreadExecutor();
	}

	public <T> QueueListenerManager<T> createListenerManager(
			final Class<T> listenerClass) {
		return new QueueListenerManager<T>(listenerClass, this);
	}

	public QueueEventManager createEventManager() {
		return new QueueEventManager(this);
	}

	@Override
	public void execute(final Runnable command) {
		mExecutorService.execute(command);
	}

	@Override
	public void shutdown() {
		mExecutorService.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return mExecutorService.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return mExecutorService.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean awaitTermination(final long timeout, final TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> Future<T> submit(final Callable<T> task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Future<T> submit(final Runnable task, final T result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> submit(final Runnable task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<Future<T>> invokeAll(
			final Collection<? extends Callable<T>> tasks)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<Future<T>> invokeAll(
			final Collection<? extends Callable<T>> tasks, final long timeout,
			final TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T invokeAny(final Collection<? extends Callable<T>> tasks)
			throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T invokeAny(final Collection<? extends Callable<T>> tasks,
			final long timeout, final TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}
}

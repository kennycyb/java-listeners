package com.wpl.common.event.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Execute using another executor. No thread pool is initialized. Execute
 * command one by one using provided thread pool.
 */
public final class PassiveExecutorService extends AbstractExecutorService {

	final private Queue<Runnable> mCommands = new LinkedList<Runnable>();
	final private String mActiveThreadName;
	final private String mIdleThreadName;
	final private ExecutorService mExecutor;
	private boolean mIsInternalActive = false;
	private boolean mIsInternalExecutor;
	private boolean mShutdown = false;

	final private Runnable mInternalCommand = new Runnable() {

		@Override
		public void run() {

			Thread.currentThread().setName(mActiveThreadName);

			Runnable command = null;

			synchronized (mCommands) {
				if (mShutdown) {
					return;
				}

				command = mCommands.poll();
				if (command == null) {
					mIsInternalActive = false;
					Thread.currentThread().setName(mIdleThreadName);
					return;
				}
			}

			try {
				command.run();
			} finally {
				Thread.currentThread().setName(mIdleThreadName);
				mExecutor.execute(mInternalCommand);
			}
		}
	};

	public PassiveExecutorService(final ExecutorService executor,
			final String activeThreadName, final String idleThreadName) {

		if (executor == null) {
			mIsInternalExecutor = true;
			mExecutor = Executors.newSingleThreadExecutor();
		} else {
			mExecutor = executor;
			mIsInternalExecutor = false;
		}

		mActiveThreadName = activeThreadName;
		mIdleThreadName = idleThreadName;
	}

	@Override
	public final void shutdown() {
		synchronized (mCommands) {
			mShutdown = true;

			if (mIsInternalExecutor) {
				mExecutor.shutdown();
			}
		}
	}

	@Override
	public final List<Runnable> shutdownNow() {

		synchronized (mCommands) {

			List<Runnable> runnables;

			if (mIsInternalExecutor) {
				runnables = mExecutor.shutdownNow();
			} else {
				runnables = new ArrayList<Runnable>();
			}

			runnables.addAll(mCommands);
			mCommands.clear();
			shutdown();
			return runnables;
		}
	}

	@Override
	public final boolean isShutdown() {
		synchronized (mCommands) {
			return mShutdown;
		}
	}

	@Override
	public final boolean isTerminated() {
		return mShutdown;
	}

	@Override
	public final boolean awaitTermination(final long timeout,
			final TimeUnit unit) throws InterruptedException {
		return true;
	}

	@Override
	public final void execute(final Runnable command) {
		synchronized (mCommands) {
			mCommands.offer(command);

			// If internal command is activated, don't reactive it.
			if (mIsInternalActive) {
				return;
			}

			// Internal command is not activate, activate it now.
			mIsInternalActive = true;
			mExecutor.execute(mInternalCommand);
		}
	}
}

# Introduction #

The QueueEventManager dispatch events using own thread, instead of caller / sender thread.  This is useful when the caller thread should not wait until the event fully processed.  For example, a TCP receiver thread that receiving TCP packet, once received, it decode the packet then add to the event queue, after that, continue receiving the next packet.

# Details #

```
final QueueEventManager em = new QueueEventManager();
final EventListener listener = new EventListener();
em.addListener(listener);
em.invoke(this, new TestEventArgs(1));
try {
    Thread.sleep(2000);
} catch (final InterruptedException e) {
}

Assert.assertTrue(listener.isTestEventCalled());
```

## Using ExecutorService ##

MyApplication.java
```
ExecutorService sGlobalExecutor = Executors.newScheduledThreadPool(7);
```

MyEventSender.java
```
QueueEventManager em = new QueueEventManager(MyApplication.sGlobalExecutor);

em.invoke(this, new TestEventArgs(1));  // Could be executed by thread 1
em.invoke(this, new TestEventArgs(2));  // could be executed by thread 2

// however, the listener will received event 1 then followed by event 2.
```
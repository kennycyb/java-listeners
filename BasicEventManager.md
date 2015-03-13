# Introduction #

The BasicEventManager will dispatch an event when invoked, immediately using the caller (or sender) thread.

# Details #

```
final BasicEventManager em = new BasicEventManager();
final EventListener listener = new EventListener();
em.addListener(listener);
em.invoke(this, new TestEventArgs(1));


```
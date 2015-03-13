## Overview ##

Java listeners library.

  * Manage list of listeners easily.
  * Switch to use caller thread or own thread easily.
  * Any listener interface can be used.
  * Use annotation for code readability, avoid using "switch case" and "if else".
  * Ability to utilize an external threadpool, to execute the event one by one using multiple thread thread pool. (1.1-SNAPSHOT).

## Event Managers ##

| **Event Manager** | **Description** | **Samples** |
|:------------------|:----------------|:------------|
| [BasicEventManager](BasicEventManager.md), [BasicListenerManager](BasicListenerManager.md) | Dispatch events using caller (or sender) thread | [SampleCodeUsingEventManager](SampleCodeUsingEventManager.md) |
| [QueueEventManager](QueueEventManager.md), [QueueListenerManager](QueueListenerManager.md) | Dispatch events using own thread |


## Sample ##

```
public void customEventTest() {

    final ActionListener listener = context.mock(ActionListener.class);

    final BasicListenerManager<ActionListener> lm = new BasicListenerManager<ActionListener>(
				ActionListener.class);

    final ActionEvent event = new ActionEvent(lm, 0, "test");

    context.checking(new Expectations() {
	{
            oneOf(listener).actionPerformed(with(event));
	}
    });

    lm.addListener(listener);

    lm.invoker().actionPerformed(event);
}
```

```
public void eventTest() {

    final BasicEventManager em = new BasicEventManager();

    final EventListener listener = new EventListener();

    em.addListener(listener);

    em.invoke(this, new TestEventArgs(1));

    Assert.assertTrue(listener.isTestEventCalled());
}
```
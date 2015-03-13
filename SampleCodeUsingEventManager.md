# Introduction #

This is a sample code on using Event Manager.


# Details #

```
public class Sample01_Sender {

	private final IEventManager mEventManager = new BasicEventManager();

	public void addListener(final IEventListener listener) {
		mEventManager.addListener(listener);
	}

	public void removeListener(final IEventListener listener) {
		mEventManager.removeListener(listener);
	}

	public void run() {

		// Some event happened, notify the listeners

		mEventManager.invoke(this, new TestEventArgs(1));
	}
}
```

```
public class Sample01_Listener implements IEventListener {

	// Default event listener
	@Override
	public void onEvent(final Object sender, final Serializable args) {
	}

	@EventHandler(TestEventArgs.class)
	public void onTestEvent(final Object sender, final TestEventArgs args) {
		// method that will be called when "TestEventArgs" is raised by the
		// sender
	}

}
```
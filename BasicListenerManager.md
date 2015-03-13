# Introduction #

BasicListenerManager dispatch event to any listener interfaces with the caller / sender thread.

# Details #

```
final ActionListener listener = context.mock(ActionListener.class);

final BasicListenerManager<ActionListener> lm = new BasicListenerManager<ActionListener>(ActionListener.class);

final ActionEvent event = new ActionEvent(lm, 0, "test");

context.checking(new Expectations() {
    {
        oneOf(listener).actionPerformed(with(event));
    }
});

lm.addListener(listener);
lm.invoker().actionPerformed(event);
```
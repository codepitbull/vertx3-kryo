# Kryo as Eventbus-Codec example

__WARNING: This is just a little experiment and not yet production ready/tested!!__

We can't use a marker-interface As Vert.x does a hard check against the class.
 
The easiest way is to wrap objects into a container object and send them over the eventbus:

To send a String do the following (see also the unit tests!!).
```java
vertx.eventBus().registerDefaultCodec(KryoContainer.class, new KryoCodec<>(KryoContainer.class));

vertx.eventBus().send(ADDRESS_TEST, new KryoContainer<>().setContainedObject("123"));
```






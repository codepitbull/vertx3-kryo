# Kryo as Eventbus-Codec example

__WARNING: This is just a little experiment and not yet production ready/tested!!__

We can't use a marker-interface As Vert.x does a hard check against the class.
 
The easiest way is to wrap objects into a container object and send them over the eventbus:

To send a String do the following (see also the unit tests!!).
```java
vertx.eventBus().registerCodec(new KryoCodec());

vertx.eventBus().send(ADDRESS_TEST, new KryoContainer<>().setContainedObject("123"));
```
Benchmark                      Mode  Cnt     Score    Error  Units
KryoVsJsonBenchmark.json       avgt   20  2008,209 ± 85,178  ns/op
KryoVsJsonBenchmark.jsonSmall  avgt   20   927,851 ± 13,066  ns/op
KryoVsJsonBenchmark.kryo       avgt   20  1992,980 ± 26,475  ns/op
KryoVsJsonBenchmark.kryoSmall  avgt   20  1497,231 ± 19,441  ns/op






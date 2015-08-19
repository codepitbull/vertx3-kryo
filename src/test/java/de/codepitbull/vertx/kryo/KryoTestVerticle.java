package de.codepitbull.vertx.kryo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class KryoTestVerticle extends AbstractVerticle{

    public static final String ADDRESS_TEST = "test";

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        vertx.eventBus().consumer(ADDRESS_TEST, msg -> {
            msg.reply(msg.body().toString());
        }).completionHandler(done -> startFuture.complete());
    }
}

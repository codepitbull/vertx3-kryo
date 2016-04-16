package de.codepitbull.vertx.kryo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.test.core.VertxTestBase;
import org.junit.Before;
import org.junit.Test;

import static de.codepitbull.vertx.kryo.KryoTestVerticleTest.KryoTestVerticle.ADDRESS_TEST;

public class KryoTestVerticleTest extends VertxTestBase {

    private TestBean reference;

    @Before
    public void setUpTest() {
        reference = new TestBean();
        reference.setVal1("val1");
        reference.setVal2(2);
        vertx.eventBus().registerCodec(new KryoCodec());
        vertx.deployVerticle(KryoTestVerticle.class.getName(), new DeploymentOptions());
        waitUntil(() -> vertx.deploymentIDs().size() == 1);
    }

    @Test
    public void testSendTestBean() {
        vertx.eventBus().send(ADDRESS_TEST, reference, new DeliveryOptions().setCodecName("kryo"), resp -> {
                    assertEquals("TestBean{val1='val1', val2=2}", resp.result().body());
                    testComplete();
                }
        );
        await();
    }

    public static class KryoTestVerticle extends AbstractVerticle {

        public static final String ADDRESS_TEST = "test";

        @Override
        public void start(Future<Void> startFuture) throws Exception {
            vertx.eventBus().consumer(ADDRESS_TEST, msg -> {
                msg.reply(msg.body().toString());
            }).completionHandler(done -> startFuture.complete());
            vertx.eventBus().send("test", new TestBean(), new DeliveryOptions().setCodecName("kryo"));
        }
    }
}

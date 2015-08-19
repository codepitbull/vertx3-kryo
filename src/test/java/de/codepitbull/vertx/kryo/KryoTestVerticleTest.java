package de.codepitbull.vertx.kryo;

import de.codepitbull.vertx.kryo.KryoTestVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.test.core.VertxTestBase;
import org.junit.Before;
import org.junit.Test;

import static de.codepitbull.vertx.kryo.KryoTestVerticle.ADDRESS_TEST;

public class KryoTestVerticleTest extends VertxTestBase{

    private TestBean reference;

    @Before
    public void setUpTest() {
        reference = new TestBean()
                .setVal1("val1")
                .setVal2(2);
        vertx.eventBus().registerDefaultCodec(TestBean.class, new KryoCodec<>(TestBean.class));
        vertx.eventBus().registerDefaultCodec(KryoContainer.class, new KryoCodec<>(KryoContainer.class));
        vertx.deployVerticle(KryoTestVerticle.class.getName(), new DeploymentOptions());
        waitUntil(() -> vertx.deploymentIDs().size() == 1);
    }

    @Test
    public void testSendTestBean() {
        vertx.eventBus().send(ADDRESS_TEST, reference, resp -> {
                    assertEquals("TestBean{val1='val1', val2=2}", resp.result().body());
                    testComplete();
                }
        );
        await();
    }

    @Test
    public void testSendKryoContainer() {
        KryoContainer<TestBean> container = new KryoContainer<>().setContainedObject("123");
        vertx.eventBus().send(ADDRESS_TEST, container, resp -> {
                    assertEquals("KryoContainer{containedObject=123}",resp.result().body());
                    testComplete();
                }
        );
        await();
    }

}

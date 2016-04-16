package de.codepitbull.vertx.kryo;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferFactoryImpl;
import io.vertx.core.eventbus.impl.codecs.JsonObjectMessageCodec;
import io.vertx.core.json.JsonObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KryoCodecTest {

    TestBean kryoTestObject;
    JsonObject jsonTestObject;
    KryoCodec codec;

    @Before
    public void setUp() {
        codec = new KryoCodec();
        codec.getKryo().register(TestBean.class);

        kryoTestObject = new TestBean();
        kryoTestObject.setVal1("val1");
        kryoTestObject.setVal2(2);

        jsonTestObject = new JsonObject().put("val1","val1").put("val2", 2);
    }

    @Test
    public void testCompareToJsonObjct() {

        Buffer kryoBuffer = new BufferFactoryImpl().buffer();
        codec.encodeToWire(kryoBuffer, kryoTestObject);

        JsonObjectMessageCodec jsonObjectMessageCodec = new JsonObjectMessageCodec();
        Buffer jsonBuffer = new BufferFactoryImpl().buffer();
        jsonObjectMessageCodec.encodeToWire(jsonBuffer, jsonTestObject);

        assertTrue(jsonBuffer.getBytes().length > kryoBuffer.getBytes().length);
    }

    @Test
    public void testKryoCodecSerialize() {
        KryoCodec codec = new KryoCodec();
        Buffer buf = new BufferFactoryImpl().buffer();
        codec.encodeToWire(buf, kryoTestObject);
        TestBean desirailized = (TestBean) codec.decodeFromWire(0, buf);
        assertEquals(kryoTestObject, desirailized);
    }

}

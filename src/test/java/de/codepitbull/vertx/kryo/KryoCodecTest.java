package de.codepitbull.vertx.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferFactoryImpl;
import io.vertx.core.spi.BufferFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class KryoCodecTest {

    private TestBean reference;

    @Before
    public void setUp() {
        reference = new TestBean()
            .setVal1("val1")
            .setVal2(2);
    }

    @Test
    public void testRawKryoSerialize() {
        Kryo kryo = new Kryo();
        kryo.addDefaultSerializer(UseKryoCodec.class, new JavaSerializer());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        kryo.writeObject(output, reference);
        Input input = new Input(new ByteArrayInputStream(bos.toByteArray()));
        TestBean deserialized = kryo.readObject(input, TestBean.class);
        assertEquals(reference, deserialized);
    }

    @Test
    public void testKryoCodecSerialize() {
        KryoCodec<TestBean> codec = new KryoCodec<>(TestBean.class);
        Buffer buf = new BufferFactoryImpl().buffer();
        codec.encodeToWire(buf, reference);
        TestBean desirailized = codec.decodeFromWire(0, buf);
        assertEquals(reference, desirailized);
    }

}

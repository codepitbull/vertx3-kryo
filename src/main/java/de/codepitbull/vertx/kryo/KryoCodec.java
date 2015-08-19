package de.codepitbull.vertx.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import de.codepitbull.vertx.kryo.UseKryoCodec;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoCodec<V extends UseKryoCodec> implements MessageCodec<V,V> {

    private Kryo kryo;
    private Class<V> clazz;

    public KryoCodec(Class<V> clazz) {
        this.clazz = clazz;
        kryo = new Kryo();
        kryo.addDefaultSerializer(UseKryoCodec.class, new JavaSerializer());
    }

    @Override
    public void encodeToWire(Buffer buffer, V useKryoCodec) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        kryo.writeObject(output, useKryoCodec);
        buffer.appendBytes(bos.toByteArray());
    }

    @Override
    public V decodeFromWire(int pos, Buffer buffer) {
        Input input = new Input(new ByteArrayInputStream(buffer.getBuffer(pos, buffer.length()).getBytes()));
        return kryo.readObject(input, clazz);
    }

    @Override
    //TODO: ignored for now, I need to make sure the object is immutable!!!
    public V transform(V useKryoCodec) {
        return useKryoCodec;
    }

    @Override
    public String name() {
        return "kryo-"+clazz.getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}

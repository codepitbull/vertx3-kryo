package de.codepitbull.vertx.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoCodec implements MessageCodec<KryoObject, KryoObject> {

    private Kryo kryo;

    public KryoCodec() {
        kryo = new Kryo();
        kryo.setDefaultSerializer(BeanSerializer.class);
    }

    public Kryo getKryo() {
        return kryo;
    }

    @Override
    public void encodeToWire(Buffer buffer, KryoObject useKryoCodec) {
        try(Output output = new Output(new ByteArrayOutputStream())) {
            kryo.writeClassAndObject(output, useKryoCodec);
            buffer.appendBytes(output.toBytes());
        }
    }

    @Override
    public KryoObject decodeFromWire(int pos, Buffer buffer) {
        try(Input input = new Input(new ByteArrayInputStream(buffer.getBuffer(pos, buffer.length()).getBytes()))) {
            input.close();
            return (KryoObject)kryo.readClassAndObject(input);
        }
    }

    @Override
    public KryoObject transform(KryoObject kryoObject) {
        return kryoObject.copy();
    }

    @Override
    public String name() {
        return "kryo";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}

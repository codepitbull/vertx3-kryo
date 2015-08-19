package de.codepitbull.vertx.kryo;

/**
 * Created by jmader on 19.08.15.
 */
public class KryoContainer<T> implements UseKryoCodec{
    private T containedObject;

    public T getContainedObject() {
        return containedObject;
    }

    public KryoContainer setContainedObject(T containedObject) {
        this.containedObject = containedObject;
        return this;
    }

    @Override
    public String toString() {
        return "KryoContainer{" +
                "containedObject=" + containedObject +
                '}';
    }
}

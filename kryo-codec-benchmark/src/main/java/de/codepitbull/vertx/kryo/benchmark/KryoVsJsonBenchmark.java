/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.codepitbull.vertx.kryo.benchmark;

import de.codepitbull.vertx.kryo.KryoCodec;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferFactoryImpl;
import io.vertx.core.eventbus.impl.codecs.JsonObjectMessageCodec;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.BufferFactory;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1, jvmArgs = {"-server", "-Xms1024M", "-Xmx1024M"})
@Threads(1)
@Warmup(iterations = 5)
@Measurement(iterations = 20)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class KryoVsJsonBenchmark {

    KryoCodec kryoCodec;
    JsonObjectMessageCodec jsonObjectMessageCodec;

    BufferFactory bufferFactory;

    TestBeanBig testBeanBig;
    TestBeanSmall testBeanSmall;
    JsonObject testJsonObject;
    JsonObject testJsonObjectSmall;

    @Setup
    public void init() throws Exception {
        bufferFactory = new BufferFactoryImpl();
        kryoCodec = new KryoCodec();
        jsonObjectMessageCodec = new JsonObjectMessageCodec();

        testBeanBig = new TestBeanBig();
        testBeanBig.setVal1("wuhuhuhdjasdhjsajdhgsjhdgahjdgasjhgdajh");
        testBeanBig.setVal2(12);
        testBeanBig.setVal3(12.1f);
        testBeanBig.setVal4("ahahah");
        testBeanBig.setVal5("12ahahah");
        testBeanBig.setVal6("ahahah1222");

        testBeanSmall = new TestBeanSmall();
        testBeanSmall.setVal1("wuha");
        testBeanSmall.setVal2(12);

        testJsonObject = new JsonObject()
                .put("val1","wuhuhuhdjasdhjsajdhgsjhdgahjdgasjhgdajh")
                .put("val2", 12)
                .put("val3", 12.1f)
                .put("val4", "ahahah")
                .put("val5", "12ahahah")
                .put("val6", "ahahah1222");

        testJsonObjectSmall = new JsonObject()
                .put("val1","wuha")
                .put("val2", 12);

        Buffer buffer = bufferFactory.buffer();
        kryoCodec.encodeToWire(buffer, testBeanBig);
        System.out.println("Encoded size of TestBeanBig "+buffer.length());
        buffer = bufferFactory.buffer();
        kryoCodec.encodeToWire(buffer, testBeanSmall);
        System.out.println("Encoded size of TestBeanSmall "+buffer.length());

        buffer = bufferFactory.buffer();
        jsonObjectMessageCodec.encodeToWire(buffer, testJsonObject);
        System.out.println("Encoded size of JsonObject "+buffer.length());
        buffer = bufferFactory.buffer();
        jsonObjectMessageCodec.encodeToWire(buffer, testJsonObjectSmall);
        System.out.println("Encoded size of small JsonObject "+buffer.length());
    }

    @Benchmark
    public void kryo() throws Exception {
        Buffer buffer = bufferFactory.buffer();
        kryoCodec.encodeToWire(buffer, testBeanBig);
        kryoCodec.decodeFromWire(0, buffer);
    }

    @Benchmark
    public void json() throws Exception {
        Buffer buffer = bufferFactory.buffer();
        jsonObjectMessageCodec.encodeToWire(buffer, testJsonObject);
        jsonObjectMessageCodec.decodeFromWire(0, buffer);
    }

    @Benchmark
    public void kryoSmall() throws Exception {
        Buffer buffer = bufferFactory.buffer();
        kryoCodec.encodeToWire(buffer, testBeanSmall);
        kryoCodec.decodeFromWire(0, buffer);
    }

    @Benchmark
    public void jsonSmall() throws Exception {
        Buffer buffer = bufferFactory.buffer();
        jsonObjectMessageCodec.encodeToWire(buffer, testJsonObjectSmall);
        jsonObjectMessageCodec.decodeFromWire(0, buffer);
    }

}

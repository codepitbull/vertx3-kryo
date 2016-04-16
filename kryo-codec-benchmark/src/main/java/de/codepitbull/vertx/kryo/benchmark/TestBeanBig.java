package de.codepitbull.vertx.kryo.benchmark;

import de.codepitbull.vertx.kryo.KryoObject;

public class TestBeanBig implements KryoObject{
    private String val1;
    private Integer val2;
    private Float val3;
    private String val4;
    private String val5;
    private String val6;

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public Integer getVal2() {
        return val2;
    }

    public void setVal2(Integer val2) {
        this.val2 = val2;
    }

    public Float getVal3() {
        return val3;
    }

    public void setVal3(Float val3) {
        this.val3 = val3;
    }

    public String getVal4() {
        return val4;
    }

    public void setVal4(String val4) {
        this.val4 = val4;
    }

    public String getVal5() {
        return val5;
    }

    public void setVal5(String val5) {
        this.val5 = val5;
    }

    public String getVal6() {
        return val6;
    }

    public void setVal6(String val6) {
        this.val6 = val6;
    }

    @Override
    public KryoObject copy() {
        TestBeanBig testBeanBig = new TestBeanBig();
        testBeanBig.setVal1(val1);
        testBeanBig.setVal2(val2);
        testBeanBig.setVal3(val3);
        testBeanBig.setVal4(val4);
        testBeanBig.setVal5(val5);
        testBeanBig.setVal6(val6);
        return testBeanBig;
    }
}

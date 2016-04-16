package de.codepitbull.vertx.kryo.benchmark;

import de.codepitbull.vertx.kryo.KryoObject;

public class TestBeanSmall implements KryoObject{
    private String val1;
    private Integer val2;

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

    @Override
    public KryoObject copy() {
        TestBeanSmall testBean = new TestBeanSmall();
        testBean.setVal1(val1);
        testBean.setVal2(val2);
        return testBean;
    }
}

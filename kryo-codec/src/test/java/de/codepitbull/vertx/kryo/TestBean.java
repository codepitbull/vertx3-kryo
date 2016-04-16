package de.codepitbull.vertx.kryo;

public class TestBean implements KryoObject{
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
    public String toString() {
        return "TestBean{" +
                "val1='" + val1 + '\'' +
                ", val2=" + val2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestBean testBean = (TestBean) o;

        if (val1 != null ? !val1.equals(testBean.val1) : testBean.val1 != null) return false;
        return !(val2 != null ? !val2.equals(testBean.val2) : testBean.val2 != null);

    }

    @Override
    public int hashCode() {
        int result = val1 != null ? val1.hashCode() : 0;
        result = 31 * result + (val2 != null ? val2.hashCode() : 0);
        return result;
    }

    @Override
    public KryoObject copy() {
        TestBean testBean = new TestBean();
        testBean.setVal1(val1);
        testBean.setVal2(val2);
        return testBean;
    }
}

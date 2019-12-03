package com.mzj.guava.utilities.objects;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

/**
 * 生成hashcode、toString、equals、compareTo
 *
 * @Auther: mazhongjia
 * @Date: 2019/12/3 11:58
 * @Version: 1.0
 */
public class ObjectsTest {

    static class Common{
        private final String name;
        private final String age;
        private final String descr;

        public Common(String name, String age, String descr) {
            this.name = name;
            this.age = age;
            this.descr = descr;
        }

        //--------------以下是IDE生成的----------------

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Common common = (Common) o;

            if (name != null ? !name.equals(common.name) : common.name != null) return false;
            if (age != null ? !age.equals(common.age) : common.age != null) return false;
            return descr != null ? descr.equals(common.descr) : common.descr == null;

        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (age != null ? age.hashCode() : 0);
            result = 31 * result + (descr != null ? descr.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Guava{");
            sb.append("name='").append(name).append('\'');
            sb.append(", age='").append(age).append('\'');
            sb.append(", descr='").append(descr).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    static class Guava implements Comparable<Guava>{
        private final String name;
        private final String age;
        private final String descr;

        public Guava(String name, String age, String descr) {
            this.name = name;
            this.age = age;
            this.descr = descr;
        }

        //--------------以下是使用通过Guava工具类实现的----------------

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Guava guava = (Guava) o;
            return Objects.equal(this.name,guava.name)
                    && Objects.equal(this.age,guava.age)
                    && Objects.equal(this.descr,guava.descr);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.name,this.age,this.descr);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).omitNullValues()
                    .add("manname",this.name)
                    .add("manage"  ,this.age)
                    .add("mandescr",this.descr).toString();
        }


        @Override
        public int compareTo(Guava o) {
            return ComparisonChain.start().compare(this.name,o.name)
                    .compare(this.age,o.age)
                    .compare(this.descr,o.descr).result();
        }
    }

}

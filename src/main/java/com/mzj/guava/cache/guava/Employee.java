package com.mzj.guava.cache.guava;

import com.google.common.base.MoreObjects;

public class Employee {

    private String name;
    private String dept;
    private String empID;

    public Employee(String name, String dept, String empID) {
        this.name = name;
        this.dept = dept;
        this.empID = empID;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getEmpID() {
        return empID;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("dept", dept)
                .add("empID", empID)
                .toString();
    }
}

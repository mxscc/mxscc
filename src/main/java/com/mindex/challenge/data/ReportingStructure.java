package com.mindex.challenge.data;

import java.util.StringJoiner;

public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ReportingStructure.class.getSimpleName() + "[", "]")
                .add("employee=" + employee)
                .add("numberOfReports=" + numberOfReports)
                .toString();
    }
}

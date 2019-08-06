package com.mindex.challenge.data;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

public class Compensation {
    @Id
    private Employee employee;
    private BigDecimal salary;
    private LocalDate effectiveDate;

    public Compensation() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Compensation.class.getSimpleName() + "[", "]")
                .add("employee=" + employee)
                .add("salary=" + salary)
                .add("effectiveDate=" + effectiveDate)
                .toString();
    }
}

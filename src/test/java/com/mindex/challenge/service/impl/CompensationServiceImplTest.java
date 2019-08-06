package com.mindex.challenge.service.impl;

import com.mindex.challenge.controller.CompensationController;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class CompensationServiceImplTest extends AbstractChallengeApplicationServiceImplTest {

    @Autowired
    private CompensationService compensationService;

    @Test
    public void testCreateAndRead() {
        Employee employee = employeeService.create(makeTestEmployee());
        BigDecimal expectedSalary = BigDecimal.valueOf(100_000);
        LocalDate expectedEffectiveDate = LocalDate.of(2001, Month.JANUARY, 1);

        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(employee);
        testCompensation.setSalary(expectedSalary);
        testCompensation.setEffectiveDate(expectedEffectiveDate);

        Compensation createdCompensation = restTemplate.postForEntity(urlStringForPath(CompensationController.PATH_BASE), testCompensation, Compensation.class).getBody();
        assertNotNull("createdCompensation", createdCompensation);
        assertCompensationEquivalence(testCompensation, createdCompensation);

        Compensation readCompensation = restTemplate.getForEntity(urlStringForPath(CompensationController.PATH_ID_TEMPLATE), Compensation.class, employee.getEmployeeId()).getBody();
        assertNotNull("readCompensation", readCompensation);
        assertCompensationEquivalence(createdCompensation, readCompensation);
    }


    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEmployeeEquivalence(expected.getEmployee(), actual.getEmployee());
        assertThat(actual, allOf(
                hasProperty("salary", equalTo(expected.getSalary())),
                hasProperty("effectiveDate", equalTo(expected.getEffectiveDate()))));
    }
}


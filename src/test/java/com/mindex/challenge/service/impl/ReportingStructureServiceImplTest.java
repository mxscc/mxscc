package com.mindex.challenge.service.impl;

import com.mindex.challenge.controller.ReportingStructureController;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReportingStructureServiceImplTest extends AbstractChallengeApplicationServiceImplTest {

    @Autowired
    private ReportingStructureService reportingStructureService;

    @Test
    public void testNumberOfReportsUnderLennon() {
        assertNumberOfReportsUnderEmployeeId(4, "16a596ae-edd3-4847-99fe-c4518e82c86f");
    }

    @Test
    public void testNumberOfReportsUnderMcCartney() {
        assertNumberOfReportsUnderEmployeeId(0, "b7839309-3348-463b-a7e3-5de1c168beb3");
    }

    @Test
    public void testNumberOfReportsUnderStarr() {
        assertNumberOfReportsUnderEmployeeId(2, "03aa1462-ffa9-4978-901b-7c001562cf6f");
    }

    @Test
    public void testNumberOfReportsUnderBest() {
        assertNumberOfReportsUnderEmployeeId(0, "62c1084e-6e34-4630-93fd-9153afb65309");
    }

    @Test
    public void testNumberOfReportsUnderHarrison() {
        assertNumberOfReportsUnderEmployeeId(0, "c0c2293d-16bd-4603-8e08-638a9d18b22c");
    }


    private void assertNumberOfReportsUnderEmployeeId(int expectedNumberOfReports, String employeeId) {
        Employee employee = employeeService.read(employeeId);
        assertNotNull("employee", employee);

        ReportingStructure actual = restTemplate.getForEntity(urlStringForPath(ReportingStructureController.PATH_ID_TEMPLATE), ReportingStructure.class, employee.getEmployeeId()).getBody();
        assertNotNull(actual);
        assertEmployeeEquivalence(employee, actual.getEmployee());
        assertEquals(expectedNumberOfReports, actual.getNumberOfReports());
    }
}

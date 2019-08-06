package com.mindex.challenge.service.impl;

import com.mindex.challenge.controller.EmployeeController;
import com.mindex.challenge.data.Employee;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmployeeServiceImplTest extends AbstractChallengeApplicationServiceImplTest {

    @Test
    public void testCreateReadUpdate() {
        Employee testEmployee = makeTestEmployee();

        // Create checks
        Employee createdEmployee = restTemplate.postForEntity(urlStringForPath(EmployeeController.PATH_BASE), testEmployee, Employee.class).getBody();
        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);


        // Read checks
        Employee readEmployee = restTemplate.getForEntity(urlStringForPath(EmployeeController.PATH_ID_TEMPLATE), Employee.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(createdEmployee, readEmployee);


        // Update checks
        readEmployee.setPosition("Development Manager");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee =
                restTemplate.exchange(urlStringForPath(EmployeeController.PATH_ID_TEMPLATE),
                        HttpMethod.PUT,
                        new HttpEntity<>(readEmployee, headers),
                        Employee.class,
                        readEmployee.getEmployeeId()).getBody();

        assertEmployeeEquivalence(readEmployee, updatedEmployee);
    }
}

package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractChallengeApplicationServiceImplTest {
    private URI hostUri;

    @Autowired protected EmployeeService employeeService;
    @Autowired protected TestRestTemplate restTemplate;
    @LocalServerPort private int port;

    @Before
    public void setup() {
        hostUri = URI.create("http://localhost:" + port);
    }

    String urlStringForPath(String path) {
        return hostUri + path;
    }

    /** not persisted */
    static Employee makeTestEmployee() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        return testEmployee;
    }

    static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertThat(actual, allOf(
                hasProperty("firstName", equalTo(expected.getFirstName())),
                hasProperty("lastName", equalTo(expected.getLastName())),
                hasProperty("department", equalTo(expected.getDepartment())),
                hasProperty("position", equalTo(expected.getPosition()))));
    }
}

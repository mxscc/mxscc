package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
    public static final String PATH_BASE = "/employee";
    public static final String PATH_ID_TEMPLATE = PATH_BASE + "/{id}";

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(PATH_BASE)
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping(PATH_ID_TEMPLATE)
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee read request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping(PATH_ID_TEMPLATE)
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee update request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }
}

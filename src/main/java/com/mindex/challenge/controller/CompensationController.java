package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);
    public static final String PATH_BASE = "/compensation";
    public static final String PATH_ID_TEMPLATE = PATH_BASE + "/{employeeId}";

    @Autowired
    private CompensationService compensationService;

    @PostMapping(PATH_BASE)
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);

        return compensationService.create(compensation);
    }

    @GetMapping(PATH_ID_TEMPLATE)
    public Compensation read(@PathVariable String employeeId) {
        LOG.debug("Received compensation read request for employee id [{}]", employeeId);

        return compensationService.read(employeeId);
    }
}

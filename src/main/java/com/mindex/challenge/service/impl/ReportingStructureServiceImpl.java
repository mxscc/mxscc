package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    /* XXX This implementation disregards:
    (1) Any cycles in the reporting graph, since we're only counting distinct employees anyway, but in production it
    would probably be good to furthermore detect and log such relations, if it were not to be added separately to the
    employee's reports modifier.

    (2) More broadly, the potential for graph modification during traversal, which could result in counts here that do
    not reflect any one coherent snapshot of the application state.  The README prompt does not specify whether it would
    be best to:
        (a) be assured that this is safely guarded against elsewhere in the system
        (b) tolerate the potentially incoherent counts, but perhaps furthermore detect and warn of concurrent modifications
        (c) lock down modifications during traversal
        (d) implement and use a snapshot functionality traverse that instead
    Any of these are doable, it'd be a conversation to determine the best spec.  This implementation is for the first
    and simplest option.
     */
    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Reading reporting structure for employee id [{}]", employeeId);

        Employee employee = employeeService.read(employeeId);

        // XXX This would be a great case for com.google.common.graph.Traverser if allowing a dependency on Guava 23.1+
        Set<String> visitedIds = new LinkedHashSet<>();
        for (Queue<String> frontierIds = new LinkedList<>(singleton(employee.getEmployeeId())); !frontierIds.isEmpty(); ) {
            String currentId = frontierIds.remove();
            if (visitedIds.add(currentId)) {
                Optional.ofNullable(employeeService.read(currentId).getDirectReports()).orElse(emptyList()).stream()
                        .map(Employee::getEmployeeId)
                        .filter(id -> !visitedIds.contains(id)) // no need to re-queue when already visited
                        .forEachOrdered(frontierIds::add);
            }
        }

        int numberOfReports = visitedIds.size() - 1; // exclude `employeeId` itself

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(numberOfReports);

        return reportingStructure;
    }
}

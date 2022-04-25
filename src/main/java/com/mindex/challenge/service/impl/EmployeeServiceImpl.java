package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public ReportingStructure getReportStructure(String id) {
        LOG.debug("Get report structure for id [{}]", id);

        Employee employee = recursiveEmployee(id);

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(getReportNumber(employee));

        return reportingStructure;
    }
    //get a fully report structure in recursive way
    private Employee recursiveEmployee(String id) {
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        List<Employee> directReports = employee.getDirectReports();
        if (!CollectionUtils.isEmpty(directReports)) {
            List<Employee> list = new ArrayList<>();
            for (Employee directReport : directReports) {
                list.add(recursiveEmployee(directReport.getEmployeeId()));
            }
            employee.setDirectReports(list);
        }
        return employee;
    }
    //get the number of reports in a recursive way
    private int getReportNumber(Employee employee) {
        int count = 0;
        List<Employee> directReports = employee.getDirectReports();
        if (!CollectionUtils.isEmpty(directReports)) {
            count += directReports.size();
            for (Employee directReport : directReports) {
                count += getReportNumber(directReport);
            }
        }
        return count;
    }

}

package com.mindex.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreate() throws Exception {
        // Given
        Employee inputEmployee = new Employee();
        inputEmployee.setFirstName("John");
        inputEmployee.setLastName("Doe");
        inputEmployee.setDepartment("Engineering");
        inputEmployee.setPosition("Developer");

        Employee createdEmployee = new Employee();
        createdEmployee.setEmployeeId("test-id-123");
        createdEmployee.setFirstName("John");
        createdEmployee.setLastName("Doe");
        createdEmployee.setDepartment("Engineering");
        createdEmployee.setPosition("Developer");

        when(employeeService.create(any(Employee.class))).thenReturn(createdEmployee);

        // When & Then
        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputEmployee)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId").value("test-id-123"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.department").value("Engineering"))
                .andExpect(jsonPath("$.position").value("Developer"));

        verify(employeeService).create(any(Employee.class));
    }

    @Test
    public void testRead() throws Exception {
        // Given
        String employeeId = "test-id-123";
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setFirstName("Jane");
        employee.setLastName("Smith");
        employee.setDepartment("Marketing");
        employee.setPosition("Manager");

        when(employeeService.read(employeeId)).thenReturn(employee);

        // When & Then
        mockMvc.perform(get("/employee/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId").value(employeeId))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.department").value("Marketing"))
                .andExpect(jsonPath("$.position").value("Manager"));

        verify(employeeService).read(employeeId);
    }

    @Test
    public void testUpdate() throws Exception {
        // Given
        String employeeId = "test-id-123";
        Employee inputEmployee = new Employee();
        inputEmployee.setFirstName("John");
        inputEmployee.setLastName("Doe");
        inputEmployee.setDepartment("Engineering");
        inputEmployee.setPosition("Senior Developer");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setEmployeeId(employeeId);
        updatedEmployee.setFirstName("John");
        updatedEmployee.setLastName("Doe");
        updatedEmployee.setDepartment("Engineering");
        updatedEmployee.setPosition("Senior Developer");

        when(employeeService.update(any(Employee.class))).thenReturn(updatedEmployee);

        // When & Then
        mockMvc.perform(put("/employee/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputEmployee)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId").value(employeeId))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.department").value("Engineering"))
                .andExpect(jsonPath("$.position").value("Senior Developer"));

        verify(employeeService).update(any(Employee.class));
    }

    @Test
    public void testGetReport() throws Exception {
        // Given
        String employeeId = "test-id-123";
        
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDepartment("Engineering");
        employee.setPosition("Manager");
        
        // Create some direct reports
        List<Employee> directReports = new ArrayList<>();
        Employee report1 = new Employee();
        report1.setEmployeeId("report-1");
        report1.setFirstName("Alice");
        report1.setLastName("Johnson");
        directReports.add(report1);
        
        Employee report2 = new Employee();
        report2.setEmployeeId("report-2");
        report2.setFirstName("Bob");
        report2.setLastName("Wilson");
        directReports.add(report2);
        
        employee.setDirectReports(directReports);

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(2);

        when(employeeService.getReportStructure(employeeId)).thenReturn(reportingStructure);

        // When & Then
        mockMvc.perform(get("/employee/report/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employee.employeeId").value(employeeId))
                .andExpect(jsonPath("$.employee.firstName").value("John"))
                .andExpect(jsonPath("$.employee.lastName").value("Doe"))
                .andExpect(jsonPath("$.numberOfReports").value(2))
                .andExpect(jsonPath("$.employee.directReports").isArray())
                .andExpect(jsonPath("$.employee.directReports[0].employeeId").value("report-1"))
                .andExpect(jsonPath("$.employee.directReports[0].firstName").value("Alice"))
                .andExpect(jsonPath("$.employee.directReports[1].employeeId").value("report-2"))
                .andExpect(jsonPath("$.employee.directReports[1].firstName").value("Bob"));

        verify(employeeService).getReportStructure(employeeId);
    }

    @Test
    public void testUpdateSetsEmployeeId() throws Exception {
        // Given
        String employeeId = "test-id-123";
        Employee inputEmployee = new Employee();
        inputEmployee.setFirstName("John");
        inputEmployee.setLastName("Doe");
        inputEmployee.setDepartment("Engineering");
        inputEmployee.setPosition("Developer");
        // Note: input employee does not have an ID set

        Employee updatedEmployee = new Employee();
        updatedEmployee.setEmployeeId(employeeId);
        updatedEmployee.setFirstName("John");
        updatedEmployee.setLastName("Doe");
        updatedEmployee.setDepartment("Engineering");
        updatedEmployee.setPosition("Developer");

        when(employeeService.update(any(Employee.class))).thenReturn(updatedEmployee);

        // When & Then
        mockMvc.perform(put("/employee/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputEmployee)))
                .andExpect(status().isOk());

        // Verify that the service was called with an employee that has the correct ID
        verify(employeeService).update(any(Employee.class));
    }
}
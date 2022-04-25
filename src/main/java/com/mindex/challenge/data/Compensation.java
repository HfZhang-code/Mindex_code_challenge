package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author HaifenG Zhang
 * @date 2022-04-23 12:19
 */
public class Compensation {

    private Employee employee;

    private BigDecimal salary;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveDate;

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

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}

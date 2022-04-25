package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @date 2022-04-23 12:34
 * @author Haifeng Zhang
 */
@RestController
public class CompensationController {

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        return compensationService.create(compensation);
    }

    @GetMapping("/compensation/employee/{employeeId}")
    public List<Compensation> listByEmployeeId(@PathVariable String employeeId) {
        return compensationService.getListByEmployeeId(employeeId);
    }

}

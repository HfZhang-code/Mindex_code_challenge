package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

import java.util.List;

/**
 * @date 2022-04-23 12:34
 * @author Haifeng Zhang
 */
public interface CompensationService {

    Compensation create(Compensation compensation);

    List<Compensation> getListByEmployeeId(String employeeId);

}

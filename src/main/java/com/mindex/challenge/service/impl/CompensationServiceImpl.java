package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @date 2022-04-23 12:34
 * @author Haifeng Zhang
 */
@Service
public class CompensationServiceImpl implements CompensationService {

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {

        compensationRepository.insert(compensation);
        return compensation;
    }

    @Override
    public List<Compensation> getListByEmployeeId(String employeeId) {
        return compensationRepository.findByEmployeeId(employeeId);
    }
}

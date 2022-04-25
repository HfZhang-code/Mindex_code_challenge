package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @date 2022-04-23 12:22
 */
@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {

    @Query(value = "{'employee.employeeId':?0}")
    List<Compensation> findByEmployeeId(String employeeId);

}

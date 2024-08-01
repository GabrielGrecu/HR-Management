package com.nagarro.si.um.repository;

import com.nagarro.si.um.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    @Query("SELECT i FROM Interview i JOIN i.users u WHERE u.userId = :userId " +
            "AND (:startDate BETWEEN i.startDate AND i.endDate " +
            "OR :endDate BETWEEN i.startDate AND i.endDate)")
    List<Interview> findOverlappingInterviews(@Param("userId") Long userId,
                                              @Param("startDate") Timestamp startDate,
                                              @Param("endDate") Timestamp endDate);
}

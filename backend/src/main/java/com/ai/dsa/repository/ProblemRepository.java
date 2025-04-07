package com.ai.dsa.repository;

import com.ai.dsa.model.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends MongoRepository<Problem, String> {

    List<Problem> findByCategory(String category);
}

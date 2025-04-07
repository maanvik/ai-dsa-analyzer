package com.ai.dsa.repository;

import com.ai.dsa.model.Solution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepository extends MongoRepository<Solution, String> {

    // Custom query method: Find solutions by user's username
    List<Solution> findByUserUsername(String username);
}

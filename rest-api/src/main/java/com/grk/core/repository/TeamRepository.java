package com.grk.core.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.grk.core.domain.Team;

public interface TeamRepository extends MongoRepository<Team, String> {

    public List<Team> findByName(String name);
}

package com.grk.core.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.grk.core.domain.Player;

public interface PlayerRepository extends MongoRepository<Player, String> {

    public List<Player> findByFirstname(String firstname);
    public List<Player> findByLastname(String lastname);

}

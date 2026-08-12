package com.footballtournament.management.persistence.crud;

import com.footballtournament.management.persistence.entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerCrudRepository
        extends CrudRepository<PlayerEntity, Integer> {

    List<PlayerEntity> findByTeam_TeamId(
            Integer teamId
    );
}

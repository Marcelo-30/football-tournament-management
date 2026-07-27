package com.footballtournament.management.persistence.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MapperContextTest {

    @Autowired
    private TournamentMapper tournamentMapper;

    @Autowired
    private TournamentMatchMapper tournamentMatchMapper;

    @Test
    void shouldRegisterMappersAsSpringBeans() {
        assertNotNull(tournamentMapper);
        assertNotNull(tournamentMatchMapper);
    }
}
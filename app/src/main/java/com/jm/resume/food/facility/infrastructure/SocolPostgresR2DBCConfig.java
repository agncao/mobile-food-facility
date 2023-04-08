package com.jm.resume.food.facility.infrastructure;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@EnableR2dbcRepositories(basePackages = {"com.jm.resume.food.facility.repository"})
public class SocolPostgresR2DBCConfig extends AbstractR2dbcConfiguration {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }

    @Override
    protected List<Object> getCustomConverters() {
        return List.of(
                new MapJsonWriter(),
                new MapJsonReader()
        );
    }
}

//    @Override
//    public ConnectionFactory connectionFactory() {
//        return new PostgresqlConnectionFactory(
//                PostgresqlConnectionConfiguration.builder()
//                        .host("localhost")
//                        .port(5432)
//                        .database("socol")
//                        .username("postgres")
//                        .password("password")
//                        .build()
//        );
//    }

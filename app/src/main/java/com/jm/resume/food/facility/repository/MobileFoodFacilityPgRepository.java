package com.jm.resume.food.facility.repository;

import com.jm.resume.food.facility.domain.MobileFoodFacility;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MobileFoodFacilityPgRepository  extends R2dbcRepository<MobileFoodFacility, Long> {
    //将MobileFoodFacility的数据存入pg数据库表mobile_food_facility中
    @Modifying
    Mono<MobileFoodFacility> save(MobileFoodFacility mobileFoodFacility);

    Flux<MobileFoodFacility> findAllByFacilityType(String facilitytype);
}

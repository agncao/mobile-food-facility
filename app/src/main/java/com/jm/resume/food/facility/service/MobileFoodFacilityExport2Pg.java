package com.jm.resume.food.facility.service;

import com.jm.resume.food.facility.domain.MobileFoodFacility;
import com.jm.resume.food.facility.repository.MobileFoodFacilityCSVRepository;
import com.jm.resume.food.facility.repository.MobileFoodFacilityPgRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Slf4j
@Component
public class MobileFoodFacilityExport2Pg implements ApplicationListener<ApplicationReadyEvent> {
    @Resource
    private MobileFoodFacilityPgRepository mobileFoodFacilityPgRepository;

    public Flux<MobileFoodFacility> export2Pg()  {
        return Mono.defer(()->Mono.just(MobileFoodFacilityCSVRepository.readCSV()))
                .flatMapMany(Flux::fromIterable)
                .flatMap(mobileFoodFacilityPgRepository::save);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("MobileFoodFacilityExport2Pg start");
        mobileFoodFacilityPgRepository.deleteAll().then(export2Pg().collectList()).subscribe();
    }
}

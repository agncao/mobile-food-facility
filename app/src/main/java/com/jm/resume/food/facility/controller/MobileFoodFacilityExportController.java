package com.jm.resume.food.facility.controller;

import com.jm.resume.food.facility.domain.MobileFoodFacility;
import com.jm.resume.food.facility.service.MobileFoodFacilityExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/food")
public class MobileFoodFacilityExportController {
    @Resource
    private MobileFoodFacilityExportService mobileFoodFacilityExportService;

    @CrossOrigin
    @PostMapping("/list")
    public Mono<List<MobileFoodFacility>> listByFacilityTyp() {
        log.info("listByFacilityTypeAndApplicant request");
        return mobileFoodFacilityExportService.findAllByFacilityTypeAndApplicant("Truck")
                .collectList();
    }
}

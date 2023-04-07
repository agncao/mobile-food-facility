package com.jm.resume.food.facility.service;

import com.jm.resume.food.facility.domain.MobileFoodFacility;
import reactor.core.publisher.Flux;

public interface MobileFoodFacilityExportService {
    Flux<MobileFoodFacility> findAllByFacilityTypeAndApplicant(String facilityType);
}

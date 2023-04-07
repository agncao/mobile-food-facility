package com.jm.resume.food.facility.service.impl;

import com.jm.resume.food.facility.domain.MobileFoodFacility;
import com.jm.resume.food.facility.repository.MobileFoodFacilityPgRepository;
import com.jm.resume.food.facility.service.MobileFoodFacilityExportService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

@Service
public class MobileFoodFacilityExportServiceImpl implements MobileFoodFacilityExportService {
    @Resource
    private MobileFoodFacilityPgRepository mobileFoodFacilityPgRepository;

    @Override
    public Flux<MobileFoodFacility> findAllByFacilityTypeAndApplicant(String facilityType) {
        return mobileFoodFacilityPgRepository.findAllByFacilityType(facilityType);
    }
}

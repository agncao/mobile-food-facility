package com.jm.resume.food.facility.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(MobileFoodFacility.TABLE_NAME)
public class MobileFoodFacility {
    public static final String TABLE_NAME = "mobile_food_facility";

    @Column("id")  @Id private Long id;
    @Column("location_id") private Integer locationId;
    @Column("applicant") private String applicant;
    @Column("facility_type") private String facilityType;
    @Column("cnn") private String cnn;
    @Column("location_description") private String locationDescription;
    @Column("address") private String address;
    @Column("block_lot") private String blockLot;
    @Column("block") private String block;
    @Column("lot") private String lot;
    @Column("permit") private String permit;
    @Column("status") private String status;
    @Column("food_items") private String foodItems;
    @Column("x") private String x;
    @Column("y") private String y;
    @Column("latitude") private String latitude;
    @Column("longitude") private String longitude;
    @Column("schedule") private String schedule;
    @Column("days_hours") private String daysHours;
    @Column("noi_sent") private String noiSent;
    @Column("approved") private String approved;
    @Column("received") private String received;
    @Column("prior_permit") private String priorPermit;
    @Column("expiration_date") private String expirationDate;
    @Column("location") private String location;
    @Column("police_districts") private String firePreventionDistricts;
    @Column("police_districts") private String policeDistricts;
    @Column("supervisor_districts") private String supervisorDistricts;
    @Column("zip_codes") private String zipCodes;
    @Column("neighborhoods") private String neighborhoods;
}

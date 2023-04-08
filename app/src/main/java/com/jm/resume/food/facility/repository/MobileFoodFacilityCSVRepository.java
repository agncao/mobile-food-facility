package com.jm.resume.food.facility.repository;

import com.jm.resume.food.facility.domain.MobileFoodFacility;
import com.jm.resume.food.facility.utils.ClassPathFileReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class MobileFoodFacilityCSVRepository {
    // 从 classpath 下的 csv 文件中读取数据, 并且打印出数据所在cvs中的行数
    public static List<MobileFoodFacility> readCSV() {
        List<MobileFoodFacility> mobileFoodFacilities = new ArrayList<>();
        try(Reader reader = new InputStreamReader(ClassPathFileReader.getCsvFile("Mobile_Food_Facility_Permit.csv"))) {

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord csvRecord : csvParser) {
//                System.out.println(csvRecord.getRecordNumber());//打印本行行数

                MobileFoodFacility mobileFoodFacility = new MobileFoodFacility();
                mobileFoodFacility.setLocationId(Integer.parseInt(csvRecord.get(0)));
                mobileFoodFacility.setApplicant(csvRecord.get("Applicant"));
                mobileFoodFacility.setFacilityType(csvRecord.get("FacilityType"));
                mobileFoodFacility.setCnn(csvRecord.get("cnn"));
                mobileFoodFacility.setLocationDescription(csvRecord.get("LocationDescription"));
                mobileFoodFacility.setAddress(csvRecord.get("Address"));
                mobileFoodFacility.setBlockLot(csvRecord.get("blocklot"));
                mobileFoodFacility.setBlock(csvRecord.get("block"));
                mobileFoodFacility.setLot(csvRecord.get("lot"));
                mobileFoodFacility.setPermit(csvRecord.get("permit"));
                mobileFoodFacility.setStatus(csvRecord.get("Status"));
                mobileFoodFacility.setFoodItems(csvRecord.get("FoodItems"));
                mobileFoodFacility.setX(csvRecord.get("X"));
                mobileFoodFacility.setY(csvRecord.get("Y"));
                mobileFoodFacility.setLatitude(csvRecord.get("Latitude"));
                mobileFoodFacility.setLongitude(csvRecord.get("Longitude"));
                mobileFoodFacility.setSchedule(csvRecord.get("Schedule"));
                mobileFoodFacility.setDaysHours(csvRecord.get("dayshours"));
                mobileFoodFacility.setNoiSent(csvRecord.get("NOISent"));
                mobileFoodFacility.setApproved(csvRecord.get("Approved"));
                mobileFoodFacility.setReceived(csvRecord.get("Received"));
                mobileFoodFacility.setPriorPermit(csvRecord.get("PriorPermit"));
                mobileFoodFacility.setExpirationDate(csvRecord.get("ExpirationDate"));
                mobileFoodFacility.setLocation(csvRecord.get("Location"));
                mobileFoodFacility.setFirePreventionDistricts(csvRecord.get("Fire Prevention Districts"));
                mobileFoodFacility.setPoliceDistricts(csvRecord.get("Police Districts"));
                mobileFoodFacility.setSupervisorDistricts(csvRecord.get("Supervisor Districts"));
                mobileFoodFacility.setZipCodes(csvRecord.get("Zip Codes"));
                mobileFoodFacility.setNeighborhoods(csvRecord.get("Neighborhoods (old)"));
                mobileFoodFacilities.add(mobileFoodFacility);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return mobileFoodFacilities;
    }
}

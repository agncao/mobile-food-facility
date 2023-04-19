package com.jm.resume.food.facility.carevoice.entity;

import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class MeetingRequest {
    private Date requestTime;
    private String employeeId;
    private Date meetingStartTime;
    private int duration;

    private Date requestOfficeStartTime;
    private Date requestOfficeEndTime;
    private Date meetingOfficeStartTime;
    private Date meetingOfficeEndTime;

    /**
     * meeting date string format: yyyy-MM-dd
     * @return
     */
    public String getMeetingDateStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(meetingStartTime);
    }

    /**
     * meeting start time string format: HH:mm
     * @return
     */
    public String getMeetingStartTimeStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(meetingStartTime);
    }

    /**
     * meeting end time string format: HH:mm
     * @return
     */
    public String getMeetingEndTimeStr(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(meetingStartTime);
        calendar.add(Calendar.HOUR, duration);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(calendar.getTime());
    }

    /**
     * meeting start and end time string format: HH:mm HH:mm
     * @return
     */
    public String getMeetingDuration(){
        return getMeetingStartTimeStr() +" " + getMeetingEndTimeStr();
    }

    public String getRequestTimeStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(requestTime);
    }

    public static MeetingRequest instance(Date requestTime, String employeeId, Date meetingStartTime, int duration,
                                          Date requestOfficeStartTime, Date requestOfficeEndTime,
                                          Date meetingOfficeStartTime, Date meetingOfficeEndTime) {
        return MeetingRequest.builder()
                .requestTime(requestTime)
                .employeeId(employeeId)
                .meetingStartTime(meetingStartTime)
                .duration(duration)
                .requestOfficeStartTime(requestOfficeStartTime)
                .requestOfficeEndTime(requestOfficeEndTime)
                .meetingOfficeStartTime(meetingOfficeStartTime)
                .meetingOfficeEndTime(meetingOfficeEndTime)
                .build();
    }

    /**
     * validate user's input and build meeting request object
     * @param officeDuration
     * @param line1
     * @param line2
     * @return
     */
    public static MeetingRequest instance(OfficeDuration officeDuration, String line1, String line2){
        // parse the request time and employee
        String [] line1Array = line1.trim().split(" ");
        if(line1Array.length != 3){
            throw new IllegalArgumentException("Invalid input format");
        }
        String dateStr = line1Array[0];
        String timeStr = line1Array[1];
        Date requestTime = OfficeDateUtils.parseTime(dateStr + " " + timeStr);
        String employeeId = line1Array[2];

        //request time must be in office time
        List<Date> requestOfficeTimes = officeDuration.getOfficeDateTimes(requestTime);
        if(null!=officeDuration){
            if(requestTime.before(requestOfficeTimes.get(0)) || requestTime.after(requestOfficeTimes.get(1))){
                throw new IllegalArgumentException("request time must be in office time");
            }
        }

        // parse the meeting time, including the date and duration
        String [] line2Array = line2.trim().split(" ");
        if(line2Array.length != 3){
            throw new IllegalArgumentException("Invalid input format");
        }
        String meetingDateStr = line2Array[0];
        String meetingTimeStr = line2Array[1];
        Date meetingTime = OfficeDateUtils.parseTime(meetingDateStr + " " + meetingTimeStr);

        //meeting time must be after request time, and must be a work day
        if(requestTime.after(meetingTime) || !OfficeDateUtils.isWorkDay(meetingTime)){
            throw new IllegalArgumentException(String.format("employeeId:%s meeting time must be after request time, and must be a work day", employeeId));
        }

        int duration = parserDuration(line2Array[2]);
        //both meeting start time and end time must be in office time
        List<Date> meetingOfficeTimes = officeDuration.getOfficeDateTimes(meetingTime);
        if(null!=officeDuration){
            if(meetingTime.before(meetingOfficeTimes.get(0)) || meetingTime.after(meetingOfficeTimes.get(1))){
                throw new IllegalArgumentException(String.format("employeeId:%s both meeting time-start and time-end must be in office time", employeeId));
            }

            if(OfficeDateUtils.addHours(meetingTime, duration).after(meetingOfficeTimes.get(1))){
                throw new IllegalArgumentException(String.format("employeeId:%s both meeting time-start and time-end must be in office time", employeeId));
            }
        }

        return instance(requestTime, employeeId, meetingTime, duration,
                requestOfficeTimes.get(0), requestOfficeTimes.get(1),
                meetingOfficeTimes.get(0), meetingOfficeTimes.get(1)
        );
    }

    private static int parserDuration(String durationStr){
        try {
            return Integer.parseInt(durationStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid duration format: " + durationStr, e);
        }
    }

}

package com.jm.resume.food.facility.carevoice.entity;

import lombok.Builder;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        Date requestTime = parseTime(dateStr + " " + timeStr);
        String employeeId = line1Array[2];

        Date requestOfficeStartTime = parseTime(dateStr + " " + officeDuration.getOfficeStartTime());
        Date requestOfficeEndTime = parseTime(dateStr + " " + officeDuration.getOfficeEndTime());
        if(null!=officeDuration){
            if(requestTime.before(requestOfficeStartTime) || requestTime.after(requestOfficeEndTime)){
                throw new IllegalArgumentException("Invalid request time");
            }
        }

        // parse the meeting time, including the date and duration
        String [] line2Array = line2.trim().split(" ");
        if(line2Array.length != 3){
            throw new IllegalArgumentException("Invalid input format");
        }
        String meetingDateStr = line2Array[0];
        String meetingTimeStr = line2Array[1];
        Date meetingReserveTime = parseTime(meetingDateStr + " " + meetingTimeStr);

        //meeting time must be after request time, and must be a work day
        if(requestTime.after(meetingReserveTime) && !isWorkDay(meetingReserveTime)){
            throw new IllegalArgumentException("Invalid meeting time");
        }

        //meeting time must be in office time
        Date meetingOfficeStartTime = parseTime(meetingDateStr + " " + officeDuration.getOfficeStartTime());
        Date meetingOfficeEndTime = parseTime(meetingDateStr + " " + officeDuration.getOfficeEndTime());
        if (meetingReserveTime.before(meetingOfficeStartTime) || meetingReserveTime.after(meetingOfficeEndTime)) {
            throw new IllegalArgumentException(String.format("line2:%s include a invalidate meeting time", line2));
        }
        int duration = parserDuration(line2Array[2]);
        return instance(requestTime, employeeId, meetingReserveTime, duration,requestOfficeStartTime,requestOfficeEndTime,meetingOfficeStartTime,meetingOfficeEndTime);
    }

    public static Date parseTime(String dateTimeStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dateFormat.setLenient(false);
            Date date = dateFormat.parse(dateTimeStr);
            return date;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid time format: " + dateTimeStr, e);
        }
    }

    private static int parserDuration(String durationStr){
        try {
            return Integer.parseInt(durationStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid duration format: " + durationStr, e);
        }
    }

    public static boolean isWorkDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY;
    }

}

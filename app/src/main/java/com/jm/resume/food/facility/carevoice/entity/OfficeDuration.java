package com.jm.resume.food.facility.carevoice.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

import static com.jm.resume.food.facility.carevoice.entity.OfficeDateUtils.parseTime;

@Data
public class OfficeDuration {
    private static final String TIME_PATTERN = "^([01]\\d|2[0-3])([0-5]\\d)\\s+([01]\\d|2[0-3])([0-5]\\d)$";
    private String officeStartTime;
    private String officeEndTime;

    /**
     * Create office duration instance
     * @param officeHours
     * @return
     */
    public static OfficeDuration instance(String officeHours) {
        validate(officeHours);
        String durations[] = officeHours.split(" ");
        String officeStartTime = durations[0].substring(0, 2) + ":" + durations[0].substring(2);
        String officeEndTime = durations[1].substring(0, 2) + ":" + durations[1].substring(2);
        OfficeDuration officeDuration = new OfficeDuration();
        officeDuration.setOfficeStartTime(officeStartTime);
        officeDuration.setOfficeEndTime(officeEndTime);
        return officeDuration;
    }

    /**
     * Validate office hours format
     * @param officeHours
     * @throws IllegalArgumentException
     */
    public static void validate(String officeHours) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile(TIME_PATTERN);
        Matcher matcher = pattern.matcher(officeHours);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid time format, must be [hhmm hhmm]");
        }

        int startHour = Integer.parseInt(matcher.group(1));
        int startMinute = Integer.parseInt(matcher.group(2));
        int endHour = Integer.parseInt(matcher.group(3));
        int endMinute = Integer.parseInt(matcher.group(4));

        if (startHour > endHour || (startHour == endHour && startMinute >= endMinute)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }

    public List<Date> getOfficeDateTimes(Date date) {
        List<Date> result = new ArrayList<>();

        //set the office start time of date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(officeStartTime.substring(0, 2)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(officeStartTime.substring(3)));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date officeStartTime = calendar.getTime();
        result.add(officeStartTime);

        //set the office end time of date
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(officeEndTime.substring(0, 2)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(officeEndTime.substring(3)));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date officeEndTime = calendar.getTime();
        result.add(officeEndTime);

        return result;
    }

}

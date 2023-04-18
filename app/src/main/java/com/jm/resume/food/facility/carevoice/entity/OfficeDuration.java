package com.jm.resume.food.facility.carevoice.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;

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
}

package com.jm.resume.food.facility.carevoice;

import com.jm.resume.food.facility.carevoice.entity.MeetingRequest;
import com.jm.resume.food.facility.carevoice.entity.OfficeDuration;

import java.util.*;
import java.util.stream.Collectors;

public abstract class MeetingRoomReserveTest {

        public static void main(String[] args) {
            // 读取开始时间和结束时间(格式是[hh:mm hh:mm])，还有预约请求
            System.out.println("Please input office start time, office end time in same line, input format like : [hhmm hhmm])");
            Scanner scanner = new Scanner(System.in);
            String officeDurationStr = scanner.nextLine().trim();
            if(officeDurationStr.toLowerCase().contains("input")){
                officeDurationStr = scanner.nextLine();
            }
            OfficeDuration officeDuration = OfficeDuration.instance(officeDurationStr);

            // 读取预约请求
            System.out.println();
            System.out.println("Please input meeting requests, if you want to end input, type the key 'Enter' 2 times'");
            List<MeetingRequest> meetingRequests = new ArrayList<>();
            while (scanner.hasNextLine() ) {
                String meetingRequestStr = scanner.nextLine().trim();
                if (meetingRequestStr.isEmpty()) {
                    break;
                }
                String meetingStartStr = scanner.nextLine().trim();
                MeetingRequest meetingRequest = MeetingRequest.instance(officeDuration, meetingRequestStr, meetingStartStr);
                meetingRequests.add(meetingRequest);
            }

            scanner.close();
//            System.out.println(String.format("you have input %s meeting request\n", meetingRequests.size()));

            //check the meeting request list contains the same employee id and request time
            Set<String> meetingRequestSet = new HashSet<>();
            meetingRequests.forEach(meetingRequest -> {
                String meetingRequestStr = String.format("%s::%s", meetingRequest.getEmployeeId(), meetingRequest.getRequestTimeStr());
                if (meetingRequestSet.contains(meetingRequestStr)) {
                    throw new RuntimeException("预订提交系统一次只允许一个提交，所以提交时间保证是唯一的");
                }
                meetingRequestSet.add(meetingRequestStr);
            });

            // group by the date of meeting request
            Map<String, List<MeetingRequest>> meetingRequestMap = meetingRequests.stream().collect(
                    Collectors.groupingBy(MeetingRequest::getMeetingDateStr)
            );
            // sort the meeting request by the start time
            meetingRequestMap.forEach((k, v) -> v.sort(Comparator.comparing(MeetingRequest::getMeetingStartTime)));
            // print the meeting request

            System.out.println("Output:");
            meetingRequestMap.forEach((k, v) -> {
                System.out.println(k);
                // if there are multiple employees who have booked the meeting room at the same time,
                // throw an exception
                Set<String> meetingDurations = new HashSet<>();
                v.forEach(meetingRequest -> {
                    if (!meetingDurations.contains(meetingRequest.getMeetingDuration())) {
                        meetingDurations.add(meetingRequest.getMeetingDuration());
                        System.out.println(String.format("%s %s %s", meetingRequest.getEmployeeId(), meetingRequest.getMeetingStartTimeStr(), meetingRequest.getMeetingEndTimeStr()));
                    }
                });
            });
        }


}

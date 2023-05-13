package com.hhb.trainbookingsystem.controller.restController;

import com.hhb.trainbookingsystem.entity.RoutelineEntity;
import com.hhb.trainbookingsystem.entity.searchResult.SearchTrip;
import com.hhb.trainbookingsystem.entity.TripEntity;

import com.hhb.trainbookingsystem.service.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/index")
public class IndexRestController {
    public final StationsService stationsService;
    public final RoutelineService routelineService;
    public final TicketManagerService ticketManagerService;
    public final UserOrderService userOrderService;
    public final TrainService trainService;
    public final TripService tripService;
    public final FareService fareService;
    public final SeatService seatService;
    //获取时间差

    public IndexRestController(StationsService stationsService, RoutelineService routelineService,
                               TicketManagerService ticketManagerService, UserOrderService userOrderService,
                               TrainService trainService, TripService tripService, FareService fareService,
                               SeatService seatService) {
        this.stationsService = stationsService;
        this.routelineService = routelineService;
        this.ticketManagerService = ticketManagerService;
        this.userOrderService = userOrderService;
        this.trainService = trainService;
        this.tripService = tripService;
        this.fareService = fareService;
        this.seatService = seatService;
    }

    public static String getDistanceTime(long time1, long time2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;

        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
        if (hour != 0) return hour + "小时" + min + "分钟" + sec + "秒";
        if (min != 0) return min + "分钟" + sec + "秒";
        if (sec != 0) return sec + "秒";
        return "0秒";
    }

    //返回用户搜索信息
    @PostMapping("/getTrips")
    // @CachePut(value = "getTrips" ,key = "#start+'-'+#end+'-'+#time")
    public Map<String, Object> getTrips(@RequestParam(value = "start", required = false) String start,
                                        @RequestParam(value = "end", required = false) String end,
                                        @RequestParam(value = "time", required = false) String time) {
        System.out.println(start);
        System.out.println(end);
        System.out.println(time);
        List<TripEntity> routeTrips = new ArrayList<>();
        //路线匹配
        List<RoutelineEntity> routeLists = routelineService.findRouteEntitiesByStations(start, end);
        System.out.println(routeLists);
        for (RoutelineEntity routelineEntity : routeLists) {
            int tripId = routelineEntity.getTripId();
            System.out.println(tripId);
            //时间匹配
            Timestamp timestamp = stationsService.getStationTimeByTripIdAndStation(start, tripId);
            System.out.println(timestamp);
            if (timestamp != null) {
                String timestampString = String.valueOf(timestamp);
                System.out.println("hello1");
                System.out.println(timestampString);
                System.out.println("hello2");
                System.out.println((timestampString.substring(0, 10)));
                if ((timestampString.substring(0, 10)).equals(time)) {
                    routeTrips.add(tripService.findTripEntityById(tripId));
                    //  System.out.println((timestampString.substring(0,10)));
                    // System.out.println(tripService.findTripEntityById(tripId));
                }
            }
        }
        List<SearchTrip> searchTrips = new ArrayList<>();
        for (TripEntity tripEntity : routeTrips) {
            //当前车次
            SearchTrip searchTrip = new SearchTrip();
            searchTrip.setStartStation(start);
            searchTrip.setEndStation(end);
            int tripId = tripEntity.getId();
            System.out.println("searchTrip.tripId = " + tripId);
            searchTrip.setTripId(tripId);
            searchTrip.setTripNumber(tripService.findTripEntityById(tripId).getTrainNumber());
            System.out.println("searchTrip.tripNumber = " + tripService.findTripEntityById(tripId).getTrainNumber());
            //时间表找出发时间
            Timestamp startTime = stationsService.getStationTimeByTripIdAndStation(start, tripId);
            searchTrip.setStartTime(String.valueOf(startTime));
            //时间表找到达时间
            Timestamp endTime = stationsService.getStationTimeByTripIdAndStation(end, tripId);
            System.out.println("endTime = " + endTime);
            searchTrip.setEndTime(String.valueOf(endTime));
            String distanceTime = getDistanceTime(startTime.getTime(), endTime.getTime());
            System.out.println("distanceTime = " + distanceTime);
            searchTrip.setSpendTime(distanceTime);
            //费用表找到费用
            searchTrip.setFareFirst(String.valueOf(fareService.getFareByStationsAndTripId(start, end, "1", tripId)));
            searchTrip.setFareSecond(String.valueOf(fareService.getFareByStationsAndTripId(start, end, "2", tripId)));
            //获取总路线
            //获取用户经过路线
            String rout = routelineService.findRoutelineEntityByTripId(tripId).getRouteLine();
            String[] TripRoute = rout.split("-");
            System.out.println(TripRoute.length);
            String myRout = "";
            for (int i = 0; i < TripRoute.length - 1; ++i) {
                if (start.equals(TripRoute[i])) {
                    myRout = myRout.concat(start).concat("-");
                    i = i + 1;
                    while (!end.equals(TripRoute[i])) {
                        myRout = myRout.concat(TripRoute[i]).concat("-");
                        i = i + 1;
                    }
                    myRout = myRout.concat(end);
                }
            }
            System.out.println("myRout = " + myRout);
            searchTrip.setRouteLine(myRout);
            //根据用户经过路线找座位
            String[] MyRoute = myRout.split("-");
            //初始化座位序列
            int trainId = tripEntity.getTrainId();
            String numberOfSeat = trainService.findSeatInfoById(trainId);
            String[] NumberOfSeat = numberOfSeat.split("-");
            //一等座座位数
            int seatFirst = Integer.parseInt(NumberOfSeat[0]);
            //二等座座位数
            int seatSecond = Integer.parseInt(NumberOfSeat[1]);
            //总座位数
            int seatNumber = seatFirst + seatSecond;
            String seatInitial = "";
            for (int m = 0; m < seatNumber; ++m) {
                seatInitial = seatInitial.concat("1");
            }
            //初始化座位
            for (int j = 0; j < MyRoute.length - 1; ++j) {
                String last = "";
                String startFirst = MyRoute[j];
                String endNext = MyRoute[j + 1];
                //查找每个二维组的座位并并起来
                String seatInfo = seatService.getSeatByStartEndTripId(startFirst, endNext, tripId);
                //    System.out.println(seatInfo);
                for (int n = 0; n < seatInfo.length(); ++n) {
                    if (j == 0) {
                        int x = (Integer.valueOf(seatInitial.charAt(n) - 48) & Integer.valueOf(seatInfo.charAt(n) - 48));
                        last = last.concat(String.valueOf(x));
                    } else {
                        int x = (Integer.valueOf(seatInitial.charAt(n) - 48) | Integer.valueOf(seatInfo.charAt(n) - 48));
                        last = last.concat(String.valueOf(x));
                    }
                }
                seatInitial = last;
                //   System.out.println(seatInitial);
            }
            String seatInfoFirst = seatInitial.substring(0, seatFirst);
            String seatInfoSecond = seatInitial.substring(seatFirst, seatFirst + seatSecond);
            int seatFirstRemain = 0;
            int seatSecondRemain = 0;
            // System.out.println(seatInfoFirst);
            // System.out.println(seatInfoSecond);
            for (int i = 0; i < seatFirst; ++i) {
                if ((seatInfoFirst.charAt(i)) == '0') {
                    seatFirstRemain += 1;
                }
            }
            for (int j = 0; j < seatSecond; ++j) {
                if ((seatInfoSecond.charAt(j)) == '0') {
                    seatSecondRemain += 1;
                }
            }
            searchTrip.setSeatFirstRemain(seatFirstRemain);
            searchTrip.setSeatSecondRemain(seatSecondRemain);
            searchTrip.setStatus(String.valueOf(tripService.findStatusById(trainId)));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String tripTime = String.valueOf(stationsService.getStationTimeByTripIdAndStation(start, tripId));
            String nowTime = df.format(new Date());
            boolean flag = isDateBefore(tripTime, nowTime);
            if (flag == false) {
                System.out.println(String.valueOf(tripService.findStatusById(tripId)));
                searchTrips.add(searchTrip);
//                if (String.valueOf(tripService.findStatusById(tripId)).equals("1")) {
//
//                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("searchTrips", searchTrips);
        map.put("sum", searchTrips.size());
        return map;
    }

    public static boolean isDateBefore(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).before(df.parse(date2));
        } catch (ParseException e) {
            System.out.print("[SYS] " + e.getMessage());
            return false;
        }
    }
}

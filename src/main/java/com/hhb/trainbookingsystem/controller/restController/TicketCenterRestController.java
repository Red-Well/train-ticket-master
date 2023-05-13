/**
 * 内容：售票端的数据请求处理
 */
package com.hhb.trainbookingsystem.controller.restController;


import com.hhb.trainbookingsystem.entity.logResult.TripBestEntity;
import com.hhb.trainbookingsystem.entity.logResult.TripWorstEntity;


import com.hhb.trainbookingsystem.entity.*;
import com.hhb.trainbookingsystem.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Api(tags = "票务端相关接口", description = "票务端相关API")
@RestController
@RequestMapping("/api/ticketCenter")
@CrossOrigin
public class TicketCenterRestController {
    public final TripBestService tripBestService;
    private final StringRedisTemplate stringRedisTemplate;
    public final StationsService stationsService;
    public final RoutelineService routelineService;
    public final TicketManagerService ticketManagerService;
    public final UserOrderService userOrderService;
    public final TrainService trainService;
    public final TripService tripService;
    public final FareService fareService;
    public final SeatService seatService;
    public final TripWorstService tripWorstService;

    public TicketCenterRestController(TripBestService tripBestService, TicketManagerService ticketManagerService, UserOrderService userOrderService,
                                      TrainService trainService, TripService tripService,
                                      RoutelineService routelineService, StationsService stationsService,
                                      FareService fareService, SeatService seatService, StringRedisTemplate stringRedisTemplate, TripWorstService tripWorstService) {
        this.tripBestService = tripBestService;
        this.ticketManagerService = ticketManagerService;
        this.userOrderService = userOrderService;
        this.trainService = trainService;
        this.tripService = tripService;
        this.routelineService = routelineService;
        this.stationsService = stationsService;
        this.fareService = fareService;
        this.seatService = seatService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.tripWorstService = tripWorstService;
    }

    @ApiIgnore
    @RequestMapping("cleanRedis")
    public Map<String, Object> cleanRedis() {
        Map<String, Object> map = new HashMap<>();
        try {
            // 获取所有key
            Set<String> keys = stringRedisTemplate.keys("*");
            assert keys != null;
            // 迭代
            Iterator<String> it1 = keys.iterator();
            while (it1.hasNext()) {
                // 循环删除
                stringRedisTemplate.delete(it1.next());
            }
            map.put("code", 1);
            map.put("msg", "清理全局缓存成功");
            return map;
        } catch (Exception e) {
            map.put("code", -1);
            map.put("msg", "清理全局缓存失败");
            return map;
        }
    }

    //更新票务管理员
    @ApiOperation("更新票务管理员")
    @PostMapping("/ticketManagerUpdate")
    public TicketManagerEntity ticketManagerUpdate(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "password", required = false)

    String password, @RequestParam(value = "id", required = false) String id) {
        return ticketManagerService.updateTicketManagerById2(name, password, Integer.parseInt(id));

    }

    //获取列车
    @ApiOperation("查看车次")
    @GetMapping("/trains")
    public List<TrainEntity> trains() {
        return trainService.findAll();
    }

    //获取列车 非缓存
    @ApiOperation("非缓存查看车次")
    @GetMapping("/newTrains")
    public List<TrainEntity> newTrains() {
        return trainService.findAllNew();
    }

    //获取订单
    @ApiOperation("查看订单")
    @GetMapping("/userOrders")
    public List<UserOrderEntity> userOrders() {
        return userOrderService.findAll();
    }

    //获取订单
    @ApiOperation("查看订单车次")
    @GetMapping("/getBestTrips")
    public List<TripBestEntity> getBestTrips() {
        return tripBestService.findAll();
    }

    @ApiOperation("查看退订改签车次")
    @GetMapping("/getWorstTrips")
    public List<TripWorstEntity> getWorstTrips() {
        return tripWorstService.findAll();
    }

    //获取订单 非缓存
    @ApiOperation("查看订单非缓存")
    @GetMapping("/userOrdersNew")
    public List<UserOrderEntity> userOrdersNew() {
        return userOrderService.findAllNew();
    }

    //查找对应的订单
    @ApiOperation("查找订单")
    @PostMapping("/findOrderByOrderId")
    public UserOrderEntity findOrderByOrderId(@RequestParam(value = "id", required = false) String id) {
        UserOrderEntity u = userOrderService.findUserOrderEntityById(Integer.parseInt(id));
        return u;
    }

    //修改订单
    @ApiOperation("编辑订单")
    @PostMapping("/editOrder")
    public UserOrderEntity editOrder(@RequestParam(value = "condition", required = false) String condition, @RequestParam("id") int id) {
        System.out.println(condition);
        return userOrderService.updateUserOrderEntityById1(condition, id);
    }

    //删除订单
    @ApiOperation("删除订单")
    @DeleteMapping("/deleteOrder/{id}")
    public void deleteOrder(@PathVariable int id) {
        userOrderService.deleteUserOrderEntityById(id);
    }

    //增加列车
    @ApiOperation("新增列车")
    @PostMapping("/addTrain")
    public TrainEntity addTrain(@RequestParam(value = "trainType", required = false) String trainType,
                                @RequestParam(value = "seatInfo", required = false) String seatInfo) {
        TrainEntity trainEntity = new TrainEntity();
        trainEntity.setSeatInfo(seatInfo);
        trainEntity.setTrainType(trainType);
        return trainService.save(trainEntity);
    }

    //修改列车信息
    @ApiOperation("修改列车信息")
    @PostMapping("/editTrain")
    public TrainEntity editTrain(@RequestParam(value = "trainType", required = false) String trainType,
                                 @RequestParam(value = "seatInfo", required = false) String seatInfo,
                                 @RequestParam("id") int id) {
        trainService.updateTrainEntityById(trainType, seatInfo, id);
        return trainService.findTrainEntityById(id);
    }

    //删除列车
    @ApiOperation("删除列车")
    @DeleteMapping("/deleteTrain/{id}")
    public void deleteTrain(@PathVariable int id) {
        trainService.deleteTrainEntityById(id);
    }

    //增加车次
    @ApiOperation("增加车次")
    @PostMapping("/addTrip")
    public TripEntity addTrip(@RequestParam(value = "de_time", required = false) String de_time,
                              @RequestParam(value = "start_station", required = false) String start_station,
                              @RequestParam(value = "end_station", required = false) String end_station,
                              @RequestParam(value = "train_number", required = false) String train_number,
                              @RequestParam(value = "train_status", required = false) String train_status,
                              @RequestParam(value = "train_id", required = false) int train_id) {
        TripEntity tripEntity = new TripEntity();
        tripEntity.setRemainseatInfo(trainService.findTrainEntityById(train_id).getSeatInfo());
        tripEntity.setTrainId(train_id);
        tripEntity.setTrainByTrainId(trainService.findTrainEntityById(train_id));
        tripEntity.setStartStation(start_station);
        tripEntity.setEndStation(end_station);
        tripEntity.setTrainNumber(train_number);
        tripEntity.setTripStatus(Byte.valueOf(train_status));
        tripEntity.setDepartureTime(Timestamp.valueOf(de_time));
        tripEntity.setRemainseatInfo(trainService.findTrainEntityById(train_id).getSeatInfo());
        tripService.save(tripEntity);
        return tripEntity;

    }

    //查看车次
    @ApiOperation("新增车次")
    @GetMapping("/trips")
    public List<TripEntity> seeTrips() {
        return tripService.findAll();
    }

    @ApiOperation("新增车次非缓存")
    @GetMapping("/newTrips")
    public List<TripEntity> seeNewTrips() {
        return tripService.findAllNew();
    }

    //编辑车次
    @ApiOperation("编辑车次")
    @PostMapping("/editTrip")
    public TrainEntity editTrip(@RequestParam(value = "edit_number", required = false) String edit_number,
                                @RequestParam(value = "edit_time", required = false) String edit_time,
                                @RequestParam(value = "edit_status", required = false) String edit_status,
                                @RequestParam("id") int id) {
        tripService.updateTripEntityById(edit_number, Timestamp.valueOf(edit_time), Byte.valueOf(edit_status), id);
        System.out.println("修改");
        return trainService.findTrainEntityById(id);
    }

    //编号搜索车次
    @ApiOperation("按照编号搜索车次")
    @PostMapping("/searchTripsByNumber")
    List<TripEntity> searchTripsByNumber(@RequestParam(value = "number", required = false) String number) {
        System.out.println(tripService.findTripEntitiesByTrainNumberContaining(number));
        return tripService.findTripEntitiesByTrainNumberContaining(number);
    }

    //状态搜索车次
    @ApiOperation("按照状态搜索车次")
    @PostMapping("/searchTripsByStatus")
    List<TripEntity> searchTripsByStatus(@RequestParam(value = "status", required = false) String status) {
        return tripService.findTripEntitiesByTripStatus(Byte.valueOf(status));
    }

    //查看路线
    @ApiOperation("查看路线")
    @GetMapping("/seeRoute/{id}")
    public RoutelineEntity seeRoute(@PathVariable int id) {
        RoutelineEntity routelineEntity = new RoutelineEntity();
        routelineEntity = routelineService.findRoutelineEntityByTripId(id);
        return routelineEntity;
    }

    //查看站点
    @ApiOperation("查看站点")
    @GetMapping("/seeStations/{id}")
    @ResponseBody
    public List<StationsEntity> seeStations(@PathVariable int id) {
        return stationsService.findStationsEntitiesByTripId(id);
    }

    //查看站点非缓存
    @ApiOperation("查看站点非缓存")
    @GetMapping("/seeNewStations/{id}")
    @ResponseBody
    public List<StationsEntity> seeNewStations(@PathVariable int id) {
        return stationsService.newFindStationsEntitiesByTripId(id);
    }

    //查看票价
    @ApiOperation("查看票价")
    @GetMapping("/seeFares/{id}")
    @ResponseBody
    public List<FareEntity> seeFares(@PathVariable int id) {
        return fareService.findFareEntitiesByTripId(id);
    }

    //编辑站点
    @ApiOperation("编辑站点")
    @PostMapping("/editStation")
    public void editStation(@RequestParam(value = "time", required = false) String time,
                            @RequestParam(value = "id", required = false) int id) {
        System.out.println(time);
        stationsService.updateStationEntityById(Timestamp.valueOf(time), id);
        System.out.println("ok");
    }

    //编辑票价
    @ApiOperation("编辑票价")
    @PostMapping("/editFare")
    public void editFare(@RequestParam(value = "price", required = false) String price,
                         @RequestParam(value = "id", required = false) int id) {
        BigDecimal bigDecimal = new BigDecimal(price);
        fareService.updateFareEntityById(bigDecimal, id);
        System.out.println("ok");
    }

    //增加站点
    @ApiOperation("增加站点")
    @PostMapping("/addStation")
    public void addStation(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "time", required = false) String time,
                           @RequestParam(value = "id") int id) {
        System.out.println("ss" + id);
        StationsEntity stationsEntity = new StationsEntity();
        stationsEntity.setStationName(name);
        stationsEntity.setArriveTime(Timestamp.valueOf(time));
        stationsEntity.setTripId(id);
        stationsEntity.setTripByTripId(tripService.findTripEntityById(id));
        stationsService.save(stationsEntity);
    }

    //增加费用
    @ApiOperation("增加票价")
    @PostMapping("/addFare")
    public void addFare(@RequestParam(value = "start", required = false) String start,
                        @RequestParam(value = "end", required = false) String end,
                        @RequestParam(value = "type", required = false) String type,
                        @RequestParam(value = "price", required = false) String price,
                        @RequestParam(value = "id", required = false) int id) {
        FareEntity fareEntity = new FareEntity();
        fareEntity.setStartStation(start);
        fareEntity.setEndStation(end);
        fareEntity.setSeatType(type);
        fareEntity.setPrice(new BigDecimal(price));
        fareEntity.setTripId(id);
        fareEntity.setTripByTripId(tripService.findTripEntityById(id));
        fareService.save(fareEntity);
    }

    //删除站点
    @ApiOperation("删除站点")
    @DeleteMapping("/deleteStation/{id}")
    public void deleteStation(@PathVariable int id) {
        stationsService.deleteStationsEntityById(id);
    }

    //删除票价
    @ApiOperation("删除票价")
    @DeleteMapping("/deleteFare/{id}")
    public void deleteFare(@PathVariable int id) {
        fareService.deleteFareEntityById(id);
    }

    //新增、编辑路线
    @ApiOperation("新增/编辑路线-》生成新站点+票价")
    @PostMapping("/editRoute")
    public RoutelineEntity editRoute(@RequestParam(value = "route", required = false) String route,
                                     @RequestParam(value = "id", required = false) int id) {
        RoutelineEntity routelineEntity = routelineService.findRoutelineEntityByTripId(id);
        String[] newCity = route.split("-");
        //路线不存在 新建路线以及对应的站点和票价
        if (routelineEntity == null) {
            RoutelineEntity routelineEntity1 = new RoutelineEntity();
            routelineEntity1.setRouteLine(route);
            routelineEntity1.setTripId(id);
            routelineEntity1.setTripByTripId(tripService.findTripEntityById(id));
            System.out.println("hello world");
            routelineService.save(routelineEntity1);
            System.out.println("hello");
            //add stations records
            for (int i = 0; i < newCity.length; ++i) {
                StationsEntity stationsEntity = new StationsEntity();
                stationsEntity.setStationName(newCity[i]);
                stationsEntity.setTripId(id);
                stationsEntity.setTripByTripId(tripService.findTripEntityById(id));
                stationsService.save(stationsEntity);
            }
            //add fare records
            for (int m = 0; m < newCity.length; ++m) {
                for (int n = m + 1; n < newCity.length; n++) {
                    //初始化票价
                    int price1 = 50 + 20 * (n - m);
                    int price2 = 30 + 20 * (n - m);
                    String price1S = price1 + "";
                    String price2S = price2 + "";

                    //一等票
                    FareEntity fareEntity1 = new FareEntity();
                    fareEntity1.setStartStation(newCity[m]);
                    fareEntity1.setEndStation(newCity[n]);
                    fareEntity1.setSeatType("1");
                    fareEntity1.setPrice(new BigDecimal(price1S));
                    fareEntity1.setTripId(id);
                    fareEntity1.setTripByTripId(tripService.findTripEntityById(id));
                    fareService.save(fareEntity1);

                    //二等票
                    FareEntity fareEntity2 = new FareEntity();
                    fareEntity2.setStartStation(newCity[m]);
                    fareEntity2.setEndStation(newCity[n]);
                    fareEntity2.setSeatType("2");
                    fareEntity2.setPrice(new BigDecimal(price2S));
                    fareEntity2.setTripId(id);
                    fareEntity2.setTripByTripId(tripService.findTripEntityById(id));
                    fareService.save(fareEntity2);
                }
            }
            //add seat records
            String seatinfo = tripService.findTripEntityById(id).getTrainByTrainId().getSeatInfo();
            String[] seatInfo = seatinfo.split("-");
            int seat1 = Integer.parseInt(seatInfo[0]);
            int seat2 = Integer.parseInt(seatInfo[1]);
            String seat1String = "";
            String seat2String = "";
            for (int a = 0; a < seat1; ++a) {
                seat1String = seat1String + "0";
            }
            for (int b = 0; b < seat2; ++b) {
                seat2String = seat2String + "0";
            }
            String seatString = seat1String + seat2String;
            for (int p = 0; p < newCity.length - 1; p++) {
                System.out.println(p);
                SeatEntity seatEntity = new SeatEntity();
                seatEntity.setFirstStation(newCity[p]);
                seatEntity.setNextStation(newCity[p + 1]);
                seatEntity.setSeatInfo(seatString);
                seatEntity.setTripId(id);
                seatEntity.setTripByTripId(tripService.findTripEntityById(id));
                seatService.save(seatEntity);
            }
            return routelineEntity1;
        }
        //路线存在 修改路线以及座位信息
        else {
            routelineService.updateRoutelineEntityById(route, routelineEntity.getId());
            seatService.deleteSeatEntitiesByTripId(id);
            //add seat records
            String seatinfo = tripService.findTripEntityById(id).getTrainByTrainId().getSeatInfo();
            System.out.println(seatinfo);
            String[] seatInfo = seatinfo.split("-");
            System.out.println(seatInfo[0]);
            System.out.println(seatInfo[1]);
            int seat1 = Integer.parseInt(seatInfo[0]);
            int seat2 = Integer.parseInt(seatInfo[1]);
            String seat1String = "";
            String seat2String = "";
            for (int a = 0; a < seat1; ++a) {
                seat1String = seat1String + "0";
            }
            for (int b = 0; b < seat2; ++b) {
                seat2String = seat2String + "0";
            }
            String seatString = seat1String + seat2String;
            byte[] bytes = seatString.getBytes();
            for (int p = 0; p < newCity.length - 1; p++) {
                SeatEntity seatEntity = new SeatEntity();
                seatEntity.setFirstStation(newCity[p]);
                seatEntity.setNextStation(newCity[p + 1]);
                seatEntity.setSeatInfo(seatString);
                seatEntity.setTripId(id);
                seatEntity.setTripByTripId(tripService.findTripEntityById(id));
                seatService.save(seatEntity);
            }
            return routelineEntity;
        }
    }


}



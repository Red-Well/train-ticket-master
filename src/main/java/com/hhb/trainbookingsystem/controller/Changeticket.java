package com.hhb.trainbookingsystem.controller;

import com.alibaba.fastjson.JSONObject;

import com.hhb.trainbookingsystem.entity.*;
import com.hhb.trainbookingsystem.entity.custom.Selectcontactor;


import com.hhb.trainbookingsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class Changeticket {
    @Autowired
    UserOrderService userOrderService;

    @Autowired
    StationsService stationsService;

    @Autowired
    TripService tripService;

    @Autowired
    ContactService contactService;

    @Autowired
    FareService fareService;

    @Autowired
    RoutelineService routelineService;

    @Autowired
    TrainService trainService;

    @Autowired
    SeatService seatService;

    @PostMapping ("/changeticket/orderlist")
    @ResponseBody
    public Map<String,Object> changeorderlist(@RequestParam("orderid") String orderid,
                               @RequestParam("tripid") int tripid,HttpSession session){
        OrdinaryUserEntity user=(OrdinaryUserEntity) session.getAttribute("user");
        //int tripid=Integer.parseInt(tripid1);
        Map<String,Object> map=new HashMap<>();
        UserOrderEntity userOrderEntity=userOrderService.findUserOrderEntityById(Integer.parseInt(orderid));
/*        TripEntity tripEntity=tripService.findTripEntityByTrainNumber(trainnum);
        int tripid=tripEntity.getId();*/
        String[] route=userOrderEntity.getRoutLine().split("-");
        Timestamp statrtime=stationsService.getStationTimeByTripIdAndStation(route[0],tripid);
        String startTime = String.valueOf(statrtime);
        Timestamp endtime=stationsService.getStationTimeByTripIdAndStation(route[route.length-1],tripid);
        String endTime  = String.valueOf(endtime);
        String[] namelist=userOrderEntity.getNameList().split(",");
        BigDecimal fare=fareService.getFareByStationsAndTripId(route[0],route[route.length-1],"1",tripid);
        BigDecimal price=new BigDecimal(0);
        List<Selectcontactor> selectcontactors=new ArrayList<>();
        for(int i=0;i<namelist.length;i++){
            Selectcontactor selectcontactor=new Selectcontactor();
            if(namelist[i].equals(user.getRealname())){
                selectcontactor.setPersonid(user.getPersonId());
            }
            else {
                ContactEntity contact=contactService.findbyname(namelist[i]);
                selectcontactor.setPersonid(contact.getPersonId());
            }
            price =price.add(fare);
            selectcontactor.setTripid(tripid);
            selectcontactor.setType("1");
            selectcontactor.setName(namelist[i]);
            selectcontactor.setPrice(fare);
            selectcontactors.add(selectcontactor);
        }
        map.put("starttime",startTime);
        map.put("endtime",endTime);
        map.put("startstation",route[0]);
        map.put("endstation",route[route.length-1]);
        map.put("peoplelist",selectcontactors);
        map.put("price",price);
        return map;
    }

    @PostMapping("/changeticket/selecttickettype")
    @ResponseBody
    public Map<String,Object> selecttickettype(@RequestBody Map<String,Object> data){
        Map<String,Object> map=new HashMap<>();
        BigDecimal totalprice=new BigDecimal(0);
        String tickettype=(String) data.get("index1");
        int indexofselect=(int)data.get("index2");
        String start=(String)data.get("start");
        String end=(String)data.get("end");
        String str=(String)data.get("selectcontactors");
        List<Selectcontactor> selectcontactor= JSONObject.parseArray(str,Selectcontactor.class);
        BigDecimal price=fareService.getFareByStationsAndTripId(start,end,tickettype,selectcontactor.get(0).getTripid());
        selectcontactor.get(indexofselect).setPrice(price);
        selectcontactor.get(indexofselect).setType(tickettype);
        for(int i=0;i<selectcontactor.size();i++){
            totalprice = totalprice.add(selectcontactor.get(i).getPrice());
        }
        map.put("total",totalprice);
        map.put("person",selectcontactor);
        return map;
    }

    @PostMapping("/changeticket/createchangeorder")
    @ResponseBody
    public String createchangeorder(@RequestBody Map<String,Object> data, HttpSession session){
        String tripid=(String)data.get("tripid");
        String start=(String)data.get("start");
        String end=(String)data.get("end");
        String str=(String)data.get("peoplelist");
        String orderid=(String)data.get("orderid");
        List<Selectcontactor> selectcontactor= JSONObject.parseArray(str,Selectcontactor.class);
       // userOrderService.addReturnLog(Integer.parseInt(tripid));
        OrdinaryUserEntity user=(OrdinaryUserEntity)session.getAttribute("user");
        TripEntity tripEntity=tripService.findTripEntityById(Integer.parseInt(tripid));
        String namelist="",seatlist="",myroute="",pricelist="",typelist="",seatNumList="";
        BigDecimal price=new BigDecimal(0);
        RoutelineEntity routelineEntity=routelineService.findRoutelineEntityByTripId(tripEntity.getId());
        if(user!=null) {
            UserOrderEntity userOrderEntity = new UserOrderEntity();
            String rou=routelineEntity.getRouteLine();
            String[] routeline=rou.split("-");
            int startindex=getindex(routeline,start);
            int endindex=getindex(routeline,end);
            for(int j=startindex;j<=endindex;j++){
                if(j==startindex){
                    myroute=myroute.concat(routeline[j]);
                }
                else{
                    myroute=myroute.concat("-").concat(routeline[j]);
                }
            }
            Timestamp time=new Timestamp(new Date().getTime());
            String [][]userSelect = new String[selectcontactor.size()][3];
            for (int i = 0; i < selectcontactor.size(); i++) {
                userSelect[i][0] = selectcontactor.get(i).getName();

                userSelect[i][1]=selectcontactor.get(i).getType();

            }

            String[][] result = getSeatsInfo(tripEntity.getId(),userSelect,myroute);
            for (int i = 0; i < selectcontactor.size(); i++) {

                if (i == 0) {
                    namelist = selectcontactor.get(i).getName();
                    pricelist=""+selectcontactor.get(i).getPrice();
                    price=selectcontactor.get(i).getPrice();
                    typelist=selectcontactor.get(i).getType();
                    seatlist =result[i][1];
                    seatNumList = result[i][2];
                    //seatlist="1-11";
                } else {
                    namelist = namelist + "," + selectcontactor.get(i).getName();
                    pricelist=""+pricelist+","+selectcontactor.get(i).getPrice();
                    price =price.add(selectcontactor.get(i).getPrice());
                    typelist=typelist+","+selectcontactor.get(i).getType();
                    //seatlist += ","+"1-11";
                    seatlist+=","+result[i][1];
                    seatNumList += "-"+result[i][2];
                }
            }
            returnticket(Integer.parseInt(orderid));
            //改签订单日志
            userOrderService.addUpdateLog(start,end,tripEntity.getTrainNumber());
            //改签订单日志
            userOrderService.addOrderLog(start,end,tripEntity.getTrainNumber());
//            userOrderService.addReturnLog(userOrderEntity.getTripId());
            userOrderEntity.setUserOrderCondition("1");
            userOrderEntity.setTripId(tripEntity.getId());
            userOrderEntity.setPrice(price);
            userOrderEntity.setOrdineryUserId(user.getId());
            userOrderEntity.setNameList(namelist);
            userOrderEntity.setSeatList(seatlist);
            userOrderEntity.setTripTime(time);
            userOrderEntity.setRoutLine(myroute);
            userOrderEntity.setTripNumber(tripEntity.getTrainNumber());
            userOrderEntity.setPricelist(pricelist);
            userOrderEntity.setTypelist(typelist);
            userOrderEntity.setSeatNumberList(seatNumList);
            userOrderService.save(userOrderEntity);

        }

        return "success";
    }

    private int getindex(String[] arr,String str){
        for(int i=0;i<arr.length;i++){
            if(arr[i].equals(str))
                return i;
        }
        return -1;
    }

    //获取座位信息
    public   String[][] getSeatsInfo(int tripId,String[][] userSelect,String myRoute){
        //获取座位数
        String result[][] = new String[userSelect.length][3];
        String numberOfSeat = trainService.findTrainEntityById(tripService.findTripEntityById(tripId).getTrainId()).getSeatInfo();
        String[] NumberOfSeat = numberOfSeat.split("-");
        //一等座座位数
        int seatFirst = Integer.parseInt(NumberOfSeat[0]);
        //二等座座位数
        int seatSecond = Integer.parseInt(NumberOfSeat[1]);
        //总座位数
        int seatNumber = seatFirst + seatSecond;
        //初始化座位

        //获取区间之间的座位状况
        String[] MyRoute = myRoute.split("-");

        //获取当前座位
        int peopleNum = userSelect.length;
        int q = 0;
        while (peopleNum!=0){
            String name = userSelect[q][0];
            String type = userSelect[q][1];
            String seatInitial = "";
            for(int m=0;m<seatNumber;++m){
                seatInitial =seatInitial.concat("1");
            }
            System.out.println(seatInitial);
            for(int j =0 ;j<MyRoute.length-1;++j){
                String  last = "";
                String startFirst = MyRoute[j];
                String endNext = MyRoute[j+1];
                //查找每个二维组的座位并并起来

                String seatInfo = seatService.getSeatByStartEndTripId(startFirst,endNext,tripId);
                System.out.println(seatInfo);
                for(int n=0;n<seatInfo.length();++n){
                    int x = (Integer.valueOf(seatInitial.charAt(n)-48)&Integer.valueOf(seatInfo.charAt(n)-48));
                    last = last.concat(String.valueOf(x));
                    System.out.println(last);
                }
                seatInitial = last;
                System.out.println(seatInitial);
            }
            String seatInfoFirst = seatInitial.substring(0,seatFirst);
            String seatInfoSecond = seatInitial.substring(seatFirst,seatFirst+seatSecond);
            int p = 0;
            int check = 1;
            if (type.equals("1")){
                while (check!=0){
                    if (seatInfoFirst.charAt(p)=='0'){
                        //当前余座
                        System.out.println("当前座位");
                        System.out.println(p);
                        check = 0;
                        result[q][0] = name;
                        int x  = (p+1)/40;
                        int y  = ((p+1)%40)/5;
                        int z  = ((p+1)%40)%5;
                        String s = "".concat(String.valueOf(x+1)).concat("-").concat(String.valueOf(y))
                                .concat("-").concat(String.valueOf(z));
                        result[q][1] = s;
                        break;
                    }
                    p = p + 1;
                    if(p==seatInfoFirst.length()){
                        System.out.println("no seat now");
                        result[q][0] = name;
                        result[q][1] = "无座";

                    }
                }
            }
            else {
                while (check!=0){
                    if (seatInfoSecond.charAt(p)=='0'){
                        //当前余座
                        System.out.println("当前座位");
                        p =  p+seatFirst;
                        System.out.println(p);
                        check = 0;

                        result[q][0] = name;
                        int x  = (p+1)/40;
                        int y  = ((p+1)%40)/5;
                        int z  = ((p+1)%40)%5;
                        String s = "".concat(String.valueOf(x+1)).concat("-").concat(String.valueOf(y))
                                .concat("-").concat(String.valueOf(z));
                        result[q][1] = s;
                    }
                    else{
                        p = p + 1;
                        if(p==seatInfoSecond.length()){
                            System.out.println("no seat now");
                            result[q][0] = name;
                            result[q][1] = "无座";
                            break;
                        }
                    }
                }
            }
            //更新余座
            System.out.println("p");
            System.out.println(p);
            result[q][2] = String.valueOf(p);
            if(result[q][1]  != "无座"){
                //更新座位表
                for(int w =0 ;w<MyRoute.length-1;++w){
                    String startFirst = MyRoute[w];
                    String endNext = MyRoute[w+1];
                    //查找每个二维组的座位并并起来
                    String seatInfo = seatService.getSeatByStartEndTripId(startFirst,endNext,tripId);
                    StringBuilder strBuilder = new StringBuilder(seatInfo);
                    strBuilder.setCharAt(p,'1');
                    seatService.updateSeatInfoByTripId(strBuilder.toString(),startFirst,endNext,tripId);
                }
            }
            q = q + 1;
            peopleNum = peopleNum -1;
        }

        return result;
    }

    public String returnticket(int id){
        UserOrderEntity userOrderEntity=userOrderService.findUserOrderEntityById(id);
        int tripid=userOrderEntity.getTripId();
        String numberOfSeat = trainService.findTrainEntityById(tripService.findTripEntityById(tripid).getTrainId()).getSeatInfo();
        String[] seatlist=userOrderEntity.getSeatList().split(",");
        String[] routelist=userOrderEntity.getRoutLine().split("-");
        String seatFirst=numberOfSeat.split("-")[0];
        String seatEnd=numberOfSeat.split("-")[1];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String tripTime = String.valueOf(stationsService.getStationTimeByTripIdAndStation(routelist[0],tripid));
        String nowTime = df.format(new Date());
        boolean flag = PCenterController.isDateBefore(tripTime, nowTime);
        //退票日志
        userOrderService.addReturnLog(routelist[0],routelist[routelist.length-1],userOrderEntity.getTripNumber());
        if (flag == true) {
            return "票已过期";
        }
        else {
            for (int i = 0; i < seatlist.length; i++) {
                String[] seat = seatlist[i].split("-");
                int startseat = (Integer.parseInt(seat[0]) - 1) * 40;
                int startseat1 = startseat + Integer.parseInt(seat[1]) * 5;
                int startseat2 = startseat1 + Integer.parseInt(seat[2]) - 1;
                for (int j = 0; j < routelist.length - 1; j++) {
                    String startstation = routelist[j];
                    String endnextstation = routelist[j + 1];
                    String seatInfo = seatService.getSeatByStartEndTripId(startstation, endnextstation, tripid);
                    StringBuilder strBuilder = new StringBuilder(seatInfo);
                    strBuilder.setCharAt(startseat2, '0');
                    seatService.updateSeatInfoByTripId(strBuilder.toString(), startstation, endnextstation, tripid);
                }
            }
            userOrderService.updateUserOrderEntityById("3", id);
            return "退票成功";
        }
    }

    @PostMapping("butticket/getremainseat")
    @ResponseBody
    public Map<String,Object> getremainseat(@RequestParam("tripid") int tripid,
                              @RequestParam("start") String start,
                              @RequestParam("end") String end){
        Map<String,Object> map=new HashMap<>();
        TripEntity tripEntity=tripService.findTripEntityById(tripid);
        String rout = routelineService.findRoutelineEntityByTripId(tripid).getRouteLine();
        String[] TripRoute = rout.split("-");
        String myRout = "";
        for(int i = 0;i<TripRoute.length;++i){
            if(start.equals(TripRoute[i])){
                myRout = myRout.concat(start).concat("-");
                i = i+1;
                while (!end.equals(TripRoute[i])){
                    myRout = myRout.concat(TripRoute[i]).concat("-");
                    i = i+1;
                }
                myRout = myRout.concat(end);
            }
        }
        String[] MyRoute = myRout.split("-");
        int trainId = tripEntity.getTrainId();
        System.out.println(trainId);
        String numberOfSeat = trainService.findSeatInfoById(trainId);
        String[] NumberOfSeat = numberOfSeat.split("-");
        //一等座座位数
        int seatFirst = Integer.parseInt(NumberOfSeat[0]);
        //二等座座位数
        int seatSecond = Integer.parseInt(NumberOfSeat[1]);
        //总座位数
        int seatNumber = seatFirst + seatSecond;
        String seatInitial = "";
        for(int m=0;m<seatNumber;++m){
            seatInitial =seatInitial.concat("1");
        }
        System.out.println(seatInitial);
        for(int j =0 ;j<MyRoute.length-1;++j){
            String  last = "";
            String startFirst = MyRoute[j];
            String endNext = MyRoute[j+1];
            //查找每个二维组的座位并并起来
            String seatInfo = seatService.getSeatByStartEndTripId(startFirst,endNext,tripid);
            System.out.println(seatInfo);
            for(int n=0;n<seatInfo.length();++n){
                int x = (Integer.valueOf(seatInitial.charAt(n)-48)&Integer.valueOf(seatInfo.charAt(n)-48));
                last = last.concat(String.valueOf(x));
                System.out.println(last);
            }
            seatInitial = last;
            System.out.println(seatInitial);
        }
        String seatInfoFirst = seatInitial.substring(0,seatFirst);
        String seatInfoSecond = seatInitial.substring(seatFirst,seatFirst+seatSecond);
        int seatFirstRemain  = 0;
        int seatSecondRemain  = 0;
        for(int i = 0;i<seatFirst;++i){
            if((seatInfoFirst.charAt(i)) == '0'){
                seatFirstRemain += 1;
            }
        }
        for(int j = 0;j<seatSecond;++j){
            if((seatInfoSecond.charAt(j)) == '0'){
                seatSecondRemain += 1;
            }
        }
        map.put("trainnum",tripEntity.getTrainNumber());
        map.put("seatremain1",seatFirstRemain);
        map.put("seatremain2",seatSecondRemain);
        return map;
    }

}

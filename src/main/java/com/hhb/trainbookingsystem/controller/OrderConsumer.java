package com.hhb.trainbookingsystem.controller;

import com.hhb.trainbookingsystem.entity.OrdinaryUserEntity;
import com.hhb.trainbookingsystem.entity.RoutelineEntity;
import com.hhb.trainbookingsystem.entity.UserOrderEntity;
import com.hhb.trainbookingsystem.entity.custom.Selectcontactor;
import com.hhb.trainbookingsystem.entity.TripEntity;

import com.hhb.trainbookingsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import com.alibaba.fastjson.JSONObject;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderConsumer {
    @Autowired
    TripService tripService;
    @Autowired
    RoutelineService routelineService;
    @Autowired
    TrainService trainService;
    @Autowired
    SeatService seatService;
    @Autowired
    UserOrderService userOrderService;
    @Autowired
    OrdinaryUserService ordinaryUserService;

    private int getindex(String[] arr,String str){
        for(int i=0;i<arr.length;i++){
            if(arr[i].equals(str))
                return i;
        }
        return -1;
    }

    public   String[][] getSeatsInfo(int tripId,String[][] userSelect,String myRoute){
        //获取座位数
        String result[][] = new String[userSelect.length][3];
        String numberOfSeat = trainService.findSeatInfoById(tripService.findTripEntityById(tripId).getTrainId());
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
            //System.out.println(seatInitial);
            for(int j =0 ;j<MyRoute.length-1;++j){
                String  last = "";
                String startFirst = MyRoute[j];
                String endNext = MyRoute[j+1];
                //查找每个二维组的座位并并起来
                String seatInfo = seatService.getSeatByStartEndTripId(startFirst,endNext,tripId);
                //System.out.println(seatInfo);
                for(int n=0;n<seatInfo.length();++n){
                    int x = (Integer.valueOf(seatInitial.charAt(n)-48)&Integer.valueOf(seatInfo.charAt(n)-48));
                    last = last.concat(String.valueOf(x));
                    //System.out.println(last);
                }
                seatInitial = last;
                //System.out.println(seatInitial);
            }
            String seatInfoFirst = seatInitial.substring(0,seatFirst);
            String seatInfoSecond = seatInitial.substring(seatFirst,seatFirst+seatSecond);
            int p = 0;
            int check = 1;
            if (type.equals("1")){
                while (check!=0){
                    if(p==seatInfoFirst.length()){
                        System.out.println("no seat now");
                        result[q][0] = name;
                        result[q][1] = "无座";
                        break;
                    }
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

                }
            }
            else {
                while (check!=0){
                    if(p==seatInfoSecond.length()){
                        System.out.println("no seat now");
                        result[q][0] = name;
                        result[q][1] = "无座";
                        break;
                    }
                    if (seatInfoSecond.charAt(p)=='0'){
                        //当前余座
                        System.out.println("当前座位");
                        System.out.println(p);
                        p =  p+seatFirst-1;
                        check = 0;
                        result[q][0] = name;
                        int x  = (p+1)/40;
                        int y  = ((p+1)%40)/5;
                        int z  = ((p+1)%40)%5;
                        String s = "".concat(String.valueOf(x+1)).concat("-").concat(String.valueOf(y))
                                .concat("-").concat(String.valueOf(z));
                        result[q][1] = s;
                    }
                    p = p + 1;

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
                    System.out.println("座位"+strBuilder.toString());
                    seatService.updateSeatInfoByTripId(strBuilder.toString(),startFirst,endNext,tripId);
                }
            }
            q = q + 1;
            peopleNum = peopleNum -1;
        }

        return result;
    }

    @JmsListener(destination = "ActiveMQQueue")
    @SendTo("RQueue")
    public Map<String, Object> readActiveQueue(String message){
        System.out.println("进入消息队列");
        String[] msg=message.split("\\+");
        JSONObject jsonObject = JSONObject.parseObject(msg[0]);
        Map<String,Object> data = (Map<String, Object>)jsonObject;
        int userid = Integer.parseInt(msg[1]);
        OrdinaryUserEntity user = ordinaryUserService.findOrdinaryUserEntityById(userid);


        String tripid=(String)data.get("tripid");
        String start=(String)data.get("start");
        String end=(String)data.get("end");
        String str=(String)data.get("selectcontactors");
        List<Selectcontactor> selectcontactor= JSONObject.parseArray(str,Selectcontactor.class);
        // JSONArray jsonArray=new JSONArray();
        Map<String,Object> map=new HashMap<>();

        TripEntity tripEntity = tripService.findTripEntityById(Integer.parseInt(tripid));
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

            boolean isOk = true;
            System.out.println("长度");
            for(int i=0;i<result.length;++i){
                System.out.println(result[i][1]);
                if(result[i][1].equals("无座")){
                    System.out.println("座位信息");
                    isOk = false;
                }
            }
            System.out.println(isOk);
            if (isOk == true){
                userOrderEntity.setUserOrderCondition("0");
                map.put("status",1);

            }
            else{
                map.put("status",0);
                userOrderEntity.setUserOrderCondition("-1");
            }
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
        return  map;
    }
}

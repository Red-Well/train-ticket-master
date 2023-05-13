package com.hhb.trainbookingsystem.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.hhb.trainbookingsystem.entity.*;
import com.hhb.trainbookingsystem.entity.custom.Pay_userinfo;
import com.hhb.trainbookingsystem.entity.custom.Selectcontactor;
import com.hhb.trainbookingsystem.entity.custom.Userorder_search;
import com.hhb.trainbookingsystem.entity.searchResult.SearchTrip;



import com.hhb.trainbookingsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jms.Queue;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 个人中心

 */

@Controller
public class PCenterController {

    @Autowired
    ContactService contactorsmethods;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    OrdinaryUserService userService;

    @Autowired
    StationsService stationsService;

    @Autowired
    SeatService seatService;

    @Autowired
    TrainService trainService;

    //生成订单
    @Autowired
    TripService tripService;

    @Autowired
      RoutelineService routelineService;

    @Autowired
     FareService fareService;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue1;

    @RequestMapping("/pcenter")
    public String showpagestu(HttpSession session, Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "page1", defaultValue = "0")int page1,
                              @RequestParam(value = "page2", defaultValue = "0")int page2,
                              @RequestParam(value = "page3",defaultValue = "0")int page3,
                              @RequestParam(value = "page4",defaultValue ="0")int page4){
        OrdinaryUserEntity user=(OrdinaryUserEntity) session.getAttribute("user");
        Timestamp nowtime=new Timestamp(System.currentTimeMillis());
        if(user!=null){
            Sort sort = Sort.by(Sort.Direction.DESC,"id");
            Pageable pageable= PageRequest.of(page,6,sort);
            Page<ContactEntity> contactuser=contactorsmethods.findallcontator(user.getId(),pageable);
            List<Userorder_search> not_payorder=new ArrayList<>();
            List<Userorder_search> not_goorder=new ArrayList<>();
            List<Userorder_search> complete_order=new ArrayList<>();
            List<Userorder_search> disabled_order=new ArrayList<>();

            Pageable pageable4=PageRequest.of(page4,6,sort);
            List<UserOrderEntity> orderdisabled=userOrderService.orderstate_get(user.getId(),"-1");
            for(int i=0;i<orderdisabled.size();i++){
                UserOrderEntity pay4=orderdisabled.get(i);
                String[] route=pay4.getRoutLine().split("-");
                String start=route[0];
                String end=route[route.length-1];
                Timestamp starttime=stationsService.getStationTimeByTripIdAndStation(start,pay4.getTripId());
                if(starttime.before(nowtime)){
                    userOrderService.updateUserOrderEntityById("2",pay4.getId());
                }
                else {
                    Timestamp endtime = stationsService.getStationTimeByTripIdAndStation(end, pay4.getTripId());
                    Userorder_search pay_4 = new Userorder_search(pay4.getId(), pay4.getTripNumber(), pay4.getNameList(), pay4.getSeatList()
                            , pay4.getPrice(), start, end, starttime, endtime);
                    not_payorder.add(pay_4);
                }
            }
            Page<Userorder_search> disabledorders=listConvertToPage(not_payorder,pageable4);


            Pageable pageable3=PageRequest.of(page3,6,sort);
            List<UserOrderEntity> ordernotpay=userOrderService.orderstate_get(user.getId(),"0");
            for(int i=0;i<ordernotpay.size();i++){
                UserOrderEntity pay0=ordernotpay.get(i);
                String[] route=pay0.getRoutLine().split("-");
                String start=route[0];
                String end=route[route.length-1];
                Timestamp starttime=stationsService.getStationTimeByTripIdAndStation(start,pay0.getTripId());
                if(starttime.before(nowtime)){
                    userOrderService.updateUserOrderEntityById("2",pay0.getId());
                }
                else {
                    Timestamp endtime = stationsService.getStationTimeByTripIdAndStation(end, pay0.getTripId());
                    Userorder_search pay_0 = new Userorder_search(pay0.getId(), pay0.getTripNumber(), pay0.getNameList(), pay0.getSeatList()
                            , pay0.getPrice(), start, end, starttime, endtime);
                    not_payorder.add(pay_0);
                }
            }
            Page<Userorder_search> notpayorders=listConvertToPage(not_payorder,pageable3);

            Pageable pageable1=PageRequest.of(page1,6,sort);
            List<UserOrderEntity> ordernotgo=userOrderService.orderstate_get(user.getId(),"1");
            for(int i=0;i<ordernotgo.size();i++){
                UserOrderEntity pay1=ordernotgo.get(i);
                String[] route=pay1.getRoutLine().split("-");
                String start=route[0];
                String end=route[route.length-1];
                Timestamp starttime=stationsService.getStationTimeByTripIdAndStation(start,pay1.getTripId());
                if(starttime.before(nowtime)){
                    userOrderService.updateUserOrderEntityById("2",pay1.getId());
                }
                else {
                    Timestamp endtime = stationsService.getStationTimeByTripIdAndStation(end, pay1.getTripId());
                    Userorder_search pay_1 = new Userorder_search(pay1.getId(), pay1.getTripNumber(), pay1.getNameList(), pay1.getSeatList()
                            , pay1.getPrice(), start, end, starttime, endtime);
                    not_goorder.add(pay_1);
                }
            }
            Page<Userorder_search> notgoorders=listConvertToPage(not_goorder,pageable1);



            Pageable pageable2=PageRequest.of(page2,6,sort);
            List<UserOrderEntity> ordercomplete=userOrderService.orderstate_get(user.getId(),"2");
            for(int i=0;i<ordercomplete.size();i++){
                UserOrderEntity pay2=ordercomplete.get(i);
                String[] route=pay2.getRoutLine().split("-");
                String start=route[0];
                String end=route[route.length-1];
                Timestamp starttime=stationsService.getStationTimeByTripIdAndStation(start,pay2.getTripId());
                Timestamp endtime=stationsService.getStationTimeByTripIdAndStation(end,pay2.getTripId());
                Userorder_search pay_2=new Userorder_search(pay2.getId(),pay2.getTripNumber(),pay2.getNameList(),pay2.getSeatList()
                        ,pay2.getPrice(),start,end,starttime,endtime);
                complete_order.add(pay_2);
            }
            Page<Userorder_search> completeorders=listConvertToPage(complete_order,pageable2);

            model.addAttribute("page",contactuser);
            model.addAttribute("notpaypage",notpayorders);
            model.addAttribute("notgopage",notgoorders);
            model.addAttribute("completepage",completeorders);
            model.addAttribute("disbaledpage",disabledorders);
            model.addAttribute("names", user.getName());
            model.addAttribute("realname",user.getRealname());
            model.addAttribute("types", user.getType());
            model.addAttribute("personIds", user.getPersonId());
            model.addAttribute("phonenums", user.getPhonenum());
        }


        return "personalcenter";
    }


    //删除联系人
    @GetMapping("/pcenter/{id}/delete")
    public String deletecontactor(@PathVariable int id, RedirectAttributes attributes){
        contactorsmethods.delecontactbyid(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/pcenter";
    }



   /* @GetMapping("/pcenter/{id}/returnticket")
=======
/*    @GetMapping("/pcenter/{id}/returnticket")
>>>>>>> Stashed changes
    public String returnticket(@PathVariable int id){
        String result =  returnTicket(id);
        System.out.println(result);
        return "redirect:/pcenter";
    }*/

    //修改个人信息
    @PostMapping("/pcenter/editinfo")
    @ResponseBody
    public String editinfo(@RequestParam("editname")String name,@RequestParam("editphonenum") String phonenum,HttpSession session){
           userService.edituinfo(phonenum,name);
           OrdinaryUserEntity user=userService.getuser(name);
           session.removeAttribute("user");
           session.setAttribute("user",user);
           return "success";
    }

    //修改联系人信息
    @PostMapping("/pcenter/altercontactor")
    @ResponseBody
    public String altercontator(@RequestParam("num") String num,@RequestParam("phonenum") String phonenum,RedirectAttributes attributes){
        int id=Integer.parseInt(num);
        contactorsmethods.altercontactor(phonenum,id);
        attributes.addFlashAttribute("message1","修改成功");
        return "success";
    }

    @PostMapping("/pcenter/addcontactor")
    @ResponseBody
    public String addcontactor(@RequestParam("name") String name,@RequestParam("personid") String personid,
                               @RequestParam("phonenum") String phonenum,HttpSession session){
        OrdinaryUserEntity user=(OrdinaryUserEntity) session.getAttribute("user");
        ContactEntity contactEntity=new ContactEntity();
        contactEntity.setName(name);
        contactEntity.setPersonId(personid);
        contactEntity.setPhonenum(phonenum);
        contactEntity.setOrdineryUserId(user.getId());
        contactorsmethods.addcontatcor(contactEntity);
        return "success";
    }



    public <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : ( start + pageable.getPageSize());
        return new PageImpl<T>(list.subList(start, end), pageable, list.size());
    }


    /**
     * 支付页面
     */
    @RequestMapping("/pay/{id}")
    public String paypage(@PathVariable int id, Model model,HttpSession session){
        OrdinaryUserEntity user=(OrdinaryUserEntity) session.getAttribute("user");
        if(user!=null){
            UserOrderEntity payorder=userOrderService.findUserOrderEntityById(id);
            List<Pay_userinfo> payUserinfos=new ArrayList<>();
            String[] namelist=payorder.getNameList().split(",");
            String[] seatlist=payorder.getSeatList().split(",");
            String[] pricelist=payorder.getPricelist().split(",");
            String[] routelist=payorder.getRoutLine().split("-");
            String[] typelist=payorder.getTypelist().split(",");
            String start=routelist[0];
            String end=routelist[routelist.length-1];
            Timestamp starttime=stationsService.getStationTimeByTripIdAndStation(start,payorder.getTripId());
            Timestamp endtime=stationsService.getStationTimeByTripIdAndStation(end,payorder.getTripId());
            for(int i=0;i<namelist.length;i++){

                Pay_userinfo payUserinfo=new Pay_userinfo();
                if(typelist[i].equals("1")){
                    payUserinfo.setSeatkind("一等座");
                }
                else if(typelist[i].equals("2")) {
                    payUserinfo.setSeatkind("二等座");
                }
                String[] carriage=seatlist[i].split("-");
                payUserinfo.setName(namelist[i]);
                //payUserinfo.setSeat(seatlist[i]);
                payUserinfo.setPricelist(pricelist[i]);
                payUserinfo.setCarriage(carriage[0]);
                payUserinfo.setSeat(carriage[1]+"排"+carriage[2]+"座");

                if(namelist[i].equals(user.getRealname())){
                    payUserinfo.setPersonid(user.getPersonId());
                }
                else {
                   ContactEntity contact=contactorsmethods.findbyname(namelist[i]);
                   payUserinfo.setPersonid(contact.getPersonId());
                }
                payUserinfos.add(payUserinfo);
            }
            model.addAttribute("names", user.getName());
            model.addAttribute("start",start);
            model.addAttribute("end",end);
            model.addAttribute("tripnum",payorder.getTripNumber());
            model.addAttribute("starttime",starttime);
            model.addAttribute("endtime",endtime);
            model.addAttribute("price",payorder.getPrice());
            model.addAttribute("person",payUserinfos);
            model.addAttribute("orderid",id);
        }
        return "pay";

    }

    @GetMapping("/pcenter/{id}/paytheorder")
    public String paytheorder(@PathVariable int id){
        userOrderService.updateUserOrderEntityById("1",id);
       // String url="redirect:/pcenter/"+id;
        return "redirect:/pcenter";
    }

    /**
     * 买票页面
     * @return
     */
    @RequestMapping("/buyTicket")
    public String getBuyTicket(HttpSession session,Model model)
    {
        OrdinaryUserEntity user=(OrdinaryUserEntity) session.getAttribute("user");
        if(user!=null){
            model.addAttribute("names",user.getName());
        }
        return "buyticket";
    }

    @GetMapping("buyticket/getcontators")
    @ResponseBody
    public Map<String,Object> getallcontator(HttpSession session){
        OrdinaryUserEntity user=(OrdinaryUserEntity)session.getAttribute("user");
        Map<String,Object> map=new HashMap<>();
        if(user!=null){
            List<ContactEntity> contators=contactorsmethods.findcontactors(user.getId());
            map.put("myuser",user);
            map.put("mycontactors",contators);
            return map;
        }
        return map;
    }

    @PostMapping("buyticket/getchooseperson")
    @ResponseBody
    public List<Selectcontactor> getchooseperson(@RequestParam MultiValueMap params, HttpSession session){
        OrdinaryUserEntity user=(OrdinaryUserEntity)session.getAttribute("user");
        List<Selectcontactor> selectcontactors=new ArrayList<>();

      if(user!=null){
          List<String> checknames=new ArrayList<>();
          String start=(String)params.getFirst("start");
          String end=(String)params.getFirst("end");
          String tripid=(String)params.getFirst("tripid");
          List<String> checkname=(LinkedList)params.get("checknames");
          String[] list=checkname.get(0).split(",");
          for(int k=0;k<list.length;k++){
              checknames.add(list[k]);
          }

          //TripEntity tripEntity=tripService.findTripEntityByTrainNumber(trainnum);
          BigDecimal fare=fareService.getFareByStationsAndTripId(start,end,"1",Integer.parseInt(tripid));
         for(int i=0;i<checknames.size();i++){
               if(checknames.get(i).equals(user.getRealname())){
                   Selectcontactor selectuser=new Selectcontactor();
                   selectuser.setName(user.getRealname());
                   selectuser.setPersonid(user.getPersonId());
                   selectuser.setPhonenum(user.getPhonenum());
                   selectuser.setPrice(fare);
                   selectuser.setTripid(Integer.parseInt(tripid));
                   selectuser.setType("1");
                   selectcontactors.add(selectuser);
               }
               else {
                   ContactEntity contactor=contactorsmethods.findbyname(checknames.get(i));
                   Selectcontactor selectuser=new Selectcontactor();
                   selectuser.setName(contactor.getName());
                   selectuser.setPersonid(contactor.getPersonId());
                   selectuser.setPhonenum(contactor.getPhonenum());
                   selectuser.setPrice(fare);
                   selectuser.setTripid(Integer.parseInt(tripid));
                   selectuser.setType("1");
                   selectcontactors.add(selectuser);
               }
          }
       }
       return selectcontactors;
    }

    @PostMapping("buyticket/selecttickettype")
    @ResponseBody
    public List<Selectcontactor> selecttickettype(@RequestBody Map<String,Object> data){
        String tickettype=(String) data.get("index1");
        int indexofselect=(int)data.get("index2");
        String start=(String)data.get("start");
        String end=(String)data.get("end");
        String str=(String)data.get("selectcontactors");
        List<Selectcontactor> selectcontactor= JSONObject.parseArray(str,Selectcontactor.class);
        BigDecimal price=fareService.getFareByStationsAndTripId(start,end,tickettype,selectcontactor.get(0).getTripid());
        selectcontactor.get(indexofselect).setPrice(price);
        selectcontactor.get(indexofselect).setType(tickettype);
        return selectcontactor;
    }



    @PostMapping("buyticket/createorder")
    @ResponseBody
    public void createorder(@RequestBody Map<String,Object> data, HttpSession session){
        String start=(String)data.get("start");
        String end=(String)data.get("end");
        String tripid=(String)data.get("tripid");
        TripEntity tripEntity = tripService.findTripEntityById(Integer.parseInt(tripid));
        userOrderService.addOrderLog(start,end,tripEntity.getTrainNumber());
        String json1 = JSON.toJSONString(data);
        OrdinaryUserEntity user=(OrdinaryUserEntity)session.getAttribute("user");
        String json2 = new JSONObject().toJSONString(user.getId());
        String json3=json1.concat("+").concat(json2);
        jmsMessagingTemplate.convertAndSend(queue1,json3);

    }

    @GetMapping("/api/gettrip")
    @ResponseBody
    public String gettrip(){
//        System.out.println("hello");
        return "success";
    }

    //查看日期是否正确
    public static boolean isDateBefore(String date1,String date2){
        try{
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).before(df.parse(date2));
        }catch(ParseException e){
            System.out.print("[SYS] " + e.getMessage());
            return false;
        }
    }

    //改签 获取当天的车次
    public Map<String,Object> getReBookTrips(int id){
        UserOrderEntity userOrderEntity = userOrderService.findUserOrderEntityById(id);
        TripEntity tripEntity1 = tripService.findTripEntityById(userOrderEntity.getTripId());
        String time = (String.valueOf(tripEntity1.getDepartureTime())).substring(0,10);
        String oldRoute = userOrderEntity.getRoutLine();
        String []OldRoute = oldRoute.split("-");
        String start = OldRoute[0];
        String end = OldRoute[OldRoute.length-1];
        System.out.println(time);
        System.out.println(start);
        System.out.println(end);
        List<TripEntity> routeTrips = new ArrayList<>();
        //路线匹配
        List<RoutelineEntity> routeLists = routelineService.findRouteEntitiesByStations(start,end);
        for(RoutelineEntity routelineEntity:routeLists){
            int tripId = routelineEntity.getTripId();
            //时间匹配
            Timestamp timestamp = stationsService.getStationTimeByTripIdAndStation(start,tripId);
            String timestampString = String.valueOf(timestamp);
            System.out.println(timestampString);
            System.out.println((timestampString.substring(0,10)));
            if((timestampString.substring(0,10)).equals(time)){
                routeTrips.add(tripService.findTripEntityById(tripId));
                System.out.println((timestampString.substring(0,10)));
                System.out.println(tripService.findTripEntityById(tripId));
            }
        }
        List<SearchTrip> searchTrips = new ArrayList<>();
        for(TripEntity tripEntity: routeTrips){
            //当前车次
            SearchTrip searchTrip = new SearchTrip();
            searchTrip.setStartStation(start);
            searchTrip.setEndStation(end);
            int tripId = tripEntity.getId();
            System.out.println("查到的车次");
            System.out.println(tripId);
            searchTrip.setTripId(tripId);
            searchTrip.setTripNumber(tripService.findTripEntityById(tripId).getTrainNumber());
            //时间表找出发时间
            System.out.println("出发时间");
            Timestamp startTime =stationsService.getStationTimeByTripIdAndStation(start,tripId);
            System.out.println(startTime);
            searchTrip.setStartTime(String.valueOf(startTime));
            //时间表找到达时间
            System.out.println("到达时间"   );
            Timestamp endTime = stationsService.getStationTimeByTripIdAndStation(end,tripId);
            System.out.println(endTime);
            searchTrip.setEndTime(String.valueOf(endTime));
            String distanceTime = getDistanceTime(startTime.getTime(),endTime.getTime());
            searchTrip.setSpendTime(distanceTime);
            //费用表找到费用
            System.out.println("费用");
            System.out.println(fareService.getFareByStationsAndTripId(start,end,"1",tripId));
            searchTrip.setFareFirst(String.valueOf(fareService.getFareByStationsAndTripId(start,end,"1",tripId)));
            System.out.println(fareService.getFareByStationsAndTripId(start,end,"2",tripId));
            searchTrip.setFareSecond(String.valueOf(fareService.getFareByStationsAndTripId(start,end,"2",tripId)));
            //获取总路线
            //获取用户经过路线
            System.out.println("总路线");
            String rout = routelineService.findRoutelineEntityByTripId(tripId).getRouteLine();
            System.out.println(rout);
            String []TripRoute = rout.split("-");
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
            System.out.println("用户路线");
            System.out.println(myRout);
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
            //    System.out.println(seatInfo);
                for(int n=0;n<seatInfo.length();++n){
                    int x = (Integer.valueOf(seatInitial.charAt(n)-48)&Integer.valueOf(seatInfo.charAt(n)-48));
                    last = last.concat(String.valueOf(x));
                  //  System.out.println(last);
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
            searchTrip.setSeatFirstRemain(seatFirstRemain);
            searchTrip.setSeatSecondRemain(seatSecondRemain);
            if(searchTrip.getTripId()!=tripEntity1.getId()){
            searchTrips.add(searchTrip);}
        }
        Map<String,Object> map=new HashMap<>();
        map.put("searchTrips",searchTrips);
        map.put("sum",searchTrips.size());
        return map;
    }
    //获取时间差
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
        if (day != 0) return day + "天"+hour + "小时"+min + "分钟" + sec + "秒";
        if (hour != 0) return hour + "小时"+ min + "分钟" + sec + "秒";
        if (min != 0) return min + "分钟" + sec + "秒";
        if (sec != 0) return sec + "秒" ;
        return "0秒";
    }


    @GetMapping("/pcenter/{id}/returnticket1")
    public String returnticket1(@PathVariable int id,Model model){
        UserOrderEntity userOrderEntity=userOrderService.findUserOrderEntityById(id);
        int tripid=userOrderEntity.getTripId();
        String numberOfSeat = trainService.findSeatInfoById(tripService.findTripEntityById(tripid).getTrainId());
        String[] seatlist=userOrderEntity.getSeatList().split(",");
        String[] routelist=userOrderEntity.getRoutLine().split("-");
        String seatFirst=numberOfSeat.split("-")[0];
        String seatEnd=numberOfSeat.split("-")[1];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String tripTime = String.valueOf(stationsService.getStationTimeByTripIdAndStation(routelist[0],tripid));
        String nowTime = df.format(new Date());
        boolean flag = isDateBefore(tripTime, nowTime);
        userOrderService.addReturnLog(routelist[0],routelist[routelist.length-1],userOrderEntity.getTripNumber());
        if (flag == true) {
            model.addAttribute("ticketinfo","票已过期");
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
        }
        return "redirect:/pcenter";

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
        boolean flag = isDateBefore(tripTime, nowTime);

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


    @PostMapping("/api/index/getchangeTrips")
    @ResponseBody
    public Map<String, Object> getchangetrips(@RequestParam("orderid")String orderid,@RequestParam("starttime") String starttime){
        return getReBookTrips(Integer.parseInt(orderid));
    }

}

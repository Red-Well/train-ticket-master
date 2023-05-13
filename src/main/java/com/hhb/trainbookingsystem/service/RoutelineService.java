
package com.hhb.trainbookingsystem.service;
import com.hhb.trainbookingsystem.entity.RoutelineEntity;

import java.util.List;

public interface RoutelineService {
    RoutelineEntity findRoutelineEntityByTripId(int id);
    RoutelineEntity  save(RoutelineEntity r);
    RoutelineEntity updateRoutelineEntityById(String route_line , int id);
    List<RoutelineEntity> findRouteEntitiesByStations(String start, String end);
    String getRouteLineByTripId(int id);
}

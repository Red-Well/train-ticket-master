package com.hhb.trainbookingsystem.entity.custom;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityUtils {

    /**
     * 将数组数据转换为实体类
     * 此处数组元素的顺序必须与实体类构造函数中的属性顺序一致
     *
     * @param list  数组对象集合
     * @param clazz 实体类
     * @param <T>   实体类
     * @param model 实例化的实体类
     * @return 实体类集合
     */
    public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz, Object model) {
        List<T> returnList = new ArrayList<T>();
        if (list.isEmpty()) return returnList;
        Object[] co = list.get(0);
        List<Map> attributeInfoList = getFiledsInfo(model);
        Class[] c2 = new Class[attributeInfoList.size()];
        if (attributeInfoList.size() != co.length) {
            return returnList;
        }
        for (int i = 0; i < attributeInfoList.size(); i++) {
            c2[i] = (Class) attributeInfoList.get(i).get("type");
        }
        try {
            for (Object[] o : list) {
                Constructor<T> constructor = clazz.getConstructor(c2);
                returnList.add(constructor.newInstance(o));
            }
        } catch (Exception ex) {
            return returnList;
        }
        return returnList;
    }

    private static Object getFieldValueByName(String fieldName, Object modle) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = modle.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(modle, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    private static List<Map> getFiledsInfo(Object model) {
        Field[] fields = model.getClass().getDeclaredFields();
        List<Map> list = new ArrayList(fields.length);
        Map infoMap = null;
        for (int i = 0; i < fields.length; i++) {
            infoMap = new HashMap(3);
            infoMap.put("type", fields[i].getType());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), model));
            list.add(infoMap);
        }
        return list;
    }


}
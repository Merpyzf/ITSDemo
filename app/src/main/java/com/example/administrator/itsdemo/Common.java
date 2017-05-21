package com.example.administrator.itsdemo;

/**
 * Created by Administrator on 2017/5/11.
 */

public class Common
{

    private static final String HTTP = "http://172.22.21.230:8080/transportservice/action/";
    private static  final String USER_NAME = "Z004";
    public  static  final String DB_NAME = "ITS.db";


    //设置小车动作
    public static final String SET_CAR_MOVE = HTTP +"SetCarMove.do";//post {"CarId":1, "CarAction":"Stop"}

    //获取小车余额
    public static final String GET_CAR_ACCOUNT_BALANCE = HTTP +"GetCarAccountBalance.do";//post {"CarId":2, "UserName":"Z0004"}

    //小车充值
    public static final String SET_CAR_ACCOUNT_RECHARGE = HTTP +"SetCarAccountRecharge.do";//{"CarId":1,"Money":200, "UserName":"Z0004"}


    public static final String GET_ALL_SENSOR = HTTP+"GetAllSense.do";

    //获取指定传感器的值
    public static final String GET_SENSE_BY_NAME = HTTP+"GetSenseByName.do";//{"SenseName":"temperature", "UserName":"Z0004"}

    //获取道路状态
    public static final String GET_ROAD_STATUS = HTTP+"GetRoadStatus.do";// {"RoadId":1,"UserName":"Z0004"}

    public static final String GET_BUS_CAPACITY = HTTP+"GetBusCapacity.do";

    //查询路灯当前的状态
    public static final String GET_ROAD_LIGHT_STATUS = HTTP +"GetRoadLightStatus.do";//{"RoadLightId":1, "UserName":"Z0004"}

    //设置路灯的状态
    public static final String SET_ROAD_LIGHT_STATUS_ACTION = HTTP +" SetRoadLightStatusAction.do";//{"RoadLightId":1,"Action":"Close", "UserName":"Z0004"}

    //设置路灯的控制模式
    public static final String SET_ROAD_LIGHT_CONTROL_MODE = HTTP +"  SetRoadLightControlMode.do";//{"ControlMode":"Manual", "UserName":"Z0004"}

    //查询红绿灯的配置信息
    public static final String GET_TRAFFIC_LIGHT_CONFIG_ACTION = HTTP +"  GetTrafficLightConfigAction.do";


    public static final int pm25_limit = 200;
    public static final int co2_limit = 5500;
    public static final int light_limit = 2500;
    public static final int humidity_limit = 60;
    public static final int temperature_limit = 30;
}

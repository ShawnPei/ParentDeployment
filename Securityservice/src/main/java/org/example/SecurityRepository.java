package com.udacity.catpoint.data;

import java.util.Set;

/**
 * Interface showing the methods our security repository will need to support
 */
public interface SecurityRepository {
    void addSensor(com.udacity.catpoint.data.Sensor sensor);
    void removeSensor(com.udacity.catpoint.data.Sensor sensor);
    void updateSensor(com.udacity.catpoint.data.Sensor sensor);
    void setAlarmStatus(com.udacity.catpoint.data.AlarmStatus alarmStatus);
    void setArmingStatus(com.udacity.catpoint.data.ArmingStatus armingStatus);
    Set<com.udacity.catpoint.data.Sensor> getSensors();
    com.udacity.catpoint.data.AlarmStatus getAlarmStatus();
    com.udacity.catpoint.data.ArmingStatus getArmingStatus();


}

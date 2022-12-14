package com.udacity.catpoint.data;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;
import java.util.prefs.Preferences;

/**
 * Fake repository implementation for demo purposes. Stores state information in local
 * memory and writes it to user preferences between app loads. This implementation is
 * intentionally a little hard to use in unit tests, so watch out!
 */
public class PretendDatabaseSecurityRepositoryImpl implements SecurityRepository{

    private Set<com.udacity.catpoint.data.Sensor> sensors;
    private com.udacity.catpoint.data.AlarmStatus alarmStatus;
    private com.udacity.catpoint.data.ArmingStatus armingStatus;

    //preference keys
    private static final String SENSORS = "SENSORS";
    private static final String ALARM_STATUS = "ALARM_STATUS";
    private static final String ARMING_STATUS = "ARMING_STATUS";

    private static final Preferences prefs = Preferences.userNodeForPackage(PretendDatabaseSecurityRepositoryImpl.class);
    private static final Gson gson = new Gson(); //used to serialize objects into JSON

    public PretendDatabaseSecurityRepositoryImpl() {
        //load system state from prefs, or else default
        alarmStatus = com.udacity.catpoint.data.AlarmStatus.valueOf(prefs.get(ALARM_STATUS, com.udacity.catpoint.data.AlarmStatus.NO_ALARM.toString()));
        armingStatus = com.udacity.catpoint.data.ArmingStatus.valueOf(prefs.get(ARMING_STATUS, com.udacity.catpoint.data.ArmingStatus.DISARMED.toString()));

        //we've serialized our sensor objects for storage, which should be a good warning sign that
        // this is likely an impractical solution for a real system
        String sensorString = prefs.get(SENSORS, null);
        if(sensorString == null) {

            sensors = new TreeSet<>();
        } else {
            Type type = new TypeToken<Set<com.udacity.catpoint.data.Sensor>>() {
            }.getType();
            sensors = gson.fromJson(sensorString, type);
        }
    }

    @Override
    public void addSensor(com.udacity.catpoint.data.Sensor sensor) {
        sensors.add(sensor);
        prefs.put(SENSORS, gson.toJson(sensors));
    }

    @Override
    public void removeSensor(com.udacity.catpoint.data.Sensor sensor) {
        sensors.remove(sensor);
        prefs.put(SENSORS, gson.toJson(sensors));
    }

    @Override
    public void updateSensor(com.udacity.catpoint.data.Sensor sensor) {
        sensors.remove(sensor);
        sensors.add(sensor);
        prefs.put(SENSORS, gson.toJson(sensors));
    }

    @Override
    public void setAlarmStatus(com.udacity.catpoint.data.AlarmStatus alarmStatus) {
        this.alarmStatus = alarmStatus;
        prefs.put(ALARM_STATUS, this.alarmStatus.toString());
    }

    @Override
    public void setArmingStatus(com.udacity.catpoint.data.ArmingStatus armingStatus) {
        this.armingStatus = armingStatus;
        prefs.put(ARMING_STATUS, this.armingStatus.toString());
    }

    @Override
    public Set<com.udacity.catpoint.data.Sensor> getSensors() {
        return sensors;
    }

    @Override
    public com.udacity.catpoint.data.AlarmStatus getAlarmStatus() {
        return alarmStatus;
    }

    @Override
    public com.udacity.catpoint.data.ArmingStatus getArmingStatus() {
        return armingStatus;
    }
}

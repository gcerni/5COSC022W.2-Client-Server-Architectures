package com.example.dao;

import com.example.model.Room;
import com.example.model.Sensor;
import com.example.model.SensorReading;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockData {
    public static final List<Room> ROOMS = new ArrayList<>();
    public static final List<Sensor> SENSORS = new ArrayList<>();
    public static final List<SensorReading> SENSORREADINGS = new ArrayList<>();

    static {
        // Initialise Rooms
        ROOMS.add(new Room("LIB-301", "Library Quiet Study", 20, "SENS-001"));
        ROOMS.add(new Room("G-201", "Big Seminar Room", 50, "SENS-002"));

        // Initialise Sensors
        SENSORS.add(new Sensor("SENS-001", " Temperature ", "ACTIVE", 28.0, "LIB-301"));
        SENSORS.add(new Sensor("SENS-002", " Temperature ", "ACTIVE", 16.0, "G-201"));

        // Initialise SensorReadings
        SENSORREADINGS.add(new SensorReading(UUID.randomUUID().toString(), 1775834995000L, 25.5));
        SENSORREADINGS.add(new SensorReading(UUID.randomUUID().toString(), 1775921395000L, 21.5));
    }
}
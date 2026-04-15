package com.example.dao;

import com.example.model.Room;
import com.example.model.Sensor;
import com.example.model.SensorReading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MockData {
    public static final List<Room> ROOMS = new ArrayList<>();
    public static final List<Sensor> SENSORS = new ArrayList<>();
    public static Map<String, List<SensorReading>> SENSORREADINGS = new HashMap<>();

    static {
        // Initialise Rooms
        ROOMS.add(new Room("LIB-301", "Library Quiet Study", 20, "SENS-001"));
        ROOMS.add(new Room("G-201", "Big Seminar Room", 50, "SENS-002"));

        // Initialise Sensors
        SENSORS.add(new Sensor("SENS-001", "Temperature", "ACTIVE", 28.0, "LIB-301"));
        SENSORS.add(new Sensor("SENS-002", "Temperature", "ACTIVE", 16.0, "G-201"));
        SENSORS.add(new Sensor("SENS-003", "CO2", "ACTIVE", 105, "G-201"));
        SENSORS.add(new Sensor("SENS-004", "CO2", "ACTIVE", 40, "LIB-301"));
    }
}
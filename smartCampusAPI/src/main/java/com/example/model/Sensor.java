package com.example.model;

public class Sensor implements BaseModel {
    private String id;
    private String name;
    private String status;
    private double currentValue;
    private String roomId;
    
    public Sensor() {}

    public Sensor(String id, String name, String status, double currentValue, String roomId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.currentValue = currentValue;
        this.roomId = roomId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
       
}
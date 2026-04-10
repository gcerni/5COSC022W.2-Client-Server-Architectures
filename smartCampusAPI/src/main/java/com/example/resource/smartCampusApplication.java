/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;
    
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/api/v1")
public class smartCampusApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(com.example.resource.Discovery.class);
        resources.add(com.example.resource.SensorRoom.class);
        // Add your other resource classes here as you create them
        return resources;
    }
}

package com.example.resource;
    
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/api/v1")
public class smartCampusApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        
        // --- Resources ---
        resources.add(com.example.resource.Discovery.class);
        resources.add(com.example.resource.SensorRoom.class);
        resources.add(com.example.resource.SensorResource.class);
        resources.add(com.example.resource.SensorReadingResource.class);
        
        // --- ExceptionMappers ---
        resources.add(com.example.exception.DataNotFoundExceptionMapper.class);
        resources.add(com.example.exception.RoomNotEmptyExceptionMapper.class);
        resources.add(com.example.exception.LinkedResourceNotFoundExceptionMapper.class);
        resources.add(com.example.exception.SensorUnavailableExceptionMapper.class);
        resources.add(com.example.exception.GlobalExceptionMapper.class);
        
        // --- LoggingFilters ---
        resources.add(com.example.filter.LoggingFilter.class);
        
        return resources;
    }
}

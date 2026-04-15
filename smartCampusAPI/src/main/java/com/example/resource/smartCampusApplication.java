package com.example.resource;
    
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("/api/v1")
public class smartCampusApplication extends ResourceConfig {
    public smartCampusApplication() {
        
        // Paths to resources, exceptions and filter folders.
        packages("com.example.resource", "com.example.exception", "com.example.filter");
        
    }
}
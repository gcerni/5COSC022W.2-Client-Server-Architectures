package com.example.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

// This maps directly to /api/v1
@Path("/") 
public class Discovery {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscoveryInfo() {
        Map<String, Object> discovery = new HashMap<>();
        
        discovery.put("version", "v1");
        discovery.put("description", "Smart Campus Sensor API");
        discovery.put("admin_contact", "w2112281@westminster.ac.uk");

        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");
        
        discovery.put("end-points", links);

        return Response.ok(discovery).build();
    }
}
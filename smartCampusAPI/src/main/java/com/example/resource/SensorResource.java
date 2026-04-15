package com.example.resource;

import com.example.dao.GenericDAO;
import com.example.dao.MockData;
import com.example.exception.DataNotFoundException;
import com.example.model.Room;
import com.example.model.Sensor;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sensors") 
public class SensorResource {
    
    private GenericDAO<Sensor> sensorDAO = new GenericDAO<>(MockData.SENSORS);
    private GenericDAO<Room> roomDAO = new GenericDAO<>(MockData.ROOMS);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getAllSensors(@QueryParam("type") String type) {
        List<Sensor> sensors = sensorDAO.getAll();
        
        if (type == null || type.isEmpty()) {
            return sensors;
        }
        
        List<Sensor> filtered = new ArrayList<>();
        for (Sensor sensor : sensors) {
            if (sensor.getType().equalsIgnoreCase(type)) {
                filtered.add(sensor);
            }
        }
        
        
        return filtered;
    }
    
    
    @GET
    @Path("/{sensorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor getSensorById(@PathParam("sensorId") String id) {
        Sensor sensor = sensorDAO.getById(id);
        if (sensor == null) {
            throw new DataNotFoundException("Sensor with ID " + id + " not found.");
        }
        return sensor;
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSensor(Sensor sensor) {
        
        // we get the roomId provided in the request body
        String targetRoomId = sensor.getRoomId();
        
        // safety check for null roomId in the request
        if (targetRoomId == null || targetRoomId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("A valid room id must be provided.")
                           .build();
        }
        
        // verify with RoomDAO if the roomId is valid
        if (roomDAO.getById(targetRoomId) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\": \"Cannot register sensor. Room '" + targetRoomId + "' does not exist.\"}")
                           .build();
        }
        
        sensorDAO.add(sensor);
        
        return Response.status(Response.Status.CREATED)
                   .entity(sensor)
                   .build();
    }
    
    
    @DELETE
    @Path("/{sensorId}")
    public void deleteSensor(@PathParam("sensorId") String id) {
        Sensor existingSensor = sensorDAO.getById(id);
        if (existingSensor == null) {
            throw new DataNotFoundException("Sensor with ID " + id + " not found.");
        }
        sensorDAO.delete(id);
    }
    
    
    // Sub-resource locator
    @Path("{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        // We pass the sensorId to the next class so it knows which sensor's readings to show
        return new SensorReadingResource(sensorId);
    }
    
}

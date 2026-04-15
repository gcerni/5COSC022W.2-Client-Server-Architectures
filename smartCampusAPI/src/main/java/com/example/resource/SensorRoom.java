package com.example.resource;

import com.example.dao.GenericDAO;
import com.example.dao.MockData;
import com.example.exception.DataNotFoundException;
import com.example.exception.RoomNotEmptyException;
import com.example.model.Room;
import com.example.model.Sensor;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

// This maps directly to /api/v1
@Path("/rooms") 
public class SensorRoom {
    
    private GenericDAO<Room> roomDAO = new GenericDAO<>(MockData.ROOMS);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms() {
        return roomDAO.getAll();
    }
    
    
    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoomById(@PathParam("roomId") String id) {
        Room room = roomDAO.getById(id);
        if (room == null) {
            throw new DataNotFoundException("Room with ID " + id + " not found.");
        }
        return room;
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room) {
        roomDAO.add(room);
        
        return Response.status(Response.Status.CREATED)
                   .entity("Room created successfully with ID: " + room.getId()) 
                   .build();
    }
    
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String id) {
        
        // We check if the room has any sensors assigned to it
        boolean hasSensor = false;
        for (Sensor sensor : MockData.SENSORS) {
            if (sensor.getRoomId().equals(id)) {
                hasSensor = true;
                break;
            }
        }
        
        // if room has a sensor assigned to it, we throw a message
        if (hasSensor) {
            throw new RoomNotEmptyException("The room " + id + " has currently sensor/s assigned to it.");
        }
        
        
        Room existingRoom = roomDAO.getById(id);
        if (existingRoom == null) {
            throw new DataNotFoundException("Room with ID " + id + " not found.");
        }
        roomDAO.delete(id);
        // Return a response 204 Success after deleting the room
        return Response.noContent().build();
    }
    
    
}

package com.example.resource;

import com.example.dao.GenericDAO;
import com.example.dao.MockData;
import com.example.exception.DataNotFoundException;
import com.example.model.Room;
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
    public void deleteRoom(@PathParam("roomId") String id) {
        Room existingRoom = roomDAO.getById(id);
        if (existingRoom == null) {
            throw new DataNotFoundException("Room with ID " + id + " not found.");
        }
        roomDAO.delete(id);
    }
    
    
}

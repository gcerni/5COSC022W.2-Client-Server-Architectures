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
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;

// This maps directly to /api/v1
@Path("/rooms") 
public class SensorRoom {
    
    private GenericDAO<Room> roomDAO = new GenericDAO<>(MockData.ROOMS);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllTeachers() {
        return roomDAO.getAll();
    }
    
    
    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoomById(@PathParam("roomId") String id) {
        Room teacher = roomDAO.getById(id);
        if (teacher == null) {
            throw new DataNotFoundException("Teacher with ID " + id + " not found.");
        }
        return teacher;
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addTeacher(Room room) {
        roomDAO.add(room);
    }
    
    
}

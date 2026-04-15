package com.example.resource;

import com.example.dao.MockData;
import com.example.exception.SensorUnavailableException;
import com.example.model.Sensor;
import com.example.model.SensorReading;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SensorReadingResource {

    private String sensorId;

    // Constructor to receive the ID from the locator
    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory() {

        // we get the list from the Map
        List<SensorReading> history = MockData.SENSORREADINGS.get(this.sensorId);

        // We check for the list to be null or empty
        if (history == null || history.isEmpty()) {
            // Return a 404 Not Found for an empty collection
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No reading history found for sensor: " + this.sensorId)
                    .build();
        }

        // If history is present we return a 201 and the list
        return Response.ok(history).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading newReading) {

        // We check the parent sensor to save its ID
        Sensor parentSensor = null;
        for (Sensor sensor : MockData.SENSORS) {
            if (sensor.getId().equals(this.sensorId)) {
                parentSensor = sensor;
                break;
            }
        }

        // If parentSensor doesn't exist we return a 404 Response.
        if (parentSensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor context " + this.sensorId + " not found.")
                    .build();
        }

        // Now we check the status of the parentSensor, so that if it's a maintenance status we throw an error.
        if ("MAINTENANCE".equalsIgnoreCase(parentSensor.getStatus())) {
            throw new SensorUnavailableException("Sensor " + this.sensorId
                    + " is currently under maintenance and cannot accept new readings.");
        }

        // Preparing data for UUID and the current timestamp
        newReading.setId(UUID.randomUUID().toString());
        newReading.setTimestamp(System.currentTimeMillis());

        
        // Now we update the History or add new history to the sensor.
        List<SensorReading> history = MockData.SENSORREADINGS.get(this.sensorId);
        if (history == null) {
            // Create a brand new list for this sensor
            history = new ArrayList<>();
            MockData.SENSORREADINGS.put(this.sensorId, history);
        }

        // Save the reading to the history list
        history.add(newReading);

        // Update currentValue on the parentSensor
        parentSensor.setCurrentValue(newReading.getValue());

        return Response.status(Response.Status.CREATED)
                .entity(newReading)
                .build();
    }
}

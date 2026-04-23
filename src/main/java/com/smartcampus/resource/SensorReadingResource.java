/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

public class SensorReadingResource {

    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorReading> getReadings() {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor not found");
        }

        return DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
    }

   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {

        
        if (reading == null) {
            throw new BadRequestException("Reading cannot be null");
        }

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor not found");
        }

        
        if (sensor.getStatus() == null ||
            "MAINTENANCE".equalsIgnoreCase(sensor.getStatus()) ||
            "OFFLINE".equalsIgnoreCase(sensor.getStatus())) {

            throw new SensorUnavailableException("Sensor unavailable");
        }

        
        DataStore.readings
                .computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        
        sensor.setCurrentValue(reading.getValue());

        
        return Response.status(Response.Status.CREATED)
                .entity("{\"message\":\"Reading added\"}")
                .build();
    }
}
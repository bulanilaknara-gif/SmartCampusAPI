/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

public class SensorReadingResource {

    private String sensorId;
    private Map<String, Sensor> sensors;
    private Map<String, List<SensorReading>> readingsMap;

    public SensorReadingResource(String sensorId,
                                 Map<String, Sensor> sensors,
                                 Map<String, List<SensorReading>> readingsMap) {
        this.sensorId = sensorId;
        this.sensors = sensors;
        this.readingsMap = readingsMap;
    }

    @GET
    public Response getReadings() {
        return Response.ok(
                readingsMap.getOrDefault(sensorId, new ArrayList<>())
        ).build();
    }

    @POST
    public Response addReading(SensorReading reading) {

        Sensor sensor = sensors.get(sensorId);

        
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is under maintenance");
        }

        reading.setId(UUID.randomUUID().toString());
        reading.setTimestamp(System.currentTimeMillis());

        readingsMap.computeIfAbsent(sensorId, k -> new ArrayList<>())
                   .add(reading);

     
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
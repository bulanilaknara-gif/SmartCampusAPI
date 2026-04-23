/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.storage.DataStore;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.Room;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/v1/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    
    @GET
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {

        if (type == null) {
            return DataStore.sensors.values();
        }

        List<Sensor> filtered = new ArrayList<>();

        for (Sensor s : DataStore.sensors.values()) {
            if (s.getType() != null && s.getType().equalsIgnoreCase(type)) {
                filtered.add(s);
            }
        }

        return filtered;
    }

    
    @GET
    @Path("/{sensorId}")
    public Sensor getSensor(@PathParam("sensorId") String id) {

        Sensor s = DataStore.sensors.get(id);

        if (s == null) {
            throw new NotFoundException("Sensor not found");
        }

        return s;
    }

   
    @POST
    public Response createSensor(Sensor sensor) {

       
        if (sensor == null ||
            sensor.getId() == null || sensor.getId().trim().isEmpty()) {

            throw new BadRequestException("Sensor id is required");
        }

        
        Room room = DataStore.rooms.get(sensor.getRoomId());

        if (room == null) {
            throw new LinkedResourceNotFoundException("Room does not exist");
        }

        
        if (DataStore.sensors.containsKey(sensor.getId())) {
            throw new BadRequestException("Sensor already exists");
        }

        
        DataStore.sensors.put(sensor.getId(), sensor);

        
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED)
                .entity("{\"message\":\"Sensor created\"}")
                .build();
    }

    
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadings(@PathParam("sensorId") String id) {

        if (!DataStore.sensors.containsKey(id)) {
            throw new NotFoundException("Sensor not found");
        }

        
        return new SensorReadingResource(id);
    }
}
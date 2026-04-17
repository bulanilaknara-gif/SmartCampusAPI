/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

@Path("/v1/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class SensorResource {

    // ✅ PART 4: Store readings per sensor
    private static Map<String, List<SensorReading>> readingsMap = new HashMap<>();

    // =========================
    // ✅ GET all sensors (with filter)
    // =========================
    @GET
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {

        if (type == null) {
            return DataStore.sensors.values();
        }

        return DataStore.sensors.values()
                .stream()
                .filter(sensor -> sensor.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    // =========================
    // ✅ POST create sensor
    // =========================
    @POST
    public Sensor createSensor(Sensor sensor) {

        // ✅ PART 5: Dependency validation (422)
        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room does not exist");
        }

        // Save sensor
        DataStore.sensors.put(sensor.getId(), sensor);

        // Add sensor to room
        DataStore.rooms.get(sensor.getRoomId())
                .getSensorIds()
                .add(sensor.getId());

        return sensor;
    }

    // =========================
    // ✅ PART 4: SUB-RESOURCE LOCATOR
    // =========================
    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadings(@PathParam("sensorId") String sensorId) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor not found");
        }

        return new SensorReadingResource(sensorId, DataStore.sensors, readingsMap);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;

@Path("/v1/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    
    @GET
    public Collection<Room> getRooms() {
        return DataStore.rooms.values();
    }

    
    @POST
    public Response createRoom(Room room) {

        if (room == null || room.getId() == null || room.getId().trim().isEmpty()) {
            throw new BadRequestException("Room id is required");
        }

        DataStore.rooms.put(room.getId(), room);

        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    
    @GET
    @Path("/{id}")
    public Room getRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            throw new NotFoundException("Room not found");
        }

        return room;
    }

    
    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            throw new NotFoundException("Room not found");
        }

        
        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room is not empty");
        }

        DataStore.rooms.remove(id);

        return Response.ok("{\"message\":\"Room deleted\"}").build();
    }
}
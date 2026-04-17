/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.storage.DataStore;
import com.smartcampus.exception.RoomNotEmptyException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import javax.ws.rs.core.Response;

@Path("/v1/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

   
    @GET
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }

  
    @POST
    public Room createRoom(Room room) {
        DataStore.rooms.put(room.getId(), room);
        return room;
    }

   
    @GET
    @Path("/{id}")
    public Room getRoomById(@PathParam("id") String id) {
        return DataStore.rooms.get(id);
    }

    
    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {

        if (!DataStore.rooms.containsKey(id)) {
            throw new NotFoundException("Room not found");
        }

        if (!DataStore.rooms.get(id).getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room has active sensors and cannot be deleted");
        }

        DataStore.rooms.remove(id);

        return Response.ok("Room deleted successfully").build();
}

   
}
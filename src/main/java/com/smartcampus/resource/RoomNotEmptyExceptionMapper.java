/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.*;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return Response.status(Response.Status.CONFLICT) // 409
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
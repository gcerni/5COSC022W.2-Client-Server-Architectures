package com.example.exception;

import com.example.model.ErrorMessage;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
    
    @Override
    public Response toResponse(DataNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
            exception.getMessage(), 
            404, 
            "Data not found. Please contact administrator."
        );
        
        return Response.status(Status.NOT_FOUND)
                       .entity(errorMessage)
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }
    
}
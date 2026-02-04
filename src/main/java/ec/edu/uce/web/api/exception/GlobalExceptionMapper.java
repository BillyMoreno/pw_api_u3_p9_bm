package ec.edu.uce.web.api.exception;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof BadRequestException) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(
                            "Bad Request",
                            exception.getMessage(),
                            400))
                    .build();
        }

        if (exception instanceof NotFoundException) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(
                            "Not Found",
                            exception.getMessage(),
                            404))
                    .build();
        }

        // Error genérico
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(
                        "Internal Server Error",
                        exception.getMessage() != null ? exception.getMessage() : "An unexpected error occurred",
                        500))
                .build();
    }
}

package io.savioromario10.quarkussocial.rest.dto;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;

import lombok.Data;

@Data
public class ResponseError {

  public static final int UNPROCESSABLE_ENTITY = 422;

  private String message;
  private Collection<FieldError> errors;

  public ResponseError(String message, Collection<FieldError> errors) {
    this.message = message;
    this.errors = errors;
  }

  public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>> violations) {

    Collection<FieldError> fieldErrors = violations
    .stream()
        .map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
        .collect(Collectors.toList());

    return new ResponseError("Validation failed", fieldErrors);
  }

  public Response withStatusCode(int statusCode) {
    return Response.status(statusCode).entity(this).build();
  }
}
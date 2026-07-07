package io.savioromario10.quarkussocial.rest;

import java.util.Set;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

import io.savioromario10.quarkussocial.domain.model.User;
import io.savioromario10.quarkussocial.domain.repository.UserRepository;
import io.savioromario10.quarkussocial.rest.dto.CreateUserRequest;
import io.savioromario10.quarkussocial.rest.dto.ResponseError;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

  private UserRepository repository;
  private Validator validator;

  @Inject
  public UserResource(UserRepository repository, Validator validator) {
    this.repository = repository;
    this.validator = validator;
  }

  @POST
  @Transactional
  public Response createUser(CreateUserRequest userRequest) {

    Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);

    if(!violations.isEmpty()){
      
      return ResponseError
        .createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY);
    }

    User user = new User();
    user.setName(userRequest.getName());
    user.setAge(userRequest.getAge());

    repository.persist(user);

    return Response
        .status(Response.Status.CREATED.getStatusCode())
        .entity(user).build();
  }

  @GET
  public Response listUsers(){
    PanacheQuery<User> users = repository.findAll();

    return Response.ok(users.list()).build();
  }
  
  @GET
  @Path("/{id}")
  public Response findById(@PathParam("id") Long id){
    User user = repository.findById(id);

    if(user != null){
      return Response.ok(user).build();
    }

    return Response.status(Response.Status.NOT_FOUND).build();
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public Response deleteUser(@PathParam("id") Long id){
    User user = repository.findById(id);

    if(user != null){
      repository.delete(user);
      return Response.noContent().build();
    }
    
    return Response.status(Response.Status.NOT_FOUND).build();
  }

  @PUT
  @Path("/{id}")
  @Transactional
  public Response updateUser(@PathParam("id") Long id, CreateUserRequest userRequest){

    User user = repository.findById(id);

    if(user != null){
      user.setName(userRequest.getName());
      user.setAge(userRequest.getAge());
      return Response.noContent().build();
    }

    return Response.status(Response.Status.NOT_FOUND).build();
  }
}
package io.savioromario10.quarkussocial.rest;

import jakarta.transaction.Transactional;
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
import io.savioromario10.quarkussocial.rest.dto.CreateUserRequest;

@Path("/users")
public class UserResource {

  @POST
  @Transactional
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createUser(CreateUserRequest userRequest) {

    User user = new User();
    user.setName(userRequest.getName());
    user.setAge(userRequest.getAge());

    user.persist();

    return Response.ok(user).build();
  }

  @GET
  public Response listUsers(){
    PanacheQuery<User> users = User.findAll();

    return Response.ok(users.list()).build();
  }
  
  @GET
  @Path("/{id}")
  public Response findById(@PathParam("id") Long id){
    User user = User.findById(id);

    if(user != null){
      return Response.ok(user).build();
    }

    return Response.status(Response.Status.NOT_FOUND).build();
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public Response deleteUser(@PathParam("id") Long id){
    User user = User.findById(id);

    if(user != null){
      user.delete();
      return Response.noContent().build();
    }
    
    return Response.status(Response.Status.NOT_FOUND).build();
  }

  @PUT
  @Path("/{id}")
  @Transactional
  public Response updateUser(@PathParam("id") Long id, CreateUserRequest userRequest){

    User user = User.findById(id);

    if(user != null){
      user.setName(userRequest.getName());
      user.setAge(userRequest.getAge());
      return Response.ok(user).build();
    }

    return Response.status(Response.Status.NOT_FOUND).build();
  }
}
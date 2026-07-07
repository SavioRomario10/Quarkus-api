package io.savioromario10.quarkussocial.rest;

import java.util.stream.Collectors;

import io.quarkus.panache.common.Sort;
import io.savioromario10.quarkussocial.domain.model.Post;
import io.savioromario10.quarkussocial.domain.model.User;
import io.savioromario10.quarkussocial.domain.repository.PostRepository;
import io.savioromario10.quarkussocial.domain.repository.UserRepository;
import io.savioromario10.quarkussocial.rest.dto.CreatePostRequest;
import io.savioromario10.quarkussocial.rest.dto.PostResponse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

  private UserRepository repository;
  private PostRepository postRepository;

  @Inject
  public PostResource(UserRepository repository, PostRepository postRepository) {
    this.repository = repository;
    this.postRepository = postRepository;
  }

  @POST
  @Transactional
  public Response savePost(
    @PathParam("userId") Long userId, CreatePostRequest postRequest){

    User user = repository.findById(userId);

    if(user == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    Post post = new Post();
    post.setText(postRequest.getText());
    post.setUser(user);

    postRepository.persist(post);

    return Response.status(Response.Status.CREATED).build();
  }

  @GET
  public Response listar(@PathParam("userId") Long userId){

    User user = repository.findById(userId);

    if(user == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    var query = postRepository.find("user",Sort.by("dateTime", Sort.Direction.Descending), user);

    var list = query.list();

    var postResponseList = list.stream().map(PostResponse::fromEntity).collect(Collectors.toList());

    return Response.ok(postResponseList).build();
  }
}
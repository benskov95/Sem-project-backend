package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.UserDTO;
import errorhandling.MissingInput;
import facades.UserFacade;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("users")
public class UserResource {


    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final UserFacade USER_FACADE = UserFacade.getUserFacade(EMF);
            

    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public String getNumberOfUsers() {
        int numberOfUsers = USER_FACADE.getAllUsers().size();
        return "{\"count\":" + numberOfUsers + "}";
    }
    
    @GET
//    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public String getUsers() {
        List<UserDTO> dtoList = USER_FACADE.getAllUsers();
        return GSON.toJson(dtoList);
    }

    @DELETE
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public String deleteUser(@PathParam("username") String userName) {
        UserDTO userDTO = USER_FACADE.deleteUser(userName);

        return GSON.toJson(userDTO);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addUser(String user) throws  AuthenticationException {
        UserDTO userDTO = GSON.fromJson(user, UserDTO.class);
        UserDTO newUser = USER_FACADE.addUser(userDTO);
        return GSON.toJson(newUser);
    }
    
    @PUT
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String editUser(@PathParam("username") String currentName, String user) throws MissingInput, AuthenticationException {
        UserDTO userDTO = GSON.fromJson(user, UserDTO.class);
        UserDTO editedUser = USER_FACADE.editUser(userDTO, currentName);
        return GSON.toJson(editedUser);
    }
    
    @POST
    @Path("change-pw/{newPw}")
    @Consumes({MediaType.APPLICATION_JSON})
    public String changePassword(@PathParam("newPw") String newPw, String user) {
        UserDTO userDTO = GSON.fromJson(user, UserDTO.class);
        USER_FACADE.changePassword(userDTO, newPw);
        return "Great success";
    }

}

package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CommentDTO;
import dto.MemeDTO;
import facades.MemeFacade;
import fetchers.CatFetcher;
import fetchers.FunnyFetcher;
import fetchers.YesOrNoFetcher;
import fetchers.DogFetcher;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import utils.EMF_Creator;

@Path("memes")
public class MemeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static ExecutorService es = Executors.newCachedThreadPool();
    public static final MemeFacade MEME_FACADE = MemeFacade.getMemeFacade(EMF);


    @GET
    @Path("/funny")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFunny() throws IOException, InterruptedException, ExecutionException, TimeoutException {

        String funnys = FunnyFetcher.fetchFunny(es, gson);

        return funnys;
    }

    @GET
    @Path("/cat")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCat() throws InterruptedException, ExecutionException, TimeoutException {

        String cats = CatFetcher.fetchCat(es, gson);

        return cats;
    }

    @GET
    @Path("/yesorno")
    @Produces(MediaType.APPLICATION_JSON)
    public String getYesOrNo() throws InterruptedException, ExecutionException, TimeoutException {

        String yesOrNo = YesOrNoFetcher.fetchYesOrNo(es, gson);

        return yesOrNo;
    }

    @GET
    @Path("/dog")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDog() throws InterruptedException, ExecutionException, TimeoutException {

        String dogs = DogFetcher.fetchDog(es, gson);

        return dogs;
    }
    
    @POST
    @Path("upvote/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public String upvoteMeme(@PathParam("username") String username, String meme) {
        MemeDTO memeDTO = gson.fromJson(meme, MemeDTO.class);
        MemeDTO upvotedMeme = MEME_FACADE.upvoteMeme(username, memeDTO);
        return gson.toJson(upvotedMeme);
    }

    @POST
    @Path("downvote/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public String downvoteMeme(@PathParam("username") String username, String meme) {
        MemeDTO memeDTO = gson.fromJson(meme, MemeDTO.class);
        MemeDTO downvotedMeme = MEME_FACADE.downvoteMeme(username, memeDTO);
        return gson.toJson(downvotedMeme);
    }


    @POST
    @Path("/comment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"user", "admin"})
    public String addComment(String comment) {

        CommentDTO commentDTO = gson.fromJson(comment , CommentDTO.class);
        CommentDTO newComment = MEME_FACADE.addComment(commentDTO);

        return gson.toJson(newComment);


    }

    @GET
    @Path("/comment/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public String getComment(@PathParam("id") int id) {
        List<CommentDTO> commentDTOList = MEME_FACADE.getAllCommentsById(id);
        return gson.toJson(commentDTOList);
    }


    @GET
    @Path("/cold")
    @Produces(MediaType.APPLICATION_JSON)
    public String getColdList() {
        List<MemeDTO> memeDTOsList = MEME_FACADE.getAllDownvotedMemes();
        return gson.toJson(memeDTOsList);
    }
    
    @GET
    @Path("/hot")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHotList() {
        List<MemeDTO> memeDTOsList = MEME_FACADE.getAllUpvotedMemes();
        return gson.toJson(memeDTOsList);
    }
    
    @GET
    @Path("/favorite/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public String getFavorite (@PathParam("username") String userName) {
        List<MemeDTO> memeDTOsList = MEME_FACADE.getFavoriteMemes(userName);
        
       return gson.toJson(memeDTOsList);
    }

}

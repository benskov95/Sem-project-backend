package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MemeDTO;
import facades.MemeFacade;
import fetchers.CatFetcher;
import fetchers.FunnyFetcher;
import fetchers.YesOrNoFetcher;
import fetchers.DogFetcher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
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

        String funnys = FunnyFetcher.fetchFunny(es,gson);

        return funnys;
    }


    @GET
    @Path("/cat")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCat () throws InterruptedException, ExecutionException, TimeoutException {

        String cats = CatFetcher.fetchCat(es,gson);

        return cats;
    }

    @GET
    @Path("/yesorno")
    @Produces(MediaType.APPLICATION_JSON)
    public String getYesOrNo () throws InterruptedException, ExecutionException, TimeoutException {

        String yesOrNo = YesOrNoFetcher.fetchYesOrNo(es,gson);

        return yesOrNo;
    }
    
    @GET
    @Path("/dog")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDog () throws InterruptedException, ExecutionException, TimeoutException {

        String dogs = DogFetcher.fetchDog(es,gson);

        return dogs;
    }
    
    @POST
    @Path("upvote/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String upvoteMeme(@PathParam("username") String username, String meme) {
        MemeDTO memeDTO = gson.fromJson(meme, MemeDTO.class);
        int currentUpvotes = MEME_FACADE.upvoteMeme(username, memeDTO);
        return "{\"currentUpvotes\":" + currentUpvotes + "}";
    }
    // add role
    @POST
    @Path("downvote/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String downvoteMeme(@PathParam("username") String username, String meme) {
        MemeDTO memeDTO = gson.fromJson(meme, MemeDTO.class);
        int currentDownvotes = MEME_FACADE.downvoteMeme(username, memeDTO);
        return "{\"currentDownvotes\":" + currentDownvotes + "}";
    }
   
}

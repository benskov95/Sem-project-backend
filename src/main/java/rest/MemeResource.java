package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fetchers.CatFetcher;
import fetchers.FunnyFetcher;
import fetchers.YesOrNoFetcher;
import fetchers.DogFetcher;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

@Path("memes")
public class MemeResource {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static ExecutorService es = Executors.newCachedThreadPool();

   
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
    @Path("/comment/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addComment(@PathParam("id") String id, String comment) {

        return null;


    }


   
}

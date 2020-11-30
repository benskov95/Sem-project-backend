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
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

@Path("memes")
public class MemeResource {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static ExecutorService es = Executors.newCachedThreadPool();
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
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
}

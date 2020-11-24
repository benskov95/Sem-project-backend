package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fetchers.FunnyFetcher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public String getData() throws IOException, InterruptedException, ExecutionException, TimeoutException {

        String data = FunnyFetcher.fetchFunny(es,gson);

        return data;
    }





   
}

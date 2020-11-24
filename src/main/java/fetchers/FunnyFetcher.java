package fetchers;

import com.google.gson.Gson;
import dto.FunnyDTO;
import dto.MemeDTO;
import utils.HttpUtils;

import java.util.concurrent.*;

public class FunnyFetcher {

    private static String funnyURL = "https://meme-api.herokuapp.com/gimme/";

    public static String fetchFunny(ExecutorService threadpool, Gson gson) throws InterruptedException, ExecutionException, TimeoutException {

        Callable<FunnyDTO> funnyTask = new Callable<FunnyDTO>() {
            @Override
            public FunnyDTO call() throws Exception {
                String funny = HttpUtils.fetchData(funnyURL);
                FunnyDTO funnyDTO = gson.fromJson(funny, FunnyDTO.class);
                return funnyDTO;
            }
        };

        Future<FunnyDTO> futureFunny = threadpool.submit(funnyTask);

        FunnyDTO funny = futureFunny.get(5, TimeUnit.SECONDS);

        MemeDTO memeDTO = new MemeDTO(funny);


        return gson.toJson(memeDTO);
    }
}
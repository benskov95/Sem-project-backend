package fetchers;

import com.google.gson.Gson;
import dto.FunnyDTO;
import dto.MemeDTO;
import utils.HttpUtils;

import java.util.ArrayList;
import java.util.concurrent.*;

public class FunnyFetcher {

    private static String funnyURL = "https://meme-api.herokuapp.com/gimme/";
    private static ArrayList<MemeDTO> tasks = new ArrayList<>();
    private static int numberOfTasks = 5;

    public static String fetchFunny(ExecutorService threadpool, Gson gson) throws InterruptedException, ExecutionException, TimeoutException {

        Callable<FunnyDTO> funnyTask = new Callable<FunnyDTO>() {
            @Override
            public FunnyDTO call() throws Exception {
                String funny = HttpUtils.fetchData(funnyURL);
                FunnyDTO funnyDTO = gson.fromJson(funny, FunnyDTO.class);
                return funnyDTO;
            }
        };

        for (int i = 0; i < numberOfTasks; i++) {
            Future<FunnyDTO> futureFunny = threadpool.submit(funnyTask);
            FunnyDTO funny = futureFunny.get(5, TimeUnit.SECONDS);
            MemeDTO memeDTO = new MemeDTO(funny);
            tasks.add(memeDTO);
        }

        return gson.toJson(tasks);
    }
}
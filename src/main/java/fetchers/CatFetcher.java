package fetchers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.CatDTO;
import dto.FunnyDTO;
import dto.MemeDTO;
import utils.HttpUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CatFetcher {

    private static String catURL = "https://api.thecatapi.com/v1/images/search";

    private static int numberOfTasks = 5;

    public static String fetchCat(ExecutorService threadpool, Gson gson) throws InterruptedException, ExecutionException, TimeoutException {


        ArrayList<MemeDTO> tasks = new ArrayList<>();

        Callable<CatDTO> catTask = new Callable<CatDTO>() {
            @Override
            public CatDTO call() throws Exception {
                String cat = HttpUtils.fetchData(catURL);
                Type listType = new TypeToken<List<CatDTO>>() {}.getType();

                List<CatDTO> catDTO = gson.fromJson(cat,listType);

                return catDTO.get(0);
            }
        };

        for (int i = 0; i < numberOfTasks; i++) {
            Future<CatDTO> futureCat = threadpool.submit(catTask);
            CatDTO cat = futureCat.get(5, TimeUnit.SECONDS);
            MemeDTO memeDTO = new MemeDTO(cat);
            tasks.add(memeDTO);
        }

        return gson.toJson(tasks);
    }
}

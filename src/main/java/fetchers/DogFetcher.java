package fetchers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.DogDTO;
import dto.MemeDTO;
import utils.HttpUtils;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DogFetcher {

    private static String dogURL = "https://dog.ceo/api/breeds/image/random";

    private static int numberOfTasks = 5;

    public static String fetchDog(ExecutorService threadpool, Gson gson) throws InterruptedException, ExecutionException, TimeoutException {


        ArrayList<MemeDTO> tasks = new ArrayList<>();

        Callable<DogDTO> dogTask = new Callable<DogDTO>() {
            @Override
            public DogDTO call() throws Exception {
                String dog = HttpUtils.fetchData(dogURL);
                Type listType = new TypeToken<List<DogDTO>>() {}.getType();

                List<DogDTO> dogDTO = gson.fromJson(dog,listType);

                return dogDTO.get(0);
            }
        };

        for (int i = 0; i < numberOfTasks; i++) {
            Future<DogDTO> futureDog = threadpool.submit(dogTask);
            DogDTO dog = futureDog.get(5, TimeUnit.SECONDS);
            MemeDTO memeDTO = new MemeDTO(dog);
            tasks.add(memeDTO);
        }

        return gson.toJson(tasks);
    }
}

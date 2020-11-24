package fetchers;

import com.google.gson.Gson;
import dto.FunnyDTO;
import dto.MemeDTO;
import dto.YesOrNoDTO;
import entities.Meme;
import utils.HttpUtils;

import java.util.ArrayList;
import java.util.concurrent.*;

public class YesOrNoFetcher {


    private static String yesOrNoURL = "https://yesno.wtf/api/";
    private static int numberOfTasks = 5;

    public static String fetchYesOrNo(ExecutorService threadpool, Gson gson) throws InterruptedException, ExecutionException, TimeoutException {

        ArrayList<MemeDTO> tasks = new ArrayList<>();


        Callable<YesOrNoDTO> catTask = new Callable<YesOrNoDTO>() {
            @Override
            public YesOrNoDTO call() throws Exception {
                String yesOrNo = HttpUtils.fetchData(yesOrNoURL);
                YesOrNoDTO yesOrNoDTO = gson.fromJson(yesOrNo, YesOrNoDTO.class);
                return yesOrNoDTO;
            }
        };

        for (int i = 0; i < numberOfTasks; i++) {
            Future<YesOrNoDTO> futureYesOrNo = threadpool.submit(catTask);
            YesOrNoDTO yesOrNo = futureYesOrNo.get(5, TimeUnit.SECONDS);
            MemeDTO memeDTO = new MemeDTO(yesOrNo);
            tasks.add(memeDTO);
        }

        return gson.toJson(tasks);
    }
}

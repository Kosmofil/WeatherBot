import com.kosmos.controller.TelegramBot;
import com.kosmos.controller.handler.GetBashorg;


import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        GetBashorg bash = new GetBashorg();
        Thread myThread = new Thread(bash);
        myThread.start();

        TelegramBot.runBot();
    }

}
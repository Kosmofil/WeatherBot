import com.kosmos.controller.TelegramBot;

public class Main{

    public static void main(String[] args) throws ExceptionInInitializerError {
        try {
            TelegramBot.runBot();
        } catch (ExceptionInInitializerError e) {
            e.getMessage();
        }
    }

}
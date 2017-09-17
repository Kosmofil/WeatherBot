
import com.kosmos.controller.handler.GetBashorg;
import com.kosmos.view.MainWindow;
import java.io.IOException;


public class Main{

    public static void main(String[] args) throws IOException {

        GetBashorg bash = new GetBashorg();
        Thread myThread = new Thread(bash);
        myThread.start();

//        myThread.isInterrupted();//проверка на
//        myThread.interrupt();//Безопасно закрываеть поток


       MainWindow window = new MainWindow();
        window.setVisible(true);

//        LoginWindow loginWindow = new LoginWindow();
//        loginWindow.setVisible(true);
//        TelegramBot.runBot();
    }

}
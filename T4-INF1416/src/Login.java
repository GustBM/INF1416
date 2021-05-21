import screen.LoginFrame;
import service.dbConnect;

public class Login {
    public static void main(String[] a) throws Exception {
    	dbConnect.register(1001);
        new LoginFrame();
    }

}

import screen.LoginFrame;
import screen.NewUserForm;
import service.dbConnect;

public class Login {
    public static void main(String[] a) {
    	dbConnect.register(1001);
        new LoginFrame();
    	// new NewUserForm();
    }

}

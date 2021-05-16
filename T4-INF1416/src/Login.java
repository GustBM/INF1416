import screen.LogView;
import screen.LoginFrame;
import screen.NewUserForm;
import screen.UserFrame;
import service.dbConnect;

public class Login {
    public static void main(String[] a) {
    	dbConnect.register(1001);
        new LoginFrame();
        // new UserFrame();
    	// new NewUserForm();
    	// new LogView();
    }

}

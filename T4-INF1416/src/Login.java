import screen.LogView;
import screen.LoginFrame;
import screen.NewUserForm;
import screen.UserFrame;
import service.AuthenticationService;
import service.dbConnect;

public class Login {
    public static void main(String[] a) {
    	dbConnect.register(1001);
    	// AuthenticationService.getInstance().checkUserEmail("daniela@gmail.com", true);
        new LoginFrame();
        // new UserFrame();
    	// new NewUserForm();
    	// new LogView();
    }

}

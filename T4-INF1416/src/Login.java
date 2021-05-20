import screen.LogView;
import screen.LoginFrame;
import screen.NewUserForm;
import screen.UserFrame;
import service.AuthenticationService;
import service.dbConnect;

public class Login {
    public static void main(String[] a) {
    	dbConnect.register(1001);
    	AuthenticationService.getInstance().checkUserEmail("admin@inf1416.puc-rio.br", true);
        // new LoginFrame();
        new UserFrame();
    	// new NewUserForm();
    	// new LogView();
    }

}

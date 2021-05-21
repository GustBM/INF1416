import java.nio.file.Path;
import java.nio.file.Paths;

import screen.FileScreen;
import screen.KeyVerificationFrame;
import screen.LogView;
import screen.LoginFrame;
import screen.NewUserForm;
import screen.UserFrame;
import service.AuthenticationService;
import service.dbConnect;

public class Login {
    public static void main(String[] a) throws Exception {
    	dbConnect.register(1001);
    	AuthenticationService.getInstance().checkUserEmail("user01@inf1416.puc-rio.br", true);
    	Path path = Paths.get("C:\\Users\\ADM\\Desktop\\T4-INF1416\\Pacote-T4\\Keys\\user01-pkcs8-des.key");
    	AuthenticationService.getPrivateKey("user01", path);
        // new LoginFrame();
        // new UserFrame();
    	// new KeyVerificationFrame();
    	// new NewUserForm();
    	// new LogView();
    	new FileScreen();
    }

}

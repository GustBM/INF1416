import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import model.User;
import screen.LogView;
import screen.LoginFrame;
import screen.NewUserForm;
import screen.UserFrame;
import service.AuthenticationService;
import service.CertKeyTools;
import service.dbConnect;

public class Login {
    public static void main(String[] a) throws CertificateException {
    	dbConnect.register(1001);
    	AuthenticationService.getInstance().checkUserEmail("admin@admin.com", true);
        new UserFrame();
    	// new NewUserForm();
    	// new LogView();
    	// System.out.println(CertKeyTools.generatePublicKeyFromCertificate("asdf@gmail.com"));
    }

}

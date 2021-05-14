import javax.swing.JFrame;

import screen.LoginFrame;
import service.dbConnect;

public class Login {
    public static void main(String[] a) {
    	dbConnect.register(1001);
        LoginFrame frame = new LoginFrame();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(10, 10, 370, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

}

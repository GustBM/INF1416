package screen;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import model.User;

public class UserFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private User user;
	
	Container container = getContentPane();
	
	public UserFrame(User user) {
		this.user = user;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

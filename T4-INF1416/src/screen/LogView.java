package screen;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.User;
import service.AuthenticationService;
import service.dbConnect;

public class LogView extends JFrame {    
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private User user;
		JFrame f = new JFrame();
		
		// Cabeçalho
		private JLabel tuserName = new JLabel("Nome: ");
		private JLabel tuserGroup = new JLabel("Grupo: ");
		private JLabel tuserLogin = new JLabel("Login: ");
		private JLabel userName = new JLabel("");
		private JLabel userGroup = new JLabel("");
		private JLabel userLogin = new JLabel("");
		
	    public LogView(){     
	    	// AuthenticationService.getInstance().checkUserEmail("asdf@gmail.com", true);
	    	this.user = AuthenticationService.getInstance().getUser();
	    	setLogTable();
		}
	    
	    private void setLogTable() {
	      String data[][];
	      data = dbConnect.setLogTable();
		  String column[]={"Cód.","Mensagem","Usuário", "Arquivo", "Data"};         
		  JTable jt=new JTable(data,column);    
		  jt.setBounds(30,150,200,300);
		  JScrollPane sp=new JScrollPane(jt);    
		  f.add(sp);          
		  f.setSize(300,400);    
		  f.setBounds(300, 90, 500, 600);
		  f.setVisible(true); 
	    }
	
}

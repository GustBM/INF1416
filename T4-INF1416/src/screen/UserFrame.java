package screen;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.User;
import service.AuthenticationService;
import service.dbConnect;

public class UserFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private User user;
	
	Container c = getContentPane();
	// Cabecalho
	private JLabel tuserName = new JLabel("Nome: ");
	private JLabel tuserGroup = new JLabel("Grupo: ");
	private JLabel tuserLogin = new JLabel("Login: ");
	private JLabel userName = new JLabel("");
	private JLabel userGroup = new JLabel("");
	private JLabel userLogin = new JLabel("");
	
	// Corpo 1
	private JLabel ttotalAcess = new JLabel("Total de Acessos: ");
	private JLabel totalAcess = new JLabel("0");
	
	// Corpo 2
	private JLabel menuTitle = new JLabel("Menu Principal");
	private JButton optionButton1 = new JButton("Novo Usuario");
	private JButton optionButton2 = new JButton("<html>Alterar Senha e<br />Certificado</html>");
	private JButton optionButton3 = new JButton("Consultar Pasta");
	private JButton optionButton4 = new JButton("Sair");
	
	public UserFrame() {
		// AuthenticationService.getInstance().checkUserEmail("asdf@gmail.com", true);
		this.user = AuthenticationService.getInstance().getUser();
		setTitle("Bem-Vindo "+user.getName());
    	setVisible(true);
    	dbConnect.register(5001, user.getEmail(), "");
    	setBounds(300, 90, 500, 600);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    	
    	c = getContentPane();
        c.setLayout(null);

        setCabecalho ();
        setCorpo1();
        setCorpo2();
	}
	
	private void setCabecalho () {
		
		// Login
		tuserLogin.setSize(300, 30);
		tuserLogin.setLocation(150, 30);
		
		userLogin.setSize(300, 30);
		userLogin.setLocation(200, 30);
		
		// Grupo
		tuserGroup.setSize(300, 30);
		tuserGroup.setLocation(150, 60);
		
		userGroup.setSize(300, 30);
		userGroup.setLocation(200, 60);
		
		// Nome
		tuserName.setSize(300, 30);
		tuserName.setLocation(150, 90);
		
		userName.setSize(300, 30);
		userName.setLocation(200, 90);
		
		userLogin.setText(user.getEmail());
		userName.setText(user.getName());
		
		if(user.getgroup() == 1) userGroup.setText("Administrador");
		else userGroup.setText("Usuario Normal");
        
		c.add(tuserLogin);
        c.add(tuserGroup);
        c.add(tuserName);
        c.add(userLogin);
        c.add(userGroup);
        c.add(userName);
	}
	
	private void setCorpo1() {
		totalAcess.setSize(300, 30);
		ttotalAcess.setSize(300, 30);
		totalAcess.setLocation(270, 120);
		ttotalAcess.setLocation(150, 120);
		
		c.add(totalAcess);
		c.add(ttotalAcess);
	}
	
	private void setCorpo2() {
		menuTitle.setSize(300, 30);
		menuTitle.setLocation(200, 200);
		
		optionButton1.setBounds(90, 250, 150, 50);
		optionButton2.setBounds(90, 310, 150, 50);
		optionButton3.setBounds(250, 250, 150, 50);
		optionButton4.setBounds(250, 310, 150, 50);
		
		c.add(menuTitle);
		if(user.getgroup() == 1) c.add(optionButton1);
		c.add(optionButton2);
		c.add(optionButton3);
		c.add(optionButton4);
		
		optionButton1.addActionListener(this);
		optionButton2.addActionListener(this);
		optionButton3.addActionListener(this);
		optionButton4.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == optionButton1) {
			dbConnect.register(5002, user.getEmail(), "");
			dispose();
			new NewUserForm();
		}
		if (e.getSource() == optionButton4) {
			dbConnect.register(5005, user.getEmail(), "");
			int input = JOptionPane.showConfirmDialog(null, 
	                "Sair e Encerrar Programa?", "Confirmacao",JOptionPane.YES_NO_OPTION);
			if(input == 0) {
				dispose();
				dbConnect.register(1002);
			}
		}
		
	}
	
}

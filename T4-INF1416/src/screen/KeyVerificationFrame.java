package screen;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import model.User;
import service.AuthenticationService;
import service.dbConnect;

public class KeyVerificationFrame extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container c = getContentPane();
	private JLabel tsecretPhrase = new JLabel("Digite a frase secreta: ");
	private JPasswordField secretPhraseField = new JPasswordField();
	private JButton sendButton = new JButton("ENVIAR");
	private JButton closeButton = new JButton("VOLTAR");
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	int numTentativas = 3;
	
	public KeyVerificationFrame() {
		dbConnect.register(4001);
		setTitle("Verificacao das Chaves ");
		tsecretPhrase.setBounds(50, 20, 200, 30);
		secretPhraseField.setBounds(50, 50, 220, 30);
		sendButton.setBounds(50, 100, 100, 30);
		closeButton.setBounds(170, 100, 100, 30);
		addComponentsToContainer();
		setLayoutManager();
		this.setVisible(true);
    	this.setBounds(10, 10, 330, 200);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setResizable(false);
		
	}
	
	private void setLayoutManager() {
        c.setLayout(null);
    }
	
	private void addComponentsToContainer() {
		closeButton.addActionListener(this);
		sendButton.addActionListener(this);
        c.add(tsecretPhrase);
        c.add(secretPhraseField);
        c.add(sendButton);
        c.add(closeButton);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeButton) {
        	dispose();
        	String userEmail = AuthenticationService.getInstance().getUser().getEmail();
        	new LoginFrame(userEmail);
        }
		
		if (e.getSource() == sendButton) {
			User user = AuthenticationService.getInstance().getUser();
			String userEmail = AuthenticationService.getInstance().getUser().getEmail();
			boolean result = false;
			int index = userEmail.indexOf('@');
			String userName = userEmail.substring(0,index);
			String st = String.valueOf(secretPhraseField.getPassword());
			String pathString = "C:\\Users\\ADM\\eclipse-workspace\\T4-INF1416\\Pacote-T4\\Keys\\"+userName+"-pkcs8-des.key";
			Path path = Paths.get(pathString);
			
			try {
				publicKey = AuthenticationService.getPublicKey(user);
			} catch (Exception e2) {
				result = false;
			}
			
			try {
				privateKey = AuthenticationService.getPrivateKey(st, path);
			} catch (Exception e1) {
				if(e1.getMessage() == "Path invalido.") {
					dbConnect.register(4004, user.getEmail(), "");
				}
				result = false;
			}
			
			try {
				result = AuthenticationService.isPrivateKeyValid(privateKey, publicKey);
			} catch (InvalidKeyException e1) {
				dbConnect.register(4005, user.getEmail(), "");
				result = false;
			} catch (NoSuchAlgorithmException e1) {
				result = false;
			} catch (SignatureException e1) {
				dbConnect.register(4006, user.getEmail(), "");
				result = false;
			}
			
			if(result) {
				dbConnect.register(4003, user.getEmail(), "");
				dbConnect.register(4002);
				user.addTotalAccesses();
        		dbConnect.updateUser(user);
        		dispose();
        		new UserFrame();
			} else {
				numTentativas--;
				if(numTentativas < 1) {
					Date date = new Date();
        			user.setBloquedAt(new Timestamp(date.getTime()));
        			dbConnect.updateUser(user);
        			JOptionPane.showMessageDialog(this, "Usuario Bloqueado por excesso de erros");
        			dbConnect.register(4007, user.getEmail(), "");
        			dispose();
        			dbConnect.register(4002);
        			new LoginFrame();
				} else
					JOptionPane.showMessageDialog(this, "Erro ao verificar a chave. " + numTentativas + " Tentativas antes do bloqueio");
			}
        }
	}

}

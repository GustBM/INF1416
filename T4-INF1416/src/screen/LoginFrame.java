package screen;
import javax.swing.*;

import model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.List;

import service.AuthenticationService;
import service.dbConnect;

public class LoginFrame extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container container = getContentPane();
    JLabel userLabel = new JLabel("E-MAIL");
    JLabel passwordLabel = new JLabel("SENHA");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton nextButton = new JButton("ENVIAR");
    JButton loginButton = new JButton("PROXIMO");
    JButton resetButton = new JButton("LIMPAR");
    JButton restartButton = new JButton("REINICIAR");
    JButton pwdButton1 = new JButton("BA");
    JButton pwdButton2 = new JButton("CA");
    JButton pwdButton3 = new JButton("DA");
    JButton pwdButton4 = new JButton("FA");
    JButton pwdButton5 = new JButton("GA");
    JButton pwdButton6 = new JButton("HA");
    

    public LoginFrame() {
    	dbConnect.register(2001);
    	this.setTitle("Login Form");
    	this.setVisible(true);
    	this.setBounds(10, 10, 370, 400);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setResizable(false);
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        // organizeFoneticButtons();
    }

    private void setLayoutManager() {
        container.setLayout(null);
    }

    private void setLocationAndSize() {
        userLabel.setBounds(50, 50, 100, 30);
        passwordLabel.setBounds(50, 120, 100, 30);
        userTextField.setBounds(150, 50, 150, 30);
        passwordField.setBounds(150, 120, 150, 30);
        loginButton.setBounds(50, 200, 100, 30);
        nextButton.setBounds(50, 200, 100, 30);
        resetButton.setBounds(200, 200, 100, 30);
    }

    private void addComponentsToContainer() {
        container.add(userLabel);
        // container.add(passwordLabel);
        container.add(userTextField);
        // container.add(passwordField);
        container.add(loginButton);
        container.add(nextButton);
        container.add(resetButton);
        
    }

    private void addActionEvent() {
    	passwordField.setEditable(false);
        loginButton.addActionListener(this);
        nextButton.addActionListener(this);
        resetButton.addActionListener(this);
        pwdButton1.addActionListener(this);
        pwdButton2.addActionListener(this);
        pwdButton3.addActionListener(this);
        pwdButton4.addActionListener(this);
        pwdButton5.addActionListener(this);
        pwdButton6.addActionListener(this);
        restartButton.addActionListener(this);
    }

    private boolean regexEmail(String email) {
    	String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    	Pattern pattern = Pattern.compile(regex);
    	return pattern.matcher(email).matches();
    }
    
    private void organizeFoneticButtons() {
    	String[] stringArray = {"BA","CA","DA","FA","GA","HA","BE","CE","DE","FE","GE","HE","BO","CO","DO","FO","GO","HO"};
    	List<String> intList = Arrays.asList(stringArray);
		Collections.shuffle(intList);
		intList.toArray(stringArray);
    	pwdButton1.setText(stringArray[0]);
    	pwdButton2.setText(stringArray[1]);
    	pwdButton3.setText(stringArray[2]);
    	pwdButton4.setText(stringArray[3]);
    	pwdButton5.setText(stringArray[4]);
    	pwdButton6.setText(stringArray[5]);
    }
    
    private void verificationPhase2() {
    	userTextField.setEditable(false);
    	
    	loginButton.setVisible(false);
    	
    	container.add(passwordLabel);
    	container.add(passwordField);
    	
    	container.add(pwdButton1);
        container.add(pwdButton2);
        container.add(pwdButton3);
        container.add(pwdButton4);
        container.add(pwdButton5);
        container.add(pwdButton6);
        
        container.add(restartButton);
        restartButton.setBounds(120, 320, 120, 30);
        
    	pwdButton1.setBounds(90, 250, 60, 30);
        pwdButton2.setBounds(150, 250, 60, 30);
        pwdButton3.setBounds(210, 250, 60, 30);
        pwdButton4.setBounds(90, 280, 60, 30);
        pwdButton5.setBounds(150, 280, 60, 30);
        pwdButton6.setBounds(210, 280, 60, 30);
    }
    
    private void resetFrame() {
    	dispose();
    	dbConnect.register(1001);
        LoginFrame frame = new LoginFrame();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(10, 10, 370, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == loginButton) {
            String userText;
            userText = userTextField.getText();
            if(!regexEmail(userText)) {
            	JOptionPane.showMessageDialog(this, "Erro! E-mail Inválido!");
            	dbConnect.register(2005);
            	return;
            }

            if(AuthenticationService.getInstance().checkUserEmail(userText, true)) {
            	if(!AuthenticationService.getInstance().isUserBlocked(userText)) {
                	JOptionPane.showMessageDialog(this, "Usuário Bloqueado!");
                	dbConnect.register(2004);
                	return;
                }
            	dbConnect.register(2002);
                verificationPhase2();
                return;
            }else {
                JOptionPane.showMessageDialog(this, "Usuário Inválidos!");
                dbConnect.register(2005);
                return;
            }

        }
        
        // Botão reset
        if (e.getSource() == resetButton) {
            // userTextField.setText("");
            passwordField.setText("");
        }
        
        if (e.getSource() == restartButton) {
        	resetFrame();
        }
        
        if (e.getSource() == nextButton) {
        	String st = String.valueOf(passwordField.getPassword());
        	if(st.length() >= 12 || st.length() < 3) {
        		JOptionPane.showMessageDialog(this, "Senha deve ter entre 3 a 6 fonemas.");
        		return;
        	}
        	User user = AuthenticationService.getInstance().getUser();
        	if(dbConnect.checkUserPassword(st, user)) {
        		JOptionPane.showMessageDialog(this, "Acesso Concedido, bem-vindo " + user.getName());
        		dispose();
        		new UserFrame();
        	} else {
        		user.addTotalAccesses();
        		if(user.getTotalAccesses() == 1) {
        			JOptionPane.showMessageDialog(this, "Senha incorreta! Mais 2 tentativas antes do bloqueio");
        			dbConnect.updateUser(user);
        			dbConnect.register(3004, user.getName(), "");
        		}
        		else if(user.getTotalAccesses() == 2) {
        			JOptionPane.showMessageDialog(this, "Senha incorreta! Mais 1 tentativas antes do bloqueio");
        			dbConnect.updateUser(user);
        			dbConnect.register(3005, user.getName(), "");
        		}
        		else if(user.getTotalAccesses() >= 3) {
        			JOptionPane.showMessageDialog(this, "Senha incorreta! Bloqueio por excesso de erros");
        			dbConnect.register(3006, user.getName(), "");
        			user.setBloquedAt(new Date(System.currentTimeMillis()));
        			dbConnect.updateUser(user);
        			dbConnect.register(3007, user.getName(), "");
        		}		
        	}

        }
        
        //Botão Fonético
        if (   e.getSource() == pwdButton1 
        	|| e.getSource() == pwdButton2
        	|| e.getSource() == pwdButton3
        	|| e.getSource() == pwdButton4
        	|| e.getSource() == pwdButton5
        	|| e.getSource() == pwdButton6) {
        	JButton bt = (JButton) e.getSource();
        	String st = String.valueOf(passwordField.getPassword());
        	if(st.length() >= 12) return;
        	String buttonText = bt.getText();
            passwordField.setText(st+buttonText);
            // System.out.println(String.valueOf(passwordField.getPassword()));
            // organizeFoneticButtons();
        }
    }

}

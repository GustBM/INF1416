package screen;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.*;

import service.AuthenticationService;
import service.dbConnect;

public class NewUserForm extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Components of the Form
    private Container c;
    private JLabel pathFile = new JLabel("Arquivo");
    private JLabel name= new JLabel("Nome");
    private JLabel email = new JLabel("Email");
    private JLabel senha = new JLabel("Senha");
    private JLabel confSenha = new JLabel("Confirmar Senha");
    private JTextField tpathFile = new JTextField();
    private JTextField tname = new JTextField();
    private JTextField temail = new JTextField();
    private JPasswordField tsenha = new JPasswordField();
    private JPasswordField tconfSenha = new JPasswordField();

    private JLabel grupo;
    private JRadioButton adm;
    private JRadioButton nml;
    private ButtonGroup gengp;
    private JButton sub;
    private JButton bck;
    private JButton clean;
    private JButton file;
    
    // Cabeçalho
 	private JLabel tuserName = new JLabel("Nome: ");
 	private JLabel tuserGroup = new JLabel("Grupo: ");
 	private JLabel tuserLogin = new JLabel("Login: ");
 	private JLabel userName = new JLabel("");
 	private JLabel userGroup = new JLabel("");
 	private JLabel userLogin = new JLabel("");
    
    private JButton[] pwdButton = new JButton[18];
    private JButton[] pwdConfButton = new JButton[18];
	
    X509Certificate certificate;
    
	public NewUserForm() {
		dbConnect.register(6001, AuthenticationService.getInstance().getUser().getName(), "");
		setTitle("Novo Usuário");
		
    	setVisible(true);
    	setBounds(300, 90, 500, 750);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    	
    	c = getContentPane();
        c.setLayout(null);

        setCabecalho ();
        
        // Arquivo
        pathFile.setSize(100, 20);
        pathFile.setLocation(100, 140);
        c.add(pathFile);
        
        tpathFile.setSize(190, 20);
        tpathFile.setLocation(200, 140);
        
        file = new JButton("Arquivo");
        file.setSize(80, 20);
        file.setLocation(390, 140);
        file.addActionListener(this);
        c.add(tpathFile);
        c.add(file);
        
        // Nome
        name.setSize(100, 20);
        name.setLocation(100, 180);
        c.add(name);
        
        tname.setSize(190, 20);
        tname.setLocation(200, 180);
        tname.setEditable(false);
        c.add(tname);
        
        // Email
        email.setSize(100, 20);
        email.setLocation(100, 220);
        c.add(email);
        
        temail.setSize(190, 20);
        temail.setLocation(200, 220);
        temail.setEditable(false);
        c.add(temail);
        
        // Grupo
        grupo = new JLabel("Grupo");
        grupo.setSize(100, 20);
        grupo.setLocation(100, 270);
        c.add(grupo);
  
        adm = new JRadioButton("Administrador");
        adm.setSelected(false);
        adm.setSize(120, 20);
        adm.setLocation(200, 270);
        c.add(adm);
  
        nml = new JRadioButton("Normal");
        nml.setSelected(true);
        nml.setSize(100, 20);
        nml.setLocation(320, 270);
        c.add(nml);
  
        gengp = new ButtonGroup();
        gengp.add(adm);
        gengp.add(nml);
        
        // Senha
        senha.setSize(100, 20);
        senha.setLocation(100, 320);
        c.add(senha);
        
        tsenha.setSize(150, 20);
        tsenha.setLocation(200, 320);
        tsenha.setEditable(false);
        c.add(tsenha);
        
        setPwdButtons(pwdButton, 350);
        
        // Confirar Senha
        confSenha.setSize(100, 20);
        confSenha.setLocation(60, 470);
        c.add(confSenha);
        
        tconfSenha.setSize(150, 20);
        tconfSenha.setLocation(200, 470);
        tconfSenha.setEditable(false);
        c.add(tconfSenha);
        
        setPwdButtons(pwdConfButton, 500);
        
        // Submit
        sub = new JButton("Enviar");
        sub.setSize(100, 20);
        sub.setLocation(130, 630);
        sub.addActionListener(this);
        c.add(sub);
        
        // Clean
        clean = new JButton("Limpar");
        clean.setSize(100, 20);
        clean.setLocation(260, 630);
        clean.addActionListener(this);
        c.add(clean);
        
        // Back
        bck = new JButton("Voltar");
        bck.setSize(100, 20);
        bck.setLocation(195, 660);
        bck.addActionListener(this);
        c.add(bck);
        
        setVisible(true);
	}
	
	private void setCabecalho () {
		tuserLogin.setSize(300, 30);
		tuserLogin.setLocation(150, 30);
		tuserGroup.setSize(300, 30);
		tuserGroup.setLocation(150, 60);
		tuserName.setSize(300, 30);
		tuserName.setLocation(150, 90);
		
		userLogin.setSize(300, 30);
		userLogin.setLocation(200, 30);
		userGroup.setSize(300, 30);
		userGroup.setLocation(200, 60);
		userName.setSize(300, 30);
		userName.setLocation(200, 90);
		
		userLogin.setText(AuthenticationService.getInstance().getUser().getEmail());
		userName.setText(AuthenticationService.getInstance().getUser().getName());
		if(AuthenticationService.getInstance().getUser().getgroup() == 1) userGroup.setText("Administrador");
		else userGroup.setText("Usuário Normal");
        
		c.add(tuserLogin);
        c.add(tuserGroup);
        c.add(tuserName);
        c.add(userLogin);
        c.add(userGroup);
        c.add(userName);
	}
	
	private boolean regexEmail(String email) {
    	String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    	Pattern pattern = Pattern.compile(regex);
    	return pattern.matcher(email).matches();
    }
	
	private void setPwdButtons(JButton[] pwdButtons, int height) {
		String[] stringArray = {"BA","CA","DA","FA","GA","HA","BE","CE","DE","FE","GE","HE","BO","CO","DO","FO","GO","HO"};
		List<String> intList = Arrays.asList(stringArray);
		int ajustex = 0;
		int ajustey = 0;
		for (int i = 0; i < 18; i++) {
			pwdButtons[i] = new JButton(" ");
			pwdButtons[i].setText(intList.get(i));
			pwdButtons[i].addActionListener(this);
			pwdButtons[i].setBounds(80+60*(i-ajustex), height+ajustey, 60, 30);
			c.add(pwdButtons[i]);
			if(i==5) { ajustex = 6; ajustey = 30;}
			if(i==11) {ajustex = 12; ajustey = 60;}
		}
	}
	
	private boolean isPwdEqual() {
		String pwdText = String.valueOf(tsenha.getPassword());
		String pwdConfText = String.valueOf(tconfSenha.getPassword());
		return pwdText.equals(pwdConfText);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == pwdButton[0] || e.getSource() == pwdButton[1] || e.getSource() == pwdButton[2] || e.getSource() == pwdButton[3] || e.getSource() == pwdButton[4] || e.getSource() == pwdButton[5] || 
		   e.getSource() == pwdButton[6] || e.getSource() == pwdButton[7] || e.getSource() == pwdButton[8] || e.getSource() == pwdButton[9] || e.getSource() == pwdButton[10] || e.getSource() == pwdButton[11] || 
		   e.getSource() == pwdButton[12] || e.getSource() == pwdButton[13] || e.getSource() == pwdButton[14] || e.getSource() == pwdButton[15] || e.getSource() == pwdButton[16] || e.getSource() == pwdButton[17]
		) {
			JButton bt = (JButton) e.getSource();
        	String st = String.valueOf(tsenha.getPassword());
        	if(st.length() >= 12) return;
        	String buttonText = bt.getText();
        	tsenha.setText(st+buttonText);
        	System.out.println(String.valueOf(tsenha.getPassword()));
		}
		
		if(e.getSource() == pwdConfButton[0] || e.getSource() == pwdConfButton[1] || e.getSource() == pwdConfButton[2] || e.getSource() == pwdConfButton[3] || e.getSource() == pwdConfButton[4] || e.getSource() == pwdConfButton[5] || 
		   e.getSource() == pwdConfButton[6] || e.getSource() == pwdConfButton[7] || e.getSource() == pwdConfButton[8] || e.getSource() == pwdConfButton[9] || e.getSource() == pwdConfButton[10] || e.getSource() == pwdConfButton[11] || 
		   e.getSource() == pwdConfButton[12] || e.getSource() == pwdConfButton[13] || e.getSource() == pwdConfButton[14] || e.getSource() == pwdConfButton[15] || e.getSource() == pwdConfButton[16] || e.getSource() == pwdConfButton[17]
		) {
			JButton bt = (JButton) e.getSource();
        	String st = String.valueOf(tconfSenha.getPassword());
        	if(st.length() >= 12) return;
        	String buttonText = bt.getText();
        	tconfSenha.setText(st+buttonText);
		}		
		
		if (e.getSource() == bck) {
			dispose();
			dbConnect.register(6007, AuthenticationService.getInstance().getUser().getName(), "");	
			new UserFrame();
		}
		
		if (e.getSource() == clean) {
			tpathFile.setText("");
			temail.setText("");
			tsenha.setText("");
			tconfSenha.setText("");
			tname.setText("");
		}
		
		if (e.getSource() == sub) {
			dbConnect.register(6002, AuthenticationService.getInstance().getUser().getName(), "");
			String nomeText = tname.getText();
			String emailText = temail.getText();
			String pwdText = String.valueOf(tsenha.getPassword());
			String pathText = tpathFile.getText();
			int group = 0;
			boolean result = false;
			
			if(adm.isSelected()) group = 1;
			
			if(nomeText.isEmpty() || emailText.isEmpty() || pwdText.isEmpty() || pathText.isEmpty()) {
            	JOptionPane.showMessageDialog(this, "Preencha todos os Campos!");
            	dbConnect.register(6006, AuthenticationService.getInstance().getUser().getName(), "");
            	return;
            }
			
			if(!isPwdEqual()) {
				JOptionPane.showMessageDialog(this, "A confirmação da senha está incorreta!");
				dbConnect.register(6003, AuthenticationService.getInstance().getUser().getName(), "");
            	return;
			}
			
        	if(pwdText.length() > 12 || pwdText.length() < 6) {
        		JOptionPane.showMessageDialog(this, "Senha deve ter entre 4 a 6 fonemas.");
        		dbConnect.register(6003, AuthenticationService.getInstance().getUser().getName(), "");
        		return;
        	}
			
			if(!regexEmail(emailText)) {
            	JOptionPane.showMessageDialog(this, "Formato de e-mail inválido.");
            	dbConnect.register(6006, AuthenticationService.getInstance().getUser().getName(), "");
            	return;
            }
			
			try {
				result = dbConnect.newUser(nomeText, emailText, group, pwdText);
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(this, "Erro!" + e1.getMessage());
				dbConnect.register(6006, AuthenticationService.getInstance().getUser().getName(), "");
				return;
			}
			
			if(result) {
				JOptionPane.showMessageDialog(this, "Novo Usuário Cadastrado com Sucesso.");
				dbConnect.register(6005, AuthenticationService.getInstance().getUser().getName(), "");
			}
			else {
				JOptionPane.showMessageDialog(this, "Erro no Cadastro!");
				dbConnect.register(6006, AuthenticationService.getInstance().getUser().getName(), "");
			}
		    
        }
	}
}

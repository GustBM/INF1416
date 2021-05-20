package screen;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
//import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.nio.file.Files;

import javax.swing.*;

import service.AuthenticationService;
import service.CertificateUtility;
import service.dbConnect;

import model.User;

public class NewUserForm extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Components of the Form
    private Container c;
    private JLabel title 			= new JLabel("Formulario de Cadastro:");
    //private JLabel name  			= new JLabel("Nome");
    //private JLabel email 			= new JLabel("Email");
    private JLabel certificate 		= new JLabel("Certificado:");
    private JLabel senha 			= new JLabel("Senha pessoal:");
//  private JTextField tname 		= new JTextField();
//  private JTextField temail 		= new JTextField();
    private JPasswordField tsenha 	= new JPasswordField();

    private JLabel grupo;
    private JRadioButton adm;
    private JRadioButton nml;
    private ButtonGroup gengp;
    private JButton sub;
    private JButton bck;
    private JButton browse;
    
    public JFileChooser fc;
    public String certificate_content_text;
    public byte[] certificate_content_bytes;
    public String email_certificate;
    public String name_certificate;

 	private JLabel tuserName 			= new JLabel("Nome: ");
 	private JLabel tuserGroup 			= new JLabel("Grupo: ");
 	private JLabel tuserLogin 			= new JLabel("Login: ");
 	private JLabel userName 			= new JLabel("");
 	private JLabel userGroup 			= new JLabel("");
 	private JLabel userLogin 			= new JLabel("");
 	private JLabel usersTotalLabel 		= new JLabel("Total de Usuarios: ");
 	private JLabel usersTotal  			= new JLabel("");
    
    private JButton[] pwdButton = new JButton[18];
    
    private User user;
	
	public NewUserForm(User user) {
		dbConnect.register(6001, AuthenticationService.getInstance().getUser().getName(), "");
		setTitle("Novo Usuario");
    	setVisible(true);
    	setBounds(300, 90, 500, 600);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    	
    	c = getContentPane();
        c.setLayout(null);
        
        setCabecalho(user);
        
        setCorpo1();
        
        setCorpo2();

        setVisible(true);
	}
	
	private void setCabecalho (User user) {
		
		// Login
		tuserLogin.setSize(300, 30);
		tuserLogin.setLocation(150, 10);
		
		userLogin.setSize(300, 30);
		userLogin.setLocation(200, 10);
		
		// Grupo
		tuserGroup.setSize(300, 30);
		tuserGroup.setLocation(150, 40);
		
		userGroup.setSize(300, 30);
		userGroup.setLocation(200, 40);
		
		// Nome
		tuserName.setSize(300, 30);
		tuserName.setLocation(150, 70);
		
		userName.setSize(300, 30);
		userName.setLocation(200, 70);
		
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
		
		// Total de usuarios
		usersTotalLabel.setSize(300, 30);
		usersTotalLabel.setLocation(100, 105);
		
		usersTotal.setSize(300, 30);
		usersTotal.setLocation(220, 105);
		usersTotal.setText(""+dbConnect.getNumberOfUsers());
        
		c.add(usersTotalLabel);
		c.add(usersTotal);
	}
	
	private void setCorpo2() {
//      // Nome
//      name.setSize(100, 20);
//      name.setLocation(100, 100);
//      c.add(name);
//        
//      tname.setSize(190, 20);
//      tname.setLocation(200, 100);
//      c.add(tname);
//        
//      // Email
//      email.setSize(100, 20);
//      email.setLocation(100, 150);
//      c.add(email);
//        
//      temail.setSize(190, 20);
//      temail.setLocation(200, 150);
//      c.add(temail);
		
		// Titulo
		title.setSize(300, 30);
        title.setLocation(100, 140);
        c.add(title);
        
        // Certificado   
        certificate.setSize(100, 20);
        certificate.setLocation(100, 190);
        c.add(certificate);
        
        browse = new JButton("Procurar...");
        browse.setSize(190, 20);
        browse.setLocation(200, 190);
        browse.addActionListener(this);
        c.add(browse);
        
        // Grupo
        grupo = new JLabel("Grupo:");
        grupo.setSize(100, 20);
        grupo.setLocation(100, 240);
        c.add(grupo);
  
        adm = new JRadioButton("Administrador");
        adm.setSelected(false);
        adm.setSize(120, 20);
        adm.setLocation(200, 240);
        c.add(adm);
  
        nml = new JRadioButton("Normal");
        nml.setSelected(true);
        nml.setSize(100, 20);
        nml.setLocation(320, 240);
        c.add(nml);
  
        gengp = new ButtonGroup();
        gengp.add(adm);
        gengp.add(nml);
        
        // Senha
        senha.setSize(100, 20);
        senha.setLocation(100, 290);
        c.add(senha);
        
        tsenha.setSize(200, 20);
        tsenha.setLocation(200, 290);
        tsenha.setEditable(false);
        c.add(tsenha);
        
        setPwdButtons();
        
        // Submit
        sub = new JButton("Cadastrar");
        sub.setSize(100, 20);
        sub.setLocation(130, 540);
        sub.addActionListener(this);
        c.add(sub);
        
        // Back
        bck = new JButton("Voltar");
        bck.setSize(100, 20);
        bck.setLocation(260, 540);
        bck.addActionListener(this);
        c.add(bck);
	}

	private boolean regexEmail(String email) {
    	String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    	Pattern pattern = Pattern.compile(regex);
    	return pattern.matcher(email).matches();
    }
	
	private void setPwdButtons() {
		String[] stringArray = {"BA","CA","DA","FA","GA","HA","BE","CE","DE","FE","GE","HE","BO","CO","DO","FO","GO","HO"};
		List<String> intList = Arrays.asList(stringArray);
		int ajustex = 0;
		int ajustey = 0;
		for (int i = 0; i < 18; i++) {
			pwdButton[i] = new JButton(" ");
			pwdButton[i].setText(intList.get(i));
			pwdButton[i].addActionListener(this);
			pwdButton[i].setBounds(80+60*(i-ajustex), 320+ajustey, 60, 30);
			c.add(pwdButton[i]);
			if(i==5) {ajustex = 6; ajustey = 30;}
			if(i==11) {ajustex = 12; ajustey = 60;}
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == pwdButton[0] || e.getSource() == pwdButton[1] || e.getSource() == pwdButton[2] || e.getSource() == pwdButton[3] || e.getSource() == pwdButton[4] || e.getSource() == pwdButton[5] || 
		   e.getSource() == pwdButton[6] || e.getSource() == pwdButton[7] || e.getSource() == pwdButton[8] || e.getSource() == pwdButton[9] || e.getSource() == pwdButton[10] || e.getSource() == pwdButton[11] || 
		   e.getSource() == pwdButton[12] || e.getSource() == pwdButton[13] || e.getSource() == pwdButton[14] || e.getSource() == pwdButton[15] || e.getSource() == pwdButton[16] || e.getSource() == pwdButton[17]
		) {
			JButton bt = (JButton) e.getSource();
        	String st = String.valueOf(tsenha.getPassword());
        	if(st.length() > 12) return;
        	String buttonText = bt.getText();
        	tsenha.setText(st+buttonText);
        	System.out.println(String.valueOf(tsenha.getPassword()));
		}
		
		if (e.getSource() == bck) {
			dispose();
			new UserFrame();
		}
		
		if (e.getSource() == sub) {
			dbConnect.register(6002, AuthenticationService.getInstance().getUser().getName(), "");
//			String nomeText = tname.getText();
//			String emailText = temail.getText();
			String pwdText = String.valueOf(tsenha.getPassword());
			int group = 0;
			boolean result = false;
			
			if(adm.isSelected()) group = 1;
			
//			if(nomeText.isEmpty() || emailText.isEmpty() || pwdText.isEmpty()) {
//            	JOptionPane.showMessageDialog(this, "Preencha todos os Campos!");
//            	return;
//            }
			
			if(pwdText.isEmpty() || certificate_content_text.isEmpty()) {
            	JOptionPane.showMessageDialog(this, "Preencha todos os Campos!");
            	return;
            }
			
        	if(pwdText.length() > 12 || pwdText.length() < 8) {
        		JOptionPane.showMessageDialog(this, "Senha deve ter de 4 a 6 fonemas.");
        		dbConnect.register(6003, AuthenticationService.getInstance().getUser().getName(), "");
        		return;
        	}
			
//			if(!regexEmail(emailText)) {
//            	JOptionPane.showMessageDialog(this, "Formato de e-mail invalido.");
//            	return;
//            }
			
//        	try {
//				result = dbConnect.newUser(nomeText, emailText, group, pwdText);
//			}catch(Exception e1) {
//				JOptionPane.showMessageDialog(this, "Erro!" + e1.getMessage());
//				return;
//			}
        	
        	try {
				result = dbConnect.newUser(name_certificate, email_certificate, group, pwdText, certificate_content_bytes);
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(this, "Erro!" + e1.getMessage());
				return;
			}
			
			if(result) {
				JOptionPane.showMessageDialog(this, "Novo Usuario Cadastrado com Sucesso.");
				dbConnect.register(6005, AuthenticationService.getInstance().getUser().getName(), "");
			}
			else {
				JOptionPane.showMessageDialog(this, "Erro no Cadastro!");
				dbConnect.register(6006, AuthenticationService.getInstance().getUser().getName(), "");
			}
		    
        }
		
		if (e.getSource() == browse) {
			fc = new JFileChooser();
		    int returnValue = fc.showOpenDialog(null);
		    if (returnValue == JFileChooser.APPROVE_OPTION) 
		    {
			    File selectedFile = fc.getSelectedFile();
			    System.out.println("Arquivo selecionado: "+ selectedFile.getAbsolutePath());
			    
			    try {
			    	certificate_content_text = new String(Files.readAllBytes(selectedFile.toPath()));
			    	certificate_content_bytes = Files.readAllBytes(selectedFile.toPath());
			    	JOptionPane.showMessageDialog(this, certificate_content_text);
			    } catch (Exception ex) {
			    	JOptionPane.showMessageDialog(this, "Erro!" + ex.getMessage());
					return;
			    }
			    
			    try {
		            email_certificate = CertificateUtility.getCertificateEMAILADDRESS(certificate_content_bytes);
		            name_certificate = CertificateUtility.getCertificateNAME(certificate_content_bytes);
		            // JOptionPane.showMessageDialog(this, email_certificate);
		            // JOptionPane.showMessageDialog(this, name_certificate);
		            
		        } catch (Exception ex) {
		        	JOptionPane.showMessageDialog(this, "Erro!" + ex.getMessage());
					return;
		        }
		    }
		}
	}
}

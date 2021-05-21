package screen;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.nio.file.Files;
import java.math.BigInteger;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

import javax.swing.*;

import service.AuthenticationService;
import service.CertificateUtility;
import service.dbConnect;


public class NewUserFormConfirm extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Components of the Form
    private Container c;

    private JButton sub;
    private JButton bck;
    
    public JFileChooser fc;
    public String certificate_content_text2;
    public byte[] certificate_content_bytes2;
    public String email_certificate2;
    public String name_certificate2;
    public int group2;

    // Dados do formulario
    private JLabel title_form	 		= new JLabel("Dados do Formulario: ");
    private JLabel nameLabel	 		= new JLabel("Nome: ");
 	private JLabel name  				= new JLabel("");
 	private JLabel emailLabel 			= new JLabel("Email:");
 	private JLabel email 				= new JLabel("");
 	private JLabel certificateLabel 	= new JLabel("Certificado:");
 	private JLabel certificate 			= new JLabel("");
 	private JLabel grupoLabel			= new JLabel("Grupo:");
 	private JLabel grupo 				= new JLabel("");
 	private JLabel senha 				= new JLabel("Senha pessoal:");
    private JPasswordField tsenha 		= new JPasswordField();
    
    // Dados do certificado digital
    private JLabel title_certificate	= new JLabel("Dados do Certificado: ");
    private JLabel versionLabel	 		= new JLabel("Versao: ");
 	private JLabel version  			= new JLabel("");
 	private JLabel serialLabel 			= new JLabel("Serie:");
 	private JLabel serial 				= new JLabel("");
 	private JLabel validityLabel	 	= new JLabel("Validade:");
 	private JLabel validity 			= new JLabel("");
 	private JLabel signatureLabel		= new JLabel("Tipo de Assinatura:");
 	private JLabel signature 			= new JLabel("");
 	private JLabel issuerLabel 			= new JLabel("Emissor:");
    private JLabel issuer 				= new JLabel("");
    private JLabel subjectLabel 		= new JLabel("Sujeito:");
    private JLabel subject 				= new JLabel("");
    private JLabel emailAddressLabel	= new JLabel("Email:");
    private JLabel emailAddress 		= new JLabel("");
	
	public NewUserFormConfirm(String name_certificate, String email_certificate, int group, String pwdText, byte[] certificate_content_bytes,  String certificate_path, int version_certificate, BigInteger serial_certificate, Date validity_certificate, String signature_certificate, X500Principal issuer_certificate, X500Principal subject_certificate, String certificate_content_text) {
		dbConnect.register(6001, AuthenticationService.getInstance().getUser().getEmail(), "");
		setTitle("Confirmar Dados");
    	setVisible(true);
    	setBounds(300, 90, 500, 600);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    	
    	c = getContentPane();
        c.setLayout(null);
        
        certificate_content_text2 = certificate_content_text;
        certificate_content_bytes2 = certificate_content_bytes;
        email_certificate2 = email_certificate;
        name_certificate2 = name_certificate;
        group2 = group;
        
        setCorpo1(name_certificate, email_certificate, certificate_path, group, pwdText);
        
        setCorpo2(version_certificate, serial_certificate, validity_certificate, signature_certificate, issuer_certificate, subject_certificate, email_certificate);
        
        setRodape();

        setVisible(true);
	}

	private void setCorpo1(String name_certificate, String email_certificate, String certificate_path, int group, String pwdText) {
		
		// Titulo
		title_form.setSize(300, 30);
		title_form.setLocation(100, 35);
        
		c.add(title_form);
		
		// Nome
		nameLabel.setSize(300, 30);
		nameLabel.setLocation(100, 65);
		
		name.setSize(300, 30);
		name.setLocation(148, 65);
		name.setText(name_certificate);
        
		c.add(nameLabel);
		c.add(name);
		
		// Email
		emailLabel.setSize(300, 30);
		emailLabel.setLocation(100, 95);
		
		email.setSize(300, 30);
		email.setLocation(145, 95);
		email.setText(email_certificate);
        
		c.add(emailLabel);
		c.add(email);
		
		// Certificado
		certificateLabel.setSize(300, 30);
		certificateLabel.setLocation(100, 125);
		
		certificate.setSize(300, 30);
		certificate.setLocation(180, 125);
		certificate.setText(certificate_path);
        
		c.add(certificateLabel);
		c.add(certificate);
		
		// Grupo
        grupoLabel.setSize(300, 30);
        grupoLabel.setLocation(100, 155);
        
        grupo.setSize(300, 30);
        grupo.setLocation(150, 155);
        if (group == 1) {
        	grupo.setText("Administrador");
        }
        else if (group == 0) {
        	grupo.setText("Usuário");
        }
        
        c.add(grupoLabel);
        c.add(grupo);
        
        // Senha
        senha.setSize(300, 30);
        senha.setLocation(100, 185);
        c.add(senha);
        
        tsenha.setSize(200, 30);
        tsenha.setLocation(195, 185);
        tsenha.setEditable(false);
        tsenha.setText(pwdText);
        c.add(tsenha);
		
	}
	
private void setCorpo2(int version_certificate, BigInteger serial_certificate, Date validity_certificate, String signature_certificate, X500Principal issuer_certificate, X500Principal subject_certificate, String email_certificate) {
		
		// Titulo
		title_certificate.setSize(300, 30);
		title_certificate.setLocation(100, 245);
		
		c.add(title_certificate);
			
		// Versao
		versionLabel.setSize(300, 30);
		versionLabel.setLocation(100, 275);
		
		version.setSize(300, 30);
		version.setLocation(154, 275);
		version.setText(""+version_certificate);
        
		c.add(versionLabel);
		c.add(version);
		
		// Serie
		serialLabel.setSize(300, 30);
		serialLabel.setLocation(100, 305);
		
		serial.setSize(300, 30);
		serial.setLocation(140, 305);
		serial.setText(""+serial_certificate);
        
		c.add(serialLabel);
		c.add(serial);
		
		// Validade
		validityLabel.setSize(300, 30);
		validityLabel.setLocation(100, 335);
		
		validity.setSize(300, 30);
		validity.setLocation(165, 335);
		validity.setText(""+validity_certificate);
        
		c.add(validityLabel);
		c.add(validity);
		
		// Tipo de Assinatura
        signatureLabel.setSize(300, 30);
        signatureLabel.setLocation(100, 365);
        
        signature.setSize(300, 30);
        signature.setLocation(228, 365);
        signature.setText(signature_certificate);
        
        c.add(signatureLabel);
        c.add(signature);
        
        // Emissor
        issuerLabel.setSize(300, 30);
        issuerLabel.setLocation(100, 395);
        
        issuer.setSize(200, 30);
        issuer.setLocation(162, 395);
        issuer.setText(""+issuer_certificate);
        
        c.add(issuerLabel);
        c.add(issuer);
        
        // Sujeito (FN)
        subjectLabel.setSize(300, 30);
        subjectLabel.setLocation(100, 425);
        
        subject.setSize(200, 30);
        subject.setLocation(155, 425);
        subject.setText(""+subject_certificate);
        
        c.add(subjectLabel);
        c.add(subject);
        
        // Email
        emailAddressLabel.setSize(300, 30);
        emailAddressLabel.setLocation(100, 455);
        
        emailAddress.setSize(200, 30);
        emailAddress.setLocation(145, 455);
        emailAddress.setText(email_certificate);
        
        c.add(emailAddressLabel);
        c.add(emailAddress);
		
	}
	
	private void setRodape() {
        
        // Submit
        sub = new JButton("Confirmar");
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == bck) {
			dispose();
			new NewUserForm();
		}
		
		if (e.getSource() == sub) {
			dbConnect.register(6002, AuthenticationService.getInstance().getUser().getEmail(), "");
			String pwdText = String.valueOf(tsenha.getPassword());
			int group2 = 0;
			boolean result = false;
			
			if(pwdText.isEmpty() || certificate_content_text2.isEmpty()) {
            	JOptionPane.showMessageDialog(this, "Preencha todos os Campos!");
            	return;
            }
			
        	if(pwdText.length() > 12 || pwdText.length() < 8) {
        		JOptionPane.showMessageDialog(this, "Senha deve ter de 4 a 6 fonemas.");
        		dbConnect.register(6003, AuthenticationService.getInstance().getUser().getEmail(), "");
        		return;
        	}
        	
        	try {
				result = dbConnect.newUser(name_certificate2, email_certificate2, group2, pwdText, certificate_content_bytes2);
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(this, "Erro! " + e1.getMessage());
				return;
			}
			
			if(result) {
				JOptionPane.showMessageDialog(this, "Novo Usuario Cadastrado com Sucesso.");
				dbConnect.register(6005, AuthenticationService.getInstance().getUser().getEmail(), "");
			}
			else {
				JOptionPane.showMessageDialog(this, "Erro no Cadastro!");
				dbConnect.register(6006, AuthenticationService.getInstance().getUser().getEmail(), "");
			}
		    
        }
	}
}

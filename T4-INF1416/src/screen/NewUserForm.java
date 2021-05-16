package screen;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel title = new JLabel("Novo Usuário");
    private JLabel name= new JLabel("Nome");
    private JLabel email = new JLabel("Email");
    private JLabel senha = new JLabel("Senha");
    private JTextField tname = new JTextField();
    private JTextField temail = new JTextField();
    private JPasswordField tsenha = new JPasswordField();

    private JLabel grupo;
    private JRadioButton adm;
    private JRadioButton nml;
    private ButtonGroup gengp;
    private JButton sub;
    private JButton bck;
    
    private JButton[] pwdButton = new JButton[18];
	
	public NewUserForm() {
		dbConnect.register(6001, AuthenticationService.getInstance().getUser().getName(), "");
		setTitle("Novo Usuário");
    	setVisible(true);
    	setBounds(300, 90, 500, 600);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    	
    	c = getContentPane();
        c.setLayout(null);

        title.setSize(300, 30);
        title.setLocation(200, 30);
        c.add(title);
        
        // Nome
        name.setSize(100, 20);
        name.setLocation(100, 100);
        c.add(name);
        
        tname.setSize(190, 20);
        tname.setLocation(200, 100);
        c.add(tname);
        
        // email
        email.setSize(100, 20);
        email.setLocation(100, 150);
        c.add(email);
        
        temail.setSize(190, 20);
        temail.setLocation(200, 150);
        c.add(temail);
        
        // Grupo
        grupo = new JLabel("Grupo");
        grupo.setSize(100, 20);
        grupo.setLocation(100, 200);
        c.add(grupo);
  
        adm = new JRadioButton("Administrador");
        adm.setSelected(false);
        adm.setSize(120, 20);
        adm.setLocation(200, 200);
        c.add(adm);
  
        nml = new JRadioButton("Normal");
        nml.setSelected(true);
        nml.setSize(100, 20);
        nml.setLocation(320, 200);
        c.add(nml);
  
        gengp = new ButtonGroup();
        gengp.add(adm);
        gengp.add(nml);
        
        // Senha
        senha.setSize(100, 20);
        senha.setLocation(100, 250);
        c.add(senha);
        
        tsenha.setSize(150, 20);
        tsenha.setLocation(200, 250);
        tsenha.setEditable(false);
        c.add(tsenha);
        
        setPwdButtons();
        
        // Submit
        sub = new JButton("Enviar");
        sub.setSize(100, 20);
        sub.setLocation(130, 450);
        sub.addActionListener(this);
        c.add(sub);
        
        // Back
        bck = new JButton("Voltar");
        bck.setSize(100, 20);
        bck.setLocation(260, 450);
        bck.addActionListener(this);
        c.add(bck);
        
        setVisible(true);
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
			pwdButton[i].setBounds(80+60*(i-ajustex), 300+ajustey, 60, 30);
			c.add(pwdButton[i]);
			if(i==5) { ajustex = 6; ajustey = 30;}
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
        	if(st.length() >= 12) return;
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
			String nomeText = tname.getText();
			String emailText = temail.getText();
			String pwdText = String.valueOf(tsenha.getPassword());
			int group = 0;
			boolean result = false;
			
			if(adm.isSelected()) group = 1;
			
			if(nomeText.isEmpty() || emailText.isEmpty() || pwdText.isEmpty()) {
            	JOptionPane.showMessageDialog(this, "Preencha todos os Campos!");
            	return;
            }
			
        	if(pwdText.length() > 12 || pwdText.length() < 6) {
        		JOptionPane.showMessageDialog(this, "Senha deve ter entre 3 a 6 fonemas.");
        		dbConnect.register(6003, AuthenticationService.getInstance().getUser().getName(), "");
        		return;
        	}
			
			if(!regexEmail(emailText)) {
            	JOptionPane.showMessageDialog(this, "Formato de e-mail inválido.");
            	return;
            }
			
			try {
				result = dbConnect.newUser(nomeText, emailText, group, pwdText);
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(this, "Erro!" + e1.getMessage());
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

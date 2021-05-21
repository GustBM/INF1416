package screen;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.User;
import service.AuthenticationService;
import service.dbConnect;

public class FileScreen extends JFrame implements ActionListener {
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
	private JLabel ttotalAcess = new JLabel("Total de Consultas: ");
	private JLabel totalAcess = new JLabel("0");
	
	// Corpo 2
	private JFileChooser fc = new JFileChooser();;
	private JLabel arqLabel = new JLabel("Caminha da Pasta:");
	private JTextField arqPath = new JTextField();
	private JButton browse = new JButton("Buscar");
	private JButton search = new JButton("Listar");
	private JButton closeButton = new JButton("VOLTAR");
	
	// Tabela
	private JLabel arqCod = new JLabel("COD_ARQUIVO");
	private JLabel arqSecretName = new JLabel("NOME_SECRETO");
	private JLabel arqOwner = new JLabel("DONO");
	private JLabel arqGroup = new JLabel("GRUPO");
	
	JLabel narqCod = new JLabel("");
	JLabel narqSecretName = new JLabel("");
	JLabel narqOwner = new JLabel("");
	JLabel narqGroup = new JLabel("");
	
	JFrame f = new JFrame();
	
	public FileScreen() {
		// AuthenticationService.getInstance().checkUserEmail("asdf@gmail.com", true);
		this.user = AuthenticationService.getInstance().getUser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		setTitle("Leitura Arquivos "+user.getName());
    	setVisible(true);
    	dbConnect.register(5001, user.getEmail(), "");
    	setBounds(300, 90, 500, 600);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    	
    	arqPath.setBounds(50, 80, 220, 30);
		arqPath.setEditable(false);
		
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
		totalAcess.setText(String.valueOf(user.getTotalAccesses()));
		
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
		arqLabel.setBounds(50, 170, 220, 30);
		arqPath.setBounds(180, 170, 220, 30);
		arqPath.setEditable(false);
		
		browse.setSize(100, 20);
        browse.setLocation(180, 220);
        browse.addActionListener(this);
        
        search.setSize(100, 20);
        search.setLocation(300, 220);
        search.addActionListener(this);
        
        arqCod.setBounds(20, 170, 300, 220);
        arqSecretName.setBounds(140, 170, 300, 220);
    	arqOwner.setBounds(280, 170, 300, 220);
    	arqGroup.setBounds(350, 170, 300, 220);
    	closeButton.setBounds(170, 300, 100, 30);
    	
    	
    	closeButton.addActionListener(this);
    	c.add(closeButton);
        
		c.add(arqLabel);
		c.add(arqPath);
		c.add(browse);
		c.add(search);
	}
	
	private Key getSecretKeyFromEnv(String filePath) throws Exception {
        Path path = Paths.get(filePath + "\\index.env");
        byte[] fileBytes = Files.readAllBytes(path);
        
        Cipher cipher = Cipher.getInstance("RSA");
        PrivateKey privateKey = AuthenticationService.getInstance().returnPriK();
        
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] seed = cipher.doFinal(fileBytes);

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(seed);

        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56, secureRandom);
        return keyGenerator.generateKey();
    }
	
	private byte[] getFileContent(String filePath) throws Exception {
		Key secretKey = getSecretKeyFromEnv(filePath);
        try {
            Path path = Paths.get(filePath + "\\index.enc");
            byte[] fileBytes = Files.readAllBytes(path);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return cipher.doFinal(fileBytes);
        } catch (Exception e) {
        	dbConnect.register(8015);
            e.printStackTrace();

            throw new Exception("Erro! Chave privada incorreta");
        }
    }
	
	public String[][] getFilesIndex(String indexPath) throws Exception  {
        String indexString;
		try {
			indexString = new String(getFileContent(indexPath));
		} catch (Exception e) {
			throw new Exception("Falha na decriptação do arquivo de índice.");
		}
        String[] indexLines = indexString.split("\n");
        String[][] indexTable = new String[indexLines.length][];

        for(int i = 0; i < indexLines.length; i++) {
            indexTable[i] = indexLines[i].split(" ");
        }

        return indexTable;
    }

	public void printData(String[][] filesContent) {

		  String column[]={"COD.","NOME_SECRETO","DONO", "GRUPO"};         
		  JTable jt=new JTable(filesContent,column);    
		  jt.setBounds(30,150,200,300);
		  JScrollPane sp=new JScrollPane(jt);    
		  f.add(sp);          
		  f.setSize(300,400);    
		  f.setBounds(300, 90, 500, 600);
		  f.setVisible(true); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeButton) {
        	dispose();
        	String userEmail = AuthenticationService.getInstance().getUser().getEmail();
        	new UserFrame();
        }
		
		if (e.getSource() == browse) {
		    int returnValue = fc.showOpenDialog(null);
		    if (returnValue == JFileChooser.APPROVE_OPTION) 
		    {
			    File selectedFile = fc.getSelectedFile();
			    // System.out.println("Pasta selecionada: "+ selectedFile.getAbsolutePath());
			    arqPath.setText(selectedFile.getAbsolutePath());
		    }
		}
		
		if (e.getSource() == search) {
			String arPath = arqPath.getText();
			
			if(Objects.isNull(arPath)) {
		    	JOptionPane.showMessageDialog(this, "Preencha o caminho do arquivo");
		    	return;
		    }
			
			try {
				printData(getFilesIndex(arPath));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}

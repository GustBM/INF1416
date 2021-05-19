package screen;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import service.dbConnect;

public class LogView extends JFrame {    
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		JFrame f = new JFrame();
		
	    public LogView(){     
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

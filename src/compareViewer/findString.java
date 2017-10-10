package compareViewer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
/*
 * 같은 String 찾기 팝업
 */
public class findString extends JFrame  {
	
	Container cp;
	JPanel jptop    = new JPanel();
	JPanel jpcenter = new JPanel();
	JPanel jpbottom = new JPanel();
	JButton jpok  = new JButton("찾기") ;
	JButton jpcancel = new JButton("닫기");;
	JTextField jtf  = new JTextField(20);
	JLabel jlb = new JLabel("찾은 단어를 입력하세요.");
	JLabel jlb2 = new JLabel("단어");
	compareView cv;
	findStringToString fst;

	
	findString(compareView cv){
		   //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		   super.setDefaultCloseOperation(HIDE_ON_CLOSE);
		   super.setResizable(false);
		   super.setSize(300	,150);
		   super.setTitle("단어 찾기");
		   this.cv = cv; 
		   cp = super.getContentPane();
		   this.setLocation(300, 200);
		   this.setAlwaysOnTop(true);
		  // setdesignfindString();
		   cp.setLayout(new BorderLayout());
			
			jpbottom.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
			jpbottom.add(jpok);
			jpbottom.add(jpcancel);
			jtf.setText("");
			
			/*
			jpcancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					jtf.setText("");
					dispose();
				}
			});
			*/
			jpcancel.addActionListener(cv);
			
			jtf.addKeyListener(cv);
			
			
			jpok.addActionListener(cv);
		
			/*
			jpok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println(jtf.getText());
				}
			});
			*/
			
			
			
			jptop.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
			//jtf = new JTextField(20);

			jpcenter.add(jlb2);
			jpcenter.add(jtf);
			
			jptop.add(jlb);
			cp.add("North", jptop);
			cp.add("Center", jpcenter);
			cp.add("South",jpbottom);
	}
	
	public void setdesignfindString(){
		
		setVisible(true);
		
	}
	
	public String getstring () {
		return jtf.getText();
	}
	
	public int getFindPos( String targetstr ,int pos){
		
	     fst  = new  findStringToString(jtf.getText() , targetstr , pos);
	     return fst.indexstartPosition();
			  
	 }

	
	

}

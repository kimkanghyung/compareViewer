package compareViewer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
public class compareView extends JFrame implements ActionListener,KeyListener,DocumentListener {
 
 int chg_gubun = 0;
 
 Boolean btgubun = true;
 Boolean docgubun = true;
 Boolean cvgubun = false;
 Boolean cvgubun2 = false;
 Boolean enterevent = false;
 String findgubun = null;
 static String findsrt = null;
 
 int FirstStartPos = 0 ;
 int FirstNextPos = 0 ;
 
 int SecondStartPos = 0 ;
 int SecondNextPos = 0 ;
 
 TextLineNumber tln;
 TextLineNumber tln2;
 
 Container cp;
 //main panel
 JPanel jpsmain;
 JPanel jptop;
 JCheckBox jcb;
 JComboBox jcom;//font combo
 JComboBox jcomencode;//font encode
 JTextField orgnmfolder;
 JTextField newnmfolder;
 static String orgfolderpath = null;
 static String newfolderpath = null;
 
 JFileChooser chooser = new JFileChooser();
 JButton orgbtn ;
 JButton newbtn ;
 JButton openfolderbtn ;
 
 Style cwStyle;
 Style cwStyle2;
 
 JPanel jpmain;
 
 
 //원본파일
 JPanel jpleft;
 JButton jbleft ;
 JTextField jbleftrestfield ;
 
 //텍스트 AREA
 JPanel jpfirstmain;
 JPanel jpfirst;
 JScrollPane jspfirst;
 JTextPane  jafirst;
 StyleContext sc;
 DefaultStyledDocument doc;
 DefaultStyledDocument orgdoc = new DefaultStyledDocument();
 //DefaultStyledDocument orgdoc2 =new DefaultStyledDocument();
 
 
 //비교파일
 JPanel jpright;
 JButton jbright ;
 JTextField jbrightrestfield ;
 
 JPanel jpsecondmain;
 JPanel jpsecond;
 JScrollPane jspsecond;
 JTextPane jasecond;
 StyleContext sc2;
 DefaultStyledDocument doc2;
 DefaultStyledDocument orgdoc2=new DefaultStyledDocument();;
 JDialog dialog = new JDialog();
 static findString fs ;
 static FolderSearch fsh;
 String Findpopup = null;
 
 compareView cv;
 
  public static void main(String args[]) {
	 
	  
	  compareView cv = new compareView();
	  fs = new findString(cv);
	  fsh = new FolderSearch(cv);
	 
	  
  }
  
  public compareView(){
   
   
   setDefaultCloseOperation(EXIT_ON_CLOSE);
   super.setResizable(true);
   super.setSize(1200,700);
   super.setTitle("소스 비교");
   cp = super.getContentPane();
   //fs = new findString(cv);
   setDesign();
   setVisible(true);
   /*
   fs.jpok.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(fs.getstring ());
			findsrt = fs.getstring ();
			//findstrtodoc();
		}}); 

   */
   
   
  }
  

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		findsrt = fs.getstring ();
		if(e.getKeyChar() == KeyEvent.VK_ENTER){
			System.out.println("keyPressed");
			 if(Findpopup.equals("left")){
				   
				   System.out.println("===================left======================");
				   try {
						setFindStrArea(findsrt, doc.getText(0, doc.getLength()), "left");
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				   
			   }else if(Findpopup.equals("right")){
				   System.out.println("===================right======================");
				   try {
						setFindStrArea(findsrt, doc2.getText(0, doc2.getLength()), "right");
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			   }
			
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
  

  
  public void actionPerformed(ActionEvent e){
   String orgnm = null;
   String comparenm = null;  
   int orglength = doc.getLength();
   int cmplength = doc2.getLength();
   //System.out.println("e.getSource()" + e.getSource());
   
   if(e.getSource().equals(fsh.jpok)){
		//getFileDirList();
	   // System.out.println("getchekboxSelectedrowOrg = " + fsh.getchekboxSelectedrowOrg());
	  String tmp1 =  orgfolderpath + "/" +fsh.getchekboxSelectedrowOrg();
	  System.out.println("getchekboxSelectedrowOrg = " + fsh.getchekboxSelectedrowOrg());
	  if(fsh.getchekboxSelectedrowOrg().equals("")){
		  jbleftrestfield.setText("");
		  try {
				jafirst.getDocument().remove(0, jafirst.getDocument().getLength());
		   } catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	  }else{
		  jbleftrestfield.setText(tmp1);
	  }
	  String tmp2 =  newfolderpath + "/" +fsh.getchekboxSelectedrowNew();
	  System.out.println("getchekboxSelectedrowNew = " + fsh.getchekboxSelectedrowNew());
	  if(fsh.getchekboxSelectedrowNew().equals("")){
		  jbrightrestfield.setText("");
		  try {
				jasecond.getDocument().remove(0, jafirst.getDocument().getLength());
		   } catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		   }
	  }else{
		  jbrightrestfield.setText(tmp2);
	  }
	  
	  if(fsh.getchekboxSelectedrowOrg().equals("") && fsh.getchekboxSelectedrowNew().equals("")){
	      
	  }else if(fsh.getchekboxSelectedrowOrg().equals("")){
		  try {
			fileRead(tmp2,"right");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	  }
	  else if(fsh.getchekboxSelectedrowNew().equals("")){
		    try {
				fileRead(tmp1,"left");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	  }else {
		     try {
				comparetxt(tmp1,tmp2);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	  }
	  //fsh.dispose();
	   
	}
	else if(e.getSource().equals(fsh.jpcancel)){
		fsh.dispose();
	}
   
   if(e.getSource().equals(orgbtn)){
	   chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	   if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
           // showopendialog 열기 창을 열고 확인 버튼을 눌렀는지 확인
          // jlb.setText("열기 경로 : " + jfc.getSelectedFile().toString());
		   System.out.println(chooser.getSelectedFile().toString());
		   orgfolderpath = chooser.getSelectedFile().toString();
		   orgnmfolder.setText(orgfolderpath);
		   
        }
    }else if (e.getSource().equals(newbtn)){
    	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
 	   if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            // showopendialog 열기 창을 열고 확인 버튼을 눌렀는지 확인
           // jlb.setText("열기 경로 : " + jfc.getSelectedFile().toString());
 		   //System.out.println(chooser.getSelectedFile().toString());
 		   newfolderpath = chooser.getSelectedFile().toString();
 		   newnmfolder.setText(newfolderpath);
 		   
         }
    }else if (e.getSource().equals(openfolderbtn)){
    	System.out.println("===============openfolderbtn===================");
    	fsh = new FolderSearch(this); 
    	fsh.setDesign(orgfolderpath,newfolderpath);
    }
 //
   
   //System.out.println("e.getSource()" + e.getSource());
   if(e.getSource().equals(fs.jpcancel)){
	   fs.dispose();
	   fs.jtf.setText("");
   }
   
   if(e.getSource().equals(fs.jpok)){
	   findsrt = fs.getstring ();
	   System.out.println("findsrt.length()2222 = " + findsrt.length());
	   if(Findpopup.equals("left")){
		   
		   System.out.println("===================left======================");
		   try {
				setFindStrArea(findsrt, doc.getText(0, doc.getLength()), "left");
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   
	   }else if(Findpopup.equals("right")){
		   System.out.println("===================right======================");
		   try {
				setFindStrArea(findsrt, doc2.getText(0, doc2.getLength()), "right");
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	   }
	  // System.out.println("findsrt = " + findsrt.length());
	   
   }
   
   
   if(e.getSource().equals(jbleft)){
      FileDialog fd = new FileDialog(this, "파일열기", FileDialog.LOAD);
      //FileDialog fd = new FileDialog(this, "파일저장", FileDialog.SAVE);
      fd.setVisible(true);
      btgubun =true;
      System.out.print("> 경로: " + fd.getDirectory() + ", ");
      System.out.println("파일명: " + fd.getFile());
      
       
		       try {
		   //fileRead(fd.getDirectory() + fd.getFile(),"left");
		       // System.out.println((fd.getDirectory() + fd.getFile()).length());
		        if((fd.getDirectory() + fd.getFile()).equals("nullnull")){
		         
		         
		        }else {
		          jbleftrestfield.setText(fd.getDirectory() + fd.getFile());
		          
		          orgnm =  jbleftrestfield.getText();
		          comparenm = jbrightrestfield.getText();
		          System.out.println("orgnm = " + orgnm);
		          System.out.println("comparenm = " + comparenm);
		          if(orgnm.equals("") || comparenm.equals("")){
		           fileRead(fd.getDirectory() + fd.getFile(),"left");
		          // comparetxt("C:/Users/Administrator/Desktop/test/zapabcrt20024_u.pc","C:/Users/Administrator/Desktop/test/zapabcrt20024_s.pc");
		          }else {
		           comparetxt(orgnm,comparenm);
		          }
		        }
			  } catch (Exception e1) {
			   // TODO Auto-generated catch block
			   e1.printStackTrace();
			  }
       
      
      }
      else if(e.getSource().equals(jbright)){
       FileDialog fd = new FileDialog(this, "파일열기", FileDialog.LOAD);
          //FileDialog fd = new FileDialog(this, "파일저장", FileDialog.SAVE);
       btgubun =true;
          fd.setVisible(true);
          System.out.print("> 경로: " + fd.getDirectory() + ", ");
          System.out.println("파일명: " + fd.getFile());
          try {
   //fileRead(fd.getDirectory() + fd.getFile(),"right");
           
        if((fd.getDirectory() + fd.getFile()).equals("nullnull")){
         
         
        }else {
            jbrightrestfield.setText(fd.getDirectory() + fd.getFile());
        
          
            orgnm =  jbleftrestfield.getText();
            comparenm = jbrightrestfield.getText();
            System.out.println("rightorgnm = " + orgnm);
            System.out.println("rightcomparenm = " + comparenm);
            
         if(orgnm.equals("") || comparenm.equals("")){
          fileRead(fd.getDirectory() + fd.getFile(),"right");
             }else {
              comparetxt(orgnm,comparenm);
             }
        }
		  } catch (Exception e1) {
		   // TODO Auto-generated catch block
		   e1.printStackTrace();
		  }
          
          
      }
   //콤보박스 이벤트
      else if(e.getSource().equals(jcom)){
     
        orgnm =  jbleftrestfield.getText();
        comparenm = jbrightrestfield.getText();
        
        
      	if(orgnm.equals("") && comparenm.equals("")){
    	
      		if(orglength > 0 && cmplength > 0 ){
		    	try {
					pasteCompare();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
      		}
      		else if(orglength > 0){
      			try {
					firstpaste(1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
      			
      		}else if(cmplength > 0){
      			try {
					firstpaste(2);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
      		}
      		}else if(orgnm.equals("")){
				 try {
					fileRead(comparenm,"right");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 }else if(comparenm.equals("")){
			   try {
						fileRead(orgnm,"left");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			 }
		   	 else {
				        try {
						  comparetxt(orgnm,comparenm);
						} catch (BadLocationException | IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
						}
		         }  	
		   
      }else if(e.getSource().equals(jcomencode)){
    	  
    	orgnm =  jbleftrestfield.getText();
        comparenm = jbrightrestfield.getText();
    	if(orgnm.equals("") && comparenm.equals("")){
    		
    		if(orglength > 0 && cmplength > 0 ){
		    	try {
					pasteCompare();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
      		}
      		else if(orglength > 0){
      			try {
					firstpaste(1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
      			
      		}else if(cmplength > 0){
      			try {
					firstpaste(2);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
      		}
		 }else if(orgnm.equals("")){
			 try {
				fileRead(comparenm,"right");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 }else if(comparenm.equals("")){
		   try {
					fileRead(orgnm,"left");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		 }
    	 else {
		        try {
				  comparetxt(orgnm,comparenm);
				} catch (BadLocationException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
				}
          }
    	
      }
       
  }
      
      
  
  public void fileRead(String filenm, String gubun) throws Exception{
   
    initDocument();
   
   cwStyle = sc.addStyle("ConstantWidth", null);
   String a = (String)jcom.getSelectedItem();
   int t = Integer.parseInt(a);
   StyleConstants.setFontSize(cwStyle, t);
   cwStyle2 = sc2.addStyle("ConstantWidth", null);
   StyleConstants.setFontSize(cwStyle2, t);
   
   String encodenm = (String)jcomencode.getSelectedItem();
   
   if(gubun.equals("left")){
	   doc.remove(0, doc.getLength());
   }
   else if(gubun.equals("right")){
	   doc2.remove(0, doc2.getLength());
   }
   File ft = null;
   ft = new File(filenm);
   
   System.out.println("fileRead 시작");
   BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(ft),encodenm));
   
   if(doc.getLength() == 0 && doc2.getLength() == 0) 
		   //&& (jbleftrestfield.getText().length() > 0 || jbrightrestfield.getText().length() > 0) ) 
   {
       while (true) {
         
        String str = in.readLine(); 
        if(str == null)break;
           if(gubun.equals("left")){
             
	          doc.insertString(doc.getLength(), str+ "\n", null);
	          //doc.setCharacterAttributes(0 , doc.getLength() , cwStyle, false);
	          
           }
           else if(gubun.equals("right")){
          
	          doc2.insertString(doc2.getLength(), str + "\n", null);
	          //doc2.setCharacterAttributes( 0 , doc2.getLength(), cwStyle2, false);
	         }
       }
       if(gubun.equals("left")){
             
          doc.setCharacterAttributes(0 , doc.getLength() , cwStyle, false);
          
           }
       if(gubun.equals("right")){
          
          doc2.setCharacterAttributes( 0 , doc2.getLength(), cwStyle2, false);
         }
   }else {
	   
	   pasteandfileCompare(filenm , gubun);
	   
   }
       in.close();
  } 
  
  
  public void setDesign() {
   
   
   //메인 panel
   jpsmain = new JPanel();
   jpsmain.setLayout(new BorderLayout());
   //상단 디자인
   
   jptop = new JPanel();
   //jptop.setLayout(new FlowLayout());
   
   JPanel jptop_s1 = new JPanel();
   JPanel jptop_s2 = new JPanel();
   JPanel jptop_s3 = new JPanel();
   
   ////////////////소스위치찾기////////////////////////
   JPanel jptop_s4 = new JPanel();
   orgnmfolder = new JTextField(15);
   orgnmfolder.setEditable(false);
   newnmfolder = new JTextField(15);
   newnmfolder.setEditable(false);
   orgbtn = new JButton("원본폴더");
   newbtn = new JButton("비교폴더");
   openfolderbtn = new JButton("열기");
   jptop_s4.add(orgnmfolder);
   jptop_s4.add(orgbtn);
   jptop_s4.add(newnmfolder);
   jptop_s4.add(newbtn);
   jptop_s4.add(openfolderbtn);
   orgbtn.addActionListener(this);
   newbtn.addActionListener(this);
   openfolderbtn.addActionListener(this);
   

   
  
   
  ////////////////소스위치찾기////////////////////////
   
   JToolBar jtoolbar = new JToolBar();
   jtoolbar.setRollover(true);
   JLabel jcomlabel = new JLabel("Font");
   jcom = new JComboBox();
   JLabel jcblabel = new JLabel("Scroll pair");
   
   jcb = new JCheckBox();
   jcb.setSelected(true);
   jcom.addItem("10");
   jcom.addItem("12");
   jcom.addItem("14");
   jcom.addItem("16");
   jcom.setSelectedIndex(2);
   
   jptop_s1.add(jcomlabel);
   jptop_s1.add(jcom);
   //jptop_s1.setBorder(new TitledBorder("Font"));
   
   jptop_s2.add(jcblabel);
   jptop_s2.add(jcb); 
   //jptop_s2.setBorder(new TitledBorder("Scroll"));
   
   JLabel jcomencodelabel = new JLabel("Encode");
   jcomencode = new JComboBox();
   jcomencode.addItem("UTF-8");
   jcomencode.addItem("EUC-KR");
   jcomencode.setSelectedIndex(1);
   
   jcomencode.addActionListener(this);
   jptop_s3.add(jcomencodelabel);
   jptop_s3.add(jcomencode); 
  // jptop_s3.setBorder(new TitledBorder("Encode"));
   jtoolbar.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 5));
   jtoolbar.add(jptop_s1);
   jtoolbar.add(jptop_s2);
   jtoolbar.add(jptop_s3);
   jtoolbar.add(jptop_s4);
   jtoolbar.addSeparator();
   jptop.add(jtoolbar);
   //jptop.set
   
   jpmain = new JPanel();
   jpmain.setLayout(new GridLayout(1,2));
   jcom.addActionListener(this);
   
   //원본파일 디자인
   jpleft = new JPanel();
   JLabel jllest = new JLabel("원본파일"); //원본파일 라벨
   jbleft = new JButton("파일열기");
   jbleftrestfield = new JTextField(20);
   jbleftrestfield.setEditable(false);
   jpleft.setBorder(new TitledBorder("원복파일"));
   jbleft.addActionListener(this);
   
   jpleft.add(jllest);
   jpleft.add(jbleftrestfield);
   jpleft.add(jbleft);
   //textpane
   sc = new StyleContext();
   doc = new DefaultStyledDocument(sc);
   
   jafirst = new JTextPane(doc);
   
   jspfirst = new JScrollPane(jafirst);

   jpfirst = new JPanel();
   
  
   tln = new TextLineNumber(jafirst);
   jspfirst.setRowHeaderView( tln );
   
   //스크롤바 움직이게 하는 event
   jspfirst.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
  
  @Override
  public void adjustmentValueChanged(AdjustmentEvent e) {
   // TODO Auto-generated method stub
   e.getAdjustable().getValue();
   if(jcb.isSelected()){
    //System.out.println("e = " + e.getAdjustable().getValue());
    jspsecond.getVerticalScrollBar().setValue(e.getAdjustable().getValue());
   }
   
  }
 });
   //첫번째 텍스트 area 이벤트처리
///////////////////////jafirst event////////////////////////////////////
   //jafirst.addKeyListener((KeyListener) this);
   jafirst.addKeyListener(new KeyListener() {
	  
	  @Override
	  public void keyTyped(KeyEvent e) {
	   // TODO Auto-generated method stub
	   //doc
		  FirstStartPos = jafirst.getCaretPosition();
		  System.out.println("keyTyped FirstStartPos = " + FirstStartPos);
		  btgubun =false;
	  }
	  
	  @Override
	  public void keyReleased(KeyEvent e) {
	   // TODO Auto-generated method stub
	   FirstStartPos = jafirst.getCaretPosition();
	   System.out.println("keyReleased FirstStartPos = " + FirstStartPos);
	   btgubun =false;
	  }
	  
	  @Override
	  public void keyPressed(KeyEvent e) {
	   // TODO Auto-generated method stub
		   btgubun =false;
		   
		   if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V){
		    //if(doc.)
		    
		    System.out.println("keyevent = " + doc.getLength());
		    cvgubun = true;
		   }
		   
		   else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F){
		    //if(doc.)
			   //fs = new findString(cv);
			  // fs = new findString(cv);
			   FirstStartPos = jafirst.getCaretPosition();
			   System.out.println("keyPressed FirstStartPos = " + FirstStartPos);
			   
			   fs.setdesignfindString();
			   Findpopup = "left";
			  
		    
		      // System.out.println("keyevent = " + doc.getLength());
		   }
		   else if(e.getKeyCode() == KeyEvent.VK_ENTER){
		    enterevent = true;
		   }
		   else {
		    cvgubun2 = true;
		    enterevent = true;
		    
		   }
   
      }
	 
	  
 });
   
   
   jafirst.addMouseListener(new MouseListener() {
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		FirstStartPos = jafirst.getCaretPosition();
		System.out.println("mousePressed FirstStartPos = " + FirstStartPos);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		FirstStartPos = jafirst.getCaretPosition();
		System.out.println("mouseClicked FirstStartPos = " + FirstStartPos);
	}
});
   ///////////////////////jafirst event////////////////////////////////////
   
   
  doc.addDocumentListener(new DocumentListener() {
		  
		  
		  @Override
		  public void removeUpdate(DocumentEvent e) {
		   // TODO Auto-generated method stub
		  
		  }
		  
		  @Override
		  public void insertUpdate(DocumentEvent e) {
		   // TODO Auto-generated method stub
		   //chageDoc();
		   //doc.getLength();
		   if(!btgubun && docgubun&&cvgubun){
		     new Thread(new Runnable() {
		         public void run() {
		           
		            
		             SwingUtilities.invokeLater(new Runnable() {
		               public void run() {
		    
		           
		            //compareDoc();
		          try {
			        	  if(jasecond.getDocument().getLength() > 0) {
						    pasteCompare();
			        	  }else {
			        		  firstpaste(1);
			        	  }
					   } catch (Exception e) {
					    // TODO Auto-generated catch block
					    e.printStackTrace();
					   }
		            //화면 이름 초기화
		            jbleftrestfield.setText("");
		             }
		         });
		             
		           
		         }
		       }).start();
		   }
		   enterevent = false;
   
  }
  
  @Override
  public void changedUpdate(DocumentEvent e) {
   // TODO Auto-generated method stub
  
   System.out.println("changedUpdate");
   
    }
   
 }); 
  
 
   
   
   jpfirst.setLayout(new BorderLayout());
   jpfirst.add("North",jpleft);
   jpfirst.add("Center",jspfirst);
   
   //스크롤 이벤트처리
   //jspfirst.add
   
   //첨브파일 디자인
   jpright = new JPanel();
   JLabel jlright = new JLabel("비교파일"); //원본파일 라벨
   jbright = new JButton("파일열기");
   jbrightrestfield = new JTextField(20);
   jbrightrestfield.setEditable(false);
   jpright.setBorder(new TitledBorder("비교파일"));
   jbright.addActionListener(this);
   
   jpright.add(jlright);
   jpright.add(jbrightrestfield);
   jpright.add(jbright);
   
   sc2 = new StyleContext();
   doc2 = new DefaultStyledDocument(sc2);
   jasecond = new JTextPane(doc2);
   jspsecond = new JScrollPane(jasecond);
   jpsecond = new JPanel();
   tln2 = new TextLineNumber(jasecond);
   jspsecond.setRowHeaderView( tln2 );
   //이벤트처리
   jspsecond.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
   
   @Override
   public void adjustmentValueChanged(AdjustmentEvent e) {
    // TODO Auto-generated method stub
    e.getAdjustable().getValue();
    if(jcb.isSelected()){
     //System.out.println("e = " + e.getAdjustable().getValue());
     jspfirst.getVerticalScrollBar().setValue(e.getAdjustable().getValue());
    }
   }
  });
   
   ///////////////////////Document event////////////////////////////////////////
   
   doc2.addDocumentListener(new DocumentListener() {
   public void removeUpdate(DocumentEvent e) {
    // TODO Auto-generated method stub
    //chageDoc();
   /* if(!btgubun && docgubun){
     System.out.println("removeUpdate");
     chg_gubun = 0;
    }
    */
   }
   
   @Override
   public void insertUpdate(DocumentEvent e) {
	    // TODO Auto-generated method stub
	    //chageDoc();
	    //doc.getLength();
	   System.out.println("btgubun =" + btgubun);
	   System.out.println("docgubun =" + docgubun);
	   System.out.println("cvgubun2 =" + cvgubun2);
	    if(!btgubun && docgubun&&cvgubun2){
	      new Thread(new Runnable() {
	          public void run() {
	              SwingUtilities.invokeLater(new Runnable() {
	                public void run() {
	            
	             //화면 이름 초기화
	             jbrightrestfield.setText("");
			             try {
			            	 if(jafirst.getDocument().getLength() > 0) {
								    pasteCompare();
					          }else {
					        		  firstpaste(2);
					          }
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	              });
	              
	            
	          }
	        }).start();
	    }
	    enterevent = false;
    
   }
   
   @Override
   public void changedUpdate(DocumentEvent e) {
    // TODO Auto-generated method stub
   
    System.out.println("changedUpdate");
 
     }
    
  });
   
   ////////////////////////jasecond event///////////////////////////////////
   jasecond.addKeyListener(new KeyListener() {
   
		   @Override
		   public void keyTyped(KeyEvent e) {
		    // TODO Auto-generated method stub
		    //doc
			SecondStartPos = jasecond.getCaretPosition();
		    System.out.println("keyTyped FirstStartPos = " + SecondStartPos);
		    btgubun =false;
		   }
		   
		   @Override
		   public void keyReleased(KeyEvent e) {
		    // TODO Auto-generated method stub
			SecondStartPos = jasecond.getCaretPosition();
			System.out.println("keyReleased FirstStartPos = " + SecondStartPos);   
		    btgubun =false;
		   }
		   
		   @Override
		   public void keyPressed(KeyEvent e) {
		    // TODO Auto-generated method stub
			    btgubun =false;
			    if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V){
			     //if(doc.)
			     
			     System.out.println("jasecond keyevent = " + doc.getLength());
			     cvgubun2 = true;
			    }
			    
			    else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F){
			      //if(doc.)
			      //fs = new findString(cv);
			      //fs = new findString(cv);
			      SecondStartPos = jasecond.getCaretPosition();
				  System.out.println("keyPressed FirstStartPos = " + SecondStartPos);	
			      fs.setdesignfindString();
			      Findpopup = "right";
			     
			    }
			    else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			     enterevent = true;
			    }
			    else {
			     cvgubun = true;
			     enterevent = true;
			    }
		    
		   }
  });
   
   jasecond.addMouseListener(new MouseListener() {
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		SecondStartPos = jasecond.getCaretPosition();
		//System.out.println("mousePressed FirstStartPos = " + SecondStartPos); 
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		SecondStartPos = jasecond.getCaretPosition();
		//System.out.println("mouseClicked FirstStartPos = " + SecondStartPos); 
		
	}
});
   
   ////////////////////////jasecond event///////////////////////////////////
   
   jpsecond.setLayout(new BorderLayout());
   jpsecond.add("North",jpright);
   jpsecond.add("Center",jspsecond);
   
   jpmain.add(jpfirst);
   jpmain.add(jpsecond);
   
   jpsmain.add("North",jptop);
   jpsmain.add("Center",jpmain);
   
   cp.add(jpsmain);
   
  }
  /***********************************
   * firstpaste() - > 첫번째로 그릴때
   * 
   ***********************************/
  public void firstpaste(int i) throws Exception{
	   System.out.println("================firstpaste Start==============");
	   InputStream is = null; 
	   TreeMap<String, String> store = new TreeMap<String, String>(); 
	   cwStyle = sc.addStyle("ConstantWidth", null);
	   String a = (String)jcom.getSelectedItem();
	   int t = Integer.parseInt(a);
	   StyleConstants.setFontSize(cwStyle, t);
	   cwStyle2 = sc2.addStyle("ConstantWidth", null);
	   StyleConstants.setFontSize(cwStyle2, t);
	   initDocument();
	   
	  if(i == 1){
		   is = new ByteArrayInputStream(doc.getText(0, doc.getLength()).getBytes());  
		   
		   
	   }else if (i == 2) {
		   is = new ByteArrayInputStream(doc2.getText(0, doc2.getLength()).getBytes());  
		   
	   }
	   
	   docgubun = false;
	   String encodenm = (String)jcomencode.getSelectedItem();
	   
	   BufferedReader bf = new BufferedReader(new InputStreamReader(is , encodenm));
	   doc.remove(0, doc.getLength());
	   doc2.remove(0, doc2.getLength());
	   
	   while(true){
	       String str = bf.readLine(); 
	       if(str == null)break; 
	       if(i == 1){//첫번째 그리도 그릴때
		       doc.insertString(doc.getLength(), str+ "\n", null);
		       StyleConstants.setForeground(cwStyle, Color.BLACK);
		       StyleConstants.setBackground(cwStyle, Color.white);
		       doc.setCharacterAttributes(doc.getLength() - str.length()-1  , str.length(), cwStyle, false);
	       }else if(i == 2){
	    	   doc2.insertString(doc2.getLength(), str+ "\n", null);
		       StyleConstants.setForeground(cwStyle2, Color.BLACK);
		       StyleConstants.setBackground(cwStyle2, Color.white);
		       doc2.setCharacterAttributes(doc2.getLength() - str.length()-1  , str.length(), cwStyle2, false);
	            
	       }
	   }
	  
		          
	   docgubun = true;
	   cvgubun = false;
	   cvgubun2 = false;
 } 
  
  /***********************************
   * paste && paste read compare
   * 
   ***********************************/
  public void pasteCompare() throws Exception{
   
	   System.out.println("================pasteCompare Start==============");
	   TreeMap<String, String> store = new TreeMap<String, String>(); 
	   TreeMap<String, String> store2 = new TreeMap<String, String>(); 
	   docgubun = false;
	   String encodenm = (String)jcomencode.getSelectedItem();
	   cwStyle = sc.addStyle("ConstantWidth", null);
	   String a = (String)jcom.getSelectedItem();
	   int t = Integer.parseInt(a);
	   StyleConstants.setFontSize(cwStyle, t);
	   cwStyle2 = sc2.addStyle("ConstantWidth", null);
	   StyleConstants.setFontSize(cwStyle2, t);
	   initDocument();
	   
	   InputStream is = new ByteArrayInputStream(doc.getText(0, doc.getLength()).getBytes());
	   InputStream is2 = new ByteArrayInputStream(doc2.getText(0, doc2.getLength()).getBytes());
	      
	   BufferedReader bf = new BufferedReader(new InputStreamReader(is , encodenm));
	   BufferedReader bf2 = new BufferedReader(new InputStreamReader(is2 , encodenm));
	   
	   while(true){
	       String str = bf.readLine(); 
	       if(str == null)break; 
	       store.put(str, str);
	       System.out.println("pasteCompare str =" + str);
	            
	    }
	   
	   while(true){
	       String str = bf2.readLine(); 
	       if(str == null)break; 
	       store2.put(str, str);
	       System.out.println("pasteCompare str2 =" + str);
	            
	    }
	   
	   is = new ByteArrayInputStream(doc.getText(0, doc.getLength()).getBytes());
	   is2 = new ByteArrayInputStream(doc2.getText(0, doc2.getLength()).getBytes());
	   
	   bf = new BufferedReader(new InputStreamReader(is));
	   bf2 = new BufferedReader(new InputStreamReader(is2));
	   
	   doc.remove(0, doc.getLength());
	   doc2.remove(0, doc2.getLength());
	   
   
	   while(true){
		      String str = bf.readLine(); 
		      
		      System.out.println("while 문 = "+str);
		      String temp = null;   
		      if(str == null) break; 
		      temp = store2.get(str);
		      
		      if(temp == null){
		    	  StyleConstants.setForeground(cwStyle, Color.blue);
		    	  StyleConstants.setBackground(cwStyle, Color.YELLOW);
		    	  System.out.println("temp = "+temp);
		          doc.insertString(doc.getLength(), str+ "\n", null);
		          doc.setCharacterAttributes(doc.getLength() - str.length()-1  , str.length(), cwStyle, false);
		      }
		      else {
		          
		          doc.insertString(doc.getLength(), str+ "\n", null);
		          StyleConstants.setForeground(cwStyle, Color.BLACK);
		          StyleConstants.setBackground(cwStyle, Color.white);
		          doc.setCharacterAttributes(doc.getLength() - str.length()-1  , str.length(), cwStyle, false);
		         }
	      }
	   
	   while(true){
		      String str = bf2.readLine(); 
		      
		      System.out.println("while 문 = "+str);
		      String temp = null;   
		      if(str == null) break; 
		     
		      temp = store.get(str);
		      
		      if(temp == null){
		    	  StyleConstants.setForeground(cwStyle2, Color.red);
		    	  StyleConstants.setBackground(cwStyle2, Color.YELLOW);
		    	  System.out.println("temp = "+temp);
		          doc2.insertString(doc2.getLength(), str+ "\n", null);
		          doc2.setCharacterAttributes(doc2.getLength() - str.length()-1  , str.length(), cwStyle2, false);
		      }
		      else {
		          
		          doc2.insertString(doc2.getLength(), str+ "\n", null);
		          StyleConstants.setForeground(cwStyle2, Color.BLACK);
		          StyleConstants.setBackground(cwStyle2, Color.white);
		          doc2.setCharacterAttributes(doc2.getLength() - str.length()-1  , str.length(), cwStyle2, false);
		         }
	   }
	   docgubun = true;
	   cvgubun = false;
	   cvgubun2 = false;
  }   
  /***********************************
   * file && grid read compare
   * 
   ***********************************/
  
  public void pasteandfileCompare(String filenm,String gubun) throws Exception{
	   
	   System.out.println("================pasteandfileCompare Start==============");
	   TreeMap<String, String> store = new TreeMap<String, String>(); 
	   TreeMap<String, String> store2 = new TreeMap<String, String>(); 
	   docgubun = false;
	   String docstring = null;
	   String fileopennm = null;
	   File ft = null;
	   initDocument();
	   
	   String encodenm = (String)jcomencode.getSelectedItem();
	   
	   cwStyle = sc.addStyle("ConstantWidth", null);
	   String a = (String)jcom.getSelectedItem();
	   int t = Integer.parseInt(a);
	   StyleConstants.setFontSize(cwStyle, t);
	   cwStyle2 = sc2.addStyle("ConstantWidth", null);
	   StyleConstants.setFontSize(cwStyle2, t);
	   //jcomencode.get
	   
	   if(gubun.equals("left")){//왼쪽 파일 열었을때
		   fileopennm = jbleftrestfield.getText();
		   docstring = doc2.getText(0, doc2.getLength());
		   
	   }else if (gubun.equals("right")) {//오른쪽 파일 열었을때
		   fileopennm = jbrightrestfield.getText();
		   docstring = doc.getText(0, doc.getLength());
	   }
	   ft = new File(fileopennm);
	    
	   InputStream is = new ByteArrayInputStream(docstring.getBytes());
	      
	   BufferedReader bf = new BufferedReader(new InputStreamReader(is));
	   BufferedReader bf2 = new BufferedReader(new InputStreamReader(new FileInputStream(ft),encodenm));//비교파일
	   
	   
	   while(true){
	       String str = bf.readLine(); 
	       if(str == null)break; 
	       store.put(str, str);
	       System.out.println("pasteCompare str =" + str);
	            
	    }
	   
	   while(true){
	       String str = bf2.readLine(); 
	       if(str == null)break; 
	       store2.put(str, str);
	       System.out.println("pasteCompare str2 =" + str);
	            
	    }
	   
	   is = new ByteArrayInputStream(docstring.getBytes());
	   
	   bf = new BufferedReader(new InputStreamReader(is));
	   bf2 = new BufferedReader(new InputStreamReader(new FileInputStream(ft),encodenm));//비교파일
	   
	   doc.remove(0, doc.getLength());
	   doc2.remove(0, doc2.getLength());
	   
       //그리드내용 읽었을때
	   while(true){
		      String str = bf.readLine(); 
		      
		      System.out.println("while 문 = "+str);
		         
		      if(str == null) break; 
		      String temp = store2.get(str);
		      
		      if(temp == null){
		    	  
		    	  if(gubun.equals("left")){
		    		  StyleConstants.setForeground(cwStyle2, Color.red);
		    		  StyleConstants.setBackground(cwStyle2, Color.YELLOW);
			    	  System.out.println("temp = "+temp);
			          doc2.insertString(doc2.getLength(), str+ "\n", null);
			          doc2.setCharacterAttributes(doc2.getLength() - str.length()-1  , str.length(), cwStyle2, false);
		    	  }else if(gubun.equals("right")){
		    		  StyleConstants.setForeground(cwStyle, Color.BLUE);
		    		  StyleConstants.setBackground(cwStyle, Color.YELLOW);
			    	  System.out.println("temp = "+temp);
			          doc.insertString(doc.getLength(), str+ "\n", null);
			          doc.setCharacterAttributes(doc.getLength() - str.length()-1  , str.length(), cwStyle, false); 
		    	  }
		      }
		      else {
		          
		    	  if(gubun.equals("left")){
		    		  doc2.insertString(doc2.getLength(), str+ "\n", null);
			          StyleConstants.setForeground(cwStyle2, Color.BLACK);
			          StyleConstants.setBackground(cwStyle2, Color.white);
			          doc2.setCharacterAttributes(doc2.getLength() - str.length()-1  , str.length(), cwStyle2, false);
		    	  }else if(gubun.equals("right")){
		    		  doc.insertString(doc.getLength(), str+ "\n", null);
			          StyleConstants.setForeground(cwStyle, Color.BLACK);
			          StyleConstants.setBackground(cwStyle, Color.white);
			          doc.setCharacterAttributes(doc.getLength() - str.length()-1  , str.length(), cwStyle, false);
		    	  }
		          
		         }
	      }
	   
	   while(true){
		      String str = bf2.readLine(); 
		      
		      System.out.println("while 문 = "+str);
		         
		      if(str == null) break; 
		      String temp = store.get(str);
		      
		      if(temp == null){
		    	  if(gubun.equals("left")){
		    		  
		    		  StyleConstants.setForeground(cwStyle, Color.BLUE);
		    		  StyleConstants.setBackground(cwStyle, Color.YELLOW);
			    	  System.out.println("temp = "+temp);
			          doc.insertString(doc.getLength(), str+ "\n", null);
			          doc.setCharacterAttributes(doc.getLength() - str.length()-1  , str.length(), cwStyle, false); 
		    		  
		    	  }else if(gubun.equals("right")){

		    		  StyleConstants.setForeground(cwStyle2, Color.red);
		    		  StyleConstants.setBackground(cwStyle2, Color.YELLOW);
			    	  System.out.println("temp = "+temp);
			          doc2.insertString(doc2.getLength(), str+ "\n", null);
			          doc2.setCharacterAttributes(doc2.getLength() - str.length()-1  , str.length(), cwStyle2, false);
			          
		    	  }
		      }
		      else {
		          
		    	  if(gubun.equals("left")){
		    		  doc.insertString(doc.getLength(), str+ "\n", null);
			          StyleConstants.setForeground(cwStyle, Color.BLACK);
			          StyleConstants.setBackground(cwStyle, Color.white);
			          doc.setCharacterAttributes(doc.getLength() - str.length()-1  , str.length(), cwStyle, false);
		    		  
		    		  
		    	  }else if(gubun.equals("right")){
		    		  
		    		  doc2.insertString(doc2.getLength(), str+ "\n", null);
			          StyleConstants.setForeground(cwStyle2, Color.BLACK);
			          StyleConstants.setBackground(cwStyle2, Color.white);
			          doc2.setCharacterAttributes(doc2.getLength() - str.length()-1  , str.length(), cwStyle2, false);
		    		  
		    	  }
		      }
	   }
	   docgubun = true;
	   cvgubun = false;
	   cvgubun2 = false;
 } 
  
  /***********************************
   * file && file read compare
   * 
   ***********************************/
  public void comparetxt(String orgfilenm, String comparefilenm ) throws BadLocationException, IOException{
	   TreeMap<String, String> store = new TreeMap<String, String>(); 
	   TreeMap<String, String> store2 = new TreeMap<String, String>(); 
	   initDocument();
	   
	   System.out.println("comparetxt 시작");
	   int initorg = 0;
	   int initcompare = 0;
	   File ft = null;
	   File ft2 = null ;
	   ft = new File(orgfilenm);
	   ft2 = new File(comparefilenm);
	   initorg = doc.getLength();
	   initcompare = doc2.getLength();
	   int count = 0; 
	   
	   String encodenm = (String)jcomencode.getSelectedItem();
	   cwStyle = sc.addStyle("ConstantWidth", null);
	   String a = (String)jcom.getSelectedItem();
	   int t = Integer.parseInt(a);
	   StyleConstants.setFontSize(cwStyle, t);
	   cwStyle2 = sc2.addStyle("ConstantWidth", null);
	   StyleConstants.setFontSize(cwStyle2, t);
	   
	   
	   if(initorg != 0){
	    doc.remove(0, doc.getLength());
	   }
	   
	   if(initcompare != 0){
	    doc2.remove(0, doc2.getLength());
	   }
	     
	  BufferedReader bf = null;
	  BufferedReader bf2 = null;
	   if(comparefilenm != null){
	   bf = new BufferedReader(new InputStreamReader(new FileInputStream(ft2),encodenm));//비교파일
	   bf2 = new BufferedReader(new InputStreamReader(new FileInputStream(ft),encodenm));//원본파일 읽기
	   
	    while(true){
	     String str = bf.readLine(); 
	     if(str == null)break; 
	     store.put(str, str);
	          
	    }
	    while(true){
	     String str = bf2.readLine(); 
	     if(str == null)break; 
	     store2.put(str, str);
	          
	    }
	   }
	   
	   
	   bf = new BufferedReader(new InputStreamReader(new FileInputStream(ft),encodenm)); 
	   bf2 = new BufferedReader(new InputStreamReader(new FileInputStream(ft2),encodenm));//비교파일 읽기
	   
	   while(true){
	    String str = bf.readLine(); 
	    count++;  
	    if(str == null)break; 
	    String temp = store.get(str);
	    if(temp == null){
	        //jasecond.color
	        
	        //int a = (int)jcom.getSelectedItem();
	   
	        
	        StyleConstants.setForeground(cwStyle, Color.blue);
	        StyleConstants.setBackground(cwStyle, Color.YELLOW);
	     
	        doc.insertString(doc.getLength(), str+ "\n", null);
	        tln.setCurrentLineForeground(Color.blue)  ;
	        
	        doc.setCharacterAttributes(doc.getLength() - str.length()-1  , str.length(), cwStyle, false);
	        
	       }else {
	        
	        doc.insertString(doc.getLength(), str+ "\n", null);
	        StyleConstants.setForeground(cwStyle, Color.BLACK);
	        StyleConstants.setBackground(cwStyle, Color.white);
	        doc.setCharacterAttributes(doc.getLength() - str.length()-1  , str.length(), cwStyle, false);
	       }
	             }
	   while(true){
			    String str = bf2.readLine(); 
			    
			    if(str == null)break; 
			    String temp = store2.get(str);
			    if(temp == null){
			        //jasecond.color
				        System.out.println("temp == null ->"+str);
				   
				        
				        StyleConstants.setForeground(cwStyle2, Color.red);
				        StyleConstants.setBackground(cwStyle2, Color.YELLOW);
				        
				           //heading2Style.addAttribute(StyleConstants.Foreground, Color.red);
				        
				       
				        doc2.insertString(doc2.getLength(), str+ "\n", null);
				        doc2.setCharacterAttributes(doc2.getLength() - str.length() - 1  , str.length(), cwStyle2, false);
				        //doc2.get
				        
				       }else {
				        
				        doc2.insertString(doc2.getLength(), str+ "\n", null);
				        StyleConstants.setForeground(cwStyle2, Color.BLACK);
				        StyleConstants.setBackground(cwStyle2, Color.white);
				        doc2.setCharacterAttributes(doc2.getLength() - str.length() - 1  , str.length(), cwStyle2, false);
				       }
			    
			  
	       }
	   
	   
	   
	  }
  
  public void findDocumentStr(int pos,int Findlength ,String gubun ){
	  //StyleConstants.setBackground(findcwStyle, Color.GREEN);
	 
	  orgdoc = new DefaultStyledDocument();
	  orgdoc.addDocumentListener(this); 

	  orgdoc2 = new DefaultStyledDocument();
	  orgdoc2.addDocumentListener(this);  
	  
	  try {
		if(gubun.equals("left")){
			
		     mergeDocument(doc,orgdoc);
		     
		}else {
			mergeDocument(doc2,orgdoc2);	
		}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	   if(gubun.equals("left")){
			
		   jafirst.setDocument(orgdoc);
		   StyleConstants.setBackground(cwStyle, Color.GREEN);
		   orgdoc.setCharacterAttributes(pos  , Findlength , cwStyle, false);
		     
		}else {
			jasecond.setDocument(orgdoc2);	
			StyleConstants.setBackground(cwStyle2, Color.GREEN);
			orgdoc2.setCharacterAttributes(pos  , Findlength , cwStyle2, false);
		}
	  
	  
	  //jafirst.setDocument(orgdoc);
	  
  }
  public void initDocument(){
	  /*
	  try {
		mergeDocument((DefaultStyledDocument) jafirst.getDocument(),doc);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  try {
			mergeDocument((DefaultStyledDocument) jasecond.getDocument(),doc2);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	    }
	    */
	  doc = (DefaultStyledDocument) jafirst.getDocument();
	  doc2 = (DefaultStyledDocument) jasecond.getDocument();
	  jafirst.setDocument(doc);
	  jasecond.setDocument(doc2);
  
  }
  
/****************Document -> Document logic ***********************/
  
  
  public static void mergeDocument(DefaultStyledDocument source, DefaultStyledDocument dest) throws BadLocationException {
      ArrayList<DefaultStyledDocument.ElementSpec> specs=new ArrayList<DefaultStyledDocument.ElementSpec>();
      DefaultStyledDocument.ElementSpec spec=new DefaultStyledDocument.ElementSpec(new SimpleAttributeSet(), 
               DefaultStyledDocument.ElementSpec.EndTagType);
      specs.add(spec);
      fillSpecs(source.getDefaultRootElement(), specs, false);
      spec=new DefaultStyledDocument.ElementSpec(new SimpleAttributeSet(), DefaultStyledDocument.ElementSpec.StartTagType);
      specs.add(spec);

      DefaultStyledDocument.ElementSpec[] arr = new DefaultStyledDocument.ElementSpec[specs.size()];
      specs.toArray(arr);
      insertSpecs(dest, dest.getLength(), arr);
  }

  protected static void insertSpecs(DefaultStyledDocument doc, int offset, DefaultStyledDocument.ElementSpec[] specs) {
      try {
//          doc.insert(0, specs);  method is protected so we have to
          //extend document or use such a hack
          Method m=DefaultStyledDocument.class.getDeclaredMethod("insert", new Class[] {int.class, DefaultStyledDocument.ElementSpec[].class});
          m.setAccessible(true);
          m.invoke(doc, new Object[] {offset, specs});
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  protected static void fillSpecs(Element elem, ArrayList<DefaultStyledDocument.ElementSpec> specs, boolean includeRoot) throws BadLocationException{
      DefaultStyledDocument.ElementSpec spec;
      if (elem.isLeaf()) {
          String str=elem.getDocument().getText(elem.getStartOffset(), elem.getEndOffset()-elem.getStartOffset());
          spec=new DefaultStyledDocument.ElementSpec(elem.getAttributes(), 
                   DefaultStyledDocument.ElementSpec.ContentType,str.toCharArray(), 0, str.length());
          specs.add(spec);
      }
      else {
          if (includeRoot) {
              spec=new DefaultStyledDocument.ElementSpec(elem.getAttributes(), DefaultStyledDocument.ElementSpec.StartTagType);
              specs.add(spec);
          }
          for (int i=0; i<elem.getElementCount(); i++) {
              fillSpecs(elem.getElement(i), specs, true);
          }

          if (includeRoot) {
              spec=new DefaultStyledDocument.ElementSpec(elem.getAttributes(), DefaultStyledDocument.ElementSpec.EndTagType);
              specs.add(spec);
          }
      }
  }
  /****************Document -> Document logic ***********************/
  
  public void setFindStrArea(String Findstrlocal,String TartgerStr,String gubun){
	 
	  
	  
			if(gubun.equals("left")){
				   
				   System.out.println("===================setFindStrArea left======================");
				   FirstNextPos = fs.getFindPos(TartgerStr, FirstStartPos);
						if(FirstNextPos != -1){
							
							System.out.println("FirstNextPos = " + FirstNextPos);
							System.out.println("FirstStartPos = " + FirstStartPos);
							//System.out.println("테스트  findstr = " + doc.getText(FirstNextPos  , Findstrlocal.length()));
							findDocumentStr(FirstNextPos ,Findstrlocal.length(), "left");
							FirstStartPos = FirstNextPos + Findstrlocal.length();
							jafirst.setCaretPosition(FirstNextPos);
							//System.out.println("FirstNextPos Next= " + FirstNextPos);
							//System.out.println("FindStr22 = " + doc.getText(1 , 2));
						}
						else {
							//System.out.println("맞는문자열이 없음");
							
								FirstStartPos = 0;					
								FirstNextPos = fs.getFindPos(TartgerStr, FirstStartPos);
								if(FirstNextPos != -1){
									System.out.println("no FirstNextPos = " + FirstNextPos);
									findDocumentStr(FirstNextPos ,Findstrlocal.length(), "left");
									FirstStartPos = FirstNextPos + Findstrlocal.length();
									jafirst.setCaretPosition(FirstNextPos);
								}else {
									JOptionPane.showMessageDialog(cv,
										    "문자열이 없습니다.",
										    "Error",
										    JOptionPane.ERROR_MESSAGE);
								}
						}
			   }else if(gubun.equals("right")){
				   System.out.println("===================setFindStrArea Right======================");
				   
				   SecondNextPos = fs.getFindPos(TartgerStr, SecondStartPos);
					
					if(SecondNextPos != -1){
						
						System.out.println("FirstNextPos = " + SecondNextPos);
						//System.out.println("Right FindStr = " + doc2.getText(SecondNextPos  , findsrt.length()));
						findDocumentStr(SecondNextPos ,Findstrlocal.length(), "right");
						SecondStartPos = SecondNextPos + Findstrlocal.length();
						jasecond.setCaretPosition(SecondNextPos);
					}
					else {
						
						//System.out.println("맞는문자열이 없음");
						
							SecondStartPos = 0;
							SecondNextPos = fs.getFindPos(TartgerStr, SecondStartPos);
							if(SecondNextPos != -1){
								findDocumentStr(SecondNextPos ,Findstrlocal.length(), "right");
								SecondStartPos = SecondNextPos + Findstrlocal.length();
								jasecond.setCaretPosition(SecondNextPos);
							}else {
								JOptionPane.showMessageDialog(cv,
									    "문자열이 없습니다.",
									    "Error",
									    JOptionPane.ERROR_MESSAGE);
							}
					}
			   }				
		}

@Override
public void changedUpdate(DocumentEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void insertUpdate(DocumentEvent e) {
	// TODO Auto-generated method stub
	System.out.println(e.getDocument());
	if(e.getDocument()==orgdoc){
		System.out.println("====================================================================");
	if(!btgubun && docgubun&&cvgubun){
	     new Thread(new Runnable() {
	         public void run() {
	           
	            
	             SwingUtilities.invokeLater(new Runnable() {
	               public void run() {
	    
	           
	            //compareDoc();
	          try {
		        	  if(jasecond.getDocument().getLength() > 0) {
					    pasteCompare();
		        	  }else {
		        		  firstpaste(1);
		        	  }
				   } catch (Exception e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
				   }
	            //화면 이름 초기화
	            jbleftrestfield.setText("");
	             }
	         });
	             
	           
	         }
	       }).start();
	   }
	   enterevent = false;
	}else if(e.getDocument()==orgdoc2){
		System.out.println("=====================orgdoc2===============================================");
		System.out.println("jafirst.getDocument().getLength() = " +jafirst.getDocument().getLength());
		System.out.println("btgubun = " + btgubun);
		System.out.println("docgubun = " + docgubun);
		System.out.println("cvgubun = " + cvgubun2);
		if(!btgubun && docgubun&&cvgubun2){
		     new Thread(new Runnable() {
		         public void run() {
		           
		            
		             SwingUtilities.invokeLater(new Runnable() {
		               public void run() {
		    
		           
		            //compareDoc();
		          try {
			        	  if(jafirst.getDocument().getLength() > 0) {
						    pasteCompare();
			        	  }else {
			        		  firstpaste(2);
			        	  }
					   } catch (Exception e) {
					    // TODO Auto-generated catch block
					    e.printStackTrace();
					   }
		            //화면 이름 초기화
		            jbrightrestfield.setText("");
		             }
		         });
		             
		           
		         }
		       }).start();
		   }
		   enterevent = false;
			
	}

}

@Override
public void removeUpdate(DocumentEvent e) {
	// TODO Auto-generated method stub
	
}



  }

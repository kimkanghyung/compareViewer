package compareViewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Iterator;


import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
//import javax.swing.text.html.HTMLDocument.Iterator;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.omg.PortableServer.ServantRetentionPolicyValue;

public class FolderSearch extends JFrame {
	Container cp;
	////////////원본파일//////////////////
	JTextField jptopta; //원본파일열기 파일경로
	JTextField jptopta2; //new파일열기 파일경로
	JButton jpcancel;  //원본파일 선택하는 버튼
	JButton jpok;  //원본파일 선택하는 버튼
	JTable jt_top;
	JTable jt;
	DefaultTableModel dtm ;
	//Object rowData1[] = {"1","1 번",false};
	
	JPanel jptop;
	
	JPanel s_jptop;
	JPanel s_jpcenter;
	JPanel s_jpbottom;
	JScrollPane jsp1;
	
	String orgPath;
	String newPath;
	compareView cv;
	//private boolean ImInLoop = false;
	
	
	FolderSearch(compareView cv){
	
		
		this.cv = cv;
		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		super.setResizable(false);
		super.setSize(600,500);
		cp = super.getContentPane();
		//setDesign();
	}
	
	public void setDesign(String orgPath, String newPath) {
		this.orgPath = orgPath;
		this.newPath = newPath;
		//setVisible(true);
		///////////////////원본패널/////////////////////////
		jptop = new JPanel();  
		s_jptop = new JPanel(); 
		s_jpcenter = new JPanel(); // Jtable
		s_jpbottom = new JPanel();
		
		jsp1 = new JScrollPane();
		//String org_header[] = {"원본파일","비교파일","chk"};
		//rowData1 = {{"1","1 번",false},{"2","2 번",true} };
		jsp1 = make_table();
	
		jpok = new JButton("확인");
		jpcancel = new JButton("취소");
		jpok.addActionListener(cv);
		jpcancel.addActionListener(cv);
		System.out.println("=========orgPath============" + orgPath);
		System.out.println("=========newPath============" + newPath);
		
		s_jpbottom.add(jpok);
		s_jpbottom.add(jpcancel);
		
		JLabel jl1 = new JLabel("원본파일경로");
		jptopta = new JTextField(10);
		jptopta.setText(orgPath);
		jptopta.setEditable(false);
		
		JLabel jl2 = new JLabel("비교파일경로");
		jptopta2 = new JTextField(10);
		jptopta2.setText(newPath);
		jptopta2.setEditable(false);
		
		
		jptop.setBorder(new TitledBorder(""));
		jptop.setLayout(new BorderLayout());
		
		s_jptop.add(jl1);
		s_jptop.add(jptopta);
		s_jptop.add(jl2);
		s_jptop.add(jptopta2);
		
		s_jpcenter.add(jsp1);
		
		jptop.add("North",s_jptop);
		jptop.add("Center",s_jpcenter);
		jptop.add("South",s_jpbottom);
		cp.add(jptop);
		getFileDirList();
		setVisible(true);
		
	}
	
	
public JScrollPane make_table(){
				
		JCheckBox checkBox = new JCheckBox();
		
		
		
		//dtm.set
				//jt.get
		dtm = new DefaultTableModel()
		{
			private boolean ImInLoop = false;

            

		    @Override
		    public boolean isCellEditable(int row, int column) {
		      if (column == 2 ){
		    	  return true;
		      }
		       return false;
		    }
		   
		    @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                if (columnIndex == 2) {
                    if (!ImInLoop) {
                        ImInLoop = true;
                        Boolean bol = (Boolean) aValue;
                        super.setValueAt(aValue, rowIndex, columnIndex);
                        for (int i = 0; i < jt.getRowCount(); i++) {
                            if (i != rowIndex) {
                            	super.setValueAt(!bol, i, columnIndex);
                            }
                        }
                        ImInLoop = false;
                    }
                } else {
                    setValueAt(aValue, rowIndex, columnIndex);
                }
            }
            
		 
		};
		dtm.addColumn("원본파일경로");
		dtm.addColumn("비교파일경로");
		dtm.addColumn("chk");
		jt = new JTable(dtm);
		
		
		autoColSize(jt,jt.getModel());
		jt.getColumn("chk").setCellRenderer(dcr);
		
		jt.getColumn("chk").setCellEditor(new DefaultCellEditor(checkBox));
		checkBox.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane jsp = new JScrollPane(jt);
		return jsp;	
		
	}
	

	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer()
	 {
	  public Component getTableCellRendererComponent  // 셀렌더러
	   (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	  {
	   JCheckBox box= new JCheckBox();
	   box.setSelected(((Boolean)value).booleanValue());  
	   box.setHorizontalAlignment(JLabel.CENTER);
	   return box;
	  }
	};
	
	public void getFileDirList(){
		

		TreeMap<String, String> store = new TreeMap<String, String>(); 
		TreeMap<String, String> store2 = new TreeMap<String, String>();

		//String path="C:\";
		File dirFile=new File(orgPath);
		File dirFile2=new File(newPath);
		File []fileList=dirFile.listFiles();
		File []fileList2=dirFile2.listFiles();
		
		
		for(File tempFile : fileList) {
		  if(tempFile.isFile()) {
		    String tempPath=tempFile.getParent();
		    String tempFileName=tempFile.getName();
		    store.put(tempFileName, tempFileName);
 
	        }
		}
		
		System.out.println("store = "+store);
		
		for(File tempFile : fileList2) {
		  if(tempFile.isFile()) {
		    String tempPath=tempFile.getParent();
		    String tempFileName=tempFile.getName();
		    store2.put(tempFileName, tempFileName);
 
	        }
		}
		
		System.out.println("store2 = "+store2);
		JTableinert(store,store2);
		
	}
	
	public void JTableinert(TreeMap<String, String> orgTmap, TreeMap<String, String> newTmap){
		
		//Object tmpData[][] = {{"","",""}};
		//Iterator it = ortTmap.keySet().iterator();
		//Collection c = orgTmap.values();
		 Iterator<String> keys = orgTmap.keySet().iterator();
	        while( keys.hasNext() ){
	            String key = keys.next();
	            
	            
	            String temp = newTmap.get(key);
	            System.out.println( "tmp = " +  temp);
	    	    if(temp == null){
	    	    	Object tmp[] = {orgTmap.get(key) , "" ,false};
	    	    	
	    	    	dtm.addRow(tmp);
	    	    	autoColSize(jt,jt.getModel());
	    	    	
	    	    }else {
	    	    	
	    	    	Object tmp[] = {temp , temp ,false};
	    	    	System.out.println("==============================================" + tmp);
	    	    	dtm.addRow(tmp);
	    	    	autoColSize(jt,jt.getModel());
	    	    }
	      }
	     
	     Iterator<String> keys2 = newTmap.keySet().iterator();
	     while( keys2.hasNext() ){
	            String key = keys2.next();
	            
	            
	            String temp = orgTmap.get(key);
	            System.out.println( "tmp = " +  temp);
	    	    if(temp == null){
	    	    	Object tmp[] = {"",key ,false};
	    	    	dtm.addRow(tmp);
	    	    	autoColSize(jt,jt.getModel());
	    	    }
	      }   

	}

	private void autoColSize(JTable table, TableModel model)
	 {
		System.out.println("================autoColSize==============================" );
	  TableColumn column = null;
	  Component comp = null;
	  int headerWidth = 0;
	  int cellWidth = 0;
	  int font = 11;
	  for (int i = 0, n = model.getColumnCount(); i < n; i++)
	  {
	   column = table.getColumnModel().getColumn(i);
	   try
	   {
	    headerWidth = column.getHeaderValue().toString().length()
	      * font;
	    column.setPreferredWidth(headerWidth);
	   }
	   catch (Exception e)
	   {
	   }
	  }
	 }
	
	public String getchekboxSelectedrowOrg(){
		String Orgresult = null;
		for(int i = 0 ; i < jt.getRowCount() ; i++){
			if((boolean) jt.getValueAt(i, 2)){
				Orgresult = (String) jt.getValueAt(i, 0);
			}
		}
		return Orgresult;
	}
	
	public String getchekboxSelectedrowNew(){
		String Newresult = null;
		for(int i = 0 ; i < jt.getRowCount() ; i++){
			if((boolean) jt.getValueAt(i, 2)){
				Newresult = (String) jt.getValueAt(i, 1);
			}
		}
		return Newresult;
	}
	
	 

}

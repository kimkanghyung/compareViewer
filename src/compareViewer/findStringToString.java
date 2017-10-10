package compareViewer;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class findStringToString {
	
	String findstr ;
	String targetString ;
	//ArrayList indexArray;
	int Startpos ;
	int nextStartpos;
	
	findStringToString(){
		
	}

	
	findStringToString(String findstr , String targetString , int Startpos){
		
		this.findstr = findstr;
		this.targetString = targetString;
		this.Startpos = Startpos;
		//int pos = targetString.indexOf(findstr);
		//System.out.println("pos = " + pos );
		findstring();
	}
	
	public int indexstartPosition(){
		
	  return   nextStartpos;
		
	}
	

	
	public void findstring(){
		int pos = 0;
		int startpos =0 ;
		//indexArray = new ArrayList();
		//System.out.println("findstr = " + findstr );
		//System.out.println("targetString = " + targetString );
		
		//targetString = "kkhkkh";
		//findstr = "h";
		
		//int pos = targetString.indexOf(findstr);
		//System.out.println("pos = " + pos );
		
		
		while(pos != -1){
			//pos = targetString.indexOf(findstr, pos + 1);
			
			pos = targetString.indexOf(findstr, Startpos);
			Startpos++;
			//if
			if(pos > 0) break;
			
			
		}
		//System.out.println("pos = " + (pos - 1) );
		nextStartpos = pos ;
		
	}
}

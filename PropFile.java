/**
 * PropFile.java
 * the Prop File container class for the Prop Editor program/project
 * Evan Widger
 * CS434xp70
*/
 
package net.widgetron.propeditor;


import java.util.*;
import java.io.*;
 
 /**
 * class  PropFile *
 * @author EvanW
 * @version 0.2
 
 */
public class PropFile {
	private String fileName;
	private Hashtable<String,String> propList;
	public boolean savedStatus = false;
	
	public PropFile(String pFN) throws FileNotFoundException,IOException{
		fileName = pFN;
		FileReader fh = new FileReader(pFN);
		propList = parseFile(fh);
		fh.close();
	}
	public Hashtable<String,String> parseFile(FileReader pFile) throws FileNotFoundException{
		Hashtable<String,String> pList = new Hashtable<String,String>();

		
			    Scanner scanner = new Scanner(pFile);
			    try {
			      //first use a Scanner to get each line
			      while ( scanner.hasNextLine() ){
			    	  String nextline = scanner.nextLine();
			    	  if( nextline.matches("^\\s*[^\\#]*") ){
					    Scanner scanner2 = new Scanner(nextline);
					    scanner2.useDelimiter("\\s*=\\s*");
					    if ( scanner2.hasNext() ){
					    	try{
					    		String name = scanner2.next("^\\s*[^\\#]*");
					    		//System.out.println("Name is : " + name );
					    		String value ="";
					    		if ( scanner2.hasNext() ){
					    			value = scanner2.next();
					    		//	System.out.println(", and Value is : " + value );
					    		}
					    		pList.put(name, value);
					    		
					    	//	System.out.println("Name is : " + name + ", and Value is : " + value );
					    	}catch(NoSuchElementException e){
					    		// e.printStackTrace();
					    	}
					    } else {
					    	// System.out.println("Empty or invalid line. Unable to process.");
					    }
			    	  
					    //(no need for finally here, since String is source)
					    scanner2.close();
			    	  }
			      }
				}
			    finally {
			      //ensure the underlying stream is always closed
			      scanner.close();
			    }
		return pList; 
		
	}
	
	public boolean saveFile(){
		return saveFileAs(fileName);
	}
	
	public boolean saveFileAs(String newFileName){
		fileName = newFileName;
		String[] wholeFile = getAllProps();
		try {
			// Create file
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
			for (String line : wholeFile){
				out.write(line + "\n");
			}
			// Close the output stream
			out.close();
			savedStatus = true;
			return true;
		} catch (Exception e) {// Catch exception if any
		//	System.err.println("Error: " + e.getMessage());
			return false;
		}
	}
	
	public boolean modProp(String pName, String pVal){
		if ( this.propList.containsKey(pName) ){
			if( ! validatePropValue(pVal)){
				return false;
			}
			propList.put(pName, pVal);
			savedStatus = false;
			return true;
		}else{
			return addProp(pName,pVal);
		}
	}
	public boolean addProp(String pName, String pVal){
		if ( ( ! this.propList.containsKey(pName) ) && ( validatePropValue(pVal) && validatePropName(pName) ) ){
			propList.put(pName, pVal);
			savedStatus = false;
			return true;
		}else{
			return false;
		}
	}
	
	public boolean validatePropName(String pName){
		String stringOfBad = "";
//FIXME^^^^^^^^^^^^^
		
		//Pattern patternOfBad = Pattern.compile(stringOfBad);
		
		if(pName.matches(stringOfBad)){
			return false;
		}
		
		return true;
	}
	public boolean validatePropValue(String pVal){
		String stringOfGood = "[a-zA-Z\\._\\d^\\s]+";
//FIXME^^^^^^^^^^^^^^^^^^^^^^^^
		
		
		//Pattern patternOfBad = Pattern.compile(stringOfBad);
		
		if(pVal.matches(stringOfGood)){
			
			return true;
		}
		
		return false;
	}
	
	public String getProp(String pName){
		if ( this.propList.containsKey(pName) ){
			return propList.get(pName);
		}else{
			return null;
		}
	}
		 
	public String[] getAllProps(){
		
		ArrayList<String> keys = new ArrayList<String>(propList.keySet());
		Collections.sort(keys);

		String[] tmpString = new String[propList.size()]; 
		
		for (int i=0; i< keys.size(); i++) {
			 String key = (String)keys.get(i);
			 tmpString[i] = key  + "=" + propList.get( key );
		} 
		return tmpString;
	}
	public Hashtable<String,String> getPropList(){
		return propList;
	}
}
	


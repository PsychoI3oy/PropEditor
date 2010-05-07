/**
* @author Evan Widger
* personal tool for making SuggestionFiles, not for distribution with PropEditor itself;
* reads build.prop files and includes values in suggestionfile if not already present; output back to suggestionfile in 
property.name;val1|val2|val3 
* format
* @version 0.2
*/
package net.widgetron.propeditor;

import java.util.*;
import java.io.*;


public class SuggestionFileMaker {
	private String propFileName;
	private String sugFileName = "SuggestionFile.txt";
	private PropFile pF;
	private SuggestionFile sF;
	private Hashtable<String, ArrayList<String>> sugList;

	public SuggestionFileMaker(String pFN) {
		propFileName = pFN;
		try {

			this.sF = new SuggestionFile(sugFileName);
			this.pF = new PropFile(propFileName);

			this.sugList = this.sF.getSugList();

			addPropsToSug(this.pF);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("yuo fial");
		}
	}

	public void addPropsToSug(PropFile pF) {
		Hashtable<String, String> propList = pF.getPropList();
		Enumeration<String> keys = propList.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			addProp(key, propList.get(key));
		}
	}

	public boolean saveFile() {

		String wholeFile = new String();

		Enumeration<String> keys = sugList.keys();
		
		while (  keys.hasMoreElements()  )
		{
			String key = (String)keys.nextElement();
			 
			   
			wholeFile += key + ";";
			for(int i = 0; i < sugList.get(key).size() ; i++){
				if(i < ( sugList.get(key).size() -1) ){
					wholeFile +=  sugList.get( key ).get(i) + "|";
				}else{
					wholeFile +=  sugList.get( key ).get(i) + "\n";
				}
			}
			
		} 
		   
		System.out.println(wholeFile);

		try {
			// Create file
			FileWriter fstream = new FileWriter(sugFileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(wholeFile);
			// Close the output stream
			out.close();
			return true;
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return false;
		}

	}

	public void addProp(String name, String value) {
		System.out.println("testing in addP: n: " + name + " v: " + value);
		if (!this.sugList.containsKey(name)) {
			ArrayList<String> tmpList = new ArrayList<String>();
			tmpList.add(value);
			sugList.put(name, tmpList);
		} else {
			System.out.println("propname already found, trying to add value");
			addValue(name, value);
		}
	}

	public void addValue(String name, String value) {
		System.out.println("testing in addV: n: " + name + " v: " + value);
		if (this.sugList.containsKey(name)
				&& (!sugList.get(name).contains(value))) {
			sugList.get(name).add(value);
		}else{
			System.out.println("propval already found, skipping");
		}
	}

	public static void main(String[] args) {
		if (args.length > 0) {
			for(String arg : args){
				SuggestionFileMaker sFm = new SuggestionFileMaker(arg);
				sFm.saveFile();
			}
		} else {
			System.out.println("you fail");
		}

	}
}

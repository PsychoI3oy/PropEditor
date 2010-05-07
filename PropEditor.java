
/**
 * @author evan
 *the "Controller" for the MVC architecture. 
 * This class coordinates a PropFile and a list of suggestions
 * @version 0.2
 */
package net.widgetron.propeditor;

public class PropEditor {

	static private String pFileName = "";
	static private String sFileName = "SuggestionFile.txt";
	

	private PropFile pF; 
	private SuggestionFile sF;
	
	
	public PropEditor(String fName, String sFName){		
		pFileName = fName;
		PropFile pFile = openPropFile(pFileName);
		if( pFile != null){
			//System.out.println("Opened prop file");
			this.pF = pFile;
			openSugFile(sFileName);			
		}
	}
	
	public PropFile openPropFile(String propFileName){
		PropFile pFil;
		try {
			pFil = new PropFile(propFileName);
			return pFil;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void closePropFile(){
		pF = null;
	}
	
	public void openSugFile(String sugFileName){
		SuggestionFile sF ;
		try {
			sF = new SuggestionFile(sugFileName);
			this.sF = sF;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SuggestionFile getSugFile(){
		return sF;
	}
	public PropFile getPropFile(){
		return pF;
	}

	public String[] getSugs(String suggestFor, String edMode){
		if(edMode.equals("Prop")){
			return  sF.getNameSugs();
		}else{
			return  sF.getValSugs(suggestFor);
		}
	}
	
}

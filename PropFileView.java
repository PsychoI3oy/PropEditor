
/**
 * @author evan
 * the View for the MVC arch
 * This handles all the gui stuff for the PropEditor
 * @version 0.2
 */
package net.widgetron.propeditor;


import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.awt.*;
import java.awt.event.*;


public class PropFileView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2298873358265327106L;
	static private String pFileName = "/home/evan/dox/CS434-java/PropEditor/propfiles/build.prop";
	static private String sFileName = "/home/evan/dox/CS434-java/PropEditor/SuggestionFile.txt";

	private JList jlPropsList;
	private JList jlSugList;
	private DefaultListModel propListModel;
	private DefaultListModel sugListModel;


	private JButton jbCancelEd;
	private JButton jbCancelSs;
	private JButton jbSave;
	private JButton jbSaveAs;

	private JPanel jpSugScreen;
	private JPanel jpEdit;
	private JPanel jpMain;
	private JFileChooser myFileChooser;

	static PropEditor pE; 

	private String currProp;
	private String editMode;


	public PropFileView(){		
		super("PropEditor");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainScreen();
		//editorScreen(this.pE.getPropFile().getAllProps());
		//suggestionScreen(sugFor);


	}

	public void mainScreen(){

		//begin main window
		JLabel jlbMainTitle = new JLabel("PropEditor");
		JLabel jlbcopyleft = new JLabel("CopyLeft 2010 Evan Widger");
		JButton jbOpen = new JButton("Open File");

		JPanel jpTitle = new JPanel();
		jpTitle.setLayout( new BorderLayout()); 


		jpTitle.add(jlbMainTitle, BorderLayout.CENTER );
		jpTitle.add(jlbcopyleft, BorderLayout.SOUTH );

		GridBagConstraints c = new GridBagConstraints();
		jpMain = new JPanel(new GridBagLayout());


		c.fill = GridBagConstraints.BOTH;

		c.gridwidth = 3;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 1;

		jpMain.add(jpTitle, c);

		c.gridheight = 1;
		c.gridwidth = 2;
		c.gridx = 2;
		c.gridy = 5;
		jpMain.add(jbOpen, c);


		getContentPane().add(jpMain);
		this.setSize(400, 600);

		myFileChooser = new JFileChooser();
		OpenHandler oH = new OpenHandler();
		jbOpen.addActionListener(oH);

		getContentPane().validate();
		getContentPane().repaint();

		//end main window
	}

	public void editorScreen(String propList[]){
		currProp = null;
		//begin editor screen
		propListModel = new DefaultListModel();

		for (String p : propList){
			propListModel.addElement(p);	
		}

		jlPropsList = new JList(propListModel);

		jlPropsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlPropsList.setLayoutOrientation(JList.VERTICAL);
		jlPropsList.setVisibleRowCount(-1);

		JScrollPane spPropListScroller = new JScrollPane(jlPropsList);
		spPropListScroller .setPreferredSize(new Dimension(350, 500));

		JLabel jlProp = new JLabel("Property ");
		JLabel jlVal = new JLabel(" Value");

		jbSave = new JButton ("Save");
		jbSaveAs = new JButton ("SaveAs");
		jbCancelEd = new JButton ("Cancel");
		JButton jbAdd = new JButton ("Add");

		GridBagConstraints eC = new GridBagConstraints();
		jpEdit = new JPanel(new GridBagLayout());

		getContentPane().removeAll();
		jpEdit.setLayout(new GridBagLayout());

		// header

		eC.gridwidth  = 2;
		eC.gridheight = 1;
		eC.gridx = 1;
		eC.gridy = 1;
		jpEdit.add(jlProp, eC);
		eC.gridx = 3;
		jpEdit.add(jlVal, eC);

		//prop list
		eC.fill = GridBagConstraints.BOTH;
		eC.anchor=GridBagConstraints.CENTER;
		eC.gridwidth  = 4;
		eC.gridheight = 6;
		eC.gridx = 1;
		eC.gridy = 2;
		jpEdit.add(spPropListScroller , eC);

		//buttons
		eC.fill = GridBagConstraints.NONE;
		eC.anchor=GridBagConstraints.SOUTH;
		eC.gridwidth  = 1;
		eC.gridheight = 1;
		eC.gridx = 1;
		eC.gridy = 8;
		jpEdit.add(jbCancelEd, eC);
		eC.gridx = 2;
		jpEdit.add(jbSaveAs, eC);
		eC.gridx = 3;
		jpEdit.add(jbSave, eC);
		eC.gridx = 4;
		jpEdit.add(jbAdd, eC);

		getContentPane().add(jpEdit);
		getContentPane().validate();
		getContentPane().repaint();

		SaveHandler sH = new SaveHandler();
		jbSaveAs.addActionListener(sH);
		jbSave.addActionListener(sH);

		AddHandler aH = new AddHandler();
		jbAdd.addActionListener(aH);
		ModHandler mH = new ModHandler();
		jlPropsList.addListSelectionListener(mH);
		CancelHandler cH = new CancelHandler();
		jbCancelEd.addActionListener(cH);

		//end editor screen
	}

	public void suggestionScreen(String suggestFor, String edMode){
		editMode = edMode;
		//begin suggestion screen
		getContentPane().removeAll();
		String[] sugList = pE.getSugs(suggestFor, edMode);		

		sugListModel = new DefaultListModel();
		for (String p : sugList){
			sugListModel.addElement(p);	
		}
		sugListModel.addElement("Manual entry");

		jlSugList = new JList(sugListModel);

		jlSugList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlSugList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane spSugListScroller = new JScrollPane(jlSugList);
		spSugListScroller.setPreferredSize(new Dimension(350, 500));

		jbCancelSs = new JButton("Cancel");
		JLabel jlSugtitle;
		if(editMode.equals("Prop")){
			jlSugtitle = new JLabel("Property name suggestions:");
		}else{
			jlSugtitle = new JLabel("Value suggestions for: ");
		}
		JLabel jlSuggestion = new JLabel(suggestFor);

		JPanel jpHeader = new JPanel( new FlowLayout());
		JPanel jpButtons = new JPanel( new GridLayout(1,2) );
		jpSugScreen = new JPanel();

		jpHeader.add(jlSugtitle);
		jpHeader.add(jlSuggestion);

		jpButtons.add(jbCancelSs);

		jpSugScreen.add(jpHeader, BorderLayout.NORTH);
		jpSugScreen.add(spSugListScroller, BorderLayout.CENTER);
		jpSugScreen.add(jpButtons, BorderLayout.SOUTH);
		CancelHandler cH = new CancelHandler();
		jbCancelSs.addActionListener(cH);

		SuggestionHandler sH = new SuggestionHandler();
		jlSugList.addListSelectionListener(sH);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jpSugScreen);

		getContentPane().validate();
		getContentPane().repaint();
		//end suggestion screen

	}

	public static void main (String[] args){
		if (args.length >= 1){
			pFileName = args[0];
		}else if (args.length >= 2){
			sFileName = args[1];
		}

		PropFileView pV = new PropFileView();

		pV.setVisible(true);

	}
	//---------------------begin inner event listener/handler classes
	class SaveHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if( e.getSource() == jbSaveAs ){
				int returnVal = myFileChooser.showSaveDialog(jpEdit);
				if (returnVal == JFileChooser.APPROVE_OPTION){
					File newFile = myFileChooser.getSelectedFile();
					pFileName = newFile.getAbsolutePath();
					pE.getPropFile().saveFileAs(pFileName);
				}
			}else if (e.getSource() == jbSave){
				pE.getPropFile().saveFile();
			}
		}
	}
	class OpenHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int returnVal = myFileChooser.showOpenDialog(jpMain);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File openFile = myFileChooser.getSelectedFile();
				boolean isProp = (openFile.getName().substring(openFile.getName().lastIndexOf('.')+1,
						openFile.getName().length()).equals("prop"));
				if(openFile.canRead() && isProp){
					pFileName = openFile.getAbsolutePath();
					pE = new PropEditor(pFileName, sFileName);
					if(pE.getPropFile() == null){
						JOptionPane.showMessageDialog(jpMain, "Failed to open prop file");
					}else if(pE.getSugFile() == null){
						JOptionPane.showMessageDialog(jpMain, "Failed to open suggestion file");
					}else{
						editorScreen(pE.getPropFile().getAllProps());
					}
				}else{
					JOptionPane.showMessageDialog(jpMain, "Filename chosen not a readable .prop file");
				}
			}
		}
	}
	class AddHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			suggestionScreen("","Prop");
		}
	}
	class ModHandler implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			String sugFor = jlPropsList.getSelectedValue().toString();
			currProp = sugFor.substring(0,sugFor.indexOf("="));
			suggestionScreen(currProp, "Value");
		}
	}
	class CancelHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if( e.getSource() == jbCancelEd ){
				getContentPane().removeAll();
				mainScreen();
			}else if (e.getSource() == jbCancelSs){
				editorScreen(pE.getPropFile().getAllProps());
			}
		}
	}
	class SuggestionHandler implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			String selectedEdit;
			String thingString;
			String selVal = jlSugList.getSelectedValue().toString();
			if (currProp == null){
				thingString = "";
			}else{
				thingString = " for " + currProp;
			}
			if( selVal.equals("Manual entry")){
				selectedEdit = (String)JOptionPane.showInputDialog(
						jpSugScreen,
						"Enter the custom "+ editMode + thingString,
						"Enter Custom Value",
						JOptionPane.PLAIN_MESSAGE,null,null,"");
			}else{
				selectedEdit = selVal;
			}
			if (!(selectedEdit == null)){ 

				if(editMode.equals("Prop")){
					currProp = selectedEdit;
					suggestionScreen(currProp, "Value");
				}else{
					if (pE.getPropFile().modProp(currProp, selectedEdit)){
						editorScreen(pE.getPropFile().getAllProps());
					}else{
						JOptionPane.showMessageDialog(jpSugScreen, "Illegal character in property name/value.");
					}
					
				}

			}

		}
	}
}

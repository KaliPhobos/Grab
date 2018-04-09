package v00s12;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;

public class Window extends JFrame implements Runnable {

	private static String[] FileNames;
	private JPanel contentPane;
	private DefaultListModel listModel = null;
	
	public static JList Gui_FileList = null;
	public static JTextArea txtBox = null;
	public static JProgressBar progressBar = null;
	
	public static TypeList MyTypeList = null;	// Holds ALL information about any file type
	public static FileList MyFileList = null;	// Holds ALL information abut any found file
	
	public static int[] SelectedListItemIndex = null;
	//List SelectedListItemIndex = Collections.synchronizedList(new ArrayList(0));
	
	boolean initial = true;
	
	public void run() {
		this.setVisible(true);
	}


	/**
	 * Create the frame.
	 */
	public Window(String path) {
		//String path = "/Users/p2/Documents";
		List<File> allFiles = General.loadFiles(path);			// "C:\\Users\\p2\\Downloads\\test"
		MyTypeList = General.loadTypes(allFiles);
		MyFileList = FileList.createFileList(General.getFileNames(path));
		FileList.sort();
		FileList.normalize();
		TypeList.sort();
		
		//txtBox.setText(TypeList.analize());			// Can't automate at startup since txtBox-element is still NULL

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnAnalyze = new JButton("Analyze");
		btnAnalyze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//listModel.clear();
				MyFileList = FileList.createFileList(General.getFileNames(path));
				FileList.sort();
				FileList.normalize();
				FillFileList();																// ############ in usecase no new files will ever pop up so this button is only ment to reset any changes
				txtBox.setText(TypeList.analize());
				// progressBar.	// how to set data?
			}
		});
		panel_1.add(btnAnalyze);
		
		JButton btnStartGrab = new JButton("Start Grab");
		panel_1.add(btnStartGrab);
		
		JButton btnDeleteAll = new JButton("Delete All");
		panel_1.add(btnDeleteAll);
		
		JButton btnAbort = new JButton("Abort");
		panel_1.add(btnAbort);
		
		progressBar = new JProgressBar();
		progressBar.setBackground(Color.BLACK);
		panel_1.add(progressBar);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		tabbedPane.addTab("Manage Files", null, panel, null);
		panel.setLayout(null);
		
		listModel = new DefaultListModel();
		FillFileList();
		JList Gui_FileList = new JList(listModel);
		JScrollPane scrollList = new JScrollPane(Gui_FileList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Gui_FileList.setBackground(Color.DARK_GRAY);
		Gui_FileList.setForeground(Color.WHITE);
		scrollList.setBackground(Color.DARK_GRAY);
		scrollList.setForeground(Color.WHITE);
		scrollList.setBounds(144, 23, 415, 234);
		Gui_FileList.addListSelectionListener(new ListSelectionListener() {		// Against all odds this listener is NOT causing the NullPointer-exception when trying to restore item focus
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()) {
					if( Gui_FileList.getSelectedIndex()!=-1) {	// Trigered when list is updating
						//SelectedListItemIndex.clear();
						SelectedListItemIndex = Gui_FileList.getSelectedIndices();	// Used to identify last selected Item for Move&Priorize buttons
						System.out.println(getListContent(SelectedListItemIndex));
					}
				}
			}
		});
		panel.add(scrollList);
		
		JCheckBox chckbxCopyExecutables = new JCheckBox("Copy Documents");
		chckbxCopyExecutables.setBackground(new Color(192, 192, 192));
		chckbxCopyExecutables.setSelected(true);
		chckbxCopyExecutables.setBounds(5, 23, 133, 18);
		panel.add(chckbxCopyExecutables);
		
		JCheckBox chckbxCopyImages = new JCheckBox("Copy Images");
		chckbxCopyImages.setBackground(new Color(192, 192, 192));
		chckbxCopyImages.setSelected(true);
		chckbxCopyImages.setBounds(5, 44, 133, 18);
		panel.add(chckbxCopyImages);
		
		JCheckBox chckbxCopyVideos = new JCheckBox("Copy Videos");
		chckbxCopyVideos.setBackground(new Color(192, 192, 192));
		chckbxCopyVideos.setSelected(true);
		chckbxCopyVideos.setBounds(5, 65, 133, 18);
		panel.add(chckbxCopyVideos);
		
		JCheckBox chckbxCopyAudio = new JCheckBox("Copy Audio");
		chckbxCopyAudio.setBackground(new Color(192, 192, 192));
		chckbxCopyAudio.setSelected(true);
		chckbxCopyAudio.setBounds(5, 86, 133, 18);
		panel.add(chckbxCopyAudio);
		
		JCheckBox checkBox_3 = new JCheckBox("Copy Executables");
		checkBox_3.setBackground(new Color(192, 192, 192));
		checkBox_3.setSelected(true);
		checkBox_3.setBounds(5, 107, 133, 18);
		panel.add(checkBox_3);
		
		JLabel lblThisIsA = new JLabel("Files ordered by individual priority (Will be copied in this order)");
		lblThisIsA.setBounds(144, 4, 415, 14);
		panel.add(lblThisIsA);
		
		JCheckBox chckbxCopyScripts = new JCheckBox("Copy Scripts");
		chckbxCopyScripts.setBackground(new Color(192, 192, 192));
		chckbxCopyScripts.setSelected(true);
		chckbxCopyScripts.setBounds(5, 128, 133, 18);
		panel.add(chckbxCopyScripts);
		
		JCheckBox chckbxCopyArchives = new JCheckBox("Copy Archives");
		chckbxCopyArchives.setBackground(new Color(192, 192, 192));
		chckbxCopyArchives.setSelected(true);
		chckbxCopyArchives.setBounds(5, 149, 133, 18);
		panel.add(chckbxCopyArchives);
		
		JCheckBox chckbxCopyBinaries = new JCheckBox("Copy Binaries");
		chckbxCopyBinaries.setBackground(new Color(192, 192, 192));
		chckbxCopyBinaries.setSelected(true);
		chckbxCopyBinaries.setBounds(5, 170, 133, 18);
		panel.add(chckbxCopyBinaries);
		
		JCheckBox chckbxCopyLogfiles = new JCheckBox("Copy Logfiles");
		chckbxCopyLogfiles.setBackground(new Color(192, 192, 192));
		chckbxCopyLogfiles.setSelected(true);
		chckbxCopyLogfiles.setBounds(5, 191, 133, 18);
		panel.add(chckbxCopyLogfiles);
		
		JLabel lblFilterFiles = new JLabel("Filter Targets:");
		lblFilterFiles.setBounds(10, 4, 128, 14);
		panel.add(lblFilterFiles);
		
		JButton btnMoveUp = new JButton("Move Up");
		btnMoveUp.setBounds(254, 263, 100, 23);
		btnMoveUp.addActionListener(new ActionListenerMoveUp());
		panel.add(btnMoveUp);
		
		JButton btnMoveDown = new JButton("Move Down");
		btnMoveDown.setBounds(364, 263, 100, 23);
		btnMoveDown.addActionListener(new ActionListenerMoveDown());
		panel.add(btnMoveDown);
		
		JButton btnTopPriority = new JButton("Top Priority");
		btnTopPriority.setBounds(144, 263, 100, 23);
		btnTopPriority.addActionListener(new ActionListenerMoveTop());
		panel.add(btnTopPriority);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(474, 263, 85, 23);
		panel.add(btnCancel);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Statistics", null, panel_2, null);
		panel_2.setLayout(null);
		txtBox = new JTextArea("");		// static element
		JScrollPane scrollBox = new JScrollPane(txtBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		txtBox.setBackground(Color.DARK_GRAY);
		txtBox.setForeground(Color.WHITE);
		scrollBox.setBackground(Color.DARK_GRAY);
		scrollBox.setForeground(Color.WHITE);
		scrollBox.setBounds(5, 0, 560, 285);
		
		//txtBox.setBounds(5, 0, 560, 285);
		panel_2.add(scrollBox);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Advanced Settings", null, panel_3, null);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_4, null);
		panel_4.setLayout(null);
		
		/*Object[] results = General.loadFiles(path);
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) result[1];
		JTree tree = new JTree(root);*/
		JTree tree = new JTree();
		tree.setVisibleRowCount(18);
		JScrollPane scrollTree = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollTree.setBackground(Color.DARK_GRAY);
		scrollTree.setForeground(Color.WHITE);
		tree.setBackground(Color.DARK_GRAY);
		tree.setForeground(Color.WHITE);
		scrollTree.setBounds(0, 0, 569, 291);

		panel_4.add(scrollTree);

		
		
		
	}
	public void FillFileList() {
		if (initial==true) {
			for(int x=0;x<FileList.getLength();x++) {
				FileItem currentFile = FileList.getFile(x);
				listModel.addElement("("+(int) currentFile.getBasicRelevance()+"/"+(int) currentFile.getAdditionalRelevance()+"/"+(int) currentFile.getNormalizedRelevance()+")	"+currentFile.getPath() + "	("+currentFile.getSizeFormatted()+")");
			}
			initial = false;
		} else {
			for(int x=0;x<FileList.getLength();x++) {
				FileItem currentFile = FileList.getFile(x);
				listModel.setElementAt("("+(int) currentFile.getBasicRelevance()+"/"+(int) currentFile.getAdditionalRelevance()+"/"+(int) currentFile.getNormalizedRelevance()+")	"+currentFile.getPath() + "	("+currentFile.getSizeFormatted()+")", x);
			}
		}
	}
	class CustomFocusListener implements FocusListener{							// No practical use yet but shall be used later 8Additional information when putting focus to certain text boxes//statistics
		public void focusGained(FocusEvent e) {
			System.out.println(e.getComponent().getClass().getSimpleName() + " gained focus. ");
		}
		public void focusLost(FocusEvent e) {
			System.out.println(e.getComponent().getClass().getSimpleName() + " lost focus. ");
		}   
	}
	class ActionListenerMoveUp implements ActionListener {						// Move all selected Items by -1 (up)
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("move up");
			if (getMin(SelectedListItemIndex)>0) {								// Item can be moved up (id>0)
				MyFileList.GuiMoveUp(SelectedListItemIndex);					// -> MOVE
				for(int i=0;i<SelectedListItemIndex.length;i++) {				// shift IDs of selected items by -1
					SelectedListItemIndex[i]--;
				}
				System.out.println(getListContent(SelectedListItemIndex));
				//listModel.clear();
				FillFileList();
				// focus, select
			}
		}
	}
	class ActionListenerMoveDown implements ActionListener {					// Move all selected Items by +1 (down)
		public void actionPerformed(ActionEvent arg0) {
			if (getMax(SelectedListItemIndex)<FileList.getLength()-1) {			// Item can be moved down (id<max)
				MyFileList.GuiMoveDown(SelectedListItemIndex);					// -> MOVE
				for(int i=0;i<SelectedListItemIndex.length;i++) {				// shift IDs of selected items by +1
					SelectedListItemIndex[i]++;
				}

				System.out.println(getListContent(SelectedListItemIndex));
				//listModel.removeAllElements();								// works just fine as well, but doesnt fix the bug with "setSelectedIndices"
				FillFileList();
				//Gui_FileList.setSelectedIndices(SelectedListItemIndex);
				//Gui_FileList.ensureIndexIsVisible(SelectedListItemIndex[0]);	// rel: https://java.wekeepcoding.com/article/11947811/Why+JList+uses+int%5b%5d+to+select+indices+when+it+require+a+Set+of+Integer%3f
			}
		}
	}
	class ActionListenerMoveTop implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (getMin(SelectedListItemIndex)>0) {
				MyFileList.GuiMoveTop(SelectedListItemIndex);
				for(int i=0;i<SelectedListItemIndex.length;i++) {
					SelectedListItemIndex[i]= i;
				}
				System.out.println(getListContent(SelectedListItemIndex));
				FillFileList();
				// focus, select
			}
		}
	}
	public static int getMin(int[] num) {
		int min = num[0];
		for(int i=0;i<num.length;i++) {
			if(num[i]<min) {
				min=num[i];
			}
		}
		return min;
	}
	/*public static int getMin(ArrayList num) {
		int min = (int) num.get(0);
		for(int x=0;x<num.size();x++) {
			if((int)num.get(x)<min) {
				min=(int)num.get(x);
			}
		}
		return min;
	}*/
	public static int getMax(int[] num) {
		int max = num[0];
		for(int i=0;i<num.length;i++) {
			if(num[i]>max) {
				max=num[i];
			}
		}
		return max;
	}
	public String getListContent(int[] values) {
		String result = "selected:";
		for(int x=0;x<values.length;x++) {
			result = result + " " + values[x];
		}
		return result;
	}
}

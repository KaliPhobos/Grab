package v00s09;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
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
import java.util.List;
import java.awt.event.ActionEvent;

public class Window extends JFrame implements Runnable {

	private static String[] FileNames;
	private JPanel contentPane;
	private DefaultListModel listModel = null;
	
	public static JList Gui_FileList = null;
	
	public static TypeList MyTypeList = null;	// Holds ALL information about any file type
	public static FileList MyFileList = null;	// Holds ALL information abut any found file
	
	public void run() {
		this.setVisible(true);
	}


	/**
	 * Create the frame.
	 */
	public Window() {
		String path = "/Users/p2/Downloads";
		List<File> allFiles = General.loadFiles(path);	// "C:\\Users\\p2\\Downloads\\test"
		MyTypeList = General.loadTypes(allFiles);
		MyFileList = FileList.createFileList(General.getFileNames(path));
		MyFileList.sort();
		MyFileList.normalize();
		

		
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
		
		JButton btnAnalyze = new JButton("Analyzeee");
		btnAnalyze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listModel.clear();
				FillFileList();
			}
		});
		panel_1.add(btnAnalyze);
		
		JButton btnStartGrab = new JButton("Start Grab");
		panel_1.add(btnStartGrab);
		
		JButton btnDeleteAll = new JButton("Delete All");
		panel_1.add(btnDeleteAll);
		
		JButton btnAbort = new JButton("Abort");
		panel_1.add(btnAbort);
		
		JProgressBar progressBar = new JProgressBar();
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
		/*FileList.setModel(new AbstractListModel() {
			String[] values = {"Analyze first"};
					public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});*/
		scrollList.setBounds(144, 23, 415, 234);
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
		panel.add(btnMoveUp);
		
		JButton btnMoveDown = new JButton("Move Down");
		btnMoveDown.setBounds(364, 263, 100, 23);
		panel.add(btnMoveDown);
		
		JButton btnTopPriority = new JButton("Top Priority");
		btnTopPriority.setBounds(144, 263, 100, 23);
		panel.add(btnTopPriority);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(474, 263, 85, 23);
		panel.add(btnCancel);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Statistics", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Advanced Settings", null, panel_3, null);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_4, null);
		panel_4.setLayout(null);
		
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
		for(int x=0;x<MyFileList.getLength();x++) {
			FileItem currentFile = FileList.getFile(x);
			listModel.addElement("("+(int) currentFile.getBasicRelevance()+"/"+(int) currentFile.getAdditionalRelevance()+"/"+(int) currentFile.getNormalizedRelevance()+")	"+currentFile.getPath() + "	("+currentFile.getSizeFormatted()+")");
		}
	}

}

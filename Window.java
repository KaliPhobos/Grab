package v00s06;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JCheckBox;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Window extends JFrame implements Runnable {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public void run() {
		Window frame = new Window();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public Window() {
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
		
		JList list = new JList();
		JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		list.setBackground(Color.DARK_GRAY);
		list.setForeground(Color.WHITE);
		scroll.setBackground(Color.DARK_GRAY);
		scroll.setForeground(Color.WHITE);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"C:\\xampp\\phpMyAdmin\\robots.txt", "C:\\xampp\\phpMyAdmin\\schema_export.php", "C:\\xampp\\phpMyAdmin\\server_binlog.php", "C:\\xampp\\phpMyAdmin\\server_collations.php", "C:\\xampp\\phpMyAdmin\\server_databases.php", "C:\\xampp\\phpMyAdmin\\server_engines.php", "C:\\xampp\\phpMyAdmin\\server_export.php", "C:\\xampp\\phpMyAdmin\\server_import.php", "C:\\xampp\\phpMyAdmin\\server_plugins.php", "C:\\xampp\\phpMyAdmin\\server_privileges.php", "C:\\xampp\\phpMyAdmin\\server_replication.php", "C:\\xampp\\phpMyAdmin\\server_sql.php", "C:\\xampp\\phpMyAdmin\\server_status.php", "C:\\xampp\\phpMyAdmin\\server_status_advisor.php", "C:\\xampp\\phpMyAdmin\\server_status_monitor.php", "C:\\xampp\\phpMyAdmin\\server_status_processes.php", "C:\\xampp\\phpMyAdmin\\server_status_queries.php", "C:\\xampp\\phpMyAdmin\\server_status_variables.php", "C:\\xampp\\phpMyAdmin\\server_user_groups.php", "C:\\xampp\\phpMyAdmin\\server_variables.php"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scroll.setBounds(144, 23, 415, 234);
		//panel.add(list);
		panel.add(scroll);
		
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
		panel_4.setLayout(new BorderLayout(0, 0));
		//panel_7.add(tree);

		
		
		
	}
}

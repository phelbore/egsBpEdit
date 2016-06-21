package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import bp.bpFile;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frmEgsBpEditor;
	private bpFile bp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmEgsBpEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEgsBpEditor = new JFrame();
		frmEgsBpEditor.setTitle("EGS BP Editor");
		frmEgsBpEditor.setBounds(100, 100, 726, 559);
		frmEgsBpEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEgsBpEditor.getContentPane().setLayout(new MigLayout("", "[]", "[]"));

		JMenuBar menuBar = new JMenuBar();
		frmEgsBpEditor.setJMenuBar(menuBar);
		
		JMenu mnMain = new JMenu("Main");
		menuBar.add(mnMain);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(frmEgsBpEditor);
				if(result == JFileChooser.APPROVE_OPTION) {
					openFile(fileChooser.getSelectedFile());
				}
			}
		});
		mnMain.add(mntmOpen);
	}
	private void openFile(File f) {
		JDialog parsing = new JDialog(frmEgsBpEditor, "Parsing file");
		parsing.getContentPane().add(new JLabel("Parsing file, please wait"));
		parsing.setLocationRelativeTo(frmEgsBpEditor);
		parsing.setSize(200, 100);
		parsing.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		parsing.validate();
		parsing.setVisible(true);
		bp = new bpFile(f);
		parsing.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		parsing.dispose();
		if(bp.isValid()) {
			JOptionPane.showMessageDialog(frmEgsBpEditor, "Parsing complete, file is valid");
		} else {
			JOptionPane.showMessageDialog(frmEgsBpEditor, "Parsing complete, file is not valid");
		}
	}
}

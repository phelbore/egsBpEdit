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

import bp.BpFile;
import bp.BpFile.FileType;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frmEgsBpEditor;
	private BpFile bp;

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
		
		JMenuItem mntmOpenEpb = new JMenuItem("Open EPB");
		mntmOpenEpb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(frmEgsBpEditor);
				if(result == JFileChooser.APPROVE_OPTION) {
					openFile(fileChooser.getSelectedFile(), FileType.EPB);
				}
			}
		});
		mnMain.add(mntmOpenEpb);
		
		JMenuItem mntmOpenZip = new JMenuItem("Open ZIP");
		mntmOpenZip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(frmEgsBpEditor);
				if(result == JFileChooser.APPROVE_OPTION) {
					openFile(fileChooser.getSelectedFile(), FileType.ZIP);
				}
			}
		});
		mnMain.add(mntmOpenZip);
		
		JMenuItem mntmSaveEpb = new JMenuItem("Save EPB");
		mnMain.add(mntmSaveEpb);
		mntmSaveEpb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showSaveDialog(frmEgsBpEditor);
				if(result == JFileChooser.APPROVE_OPTION) {
					saveFile(fileChooser.getSelectedFile(), FileType.EPB);
				}
			}
		});
		
		JMenuItem mntmSaveZip = new JMenuItem("Save ZIP");
		mnMain.add(mntmSaveZip);
	}
	
	private void openFile(File f, FileType t) {
		JDialog parsing = new JDialog(frmEgsBpEditor, "Parsing file");
		parsing.getContentPane().add(new JLabel("Parsing file, please wait"));
		parsing.setLocationRelativeTo(frmEgsBpEditor);
		parsing.setSize(200, 100);
		parsing.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		parsing.validate();
		parsing.setVisible(true);
		bp = new BpFile(f, t);
		parsing.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		parsing.dispose();
		if(bp.isValid()) {
			JOptionPane.showMessageDialog(frmEgsBpEditor, "Parsing complete, file is valid");
		} else {
			JOptionPane.showMessageDialog(frmEgsBpEditor, "Parsing complete, file is not valid");
		}
	}
	
	private void saveFile(File f, FileType t) {
		JDialog parsing = new JDialog(frmEgsBpEditor, "Saving file");
		parsing.getContentPane().add(new JLabel("Saving file, please wait"));
		parsing.setLocationRelativeTo(frmEgsBpEditor);
		parsing.setSize(200, 100);
		parsing.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		parsing.validate();
		parsing.setVisible(true);
		parsing.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		parsing.dispose();
		if(bp.saveFile(f, t)) {
			JOptionPane.showMessageDialog(frmEgsBpEditor, "File saved.");
		} else {
			JOptionPane.showMessageDialog(frmEgsBpEditor, "File could not be saved.");
		}
	}
}
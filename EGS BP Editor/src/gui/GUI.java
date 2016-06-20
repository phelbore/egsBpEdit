package gui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

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
		bp = new bpFile(f);
	}
}

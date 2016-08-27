package com.iwebcoding.mobile.tool.component;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.iwebcoding.mobile.tool.constant.GUI;
import com.iwebcoding.mobile.tool.constant.nls.ProgressNLS;
import com.iwebcoding.mobile.tool.util.I18n;

public class ProgressDialog extends JDialog {
	private static final long serialVersionUID = 7854380261947197148L;
	
	public ProgressDialog() {
		setSize(GUI.PROGRESS_WIDTH, GUI.PROGRESS_HEIGHT);
		setResizable(false);
		setUndecorated(true);
		setModal(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		buildUI();
	}
	
	private void buildUI() {
		JPanel container = new JPanel(new BorderLayout());
		container.setBorder(BorderFactory.createTitledBorder(""));
		
		container.add(new JLabel(I18n.PROGRESS.getString(ProgressNLS.MSG_WAIT), JLabel.CENTER), BorderLayout.NORTH);
		
		JProgressBar progress = new JProgressBar();
		progress.setIndeterminate(true);
		progress.setStringPainted(true);
		progress.setString(I18n.PROGRESS.getString(ProgressNLS.MSG_PROGRESS));
		container.add(progress, BorderLayout.CENTER);
		
		getContentPane().add(container);
	}
}

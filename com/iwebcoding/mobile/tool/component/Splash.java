package com.iwebcoding.mobile.tool.component;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.iwebcoding.mobile.tool.constant.GUI;
import com.iwebcoding.mobile.tool.constant.nls.AppViewNLS;
import com.iwebcoding.mobile.tool.util.I18n;

public class Splash extends JDialog {
	private static final long serialVersionUID = -791506858053222875L;
	private JPanel container;
	
	public Splash() {
		setUndecorated(true);
		setDefaultLookAndFeelDecorated(true);
		setSize(GUI.SPLASH_WIDTH, GUI.SPLASH_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		buildUI();
	}
	
	protected void buildUI() {
		container = new JPanel(new BorderLayout());
		container.add(new JLabel(new ImageIcon(GUI.ICON_SPLASH)), BorderLayout.CENTER);
		
		JProgressBar progress = new JProgressBar();
		progress.setIndeterminate(true);
		progress.setStringPainted(true);
		progress.setString(I18n.APPVIEW.getString(AppViewNLS.INIT_APPVIEW));
		container.add(progress, BorderLayout.SOUTH);
		
		getContentPane().add(container);
	}
}

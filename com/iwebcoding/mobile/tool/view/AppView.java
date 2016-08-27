package com.iwebcoding.mobile.tool.view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.iwebcoding.mobile.tool.component.AppCfgPane;
import com.iwebcoding.mobile.tool.component.ContactDialog;
import com.iwebcoding.mobile.tool.component.SettingDialog;
import com.iwebcoding.mobile.tool.constant.App;
import com.iwebcoding.mobile.tool.constant.GUI;
import com.iwebcoding.mobile.tool.constant.nls.AppViewNLS;
import com.iwebcoding.mobile.tool.controller.DashBoardController;
import com.iwebcoding.mobile.tool.framework.View;
import com.iwebcoding.mobile.tool.util.AppCfgUtil;
import com.iwebcoding.mobile.tool.util.FileUtil;
import com.iwebcoding.mobile.tool.util.I18n;
import com.iwebcoding.mobile.tool.util.LogUtil;
import com.iwebcoding.mobile.tool.util.SysCfgUtil;

public class AppView extends JFrame implements View {
	private static final long serialVersionUID = -4336503052003242284L;
	private JPanel container;
	private JMenuItem settingMenu = new JMenuItem(I18n.APPVIEW.getString(AppViewNLS.MENU_SYS_CFG));
	private JMenuItem exitMenu = new JMenuItem(I18n.APPVIEW.getString(AppViewNLS.MENU_SYS_EXIT));
	private JMenuItem guideMenu = new JMenuItem(I18n.APPVIEW.getString(AppViewNLS.MENU_HELP_GUIDE));
	private JMenuItem contactMenu = new JMenuItem(I18n.APPVIEW.getString(AppViewNLS.MENU_ABOUT_CONTACT));
	
	public AppView () {
		setDefaultLookAndFeelDecorated(true);
		setTitle(getTitle());
		setSize(GUI.APPVIEW_WIDTH, GUI.APPVIEW_HEIGHT);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(GUI.ICON_TOOL).getImage());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				if (Boolean.valueOf(SysCfgUtil.getSystemConfig(
						App.KEY_SYSCFG_CLEAR)).booleanValue()) {
					File download = new File(SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_DOWNLOAD_DIR));
					if (download.exists()) {
						FileUtil.deleteFiles(download);
					}
				}
			}
		});
		buildUI();
	}
	
	protected void buildUI() {
		container = new JPanel(new BorderLayout());
		buildSysMenuBar();
		buildWelcomPane();
		buildAppCfgPane();
		buildCommandsPane();
		buildCopyrightPane();
		getContentPane().add(container);
	}
	
	private void buildSysMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu sysMenu = new JMenu(I18n.APPVIEW.getString(AppViewNLS.MENU_SYS));
		settingMenu.setIcon(new ImageIcon(GUI.ICON_SETTINGS));
		sysMenu.add(settingMenu);
		
		sysMenu.addSeparator();
		exitMenu.setIcon(new ImageIcon(GUI.ICON_EXIT));
		sysMenu.add(exitMenu);
		
		JMenu helpMenu = new JMenu(I18n.APPVIEW.getString(AppViewNLS.MENU_HELP));
		guideMenu.setIcon(new ImageIcon(GUI.ICON_HELP));
		helpMenu.add(guideMenu);
		
		JMenu aboutMenu = new JMenu(I18n.APPVIEW.getString(AppViewNLS.MENU_ABOUT));
		contactMenu.setIcon(new ImageIcon(GUI.ICON_CONTACT));
		aboutMenu.add(contactMenu);
		
		menuBar.add(sysMenu);
		menuBar.add(helpMenu);
		menuBar.add(aboutMenu);
		attachMenuListeners();
		setJMenuBar(menuBar);
	}
	
	private void attachMenuListeners() {
		settingMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				onShowSettingsDialog();
			}
		});
		
		exitMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				onExitApp();
			}
		});
		
		guideMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				onShowUserGuide();
			}
		});
		
		contactMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				onShowContactInfo();
			}
		});
	}
	
	private void onShowSettingsDialog() {
		SettingDialog dialog = new SettingDialog();
		dialog.setVisible(true);
	}
	
	private void onExitApp() {
		dispose();
		System.exit(0);
	}
	
	private void onShowUserGuide() {
		final String MTHNAME = "onShowUserGuide";
		try {
			Desktop.getDesktop().open(new File(AppCfgUtil.getAppCfg(App.KEY_APPCFG_USERDUIDE)));
		} catch (IOException e) {
			LogUtil.logError(AppView.class.getName(), MTHNAME, e.getMessage(), e);
		}
	}
	
	private void onShowContactInfo() {
		ContactDialog dialog = new ContactDialog();
		dialog.setVisible(true);
	}
	
	private void buildWelcomPane() {
		JPanel wlcPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		wlcPane.setBorder(BorderFactory.createTitledBorder(""));
		
		JLabel wlcMsg = new JLabel(I18n.APPVIEW.getString(AppViewNLS.MSG_WELCOM));
		wlcMsg.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		wlcPane.add(wlcMsg);
		
		container.add(wlcPane, BorderLayout.NORTH);
	}
	
	private void buildAppCfgPane() {
		AppCfgPane appCfgPane = new AppCfgPane();
		appCfgPane.setVisible(true);
		container.add(appCfgPane, BorderLayout.WEST);
	}
	
	private void buildCommandsPane() {
		DashBoardController controller = new DashBoardController();
		container.add(controller.getPageView().asComponet(), BorderLayout.CENTER);
	}
	
	private void buildCopyrightPane() {
		JPanel copyright = new JPanel(new FlowLayout(FlowLayout.CENTER));
		copyright.setBorder(BorderFactory.createTitledBorder(""));
		
		MessageFormat formtter = new MessageFormat(I18n.APPVIEW.getString(AppViewNLS.MSG_COPYRIGHT));
		SimpleDateFormat dateFormatter = new SimpleDateFormat(App.DATE_SHORT);
		JLabel msg = new JLabel(formtter.format(new String[] {dateFormatter.format(new Date())}));
		copyright.add(msg);
		
		container.add(copyright, BorderLayout.SOUTH);
	}
	
	@Override
	public String getTitle() {
		return I18n.APPVIEW.getString(AppViewNLS.TITLE_APPVIEW);
	}
}

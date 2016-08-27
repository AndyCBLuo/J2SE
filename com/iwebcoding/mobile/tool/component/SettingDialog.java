package com.iwebcoding.mobile.tool.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.iwebcoding.mobile.tool.constant.App;
import com.iwebcoding.mobile.tool.constant.GUI;
import com.iwebcoding.mobile.tool.constant.nls.SettingNLS;
import com.iwebcoding.mobile.tool.util.I18n;
import com.iwebcoding.mobile.tool.util.SysCfgUtil;
import com.iwebcoding.mobile.tool.util.ViewHelper;

public class SettingDialog extends JDialog {
	private static final long serialVersionUID = 2783582020915619712L;
	
	private JPanel pageView;
	private JPanel apperancePane;
	private JPanel advancePane;
	private JPanel actPane;
	private JPanel netModePane = new JPanel(new GridBagLayout());
	private JPanel downloadPane = new JPanel(new GridBagLayout());
	
	private JLabel proxySrv = new JLabel(I18n.SETTING.getString(SettingNLS.LABEL_PROXY_SERVER));
	private JLabel portNum = new JLabel(I18n.SETTING.getString(SettingNLS.LABEL_PROXY_PORT));
	private JLabel network = new JLabel(I18n.SETTING.getString(SettingNLS.LABEL_NET_TYPE));
	private JLabel download = new JLabel(I18n.SETTING.getString(SettingNLS.LABEL_DOWNLOAD));
	
	private JButton cancelBtn = new JButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_CANCEL));
	private JButton applyBtn = new JButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_APPLY));
	private JButton browseBtn = new JButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_BROWSE));
	
	private JRadioButton webLnf = new JRadioButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_LNF_WEB));
	private JRadioButton metalLnf = new JRadioButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_LNF_METAL));
	private JRadioButton sysLnf = new JRadioButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_LNF_SYS));
	private JRadioButton enLocale = new JRadioButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_LOCALE_EN));
	private JRadioButton scLocale = new JRadioButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_LOCALE_SC));
	private JRadioButton tcLocale = new JRadioButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_LOCALE_TC));
	private JRadioButton serif = new JRadioButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_FONT_SERIF));
	private JRadioButton sansSerif = new JRadioButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_FONT_SANSERIF));
	private JRadioButton lishu = new JRadioButton(I18n.SETTING.getString(SettingNLS.TITLE_BTN_FONT_LISHU));
	private JCheckBox clearDownload = new JCheckBox(I18n.SETTING.getString(SettingNLS.TITLE_BTN_CLEAR));
	
	private JTextField downloadField = new JTextField(24);
	private JTextField proxySrvField = new JTextField(14);
	private JTextField portNumField = new JTextField(4);
	private JComboBox netTypeList = new JComboBox();
	
	private ButtonGroup lnfGroup = new ButtonGroup();
	private ButtonGroup localeGroup = new ButtonGroup();
	private ButtonGroup fontGroup = new ButtonGroup();
	
	public SettingDialog() {
		setModal(true);
		setResizable(false);
		setSize(GUI.SETTING_WIDTH, GUI.SETTING_HEIGHT);
		setTitle(I18n.SETTING.getString(SettingNLS.TITLE_WIN));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		buildUI();
	}
	
	private void buildUI() {
		pageView = new JPanel(new BorderLayout());
		pageView.setBorder(BorderFactory.createTitledBorder(
				I18n.SETTING.getString(SettingNLS.TITLE_BORDER_WIN)));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		buildApperancePane();
		buildAdvancedPane();
		tabbedPane.addTab(I18n.SETTING.getString(SettingNLS.TITLE_TAB_APPERANCE), apperancePane);
		tabbedPane.addTab(I18n.SETTING.getString(SettingNLS.TITLE_TAB_ADVANCED), advancePane);
		pageView.add(tabbedPane, BorderLayout.CENTER);
		
		buildActPane();
		getContentPane().add(pageView);
	}
	
	private void buildApperancePane() {
		apperancePane = new JPanel();
		apperancePane.setLayout(new BoxLayout(apperancePane, BoxLayout.Y_AXIS));
		apperancePane.setBorder(BorderFactory.createTitledBorder(""));
		
		buildLnFPaneComponent();
		buildLocalePaneComponent();
		buildSysFontPaneComponent();	
	}
	
	private void buildLnFPaneComponent() {
		JPanel lnfPane = new JPanel(new GridLayout(1, 3));
		lnfPane.setBorder(BorderFactory.createTitledBorder(
				I18n.SETTING.getString(SettingNLS.TITLE_BORDER_LNF)));
		
		webLnf.setActionCommand(App.LNF_WEB);
		metalLnf.setActionCommand(App.LNF_METAL);
		sysLnf.setActionCommand(App.LNF_SYSTEM);
		
		lnfGroup.add(webLnf);
		lnfGroup.add(metalLnf);
		lnfGroup.add(sysLnf);
		
		lnfPane.add(webLnf);
		lnfPane.add(metalLnf);
		lnfPane.add(sysLnf);
		
		String cfgLnf = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_LOOKANDFEEL);
		if (App.LNF_WEB.equals(cfgLnf)) {
			webLnf.setSelected(true);
		} else if (App.LNF_METAL.equals(cfgLnf)) {
			metalLnf.setSelected(true);
		} else if (App.LNF_SYSTEM.equals(cfgLnf)) {
			sysLnf.setSelected(true);
		} else {
			webLnf.setSelected(true);
		}
		apperancePane.add(lnfPane);
	}
	
	private void buildLocalePaneComponent() {
		JPanel localPane = new JPanel(new GridLayout(1, 3));
		localPane.setBorder(BorderFactory.createTitledBorder(
				I18n.SETTING.getString(SettingNLS.TITLE_BORDER_LOCALE)));
		
		enLocale.setActionCommand(App.LOCALE_EN);
		scLocale.setActionCommand(App.LOCALE_SC);
		tcLocale.setActionCommand(App.LOCALE_TC);
		
		localeGroup.add(enLocale);
		localeGroup.add(scLocale);
		localeGroup.add(tcLocale);
		
		localPane.add(enLocale);
		localPane.add(scLocale);
		localPane.add(tcLocale);
		
		String cfgLocale = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_LOCALE);
		if (App.LOCALE_EN.equals(cfgLocale)) {
			enLocale.setSelected(true);
		} else if (App.LOCALE_SC.equals(cfgLocale)) {
			scLocale.setSelected(true);
		} else if (App.LOCALE_TC.equals(cfgLocale)) {
			tcLocale.setSelected(true);
		} else {
			enLocale.setSelected(true);
		}
		apperancePane.add(localPane);	
	}
	
	private void buildSysFontPaneComponent() {
		JPanel fontPane = new JPanel(new GridLayout(1, 3));
		fontPane.setBorder(BorderFactory.createTitledBorder(
				I18n.SETTING.getString(SettingNLS.TITLE_BORDER_FONT)));
		
		serif.setActionCommand(App.FONT_SERIF);
		sansSerif.setActionCommand(App.FONT_SANS_SERIF);
		lishu.setActionCommand(App.FONT_LISHU);
		
		fontGroup.add(serif);
		fontGroup.add(sansSerif);
		fontGroup.add(lishu);
		
		fontPane.add(serif);
		fontPane.add(sansSerif);
		fontPane.add(lishu);
		
		String cfgFont = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_FONT);
		if (App.FONT_SANS_SERIF.equals(cfgFont)) {
			sansSerif.setSelected(true);
		} else if (App.FONT_SERIF.equals(cfgFont)) {
			serif.setSelected(true);
		} else if (App.FONT_LISHU.equals(cfgFont)) {
			lishu.setSelected(true);
		} else {
			serif.setSelected(true);
		}
		apperancePane.add(fontPane);
	}
	
	private void buildActPane() {
		actPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		actPane.add(cancelBtn);
		actPane.add(applyBtn);
		
		cancelBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});
		
		applyBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				SysCfgUtil.updateSystemConfig(App.KEY_SYSCFG_LOOKANDFEEL, 
						lnfGroup.getSelection().getActionCommand());
				
				SysCfgUtil.updateSystemConfig(App.KEY_SYSCFG_LOCALE, 
						localeGroup.getSelection().getActionCommand());
				
				SysCfgUtil.updateSystemConfig(App.KEY_SYSCFG_FONT, 
						fontGroup.getSelection().getActionCommand());
				
				SysCfgUtil.updateSystemConfig(App.KEY_SYSCFG_NETMODE, 
						(String) netTypeList.getSelectedItem());
				
				if (App.NET_MODE_PROXY.equals((String) netTypeList.getSelectedItem())) {
					SysCfgUtil.updateSystemConfig(App.KEY_SYSCFG_PROXY_SERVER, 
							proxySrvField.getText());
					SysCfgUtil.updateSystemConfig(App.KEY_SYSCFG_PROXY_PORT, 
							portNumField.getText());
				}
				
				SysCfgUtil.updateSystemConfig(App.KEY_SYSCFG_DOWNLOAD_DIR, 
						downloadField.getText());
				
				SysCfgUtil.updateSystemConfig(App.KEY_SYSCFG_CLEAR, 
						String.valueOf(clearDownload.isSelected()));
				
				SysCfgUtil.saveSystemSettings();
				
				JOptionPane.showMessageDialog(null, I18n.SETTING.getString(SettingNLS.MSG_UPDATE_CFG), 
						I18n.SETTING.getString(SettingNLS.MSG_TITLE_WARN), JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}
		});
		
		pageView.add(actPane, BorderLayout.SOUTH);
	}
	
	private void buildAdvancedPane() {
		advancePane = new JPanel();
		advancePane.setLayout(new BoxLayout(advancePane, BoxLayout.Y_AXIS));
		advancePane.setBorder(BorderFactory.createTitledBorder(""));
		
		buildNetModePaneComponents();
		buildDownloadPaneComponents();
		
		advancePane.add(netModePane);
		advancePane.add(downloadPane);
	}
	
	private void buildNetModePaneComponents() {
		netModePane.setBorder(BorderFactory.createTitledBorder(
				I18n.SETTING.getString(SettingNLS.TITLE_BORDER_NET)));
		
		GridBagConstraints cst = new GridBagConstraints();
		cst.insets = new Insets(3, 3, 3, 3);
		cst.gridx = 0;
		cst.gridy = 0;
		cst.anchor = GridBagConstraints.WEST;
		netModePane.add(network, cst);
		
		cst.gridx = 1;
		cst.gridy = 0;
		refreshNetModeList();
		netTypeList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				String netMode = (String) netTypeList.getSelectedItem();
				if (App.NET_MODE_PROXY.equals(netMode)) {
					proxySrvField.setEnabled(true);
					portNumField.setEnabled(true);
					
					proxySrvField.setText(SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_PROXY_SERVER));
					portNumField.setText(SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_PROXY_PORT));
				} else {
					proxySrvField.setEnabled(false);
					portNumField.setEnabled(false);
				}
			}
		});
		netTypeList.setSelectedItem(SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_NETMODE));
		netModePane.add(netTypeList, cst);
		
		cst.gridx = 0;
		cst.gridy = 1;
		netModePane.add(proxySrv, cst);
		
		cst.gridx = 1;
		cst.gridy = 1;
		netModePane.add(proxySrvField, cst);
		
		cst.gridx = 2;
		cst.gridy = 1;
		netModePane.add(portNum, cst);
		
		cst.gridx = 3;
		cst.gridy = 1;
		netModePane.add(portNumField, cst);
	}
	
	private void refreshNetModeList() {
		netTypeList.addItem(App.NET_MODE_PROXY);
		netTypeList.addItem(App.NET_MODE_DIRECT);
	}
	
	private void buildDownloadPaneComponents() {
		downloadPane.setBorder(BorderFactory.createTitledBorder(
				I18n.SETTING.getString(SettingNLS.TITLE_BORDER_DOWNLOAD)));
		
		GridBagConstraints gbcst = new GridBagConstraints();
		gbcst.insets = new Insets(3, 3, 3, 3);
		gbcst.gridx = 0;
		gbcst.gridy = 0;
		gbcst.anchor = GridBagConstraints.WEST;
		downloadPane.add(download, gbcst);
		
		gbcst.gridx = 1;
		gbcst.gridy = 0;
		downloadField.setText(new File(SysCfgUtil.getSystemConfig(
				App.KEY_SYSCFG_DOWNLOAD_DIR)).getAbsolutePath());
		downloadPane.add(downloadField, gbcst);
		
		gbcst.gridx = 2;
		gbcst.gridy = 0;
		browseBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fileChooser = ViewHelper.getFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
					downloadField.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		downloadPane.add(browseBtn, gbcst);
		
		gbcst.gridx = 0;
		gbcst.gridy = 1;
		gbcst.gridwidth = 3;
		gbcst.anchor = GridBagConstraints.EAST;
		clearDownload.setSelected(Boolean.valueOf(SysCfgUtil.getSystemConfig(
				App.KEY_SYSCFG_CLEAR)).booleanValue());
		downloadPane.add(clearDownload, gbcst);
	}
}

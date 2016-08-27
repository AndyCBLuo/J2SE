package com.iwebcoding.mobile.tool.component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.iwebcoding.mobile.tool.constant.nls.AppCfgNLS;
import com.iwebcoding.mobile.tool.constant.nls.ProgressNLS;
import com.iwebcoding.mobile.tool.model.EntCfgBean;
import com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig;
import com.iwebcoding.mobile.tool.util.EntCfgUtil;
import com.iwebcoding.mobile.tool.util.I18n;

public class AppCfgPane extends JPanel {
	private static final long serialVersionUID = 545422533223472111L;
	
	private static JComboBox appVerBox = new JComboBox();
	private static JComboBox platformBox = new JComboBox();
	private static JComboBox deviceBox = new JComboBox();
	private static JList entityList = new JList();
	
	private List<String> appVerList = null;
	private List<String> platformList = null;
	private List<DeviceConfig> devTypeList = null;
	private static List<EntCfgBean> entList = null;
	
	public AppCfgPane () {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder(I18n.APPCFG.getString(AppCfgNLS.TITLE_PANE_BORDER)));
		buildUI();
	}
	
	private void buildUI() {
		GridBagConstraints cst = new GridBagConstraints();
		cst.insets = new Insets(4, 4, 4, 4);
		cst.gridx = 0;
		cst.gridy = 0;
		cst.fill = GridBagConstraints.HORIZONTAL;
		add(new JLabel(I18n.APPCFG.getString(AppCfgNLS.LABEL_CHOOSE_APPVER)), cst);
		
		cst.gridx = 1;
		cst.gridy = 0;
		add(appVerBox, cst);
		refreshAppVerList();
		
		cst.gridx = 0;
		cst.gridy = 1;
		add(new JLabel(I18n.APPCFG.getString(AppCfgNLS.LABEL_CHOOSE_PLATFORM)), cst);
		
		cst.gridx = 1;
		cst.gridy = 1;
		add(platformBox, cst);
		refreshPlatformList();
		
		cst.gridx = 0;
		cst.gridy = 2;
		add(new JLabel(I18n.APPCFG.getString(AppCfgNLS.LABEL_CHOOSE_DEVTYPE)), cst);
		
		cst.gridx = 1;
		cst.gridy = 2;
		add(deviceBox, cst);
		refreshDevTypeList();
		
		cst.fill = GridBagConstraints.BOTH;
		cst.gridx = 0;
		cst.gridy = 3;
		cst.gridwidth = 2;
		cst.weighty = 1;
		JScrollPane scrollPane = new JScrollPane(entityList);
		add(scrollPane, cst);
		refreshEntityList();
	}
	
	/**
	 * 
	 */
	private void refreshAppVerList() {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		appVerList = EntCfgUtil.getAppVerList();
		for (String appVer : appVerList) {
			model.addElement(appVer);
		}
		appVerBox.setModel(model);
		appVerBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				refreshPlatformList();
			}
		});
	}
	
	/**
	 * 
	 */
	private void refreshPlatformList() {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		platformList = EntCfgUtil.getPlatformList((String)appVerBox.getSelectedItem());
		for (String platform : platformList) {
			model.addElement(platform);
		}
		platformBox.setModel(model);
		platformBox.setSelectedIndex(0);
		platformBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				refreshDevTypeList();
			}
		});
	}
	
	/**
	 * 
	 */
	private void refreshDevTypeList() {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		String appVer = (String) appVerBox.getSelectedItem();
		String platform = (String) platformBox.getSelectedItem();
		devTypeList = EntCfgUtil.getDevTypeList(appVer, platform);
		for (DeviceConfig cfg : devTypeList) {
			model.addElement(cfg.getDevName());
		}
		deviceBox.setModel(model);
		deviceBox.setSelectedIndex(0);
		deviceBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				refreshEntityList();
			}
		});
	}
	
	/**
	 * 
	 */
	private void refreshEntityList() {
		final DefaultListModel model = new DefaultListModel();
		model.addElement(I18n.PROGRESS.getString(ProgressNLS.MSG_LOADING));
		final String configUrl = devTypeList.get(deviceBox.getSelectedIndex()).getConfigUrl();
		model.removeAllElements();
		Thread t = new Thread() {
			public void run() {
				entList = EntCfgUtil.getEntCountryCfgList(configUrl);
				for (EntCfgBean cfgBean : entList) {
					model.addElement(cfgBean.getName());
				}
				entityList.setModel(model);
				entityList.setSelectedIndex(0);
			}
		};
		t.start();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getSelectedVer() {
		return (String) appVerBox.getSelectedItem();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getSelectedPlatform() {
		return (String) platformBox.getSelectedItem();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getSelectedDevice() {
		return (String) deviceBox.getSelectedItem();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getSelectedEntityUrl() {
		return entList.get(entityList.getSelectedIndex()).getUrl();
	}
}

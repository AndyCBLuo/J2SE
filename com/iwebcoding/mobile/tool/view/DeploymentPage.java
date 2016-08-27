package com.iwebcoding.mobile.tool.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.iwebcoding.mobile.tool.constant.App;
import com.iwebcoding.mobile.tool.constant.GUI;
import com.iwebcoding.mobile.tool.constant.nls.AppViewNLS;
import com.iwebcoding.mobile.tool.constant.nls.DeployNLS;
import com.iwebcoding.mobile.tool.constant.nls.ErrNLS;
import com.iwebcoding.mobile.tool.framework.DataPageController;
import com.iwebcoding.mobile.tool.framework.DataPageView;
import com.iwebcoding.mobile.tool.model.DeployBean;
import com.iwebcoding.mobile.tool.util.AppCfgUtil;
import com.iwebcoding.mobile.tool.util.DeployUtil;
import com.iwebcoding.mobile.tool.util.I18n;
import com.iwebcoding.mobile.tool.util.ViewHelper;

public class DeploymentPage implements DataPageView {
	private DataPageController controller;
	private JPanel pageView;
	private JComboBox envList = new JComboBox();
	private JTextArea consoleArea = new JTextArea();
	private JTextField fileField = new JTextField(45);
	private JTextField subPath = new JTextField(30);
	private JLabel chooseEnv = new JLabel(I18n.DEPLOY.getString(DeployNLS.LABEL_SERVER));
	private JLabel browseFile = new JLabel(I18n.DEPLOY.getString(DeployNLS.LABEL_FILE));
	private JButton browseBtn = new JButton(I18n.DEPLOY.getString(DeployNLS.LABEL_BTN_BROWSE));
	private JButton uploadBtn = new JButton(I18n.DEPLOY.getString(DeployNLS.LABEL_BTN_UPLOAD));
	private JLabel copyCmd = new JLabel(new ImageIcon(GUI.ICON_COPY));
	private JLabel clearCmd = new JLabel(new ImageIcon(GUI.ICON_CLEAR));
	private JCheckBox specifySubBtn = new JCheckBox(I18n.DEPLOY.getString(DeployNLS.LABEL_BTN_SPECIFY_PATH));
	private List<DeployBean> deployCfgList = null;
	
	@Override
	public String getTitle() {
		return I18n.APPVIEW.getString(AppViewNLS.TITLE_TAB_DEPLOY);
	}

	@Override
	public void init(DataPageController controller) {
		this.controller = controller;
		initComponents();
	}
	
	/**
	 * 
	 */
	private void initComponents() {
		pageView = new JPanel(new BorderLayout());
		pageView.setBorder(BorderFactory.createTitledBorder(I18n.DEPLOY.getString(DeployNLS.BORDER_DEPLOY_MANAGE)));
		
		JPanel msgPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		MessageFormat formatter = new MessageFormat(I18n.DEPLOY.getString(DeployNLS.LABEL_DEPLOY_ROOT));
		msgPane.add(new JLabel(formatter.format(new String[] {AppCfgUtil.getAppCfg(App.KEY_APPCFG_DEPLOY_ROOT)})));
		pageView.add(msgPane, BorderLayout.NORTH);
		
		JPanel deployPane = new JPanel(new BorderLayout());
		initSettingsPaneComponents(deployPane);
		initConsolePaneComponents(deployPane);
		pageView.add(deployPane, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * @param deployPane
	 */
	private void initSettingsPaneComponents(final JPanel deployPane) {
		JPanel settingsPane = new JPanel();
		settingsPane.setLayout(new BoxLayout(settingsPane, BoxLayout.Y_AXIS));
		settingsPane.setBorder(BorderFactory.createTitledBorder(I18n.DEPLOY.getString(DeployNLS.BORDER_SETTING)));
		
		JPanel actPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		fileField.setEditable(false);
		actPane.add(chooseEnv);
		actPane.add(envList);
		actPane.add(browseFile);
		actPane.add(fileField);
		actPane.add(browseBtn);
		actPane.add(uploadBtn);
		refreshServerList();
		attachActPaneListeners();
		settingsPane.add(actPane);
		
		JPanel filterPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		filterPane.add(new JLabel(I18n.DEPLOY.getString(DeployNLS.LABEL_SPECIFY_PATH)));
		subPath.setEnabled(false);
		filterPane.add(subPath);
		specifySubBtn.setSelected(false);
		filterPane.add(specifySubBtn);
		attachFilterPaneListeners();
		settingsPane.add(filterPane);
		
		deployPane.add(settingsPane, BorderLayout.NORTH);
	}
	
	/**
	 * 
	 */
	private void refreshServerList() {
		deployCfgList = DeployUtil.getInstance().getDeployConfigList();
		for(DeployBean bean : deployCfgList) {
			envList.addItem(bean.getEnvName());
		}
		envList.setSelectedIndex(0);
	}
	
	/**
	 * 
	 */
	private void attachActPaneListeners() {
		browseBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fileChooser = ViewHelper.getFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
					fileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		uploadBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				String uploadPath = fileField.getText();
				if (!uploadPath.isEmpty()) {
					StringBuffer report = new StringBuffer();
					String envDomain = deployCfgList.get(envList.getSelectedIndex()).getEnvDomain();
					String deployPath = AppCfgUtil.getAppCfg(App.KEY_APPCFG_DEPLOY_ROOT);
					if (specifySubBtn.isSelected()) {
						deployPath += subPath.getText();
						if (DeployUtil.getInstance().isValidServerPath(envDomain, deployPath)) {
							DeployUtil.getInstance().deployFiles(envDomain, deployPath, uploadPath, report);
							consoleArea.setText(report.toString());
						} else {
							MessageFormat formatter = new MessageFormat(I18n.DEPLOY.getString(DeployNLS.MSG_INVALID_PATH));
							JOptionPane.showMessageDialog(null, formatter.format(new String[] {deployPath}),
									I18n.ERROR.getString(ErrNLS.TITLE_ERR), JOptionPane.ERROR_MESSAGE);
						}
					} else {
						DeployUtil.getInstance().deployFiles(envDomain, deployPath, uploadPath, report);
						consoleArea.setText(report.toString());
					}
				}
			}
		});
	}
	
	/**
	 * 
	 */
	private void attachFilterPaneListeners() {
		specifySubBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (specifySubBtn.isSelected()) {
					subPath.setEnabled(true);
				} else {
					subPath.setText("");
					subPath.setEnabled(false);
				}
			}
		});
	}
	
	/**
	 * 
	 * @param deployPane
	 */
	private void initConsolePaneComponents(final JPanel deployPane) {
		JPanel consolePane = new JPanel(new BorderLayout());
		consolePane.setBorder(BorderFactory.createTitledBorder(I18n.DEPLOY.getString(DeployNLS.BORDER_CONSOLE)));
		
		JPanel consoleCmdPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		copyCmd.setBorder(BorderFactory.createTitledBorder(""));
		clearCmd.setBorder(BorderFactory.createTitledBorder(""));
		copyCmd.setToolTipText(I18n.DEPLOY.getString(DeployNLS.TOOLTIP_COPY));
		clearCmd.setToolTipText(I18n.DEPLOY.getString(DeployNLS.TOOLTIP_CLEAR));
		consoleCmdPane.add(copyCmd);
		consoleCmdPane.add(clearCmd);
		attachConsolePaneListeners();
		consolePane.add(consoleCmdPane, BorderLayout.NORTH);
		
		consoleArea.setText("");
		consoleArea.setEditable(false);
		consoleArea.setFocusable(true);
		JScrollPane scrollPane = new JScrollPane(consoleArea);
		consolePane.add(scrollPane, BorderLayout.CENTER);
		deployPane.add(consolePane, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 */
	private void attachConsolePaneListeners() {
		copyCmd.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mEvt) {
				ViewHelper.copyToClipboard(consoleArea.getText());
			}
		});
		
		clearCmd.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mEvt) {
				consoleArea.setText("");
			}
		});
	}

	@Override
	public Component asComponet() {
		return pageView;
	}

	@Override
	public DataPageController getController() {
		return controller;
	}

	@Override
	public void refreshData() {
		
	}

}

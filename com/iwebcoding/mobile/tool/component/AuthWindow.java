package com.iwebcoding.mobile.tool.component;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.iwebcoding.mobile.tool.constant.App;
import com.iwebcoding.mobile.tool.constant.GUI;
import com.iwebcoding.mobile.tool.constant.nls.AuthNLS;
import com.iwebcoding.mobile.tool.constant.nls.ErrNLS;
import com.iwebcoding.mobile.tool.util.I18n;
import com.iwebcoding.mobile.tool.util.LogUtil;

public class AuthWindow extends JDialog {
	private static final long serialVersionUID = -9217965747762202200L;
	private JPanel pageView;
	private JTextField userNameTxt = new JTextField(12);
	private JPasswordField passwordTxt = new JPasswordField(12);
	private JButton loginBtn = new JButton(I18n.AUTH.getString(AuthNLS.TITLE_BTN_LOGIN));
	private JButton cancelBtn = new JButton(I18n.AUTH.getString(AuthNLS.TITLE_BTN_CANCEL));
	private boolean authenticated = false;
	private final String CLASSNAME = AuthWindow.class.getName();
	
	public AuthWindow() {
		setModal(true);
		setResizable(false);
		setSize(GUI.AUTH_WIDTH, GUI.AUTH_HEIGHT);
		setLocationRelativeTo(null);
		setTitle(I18n.AUTH.getString(AuthNLS.TITLE_WIN));
		buildUI();
	}
	
	private void buildUI() {
		JLabel userName = new JLabel(I18n.AUTH.getString(AuthNLS.LABEL_USERNAME));
		JLabel password = new JLabel(I18n.AUTH.getString(AuthNLS.LABEL_PASSWORD));
		JPanel actPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		pageView = new JPanel(new GridBagLayout());
		pageView.setBorder(BorderFactory.createTitledBorder(I18n.AUTH.getString(
				AuthNLS.TITLE_BORDER)));
		
		GridBagConstraints cst = new GridBagConstraints();
		cst.insets = new Insets(6, 6, 6, 6);
		cst.gridx = 0;
		cst.gridy = 0;
		
		pageView.add(userName, cst);
		cst.gridx = 1;
		cst.gridy = 0;
		pageView.add(userNameTxt, cst);
		
		cst.gridx = 0;
		cst.gridy = 1;
		pageView.add(password, cst);
		
		cst.gridx = 1;
		cst.gridy = 1;
		pageView.add(passwordTxt, cst);
		
		cst.gridx = 1;
		cst.gridy = 2;
		cst.fill = GridBagConstraints.HORIZONTAL;
		actPane.add(cancelBtn);
		actPane.add(loginBtn);
		attachActionListeners();
		pageView.add(actPane, cst);
		
		getContentPane().add(pageView);
	}
	
	private void attachActionListeners() {
		cancelBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				closeDialog();
			}
		});
		
		loginBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				authenticateUser();
			}
		});
	}
	
	private void authenticateUser() {
		final String MTHNAME = "authenticateUser()";
		String userName = userNameTxt.getText();
		String password = new String(passwordTxt.getPassword());
		try {
			MessageDigest md5 = MessageDigest.getInstance(App.ALGORITHM_MD5);
			byte[] md5Bytes = md5.digest(password.getBytes());
			StringBuffer temp = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				temp.append(Integer.toHexString((md5Bytes[i] & 0xFF) + 256).substring(1).toUpperCase());
			}
			
			if (App.AUTH_USERNAME.equals(userName) && 
					App.AUTH_PASSWORD.equals(temp.toString())) {
				setAuthenticated(true);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, I18n.AUTH.getString(AuthNLS.MSG_INVALID_CREDENTIAL), 
						I18n.ERROR.getString(ErrNLS.TITLE_WARN), JOptionPane.WARNING_MESSAGE);
			}
		} catch (NoSuchAlgorithmException e) {
			LogUtil.logError(CLASSNAME, MTHNAME, e.getMessage(), e);
		}
	}
	
	private void closeDialog() {
		this.dispose();
	}
	
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	public boolean isAuthenticated() {
		return this.authenticated;
	}
}

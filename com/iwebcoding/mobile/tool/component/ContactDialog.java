package com.iwebcoding.mobile.tool.component;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.iwebcoding.mobile.tool.constant.GUI;
import com.iwebcoding.mobile.tool.constant.nls.ContactNLS;
import com.iwebcoding.mobile.tool.util.I18n;

public class ContactDialog extends JDialog {
	private static final long serialVersionUID = -5312695058404251105L;
	
	public ContactDialog() {
		setTitle(I18n.CONTACT.getString(ContactNLS.TITLE_WIN));
		setResizable(false);
		setModal(true);
		setSize(GUI.CONTACT_WIDTH, GUI.CONTACT_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		buildUI();
	}
	
	private void buildUI() {
		JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER));
		container.setBorder(BorderFactory.createTitledBorder(I18n.CONTACT.getString(ContactNLS.TITLE_BORDER)));
		
		JLabel contact = new JLabel(I18n.CONTACT.getString(ContactNLS.MSG_CONTACT));
		contact.setIcon(new ImageIcon(GUI.ICON_EMAIL));
		container.add(contact);
		
		getContentPane().add(container);
	}
}

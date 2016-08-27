package com.iwebcoding.mobile.tool.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.iwebcoding.mobile.tool.constant.GUI;
import com.iwebcoding.mobile.tool.constant.nls.SubJsonNLS;
import com.iwebcoding.mobile.tool.util.I18n;

public class SubJsonDialog extends JDialog {
	private static final long serialVersionUID = -4181083334239565582L;
	private List<String> viewData;
	private String subJson;
	
	public SubJsonDialog() {
		setResizable(false);
		setSize(GUI.SUBJSON_WIDTH, GUI.SUBJSON_HEIGHT);
		setModal(true);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public void popupWindow() {
		JPanel container = new JPanel(new BorderLayout());
		container.setBorder(BorderFactory.createTitledBorder(""));
		
		JPanel msgPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		msgPane.add(new JLabel(I18n.SUBJSON.getString(SubJsonNLS.MSG_SELECT)));
		container.add(msgPane, BorderLayout.NORTH);
		
		JPanel listPanel = new JPanel(new BorderLayout());
		DefaultListModel model = new DefaultListModel();
		for (String subUrl : this.viewData) {
			model.addElement(subUrl);
		}
		final JList list = new JList(model);
		JScrollPane scrollPane = new JScrollPane(list);
		listPanel.add(scrollPane, BorderLayout.CENTER);
		container.add(listPanel, BorderLayout.CENTER);
		
		JPanel actionPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton viewBtn = new JButton(I18n.SUBJSON.getString(SubJsonNLS.BTN_VIEW));
		viewBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				subJson = (String) list.getSelectedValue();
				dispose();
			}
		});
		actionPane.add(viewBtn);
		container.add(actionPane, BorderLayout.SOUTH);
		getContentPane().add(container);
		setVisible(true);
	}
	
	public List<String> getViewData() {
		return this.viewData;
	}
	
	public void setViewData(List<String> viewData) {
		this.viewData = viewData;
	}
	
	public String getSelectSubJson() {
		return this.subJson;
	}
}

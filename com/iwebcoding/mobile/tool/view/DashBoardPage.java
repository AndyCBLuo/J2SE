package com.iwebcoding.mobile.tool.view;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.iwebcoding.mobile.tool.component.AuthWindow;
import com.iwebcoding.mobile.tool.constant.GUI;
import com.iwebcoding.mobile.tool.constant.nls.AppViewNLS;
import com.iwebcoding.mobile.tool.controller.ChecksumController;
import com.iwebcoding.mobile.tool.controller.DeploymentController;
import com.iwebcoding.mobile.tool.controller.JsonViewerController;
import com.iwebcoding.mobile.tool.framework.PageController;
import com.iwebcoding.mobile.tool.framework.PageView;
import com.iwebcoding.mobile.tool.util.I18n;

public class DashBoardPage implements PageView {
	private JPanel pageView;
	private PageController controller;
	private AuthWindow auth = new AuthWindow();
	
	@Override
	public String getTitle() {
		return DashBoardPage.class.getName();
	}

	@Override
	public void init(PageController controller) {
		this.controller = controller;
		initComponents();
	}
	
	/**
	 * 
	 */
	private void initComponents() {
		pageView = new JPanel(new BorderLayout());
		
		JsonViewerController jController = new JsonViewerController();
		ChecksumController cController = new ChecksumController();
		DeploymentController dController = new DeploymentController();
		
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab(I18n.APPVIEW.getString(AppViewNLS.TITLE_TAB_JVIEWER), 
				new ImageIcon(GUI.ICON_VIEW), jController.getPageView().asComponet());
		tabbedPane.addTab(I18n.APPVIEW.getString(AppViewNLS.TITLE_TAB_GENCHKSUM), 
				new ImageIcon(GUI.ICON_EDIT), cController.getPageView().asComponet());
		tabbedPane.addTab(I18n.APPVIEW.getString(AppViewNLS.TITLE_TAB_DEPLOY), 
				new ImageIcon(GUI.ICON_UPLOAD), dController.getPageView().asComponet());
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				JTabbedPane selected = (JTabbedPane) evt.getSource();
				if (I18n.APPVIEW.getString(AppViewNLS.TITLE_TAB_DEPLOY).equals(
						selected.getTitleAt(selected.getSelectedIndex()))) {
					if (!auth.isAuthenticated()) {
						auth.setVisible(true);
					}
					if (!auth.isAuthenticated()) {
						tabbedPane.setSelectedIndex(0);
					}
				}
			}
		});
		pageView.add(tabbedPane, BorderLayout.CENTER);
	}

	@Override
	public Component asComponet() {
		return pageView;
	}

	@Override
	public PageController getController() {
		return controller;
	}

}

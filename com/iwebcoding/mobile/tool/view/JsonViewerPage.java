package com.iwebcoding.mobile.tool.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.iwebcoding.mobile.tool.component.AppCfgPane;
import com.iwebcoding.mobile.tool.component.ProgressDialog;
import com.iwebcoding.mobile.tool.component.SubJsonDialog;
import com.iwebcoding.mobile.tool.constant.GUI;
import com.iwebcoding.mobile.tool.constant.nls.AppViewNLS;
import com.iwebcoding.mobile.tool.constant.nls.ErrNLS;
import com.iwebcoding.mobile.tool.constant.nls.JViewerNLS;
import com.iwebcoding.mobile.tool.framework.PageController;
import com.iwebcoding.mobile.tool.framework.PageView;
import com.iwebcoding.mobile.tool.model.JsonVryBean;
import com.iwebcoding.mobile.tool.model.VerifyBean;
import com.iwebcoding.mobile.tool.model.ZipVryBean;
import com.iwebcoding.mobile.tool.util.FileUtil;
import com.iwebcoding.mobile.tool.util.I18n;
import com.iwebcoding.mobile.tool.util.LogUtil;
import com.iwebcoding.mobile.tool.util.ViewHelper;

public class JsonViewerPage implements PageView {
	private PageController controller;
	private JPanel pageView;
	private JTextArea textArea = new JTextArea();
	private JTextArea consoleArea = new JTextArea();
	private final String CLASSNAME = JsonViewerPage.class.getName();
	
	@Override
	public String getTitle() {
		return I18n.APPVIEW.getString(AppViewNLS.TITLE_TAB_JVIEWER);
	}

	@Override
	public void init(PageController controller) {
		this.controller = controller;
		initComponents();
	}
	
	private void initComponents() {
		pageView = new JPanel(new BorderLayout());
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(GUI.DEVIDER_LOCATION);
		
		JPanel viewerPane = new JPanel(new BorderLayout());
		viewerPane.setBorder(BorderFactory.createTitledBorder(I18n.JVIEWER.getString(JViewerNLS.TITLE_BORDER_VIEWER)));
		
		JPanel actionPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel masterJson = new JLabel(I18n.JVIEWER.getString(JViewerNLS.LABLE_MASTER_JSON));
		masterJson.setBorder(BorderFactory.createTitledBorder(""));
		masterJson.setIcon(new ImageIcon(GUI.ICON_LOOKUP));
		actionPane.add(masterJson);
		viewerPane.add(actionPane, BorderLayout.NORTH);
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		JPanel textViewerPane = new JPanel(new BorderLayout());
		textArea.setEditable(false);
		textArea.setFocusable(true);
		JScrollPane tScrollPane = new JScrollPane(textArea);
		textViewerPane.add(tScrollPane, BorderLayout.CENTER);
		tabbedPane.add(I18n.JVIEWER.getString(JViewerNLS.TAB_TEXT), textViewerPane);
		
		JPanel jsonViewerPane = new JPanel(new BorderLayout());
		final JScrollPane jScrollPane = new JScrollPane();
		jsonViewerPane.add(jScrollPane, BorderLayout.CENTER);
		tabbedPane.add(I18n.JVIEWER.getString(JViewerNLS.TAB_JSON), jsonViewerPane);
		
		masterJson.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent evt) {
				textArea.setText("");
				final ProgressDialog dialog = new ProgressDialog();
				Thread t = new Thread(){
					public void run() {
						dialog.setVisible(true);
					}
				};
				t.start();
				String jsonContent = FileUtil.getMasterJSONConfigContent(AppCfgPane.getSelectedEntityUrl());
				dialog.dispose();
				textArea.setText(jsonContent);
				tabbedPane.setSelectedIndex(0);
				buildViewerContextPopupMenu();
			}
		});
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				final String MTHNAME = "stateChanged(ChangeEvent evt)";
				
				JTabbedPane source = (JTabbedPane) evt.getSource();
				if (I18n.JVIEWER.getString(JViewerNLS.TAB_JSON).equals(
						source.getTitleAt(source.getSelectedIndex()))) {
					DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("{}");
					JTree jsonTree = new JTree(topNode);
					try {
						if (!"".equals(textArea.getText())) {
							JSONParser parser = new JSONParser();
							JSONObject jsonObj = (JSONObject) parser.parse(textArea.getText());
							convertJsonView(topNode, jsonObj);
							
							jsonTree.expandRow(0);
							jScrollPane.setViewportView(jsonTree);
						}
					} catch (ParseException e) {
						String msg = I18n.ERROR.getString(ErrNLS.MSG_INVALID_JSON);
						LogUtil.logError(CLASSNAME, MTHNAME, msg, e);
						JOptionPane.showMessageDialog(null, msg, I18n.ERROR.getString(ErrNLS.TITLE_ERR), 
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		viewerPane.add(tabbedPane, BorderLayout.CENTER);
		splitPane.add(viewerPane);
		
		JPanel consolePane = new JPanel(new BorderLayout());
		consolePane.setBorder(BorderFactory.createTitledBorder(I18n.JVIEWER.getString(JViewerNLS.TITLE_BORDER_CONSOLE)));
		
		JPanel consoleCmdPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel copyMenu = new JLabel(new ImageIcon(GUI.ICON_COPY));
		copyMenu.setToolTipText(I18n.JVIEWER.getString(JViewerNLS.TOOLTIP_COPY));
		copyMenu.setBorder(BorderFactory.createTitledBorder(""));
		copyMenu.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mEvt) {
				onCopyClipboard(consoleArea.getText());
			}
		});
		consoleCmdPane.add(copyMenu);
		
		JLabel clearMenu = new JLabel(new ImageIcon(GUI.ICON_CLEAR));
		clearMenu.setToolTipText(I18n.JVIEWER.getString(JViewerNLS.TOOLTIP_CLEAR));
		clearMenu.setBorder(BorderFactory.createTitledBorder(""));
		clearMenu.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mEvt) {
				consoleArea.setText("");
			}
		});
		consoleCmdPane.add(clearMenu);
		consolePane.add(consoleCmdPane, BorderLayout.NORTH);
		
		consoleArea.setEditable(false);
		consoleArea.setFocusable(true);
		JScrollPane console = new JScrollPane(consoleArea);
		consolePane.add(console, BorderLayout.CENTER);
		
		splitPane.add(consolePane);
		pageView.add(splitPane, BorderLayout.CENTER);
	}
	
	@SuppressWarnings("unchecked")
	private void convertJsonView(final DefaultMutableTreeNode topNode, final JSONObject jsonObj) throws ParseException {
		Iterator<String> keyIterator = jsonObj.keySet().iterator();
		while (keyIterator.hasNext()) {
			String cfgKey = (String) keyIterator.next();
			Object cfgValue = jsonObj.get(cfgKey);
			
			if (cfgValue instanceof JSONObject) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode("{}");
				topNode.add(node);
				convertJsonView(node, (JSONObject) cfgValue);
			}
			
			if (cfgValue instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) cfgValue;
				
				DefaultMutableTreeNode node = new DefaultMutableTreeNode("\"" + cfgKey + "\": []");
				topNode.add(node);
				
				for (int i = 0; i < jsonArray.size(); i++) {
					Object value = jsonArray.get(i);
					if (value instanceof JSONObject) {
						DefaultMutableTreeNode child = new DefaultMutableTreeNode("{}");
						node.add(child);
						convertJsonView(child, (JSONObject) value);
					}
				}
			}
			
			if (cfgValue instanceof String) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode("\"" + cfgKey + "\":\"" + cfgValue + "\"");
				topNode.add(node);
			}
		}
	}
	
	/**
	 * 
	 */
	private void buildViewerContextPopupMenu() {
		JPopupMenu ctxMenu = new JPopupMenu();
		
		JMenuItem copyMenu = new JMenuItem(I18n.JVIEWER.getString(JViewerNLS.CTX_MENU_COPY));
		copyMenu.setIcon(new ImageIcon(GUI.ICON_COPY));
		copyMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				onCopyClipboard(textArea.getText());
			}
		});
		ctxMenu.add(copyMenu);
		
		ctxMenu.addSeparator();
		JMenuItem exportMenu = new JMenuItem(I18n.JVIEWER.getString(JViewerNLS.CTX_MENU_EXPORT));
		exportMenu.setIcon(new ImageIcon(GUI.ICON_EXPORT));
		exportMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				onExportFile();
			}
		});
		ctxMenu.add(exportMenu);
		
		ctxMenu.addSeparator();
		JMenuItem verifyMenu = new JMenuItem(I18n.JVIEWER.getString(JViewerNLS.CTX_MENU_VERIFY));
		verifyMenu.setIcon(new ImageIcon(GUI.ICON_CHECK));
		verifyMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Thread t = new Thread() {
					public void run() {
						onVerifyClientPack();
					}
				};
				t.start();
			}
		});
		ctxMenu.add(verifyMenu);
		
		ctxMenu.addSeparator();
		JMenuItem openDirMenu = new JMenuItem(I18n.JVIEWER.getString(JViewerNLS.CTX_MENU_OPEN));
		openDirMenu.setIcon(new ImageIcon(GUI.ICON_OPEN));
		openDirMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				onOpenDir();
			}
		});
		ctxMenu.add(openDirMenu);
		
		ctxMenu.addSeparator();
		JMenuItem subJsonMenu = new JMenuItem(I18n.JVIEWER.getString(JViewerNLS.CTX_MENU_SUBJSON));
		subJsonMenu.setIcon(new ImageIcon(GUI.ICON_LOOKUP));
		subJsonMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				onViewSubJsonCfg();
			}
		});
		ctxMenu.add(subJsonMenu);
		
		textArea.setComponentPopupMenu(ctxMenu);
	}
	
	private void onCopyClipboard(final String content) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
				new StringSelection(content), null);
	}
	
	private void onExportFile() {
		JFileChooser fileChooser = ViewHelper.getFileChooser();
		if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
			FileUtil.saveContent2File(textArea.getText(), 
					fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	private void onOpenDir() {
		final String MTHNAME = "onOpenDir()";
		try {
			Desktop.getDesktop().open(new File(FileUtil.getEntityDownloadPath()));
		} catch (IOException e) {
			LogUtil.logError(CLASSNAME, MTHNAME, e.getMessage(), e);
		}
	}
	
	private void onViewSubJsonCfg() {
		String jsonContent = textArea.getText();
		List<String> subJsonList = FileUtil.getSubConfigList(jsonContent);
		
		SubJsonDialog subDialog = new SubJsonDialog();
		subDialog.setViewData(subJsonList);
		subDialog.popupWindow();
		
		final String selectedUrl = subDialog.getSelectSubJson();
		if (selectedUrl != null) {
			textArea.setText("");
			textArea.setText(FileUtil.getSubJSONConfigContent(selectedUrl));
		}
	}
	
	private void onVerifyClientPack() {
		consoleArea.setText("");
		consoleArea.setText(I18n.JVIEWER.getString(JViewerNLS.VERIFY_PROGRESS));
		StringBuffer result = new StringBuffer();
		VerifyBean vBean = FileUtil.verifyMobileClientPack(textArea.getText());
		List<ZipVryBean> zipVryList = vBean.getZipVryList();
		for (ZipVryBean zBean : zipVryList) {
			result.append("{\n");
			result.append(I18n.JVIEWER.getString(JViewerNLS.ZIP_CONFIG_URL) + zBean.getZipUrl() + "\"\n");
			result.append(I18n.JVIEWER.getString(JViewerNLS.ZIP_CONFIG_HASHCODE) + zBean.getCfgHash() + "\"\n");
			result.append(I18n.JVIEWER.getString(JViewerNLS.ZIP_VERIFY_HASHCODE) + zBean.getVryHash() + "\"\n");
			result.append(I18n.JVIEWER.getString(JViewerNLS.ZIP_VERIFY_RESULT) + zBean.getVryRsult() + "\"\n");
			result.append("}\n\n");
		}
		
		List<JsonVryBean> jsonVryList = vBean.getJsonVryList();
		for (JsonVryBean jBean : jsonVryList) {
			result.append("{\n");
			result.append(I18n.JVIEWER.getString(JViewerNLS.JSON_CONFIG_URL) + jBean.getCfgUrl() + "\"\n");
			result.append(I18n.JVIEWER.getString(JViewerNLS.JSON_VERIFY_RESULT) + jBean.getResult() + "\"\n");
			result.append("}\n\n");
		}
		consoleArea.setText("");
		consoleArea.setText(result.toString());
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

package com.iwebcoding.mobile.tool.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.iwebcoding.mobile.tool.constant.GUI;
import com.iwebcoding.mobile.tool.constant.nls.AppViewNLS;
import com.iwebcoding.mobile.tool.constant.nls.ChksumNLS;
import com.iwebcoding.mobile.tool.constant.nls.ErrNLS;
import com.iwebcoding.mobile.tool.framework.PageController;
import com.iwebcoding.mobile.tool.framework.PageView;
import com.iwebcoding.mobile.tool.model.ChksumBean;
import com.iwebcoding.mobile.tool.util.FileUtil;
import com.iwebcoding.mobile.tool.util.I18n;
import com.iwebcoding.mobile.tool.util.LogUtil;
import com.iwebcoding.mobile.tool.util.ViewHelper;

public class ChecksumPage implements PageView {
	private JTextArea consoleArea = new JTextArea();
	private JTextField fileField = new JTextField(50);
	private JRadioButton generateRadio = new JRadioButton(I18n.CHKSUM.getString(ChksumNLS.RADIO_GENERATE_CHKSUM));
	private JRadioButton replaceRadio = new JRadioButton(I18n.CHKSUM.getString(ChksumNLS.RADIO_REPLACE_CHKSUM));
	private JButton browse = new JButton(I18n.CHKSUM.getString(ChksumNLS.BROWSE_FILE));
	private JButton genChksum = new JButton(I18n.CHKSUM.getString(ChksumNLS.BTN_GENERATE));
	private JLabel copyMenu = new JLabel(new ImageIcon(GUI.ICON_COPY));
	private JLabel clearMenu = new JLabel(new ImageIcon(GUI.ICON_CLEAR));
	
	private final String CLASSNAME = ChecksumPage.class.getName();
	private List<String> jsonFileList = new ArrayList<String>();
	private List<String> localZipList = new ArrayList<String>();
	private PageController controller;
	private JPanel pageView;
	
	@Override
	public String getTitle() {
		return I18n.APPVIEW.getString(AppViewNLS.TITLE_TAB_GENCHKSUM);
	}

	@Override
	public void init(PageController controller) {
		this.controller = controller;
		initComponents();
	}
	
	@Override
	public Component asComponet() {
		return pageView;
	}

	@Override
	public PageController getController() {
		return controller;
	}

	/**
	 * This method initialize the checksum page components
	 */
	private void initComponents() {
		pageView = new JPanel(new BorderLayout());
		pageView.setBorder(BorderFactory.createTitledBorder(I18n.CHKSUM.getString(ChksumNLS.BORDER_CHKSUM_MANAGE)));
		createActionPane();
		createConsolePane();
	}
	
	/**
	 * This method creates the action pane of the page
	 */
	private void createActionPane() {
		initActionPaneComponents();
		JPanel actionPane = new JPanel(new BorderLayout());
		actionPane.setBorder(BorderFactory.createTitledBorder(""));
		
		JPanel browsePane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		browsePane.add(new JLabel(I18n.CHKSUM.getString(ChksumNLS.CHOOSE_FILE)));
		
		browsePane.add(fileField);
		browsePane.add(browse);
		browsePane.add(genChksum);
		actionPane.add(browsePane, BorderLayout.CENTER);
		
		JPanel filterPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		filterPane.add(generateRadio);
		filterPane.add(replaceRadio);
		
		ButtonGroup radioBtnGroup = new ButtonGroup();
		radioBtnGroup.add(generateRadio);
		radioBtnGroup.add(replaceRadio);
		attachActionListeners();
		actionPane.add(filterPane, BorderLayout.SOUTH);
		
		pageView.add(actionPane, BorderLayout.NORTH);
	}
	
	/**
	 * Initialize the action pane component state
	 */
	private void initActionPaneComponents() {
		fileField.setToolTipText(I18n.CHKSUM.getString(ChksumNLS.TXT_FILE_FIELD));
		fileField.setText(I18n.CHKSUM.getString(ChksumNLS.TXT_FILE_FIELD));
		fileField.setEditable(false);
		fileField.setDropTarget(new FileDropTarget());
		
		generateRadio.setSelected(true);
		replaceRadio.setSelected(false);
	}
	
	/**
	 * This method attaches the action listeners to the action buttons
	 */
	private void attachActionListeners() {
		browse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fileChooser = ViewHelper.getFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
					String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
					fileField.setText(selectedFile);
				}
			}
		});
		
		genChksum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (!"".equals(fileField.getText())) {
					new GenOrReplaceTask().start();
				}
			}
		});
	}
	
	/**
	 * This method creates the page console pane
	 */
	private void createConsolePane() {
		initConsolePaneComponent();
		
		JPanel consolePane = new JPanel(new BorderLayout());
		consolePane.setBorder(BorderFactory.createTitledBorder(I18n.CHKSUM.getString(ChksumNLS.BORDER_CONSOLE)));
		createConsoleCmdPane(consolePane);
		createConsoleAreaPane(consolePane);
		pageView.add(consolePane, BorderLayout.CENTER);
	}
	
	/**
	 * This method creates the console command pane components
	 * 
	 * @param consolePane
	 */
	private void createConsoleCmdPane(final JPanel consolePane) {
		JPanel consoleCmdPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		copyMenu.setToolTipText(I18n.CHKSUM.getString(ChksumNLS.TOOLTIP_COPY));
		copyMenu.setBorder(BorderFactory.createTitledBorder(""));
		
		clearMenu.setToolTipText(I18n.CHKSUM.getString(ChksumNLS.TOOLTIP_CLEAR));
		clearMenu.setBorder(BorderFactory.createTitledBorder(""));
		
		attachConsoleCmdListeners();
		consoleCmdPane.add(copyMenu);
		consoleCmdPane.add(clearMenu);
		consolePane.add(consoleCmdPane, BorderLayout.NORTH);
	}
	
	/**
	 * This method attaches the console command component listeners
	 */
	private void attachConsoleCmdListeners() {
		copyMenu.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mEvt) {
				ViewHelper.copyToClipboard(consoleArea.getText());
			}
		});
		
		clearMenu.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mEvt) {
				consoleArea.setText("");
			}
		});
	}
	
	/**
	 * This method creates the console results pane
	 * 
	 * @param consolePane
	 */
	private void createConsoleAreaPane(final JPanel consolePane) {
		JScrollPane scrollPane = new JScrollPane(consoleArea);
		consolePane.add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * This method initialize the console page component state
	 */
	private void initConsolePaneComponent() {
		consoleArea.setText("");
		consoleArea.setEditable(false);
		consoleArea.setFocusable(true);
	}
	
	/**
	 * This method prints the checksum report to console
	 */
	private void printGenChksumReport() {
		consoleArea.setText("");
		StringBuffer report = new StringBuffer();
		List<ChksumBean> results = new ArrayList<ChksumBean>();
		FileUtil.generateCheksum(results, fileField.getText());
		
		if (results.isEmpty()) {
			JOptionPane.showMessageDialog(null, I18n.ERROR.getString(ErrNLS.MSG_ZIP_NOT_FOUND), 
					I18n.ERROR.getString(ErrNLS.TITLE_WARN), JOptionPane.WARNING_MESSAGE);
		} else {
			for(ChksumBean bean : results) {
				report.append("{\n");
				report.append(I18n.CHKSUM.getString(ChksumNLS.REPORT_ZIPURL) + bean.getZipUrl() + "\",\n");
				report.append(I18n.CHKSUM.getString(ChksumNLS.REPORT_HASHCODE) + bean.getHashcode() + "\",\n");
				report.append(I18n.CHKSUM.getString(ChksumNLS.REPORT_FILESIZE) + bean.getSize() + "\"\n");
				report.append("}\n\n");
			}
			consoleArea.setText(report.toString());
		}
	}
	
	/**
	 * Prerequisite Checking Process
	 * 
	 * @param report
	 * @return
	 */
	private boolean prerequisiteChecking(final StringBuffer report) {
		resetData();
		
		boolean passed = false;
		if (detectJsonFiles(report))
			if (detectZipFiles(report))
				passed = true;
		
		if (passed) {
			appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.PREREQ_CHECK_PASSED));
		} else {
			appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.PREREQ_CHECK_FAILED));
		}
		return passed;
	}
	
	/**
	 * Reset data of the array list object, prevent cache issue
	 */
	private void resetData() {
		jsonFileList.clear();
		localZipList.clear();
	}
	
	/**
	 * This method validates the configure json file
	 * 
	 * @param report
	 * @return
	 */
	private boolean detectJsonFiles(final StringBuffer report) {
		consoleArea.setText("");
		
		boolean isValid = true;
		appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.PREREQ_CHECK));
		appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.PREREQ_STEP1));
		FileUtil.detectJsonFiles(jsonFileList, fileField.getText());
		if (jsonFileList.isEmpty()) {
			isValid = false;
			appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.MSG_NO_JSON_FILE));
		} else {
			for (String fileName : jsonFileList) {
				appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.LABEL_FILE_NAME) + fileName + "\n");
			}
			appendConsole(report, "\n\n");
		}
		return isValid;
	}
	
	/**
	 * This method validates the configure zip file
	 * 
	 * @param report
	 * @return
	 */
	private boolean detectZipFiles(final StringBuffer report) {
		appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.PREREQ_STEP2));
		
		boolean isValid = true;
		for (String fileName : jsonFileList) {
			MessageFormat formatter = new MessageFormat(I18n.CHKSUM.getString(ChksumNLS.MSG_VALIDATE));
			appendConsole(report, formatter.format(new String[] {fileName}));
			try {
				List<String> zipList = FileUtil.getJsonConfigZips(fileName);
				for (String zipUrl : zipList) {
					String zipName = zipUrl.substring(zipUrl.lastIndexOf("/") + 1);
					appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.LABEL_ZIP_FILE) + zipName + "\n");
					
					if (FileUtil.detectZipFile(zipName, fileField.getText(), localZipList)) {
						appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.MSG_FIND_ZIP));
					} else {
						appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.MSG_MISS_ZIP));
						isValid = false;
					}
				}
			} catch (Exception e) {
				isValid = false;
				appendConsole(report, I18n.ERROR.getString(ErrNLS.MSG_DETECT_ZIP) + fileName + "\n");
			}
			appendConsole(report, "\n");
		}
		return isValid;
	}
	
	/**
	 * This method replace the zip file hashcode configure in target json configure file(s)
	 * 
	 * @param report
	 */
	private void replaceChksumWithReport(final StringBuffer report) {
		appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.REPLACE_PROGRESS));
		generateJsonBackup(report);
		replaceFileChecksum(report);
	}
	
	/**
	 * This method creates the json back up file(s), in .bak file format
	 * 
	 * @param report
	 */
	private void generateJsonBackup(final StringBuffer report) {
		appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.BACKUP_JSON));
		for (String fileName : jsonFileList) {
			try {
				FileUtil.generateBackupFile(fileName);
				MessageFormat formatter = new MessageFormat(I18n.CHKSUM.getString(ChksumNLS.LABEL_BACKUP_FILE));
				String msg = formatter.format(new String[] {FileUtil.getBackupFileName(fileName)});
				appendConsole(report, msg);
			} catch (IOException e) {
				MessageFormat formatter = new MessageFormat(I18n.ERROR.getString(ErrNLS.MSG_BACKUP_FILE));
				String errMsg = formatter.format(new String[] {fileName});
				appendConsole(report, errMsg);
				break;
			}
		}
		appendConsole(report, "\n");
	}
	
	/**
	 * This method replace the file hashcode value in the target json file(s)
	 * 
	 * @param report
	 */
	private void replaceFileChecksum(final StringBuffer report) {
		final String MTHNAME = "replaceFileChecksum(final StringBuffer report)";
		appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.REPLACE_JSON));
		
		boolean hasErr = false;
		for (String fileName : jsonFileList) {
			try {
				FileUtil.replaceJsonFile(fileName, localZipList);
				appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.MSG_CHKSUM_REPLACED) + fileName + "\n");
			} catch (Exception e) {
				MessageFormat formatter = new MessageFormat(I18n.ERROR.getString(ErrNLS.MSG_REPACE_CHKSUM));
				String errMsg = formatter.format(new String[] {fileName});
				appendConsole(report, errMsg);
				LogUtil.logError(CLASSNAME, MTHNAME, errMsg, e);
				hasErr = true;
				break;
			}
		}
		appendConsole(report, "\n");
		
		if (hasErr) {
			appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.MSG_REPLACE_FAILED));
		} else {
			appendConsole(report, I18n.CHKSUM.getString(ChksumNLS.MSG_REPLACE_PASSED));
		}
	}
	
	/**
	 * This method appends the output to console area.
	 * 
	 * @param buffer
	 * @param msg
	 */
	private void appendConsole(final StringBuffer buffer, final String msg) {
		buffer.append(msg);
		consoleArea.setText(buffer.toString());
		ViewHelper.delay(100);
	}
	
	/**
	 * 
	 * @author Andy Luo
	 *
	 */
	private class FileDropTarget extends DropTarget {
		private static final long serialVersionUID = 448896480037836506L;
		private final String CLASSNAME = FileDropTarget.class.getName();
		
		@SuppressWarnings("unchecked")
		@Override
		public synchronized void drop(DropTargetDropEvent dEvt) {
			final String MTHNAME = "drop(DropTargetDropEvent dEvt)";
			dEvt.acceptDrop(DnDConstants.ACTION_COPY);
			try {
				List<File> dFileList = (List<File>) dEvt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
				if (dFileList != null) {
					if (dFileList.size() > 1) {
						JOptionPane.showMessageDialog(null, I18n.ERROR.getString(ErrNLS.MSG_EXCEED_LIMIT), 
								I18n.ERROR.getString(ErrNLS.TITLE_WARN), JOptionPane.WARNING_MESSAGE);
					} else {
						File selectedFile = dFileList.get(0);
						fileField.setText(selectedFile.getAbsolutePath());
						new GenOrReplaceTask().start();
					}
				}
			} catch (Exception e) {
				LogUtil.logError(CLASSNAME, MTHNAME, I18n.ERROR.getString(ErrNLS.MSG_DRAG_DROP), e);
			}
		}
	}
	
	/**
	 * 
	 * @author Andy Luo
	 *
	 */
	private class GenOrReplaceTask extends Thread {
		public void run() {
			StringBuffer report = new StringBuffer();
			if (generateRadio.isSelected()) {
				printGenChksumReport();
			} else {
				if (new File(fileField.getText()).isDirectory()) {
					if(prerequisiteChecking(report)) {
						replaceChksumWithReport(report);
					}
				} else {
					JOptionPane.showMessageDialog(null, I18n.ERROR.getString(ErrNLS.MSG_NOT_FOLDER), 
							I18n.ERROR.getString(ErrNLS.TITLE_WARN), JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
}

package com.iwebcoding.mobile.tool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.exolab.castor.xml.Unmarshaller;

import com.iwebcoding.mobile.tool.constant.App;
import com.iwebcoding.mobile.tool.constant.nls.DeployNLS;
import com.iwebcoding.mobile.tool.constant.nls.ErrNLS;
import com.iwebcoding.mobile.tool.model.DeployBean;
import com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg;
import com.iwebcoding.mobile.tool.model.castor.bean.DeployCfgList;
import com.sun.org.apache.xml.internal.security.utils.Base64;



public class DeployUtil {
	private final String CLASSNAME = DeployUtil.class.getName();
	private static DeployUtil instance = null;
	private DeployCfgList deployCfgList = null;
	private List<DeployBean> deployCfgs = new ArrayList<DeployBean>();
	private List<File> uploadList = new ArrayList<File>();
	private List<String> deployList = new ArrayList<String>();
	
	private DeployUtil() {
		loadDeployCfg();
	}
	
	public static DeployUtil getInstance() {
		return instance;
	}
	
	static {
		instance = new DeployUtil();
	}
	
	/**
	 * 
	 */
	private void loadDeployCfg() {
		final String MTHNAME = "loadDeployCfg()";
		InputStreamReader reader = null;
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(AppCfgUtil.getAppCfg(App.KEY_APPCFG_DEPLOY));
			reader = new InputStreamReader(inStream);
			deployCfgList = (DeployCfgList) Unmarshaller.unmarshal(DeployCfgList.class, reader);
			intepretConfig();
		} catch (Exception e) {
			LogUtil.logError(CLASSNAME, MTHNAME, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), I18n.ERROR.getString(ErrNLS.TITLE_ERR), JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LogUtil.logError(CLASSNAME, MTHNAME, I18n.ERROR.getString(ErrNLS.MSG_CLOSE_STREAM), e);
					JOptionPane.showMessageDialog(null, I18n.ERROR.getString(ErrNLS.MSG_CLOSE_STREAM), 
							I18n.ERROR.getString(ErrNLS.TITLE_ERR), JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		}
	}
	
	/**
	 * 
	 */
	private void intepretConfig() {
		DeployCfg[] configs = deployCfgList.getDeployCfg();
		DeployBean bean = null;
		for (DeployCfg config : configs) {
			bean = new DeployBean();
			bean.setEnvName(config.getEnvName());
			bean.setEnvDomain(config.getEnvDomain());
			deployCfgs.add(bean);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<DeployBean> getDeployConfigList() {
		return this.deployCfgs;
	}
	
	/**
	 * 
	 */
	public void init() {
		
	}
	
	/**
	 * 
	 * @param domain
	 * @param srvPath
	 * @param uploadPath
	 * @param report
	 */
	public void deployFiles(final String domain, final String srvPath, final String uploadPath, final StringBuffer report) {
		report.append(I18n.DEPLOY.getString(DeployNLS.MSG_DEPLOY_PROGRESS));
		resetData();
		getUploadedFileList(uploadList, uploadPath);
		String magicUrl = getServerDeployUrl(domain);
		if (magicUrl != null) {
			for (File file : uploadList) {
				if(transportFile(magicUrl, srvPath, file)) {
					MessageFormat formatter = new MessageFormat(I18n.DEPLOY.getString(DeployNLS.MSG_FILE_UPLOADED));
					report.append(formatter.format(new String[] {file.getAbsolutePath(), srvPath}));
				} else {
					MessageFormat formatter = new MessageFormat(I18n.DEPLOY.getString(DeployNLS.MSG_FILE_FAILED));
					report.append(formatter.format(new String[] {file.getAbsolutePath()}));
				}
			}
		}
	}
	
	/**
	 * 
	 */
	private void resetData() {
		uploadList.clear();
		deployList.clear();
	}
	
	/**
	 * 
	 * @param url
	 * @param srvPath
	 * @param file
	 * @return
	 */
	private boolean transportFile(final String url, final String srvPath, final File file) {
		final String MTHNAME = "deployFilesToServer(final String url, final String deployPath, final File file)";
		boolean success = false;
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(getHttpEntity(srvPath, file));
		
		CloseableHttpResponse response = null;
		CloseableHttpClient client = getCloseableHttpClient();
		try {
			response = client.execute(httppost);
			if (App.HTTP_STATUS_SUCCESS == response.getStatusLine().getStatusCode()) {
				success = true;
			}
		} catch (Exception e) {
			LogUtil.logError(CLASSNAME, MTHNAME, e.getMessage(), e);
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				LogUtil.logError(CLASSNAME, MTHNAME, e.getMessage(), e);
			}
		}
		return success;
	}
	
	/**
	 * 
	 * @param deployPath
	 * @param file
	 * @return
	 */
	private HttpEntity getHttpEntity(final String deployPath, final File file) {
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		StringBody pathValue = new StringBody(deployPath, ContentType.TEXT_PLAIN);
		builder.addPart(App.PARAM_PATH, pathValue);
		
		FileBody fileValue = new FileBody(file);
		builder.addPart(App.PARAM_FILE, fileValue);
		
		HttpEntity reqEntity = builder.build();
		return reqEntity;
	}
	
	/**
	 * 
	 * @return
	 */
	private CloseableHttpClient getCloseableHttpClient() {
		CloseableHttpClient client = null;
		String netMode = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_NETMODE);
		if (App.NET_MODE_PROXY.equals(netMode)) {
			String proxySrv = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_PROXY_SERVER);
			int portNum = Integer.parseInt(SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_PROXY_PORT));
			
			HttpHost proxy = new HttpHost(proxySrv, portNum);
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			client = HttpClients.custom().setRoutePlanner(routePlanner).build();
		} else {
			client = HttpClients.createDefault();
		}
		return client;
	}
	
	/**
	 * 
	 * @return
	 */
	private String getServerDeployUrl(final String domain) {
		final String MTHNAME = "getServerDeployUrl(final String domain)";
		String magicUrl = null;
		try {
			String[] magics = (new String(Base64.decode(AppCfgUtil.getAppCfg(
					App.KEY_APPCFG_MAGIC)), App.UTF8)).split(";");
			for (int i = 0; i < magics.length; i++) {
				String temp = domain + "/1/" + magics[i] + App.UPLOAD_GIF;
				if (transportFile(temp, AppCfgUtil.getAppCfg(App.KEY_APPCFG_DEPLOY_ROOT), getDummyFile())) {
					magicUrl = temp;
					break;
				}
			}
		} catch (Exception e) {
			LogUtil.logError(CLASSNAME, MTHNAME, e.getMessage(), e);
		}
		return magicUrl;
	}
	
	/**
	 * 
	 * @param fileList
	 * @param filePath
	 * @return
	 */
	private List<File> getUploadedFileList(final List<File> fileList, final String filePath) {
		File curFile = new File(filePath);
		if (curFile.isDirectory()) {
			File[] files = curFile.listFiles();
			for (File f : files) {
				getUploadedFileList(fileList, f.getAbsolutePath());
			}
		} else {
			if (curFile.getName().endsWith(App.EXT_TXT)
					|| curFile.getName().endsWith(App.EXT_ZIP)) {
				fileList.add(curFile);
			}
		}
		
		return fileList;
	}
	
	/**
	 * 
	 * @param domain
	 * @param srvPath
	 * @return
	 */
	public boolean isValidServerPath(final String domain, final String srvPath) {
		boolean isValid = true;
		File dummy = getDummyFile();
		transportFile(getServerDeployUrl(domain), srvPath, dummy);
		try {
			String url = domain + AppCfgUtil.getAppCfg(App.KEY_APPCFG_DEPLOY_RUI) 
			           + srvPath.substring(AppCfgUtil.getAppCfg(App.KEY_APPCFG_DEPLOY_ROOT).length()) 
			           + "/" + dummy.getName();
			FileUtil.loadRemoteResource(url.replace(App.PROTOCOL_HTTP, App.PROTOCOL_HTTPS));
		} catch (Exception e) {
			isValid = false;
		}
		return isValid;
	}
	
	/**
	 * 
	 * @return
	 */
	private File getDummyFile() {
		final String MTHNAME = "getDummyFile()";
		File dummy = new File(AppCfgUtil.getAppCfg(App.KEY_APPCFG_DUMMY));
		if (!dummy.exists()) {
			try {
				dummy.createNewFile();
			} catch (IOException e) {
				LogUtil.logError(CLASSNAME, MTHNAME, e.getMessage(), e);
			}
		}
		return dummy;
	}
}

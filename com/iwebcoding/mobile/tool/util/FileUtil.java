package com.iwebcoding.mobile.tool.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.iwebcoding.mobile.tool.component.AppCfgPane;
import com.iwebcoding.mobile.tool.constant.App;
import com.iwebcoding.mobile.tool.constant.nls.ErrNLS;
import com.iwebcoding.mobile.tool.constant.nls.JViewerNLS;
import com.iwebcoding.mobile.tool.model.ChksumBean;
import com.iwebcoding.mobile.tool.model.EntCfgBean;
import com.iwebcoding.mobile.tool.model.JsonVryBean;
import com.iwebcoding.mobile.tool.model.VerifyBean;
import com.iwebcoding.mobile.tool.model.ZipVryBean;
import com.thoughtworks.xstream.core.util.Base64Encoder;

public class FileUtil {
	private static final String CLASSNAME = FileUtil.class.getName();
	private static FileUtil instance = null;
	
	private FileUtil() {}
	
	public static FileUtil getInstance() {
		return instance;
	}
	
	static {
		instance = new FileUtil();
	}
	
	public void init() {
		handleHttpsCertCheck();
	}
	
	/**
	 * 
	 */
	private void handleHttpsCertCheck() {
		final String MTHNAME = "handleHttpsCertCheck()";
		
		String disableHttps = AppCfgUtil.getAppCfg(App.KEY_APPCFG_DISHTTPS);
		if (Boolean.valueOf(disableHttps).booleanValue()) {
			TrustManager[] trustAllManagers = {new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {}

				@Override
				public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			}};
			
			try {
				SSLContext context = SSLContext.getInstance(AppCfgUtil.getAppCfg(App.KEY_APPCFG_SSL_ALGORITHM));
				context.init(null, trustAllManagers, new SecureRandom());
				
				HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
				HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
					@Override
					public boolean verify(String hostname, SSLSession ssl) {
						return true;
					}
				});
			} catch (Exception e) {
				LogUtil.logError(CLASSNAME, MTHNAME, I18n.ERROR.getString(ErrNLS.MSG_CERT_CHECK), e);
			}
		}
	}
	
	/**
	 * 
	 * @param cfgUrl
	 * @return
	 */
	public static List<EntCfgBean> getEntCountryCfgList(final String cfgUrl) {
		final String MTHNAME = "getEntCountryCfgList(final String cfgUrl)";
		List<EntCfgBean> cfgList = new ArrayList<EntCfgBean>();
		try {
			loadRemoteResource(cfgUrl);
		} catch (Exception e) {
			MessageFormat msg = new MessageFormat(I18n.ERROR.getString(ErrNLS.MSG_DOWNLOAD_GROUPCFG));
			LogUtil.logError(CLASSNAME, MTHNAME, msg.format(new String[] {cfgUrl}), e);
			JOptionPane.showMessageDialog(null, msg.format(new String[] {cfgUrl}),
					I18n.ERROR.getString(ErrNLS.TITLE_ERR), JOptionPane.ERROR_MESSAGE);
		}
		cfgList = intepretGroupXMLCfg(cfgUrl);
		return cfgList;
	}
	
	/**
	 * 
	 * @param cfgUrl
	 * @return
	 */
	private static List<EntCfgBean> intepretGroupXMLCfg(final String cfgUrl) {
		List<EntCfgBean> cfgList = new ArrayList<EntCfgBean>();
		
		try {
			Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(getFilePath(cfgUrl)));
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			XPathExpression entNameExpression = xPath.compile(App.XPATH_ENTNAME);
			XPathExpression entUrlExpression = xPath.compile(App.XPATH_ENTURL);
			if (AppCfgPane.getSelectedDevice() != null && 
					App.IPAD.equals(AppCfgPane.getSelectedDevice())) {
				entUrlExpression = xPath.compile(App.XPATH_ENTURL_IPAD);
			}
			NodeList entNameList = (NodeList) entNameExpression.evaluate(xmlDoc, XPathConstants.NODESET);
			NodeList entUrlList = (NodeList) entUrlExpression.evaluate(xmlDoc, XPathConstants.NODESET);
			
			EntCfgBean cfgBean = null;
			for (int i = 0; i < entNameList.getLength(); i++) {
				String name = entNameList.item(i).getNodeValue();
				String url = entUrlList.item(i).getNodeValue();
				
				cfgBean = new EntCfgBean();
				cfgBean.setName(name);
				cfgBean.setUrl(url);
				cfgList.add(cfgBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return cfgList;
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String getMasterJSONConfigContent(final String url) {
		final String MTHNAME = "getMasterJSONConfigContent(final String url)";
		
		String masterJson = "";
		try {
			masterJson = getJSONConfigContent(url);
		} catch (Exception e) {
			MessageFormat formatter = new MessageFormat(I18n.ERROR.getString(ErrNLS.MSG_READ_JSON));
			String msg = formatter.format(new String[] {url});
			
			LogUtil.logError(CLASSNAME, MTHNAME, msg, e);
			JOptionPane.showMessageDialog(null, msg, I18n.ERROR.getString(ErrNLS.TITLE_ERR), 
					JOptionPane.ERROR_MESSAGE);
		}
		
		return masterJson;
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String getSubJSONConfigContent(final String url) {
		final String MTHNAME = "getSubJSONConfigContent(final String url)";
		
		String subJson = "";
		try {
			subJson = getJSONConfigContent(url);
		} catch (Exception e) {
			MessageFormat formatter = new MessageFormat(I18n.ERROR.getString(ErrNLS.MSG_READ_JSON));
			String msg = formatter.format(new String[] {url});
			
			LogUtil.logError(CLASSNAME, MTHNAME, msg, e);
			JOptionPane.showMessageDialog(null, msg, I18n.ERROR.getString(ErrNLS.TITLE_ERR), 
					JOptionPane.ERROR_MESSAGE);
			
			deleteTempFile(url);
		}
		
		return subJson;
	}
	
	/**
	 * 
	 * @param url
	 */
	private static void deleteTempFile(final String url) {
		File temp = new File(getFilePath(url));
		if (temp.exists()) {
			temp.delete();
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String getJSONConfigContent(final String url) throws Exception {
		loadRemoteResource(url);
		return getFileContent(getFilePath(url));
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private static String getFileContent(final String fileName) throws Exception {
		StringBuffer buffer = new StringBuffer();
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), App.UTF8));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}
		} finally {
			reader.close();
		}
		
		return buffer.toString();
	}
	
	/**
	 * 
	 * @param url
	 */
	public static void loadRemoteResource(final String url) throws Exception {
		loadRemoteResource(url, getFilePath(url));
	}
	
	/**
	 * 
	 * @param url
	 * @param filePath
	 */
	public static void loadRemoteResource(final String url, final String filePath) throws Exception {
		final String MTHNAME = "loadRemoteResource(final String url, final String filePath)";
		
		FileOutputStream outStream = null;
		HttpsURLConnection httpsCon = null;
		try {
			URL cfgUrl = new URL(url);
			
			httpsCon = getHttpsConnection(cfgUrl);
			handleAuthorization(cfgUrl, httpsCon);
			httpsCon.connect();
			
			int bytesRead = 0;
			byte[] blob = new byte[8192];
			outStream = new FileOutputStream(filePath);
			while ((bytesRead = httpsCon.getInputStream().read(blob)) != -1) {
				outStream.write(blob, 0, bytesRead);
			}
			outStream.flush();
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					LogUtil.logError(CLASSNAME, MTHNAME, I18n.ERROR.getString(ErrNLS.MSG_CLOSE_STREAM), e);
				}
			}
			
			if (httpsCon != null) {
				httpsCon.disconnect();
			}
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static HttpsURLConnection getHttpsConnection(final URL url) throws IOException {
		String netMode = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_NETMODE);
		HttpsURLConnection connection = null;
		
		if (App.NET_MODE_PROXY.equals(netMode)) {
			String proxyServer = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_PROXY_SERVER);
			String proxyPort = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_PROXY_PORT);
			
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServer, Integer.parseInt(proxyPort)));
			connection = (HttpsURLConnection) url.openConnection(proxy);
		} else {
			connection = (HttpsURLConnection) url.openConnection();
		}
		
		return connection;
	}
	
	/**
	 * 
	 * @param url
	 * @param connection
	 * @throws UnsupportedEncodingException 
	 */
	private static void handleAuthorization(final URL url, final HttpsURLConnection 
			connection) throws UnsupportedEncodingException {
		String userInfo = url.getUserInfo();
		if (userInfo != null && !"".equals(userInfo)) {
			connection.setRequestProperty(App.PARAM_AUTH, App.BASIC + 
					new Base64Encoder().encode(userInfo.getBytes(App.UTF8)));
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	private static String getFilePath(final String url) {
		String download = getEntityDownloadPath();
		return download += url.substring(url.lastIndexOf("/") + 1);
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getEntityDownloadPath() {
		String download = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_DOWNLOAD_DIR) + File.separator;
		
		if (AppCfgPane.getSelectedVer() != null) {
			download += AppCfgPane.getSelectedVer() + File.separator;
		}
		
		if (AppCfgPane.getSelectedPlatform() != null) {
			download += AppCfgPane.getSelectedPlatform() + File.separator;
		}
		
		if (AppCfgPane.getSelectedDevice() != null) {
			download += AppCfgPane.getSelectedDevice() + File.separator;
		}
		
		File dir = new File(download);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		return download;
	}
	
	/**
	 * 
	 * @param content
	 * @param fileName
	 */
	public static void saveContent2File(final String content, final String fileName) {
		final String MTHNAME = "saveContent2File(final String content, final String fileName)";
		
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(fileName), App.UTF8);
			writer.write(content);
		} catch (IOException e) {
			LogUtil.logError(CLASSNAME, MTHNAME, I18n.ERROR.getString(ErrNLS.MSG_WRITE_FILE), e);
			JOptionPane.showMessageDialog(null, I18n.ERROR.getString(ErrNLS.MSG_WRITE_FILE), 
					I18n.ERROR.getString(ErrNLS.TITLE_ERR), JOptionPane.ERROR_MESSAGE);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
					LogUtil.logError(CLASSNAME, MTHNAME, I18n.ERROR.getString(ErrNLS.MSG_CLOSE_STREAM), e1);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param jsonContent
	 * @return
	 */
	public static List<String> getSubConfigList(final String jsonContent) {
		List<String> cfgList = new ArrayList<String>();
		
		Pattern globalTemplateUrl = Pattern.compile(App.REG_GLOBAL_TEMPLATE_URL, Pattern.MULTILINE);
		Pattern subJsonUrl = Pattern.compile(App.REG_SUB_JSON, Pattern.MULTILINE);
		
		Matcher globalTemplateUrlMatcher = globalTemplateUrl.matcher("");
		Matcher subJsonUrlMatcher = subJsonUrl.matcher("");
		
		globalTemplateUrlMatcher.reset(jsonContent);
		while (globalTemplateUrlMatcher.find()) {
			subJsonUrlMatcher.reset(globalTemplateUrlMatcher.group(1));
			while (subJsonUrlMatcher.find()) {
				cfgList.add(subJsonUrlMatcher.group(1));
			}
		}
		
		return cfgList;
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static List<String> getJsonConfigZips(final String fileName) throws Exception {
		List<String> cfgList = new ArrayList<String>();
		
		String fileContent = getFileContent(fileName);
		Pattern zipUrlPattern = Pattern.compile(App.REG_ZIP_URL, Pattern.MULTILINE);
		Matcher zipUrlMatcher = zipUrlPattern.matcher("");
		zipUrlMatcher.reset(fileContent);
		while (zipUrlMatcher.find()) {
			cfgList.add(zipUrlMatcher.group(1));
		}
		
		return cfgList;
	}
	
	/**
	 * 
	 * @param jsonContent
	 * @return
	 */
	public static VerifyBean verifyMobileClientPack(final String jsonContent) {
		VerifyBean vryBean = new VerifyBean();
		
		List<ZipVryBean> zipVryList = new ArrayList<ZipVryBean>();
		getZipCfgList(zipVryList, jsonContent);
		verifyZipChecksum(zipVryList);
		vryBean.setZipVryList(zipVryList);
		
		List<JsonVryBean> jsonVryList = getVryJsonList(jsonContent);
		vryBean.setJsonVryList(jsonVryList);
		
		return vryBean;
	}
	
	/**
	 * 
	 * @param zipVryList
	 */
	private static void verifyZipChecksum(final List<ZipVryBean> zipVryList) {
		downloadClientPackZips(zipVryList);
		for (ZipVryBean bean : zipVryList) {
			String fileName = getFilePath(bean.getZipUrl());
			if (new File(fileName).exists()) {
				String chksum = getFileChecksum(fileName);
				bean.setVryHash(chksum);
				
				if (bean.getCfgHash().equalsIgnoreCase(chksum)) {
					bean.setVryRsult(I18n.JVIEWER.getString(JViewerNLS.TXT_PASSED));
				} else {
					bean.setVryRsult(I18n.JVIEWER.getString(JViewerNLS.TXT_FAILED));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileChecksum(final String fileName) {
		final String MTHNAME = "getFileChecksum(final String fileName)";
		
		StringBuffer checksum = new StringBuffer();
		InputStream inStream = null;
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(App.ALGORITHM_SHA512);
			inStream = new FileInputStream(fileName);
			
			byte[] blob = new byte[8192];
			int bRead = 0;
			while ((bRead = inStream.read(blob)) != -1) {
				digest.update(blob, 0, bRead);
			}
			
			byte[] md = digest.digest();
			for (int i = 0; i < md.length; i++) {
				checksum.append(Integer.toHexString((md[i] & 0xFF) + 256).substring(1).toUpperCase());
			}
		} catch (Exception e) {
			LogUtil.logError(CLASSNAME, MTHNAME, I18n.ERROR.getString(ErrNLS.MSG_GEN_CHECKSUM), e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					LogUtil.logError(CLASSNAME, MTHNAME, I18n.ERROR.getString(ErrNLS.MSG_CLOSE_STREAM), e);
				}
			}
		}
		
		return checksum.toString();
	}
	
	/**
	 * 
	 * @param zipVryList
	 */
	private static void downloadClientPackZips(final List<ZipVryBean> zipVryList) {
		final String MTHNAME = "downloadClientPackZips(final List<ZipVryBean> zipVryList)";
		
		for (ZipVryBean bean : zipVryList) {
			try {
				loadRemoteResource(bean.getZipUrl());
			} catch (Exception e) {
				bean.setVryHash(e.getMessage());
				bean.setVryRsult(I18n.JVIEWER.getString(JViewerNLS.TXT_FAILED));
				LogUtil.logError(CLASSNAME, MTHNAME, I18n.ERROR.getString(ErrNLS.MSG_LOAD_FILE), e);
				deleteTempFile(bean.getZipUrl());
			}
		}
	}
	
	/**
	 * 
	 * @param jsonContent
	 * @return
	 */
	private static List<JsonVryBean> getVryJsonList(final String jsonContent) {
		final String MTHNAME = "getVryJsonList(final String jsonContent)";
		
		List<String> subCfgList = getSubConfigList(jsonContent);
		JsonVryBean jVryBean = null;
		List<JsonVryBean> jsonVryList = new ArrayList<JsonVryBean>();
		for (String cfgUrl : subCfgList) {
			jVryBean = new JsonVryBean();
			jVryBean.setCfgUrl(cfgUrl);
			try {
				String subJsonContent = getJSONConfigContent(cfgUrl);
				JSONParser parser = new JSONParser();
				parser.parse(subJsonContent);
				jVryBean.setResult(I18n.JVIEWER.getString(JViewerNLS.TXT_VALID_JSON));
			} catch (ParseException e) {
				jVryBean.setResult(I18n.ERROR.getString(ErrNLS.MSG_INVALID_JSON));
				LogUtil.logError(CLASSNAME, MTHNAME, I18n.ERROR.getString(ErrNLS.MSG_INVALID_JSON), e);
			} catch (Exception e) {
				jVryBean.setResult(I18n.ERROR.getString(ErrNLS.MSG_LOAD_FILE));
				MessageFormat formatter = new MessageFormat(I18n.ERROR.getString(ErrNLS.MSG_READ_JSON));
				String msg = formatter.format(new String[] {cfgUrl});
				LogUtil.logError(CLASSNAME, MTHNAME, msg, e);
				deleteTempFile(cfgUrl);
			}
			jsonVryList.add(jVryBean);
		}
		
		return jsonVryList;
	}
	
	/**
	 * 
	 * @param vryList
	 * @param jsonContent
	 */
	private static void getZipCfgList(final List<ZipVryBean> vryList, final String jsonContent) {
		final String MTHNAME = "getZipCfgList(final List<VerifyBean> vryList, final String jsonContent)";
		
		Pattern zipUrlPattern = Pattern.compile(App.REG_ZIP_URL, Pattern.MULTILINE);
		Pattern hashcodePattern = Pattern.compile(App.REG_HASHCODE, Pattern.MULTILINE);
		
		Matcher zipUrlMatcher = zipUrlPattern.matcher("");
		Matcher hashcodeMatcher = hashcodePattern.matcher("");
		
		zipUrlMatcher.reset(jsonContent);
		hashcodeMatcher.reset(jsonContent);
		ZipVryBean bean = null;
		while (zipUrlMatcher.find() && hashcodeMatcher.find()) {
			bean = new ZipVryBean();
			bean.setZipUrl(zipUrlMatcher.group(1));
			bean.setCfgHash(hashcodeMatcher.group(1));
			vryList.add(bean);
		}
		
		List<String> subCfgList = getSubConfigList(jsonContent);
		for (String cfgUrl : subCfgList) {
			try {
				getZipCfgList(vryList, getJSONConfigContent(cfgUrl));
			} catch (Exception e) {
				MessageFormat formatter = new MessageFormat(I18n.ERROR.getString(ErrNLS.MSG_READ_JSON));
				String msg = formatter.format(new String[] {cfgUrl});
				LogUtil.logError(CLASSNAME, MTHNAME, msg, e);
			}
		}
	}
	
	/**
	 * 
	 * @param report
	 * @param fileName
	 */
	public static void generateCheksum(final List<ChksumBean> report, 
			final String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] child = file.listFiles();
				for (int i = 0; i < child.length; i++) {
					generateCheksum(report, child[i].getAbsolutePath());
				}
			} else {
				String fName = file.getAbsolutePath();
				if (fName.endsWith(App.EXT_ZIP)) {
					ChksumBean bean = new ChksumBean();
					bean.setZipUrl(fName);
					bean.setHashcode(getFileChecksum(fName));
					bean.setSize(String.valueOf(file.length() / 1024));
					report.add(bean);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param fileList
	 * @param fileName
	 */
	public static void detectJsonFiles(final List<String> fileList, final String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] child = file.listFiles();
				for (int i = 0; i < child.length; i++) {
					detectJsonFiles(fileList, child[i].getAbsolutePath());
				}
			} else {
				String fName = file.getAbsolutePath();
				if (fName.endsWith(App.EXT_TXT)) {
					fileList.add(fName);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param fileName
	 * @param folderName
	 * @return
	 */
	public static boolean detectZipFile(final String fileName, final String folderName, final List<String> localZipList) {
		boolean find = false;
		File folder = new File(folderName);
		if (folder.exists()) {
			File[] files = folder.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					detectZipFile(fileName, f.getAbsolutePath(), localZipList);
				} else {
					if (f.getName().equals(fileName)) {
						find = true;
						localZipList.add(f.getAbsolutePath());
						break;
					}
				}
			}
		}
		return find;
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public static void generateBackupFile(final String fileName) throws IOException {
		File backFile = new File(getBackupFileName(fileName));
		if (backFile.exists()) {
			backFile.delete();
		}
		
		File curFile = new File(fileName);
		curFile.renameTo(backFile);
	}
	
	/**
	 * 
	 * @param fileName
	 */
	private static void createNewEmptyFile(final String fileName) throws IOException {
		File curFile = new File(fileName);
		curFile.createNewFile();
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public static void replaceJsonFile(final String fileName, final List<String> zipList) throws Exception{
		createNewEmptyFile(fileName);
		
		String line = null;
		String temp = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getBackupFileName(fileName)), App.UTF8));
		BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), App.UTF8));
		try {
			while((line = reader.readLine()) != null) {
				boolean findSizeCfg = false;
				if (line.indexOf(App.ZIPURL) >= 0) {
					temp = line;
					writer.write(line + "\n");
					while ((line = reader.readLine()).indexOf(App.HASHCODE) < 0) {
						if ((line = reader.readLine()).indexOf(App.FILESIZE) < 0) {
							writer.write(line + "\n");
						} else {
							findSizeCfg = true;
							writeFileSize(writer, temp, zipList, line.endsWith(","), 
									line.substring(0, line.indexOf("\"")));
						}
					}
					writeHashcode(writer, temp, zipList, line.endsWith(","), 
							line.substring(0, line.indexOf("\"")));
				} else if (line.indexOf(App.HASHCODE) >= 0) {
					String sizeIndent = null;
					String hashIndent = line.substring(0, line.indexOf("\""));
					boolean sizeComma = false;
					boolean hashComma = line.endsWith(",");
					while ((line = reader.readLine()).indexOf(App.ZIPURL) < 0) {
						if ((line = reader.readLine()).indexOf(App.FILESIZE) < 0) {
							writer.write(line + "\n");
						} else {
							findSizeCfg = true;
							sizeComma = line.endsWith(",");
							sizeIndent = line.substring(0, line.indexOf("\""));
						}
					}
					temp = line;
					writer.write(line + "\n");
					writeHashcode(writer, line, zipList, hashComma, hashIndent);
					if (findSizeCfg) {
						writeFileSize(writer, line, zipList, sizeComma, sizeIndent);
					}
				} else {
					if (line.indexOf(App.FILESIZE) < 0) {
						writer.write(line + "\n");
					} else {
						if (!findSizeCfg) {
							writeFileSize(writer, temp, zipList, line.endsWith(","), 
									line.substring(0, line.indexOf("\"")));
						}
					}
				}
			}
			writer.flush();
		} finally {
			reader.close();
			writer.close();
		}
	}
	
	/**
	 * 
	 * @param writer
	 * @param zipUrlLine
	 * @param zipList
	 * @param withComma
	 * @param indent
	 * @throws Exception
	 */
	private static void writeHashcode(final BufferedWriter writer, final String zipUrlLine, 
			final List<String> zipList, final boolean withComma, final String indent) throws Exception {
		StringBuffer msgBuffer = new StringBuffer();
		String zipName = getZipFileNameFromLine(zipUrlLine);
		for (String fName : zipList) {
			if (fName.contains(zipName)) {
				String chksum = getFileChecksum(fName);
				msgBuffer.append(indent).append(App.HASHCODE).append(":\"").append(chksum);
				if(withComma) {
					msgBuffer.append("\",\n");
				} else {
					msgBuffer.append("\"\n");
				}
				writer.write(msgBuffer.toString());
			}
		}
	}
	
	/**
	 * 
	 * @param line
	 * @return
	 */
	private static String getZipFileNameFromLine(final String line) {
		Pattern zipNamePattern = Pattern.compile(App.REG_ZIP_URL, Pattern.CASE_INSENSITIVE);
		Matcher zipNameMatcher = zipNamePattern.matcher("");
		zipNameMatcher.reset(line);
		
		String zipName = null;
		if (zipNameMatcher.find()) {
			zipName = zipNameMatcher.group(1);
			zipName = zipName.substring(zipName.lastIndexOf("/") + 1);
		}
		return zipName;
	}
	
	/**
	 * 
	 * @param writer
	 * @param zipUrlLine
	 * @param zipList
	 * @param withComma
	 * @param indent
	 * @throws Exception
	 */
	private static void writeFileSize(final BufferedWriter writer, final String zipUrlLine, 
			final List<String> zipList, final boolean withComma, final String indent) throws Exception  {
		StringBuffer msgBuffer = new StringBuffer();
		String zipName = getZipFileNameFromLine(zipUrlLine);
		for (String fName : zipList) {
			if (fName.contains(zipName)) {
				File file = new File(fName);
				msgBuffer.append(indent).append(App.FILESIZE).append(":").append((file.length() / 1024));
				if(withComma) {
					msgBuffer.append("\",\n");
				} else {
					msgBuffer.append("\"\n");
				}
				writer.write(msgBuffer.toString());
			}
		}
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getBackupFileName(final String fileName) {
		return fileName + ".bak";
	}
	
	/**
	 * 
	 * @param file
	 */
	public static void deleteFiles(final File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				deleteFiles(f);
			}
		} else {
			file.delete();
		}
	}
}

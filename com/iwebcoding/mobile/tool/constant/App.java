package com.iwebcoding.mobile.tool.constant;

public interface App {
	int HTTP_STATUS_SUCCESS = 200;
	String NLS_BASE = "i18n/";
	String DATE_SHORT = "yyyy";
	String ZIPURL = "\"zipurl\"";
	String HASHCODE = "\"hashcode\"";
	String FILESIZE = "\"size\"";
	String UPLOAD_GIF = "/upload.gif";
	String DEPLOY_URI = "/P2GTheme/mobile/";
	String BASIC = "Basic ";
	String UTF8 = "UTF-8";
	String IPAD = "iPad";
	
	String NET_MODE_PROXY = "Proxy";
	String NET_MODE_DIRECT = "Direct";
	
	String ALGORITHM_MD5 = "MD5";
	String ALGORITHM_SHA512 = "SHA-512";
	
	String PARAM_AUTH = "Authorization";
	String PARAM_PATH = "path";
	String PARAM_FILE = "theFile";
	
	String PROTOCOL_HTTP = "http://";
	String PROTOCOL_HTTPS = "https://";
	
	String AUTH_USERNAME = "admin";
	String AUTH_PASSWORD = "1236E9D89C03E9C7C6765873C6D42B6E";
	
	String EXT_ZIP = ".zip";
	String EXT_TXT = ".txt";
	
	String CFG_PATH_LOGGER = "logger.properties";
	String CFG_PATH_APP = "config/appConfig.properties";
	
	String KEY_APPCFG_SYSCFG = "sysConfig";
	String KEY_APPCFG_ENTCFG = "entConfig";
	String KEY_APPCFG_DUMMY = "dummyFile";
	String KEY_APPCFG_MAGIC = "magic";
	String KEY_APPCFG_DEPLOY = "deployConfig";
	String KEY_APPCFG_DEPLOY_ROOT = "deployRoot";
	String KEY_APPCFG_DEPLOY_RUI = "deployURI";
	String KEY_APPCFG_DISHTTPS = "disableHttpsCheck";
	String KEY_APPCFG_SSL_ALGORITHM = "sslAlgorithm";
	String KEY_APPCFG_USERDUIDE = "userGuide";
	
	String KEY_SYSCFG_LOCALE = "Locale";
	String KEY_SYSCFG_FONT = "sysFont";
	String KEY_SYSCFG_LOOKANDFEEL = "lookAndFeel";
	String KEY_SYSCFG_NETMODE = "netMode";
	String KEY_SYSCFG_PROXY_SERVER = "Proxy_Server";
	String KEY_SYSCFG_PROXY_PORT = "Proxy_SvPort";
	String KEY_SYSCFG_DOWNLOAD_DIR = "download";
	String KEY_SYSCFG_CLEAR = "clearDownload";
	
	String LNF_WEB = "WebLookAndFeel";
	String LNF_METAL = "MetalLookAndFeel";
	String LNF_SYSTEM = "SystemLookAndFeel";
	
	String LOCALE_EN = "en";
	String LOCALE_SC = "zh_CN";
	String LOCALE_TC = "zh_TW";
	
	String LANG_EN = "en";
	String LANG_ZH = "zh";
	
	String COUNTRY_US = "US";
	String COUNTRY_CN = "CN";
	String COUNTRY_TW = "TW";
	
	String FONT_SANS_SERIF = "SansSerif";
	String FONT_SERIF = "Serif";
	String FONT_LISHU = "\u96B6\u4E66";
	
	String XPATH_ENTNAME = "/entityList/entity/localised[@locale='en']/@name";
	String XPATH_ENTURL = "/entityList/entity/configurl/text()";
	String XPATH_ENTURL_IPAD = "/entityList/entity/supportedDevices/device[@name='iPad']/configurl/text()";
	
	String REG_GLOBAL_TEMPLATE_URL = "\"globaltemplateurl\"\\s*:\\s*\\[([^\\]]+)";
	String REG_SUB_JSON = "\"url\"\\s*:\\s*\"([^\"]+)";
	String REG_ZIP_URL = "\"zipurl\"\\s*:\\s*\"([^\"]+)";
	String REG_HASHCODE = "\"hashcode\"\\s*:\\s*\"([^\"]+)";
}

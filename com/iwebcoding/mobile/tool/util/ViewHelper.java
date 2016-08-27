package com.iwebcoding.mobile.tool.util;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.filechooser.WebFileChooser;
import com.iwebcoding.mobile.tool.constant.App;

public class ViewHelper {
	/**
	 * 
	 * @return
	 */
	public static String getLookAndFeelClassName() {
		String lnfClassName = UIManager.getSystemLookAndFeelClassName();
		
		String cfgLnf = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_LOOKANDFEEL);
		if (App.LNF_WEB.equals(cfgLnf)) {
			lnfClassName = WebLookAndFeel.class.getCanonicalName();
		} else if (App.LNF_METAL.equals(cfgLnf)) {
			lnfClassName = MetalLookAndFeel.class.getCanonicalName();
		} else if (App.LNF_SYSTEM.equals(cfgLnf)) {
			lnfClassName = UIManager.getSystemLookAndFeelClassName();
		} else {
			lnfClassName = UIManager.getSystemLookAndFeelClassName();
		}
		
		return lnfClassName;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getSysFontConfig() {
		String sysFont = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_FONT);
		
		if (App.FONT_SANS_SERIF.equals(sysFont)) {
			sysFont = App.FONT_SANS_SERIF;
		} else if (App.FONT_SERIF.equals(sysFont)) {
			sysFont = App.FONT_SERIF;
		} else if (App.FONT_LISHU.equals(sysFont)) {
			sysFont = App.FONT_LISHU;
		} else {
			sysFont = App.FONT_SANS_SERIF;
		}
		
		return sysFont;
	}
	
	/**
	 * 
	 * @return
	 */
	public static Locale getSysCfgLocale() {
		Locale defLocale = new Locale(App.LANG_EN, App.COUNTRY_US);
		
		String cfgLocale = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_LOCALE);
		if (App.LOCALE_EN.equals(cfgLocale)) {
			defLocale = new Locale(App.LANG_EN, App.COUNTRY_US);
		} else if (App.LOCALE_SC.equals(cfgLocale)) {
			defLocale = new Locale(App.LANG_ZH, App.COUNTRY_CN);
		} else if (App.LOCALE_TC.equals(cfgLocale)) {
			defLocale = new Locale(App.LANG_ZH, App.COUNTRY_TW);
		}
		
		return defLocale;
	}
	
	/**
	 * 
	 * @return
	 */
	public static JFileChooser getFileChooser() {
		JFileChooser fileChooser = null;
		
		String sysLookAndFeel = SysCfgUtil.getSystemConfig(App.KEY_SYSCFG_LOOKANDFEEL);
		if (App.LNF_WEB.equals(sysLookAndFeel)) {
			fileChooser = new WebFileChooser();
		} else {
			fileChooser = new JFileChooser();
		}
		
		return fileChooser;
	}
	
	/**
	 * 
	 * @param content
	 */
	public static void copyToClipboard(final String content) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
				new StringSelection(content), null);
	}
	
	/**
	 * 
	 * @param interval
	 */
	public static void delay(long interval) {
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			LogUtil.logError(ViewHelper.class.getName(), "delay(long interval)", e.getMessage(), e);
		}
	}
}

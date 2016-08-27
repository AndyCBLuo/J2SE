package com.iwebcoding.mobile.tool.controller;

import com.iwebcoding.mobile.tool.framework.AbstractPageController;
import com.iwebcoding.mobile.tool.framework.PageView;
import com.iwebcoding.mobile.tool.view.ChecksumPage;

public class ChecksumController extends AbstractPageController {

	@Override
	public String getName() {
		return ChecksumController.class.getName();
	}

	@Override
	protected PageView createPageView() {
		return new ChecksumPage();
	}

}

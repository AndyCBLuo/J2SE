package com.iwebcoding.mobile.tool.controller;

import com.iwebcoding.mobile.tool.framework.AbstractPageController;
import com.iwebcoding.mobile.tool.framework.PageView;
import com.iwebcoding.mobile.tool.view.JsonViewerPage;

public class JsonViewerController extends AbstractPageController {

	@Override
	public String getName() {
		return JsonViewerController.class.getName();
	}

	@Override
	protected PageView createPageView() {
		return new JsonViewerPage();
	}

}

package com.iwebcoding.mobile.tool.controller;

import com.iwebcoding.mobile.tool.framework.AbstractPageController;
import com.iwebcoding.mobile.tool.framework.PageView;
import com.iwebcoding.mobile.tool.view.DashBoardPage;

public class DashBoardController extends AbstractPageController {

	@Override
	public String getName() {
		return DashBoardController.class.getName();
	}

	@Override
	protected PageView createPageView() {
		return new DashBoardPage();
	}

}

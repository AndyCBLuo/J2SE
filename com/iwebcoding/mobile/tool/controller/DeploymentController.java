package com.iwebcoding.mobile.tool.controller;

import com.iwebcoding.mobile.tool.framework.AbstractDataPageController;
import com.iwebcoding.mobile.tool.framework.DataPageView;
import com.iwebcoding.mobile.tool.service.AbstractService;
import com.iwebcoding.mobile.tool.service.impl.EntitySrvImpl;
import com.iwebcoding.mobile.tool.view.DeploymentPage;

public class DeploymentController extends AbstractDataPageController {

	@Override
	public String getName() {
		return DeploymentController.class.getName();
	}

	@Override
	protected AbstractService createService() {
		return new EntitySrvImpl();
	}

	@Override
	protected DataPageView createPageView() {
		return new DeploymentPage();
	}

}

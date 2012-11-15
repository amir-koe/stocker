package com.stocker.web.pages;

import javax.inject.Inject;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.services.Request;

import com.stocker.web.components.Layout;

@Import(stylesheet = { "../css/unicorn.login.css",
		"../css/bootstrap-responsive.min.css", "../css/bootstrap.min.css" }, library = {
		"../js/jquery.min.js", "../js/unicorn.login.js" })
public class Login {
	
	@Component
	private Layout layout;

	@Inject
	private Request request;

	private boolean showError;

	@OnEvent(EventConstants.ACTIVATE)
	void loginError(String context) {
		if ("error".equals(context)) {
			showError = true;
		}
	}

	public boolean getShowError() {
		return showError;
	}

	public String getContextPath() {
		return request.getContextPath();
	}
}

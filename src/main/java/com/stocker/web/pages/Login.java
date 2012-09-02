package com.stocker.web.pages;

import javax.inject.Inject;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.services.Request;

public class Login
{
    @Inject
    private Request request;

    private boolean showError;

    @OnEvent(EventConstants.ACTIVATE)
    void loginError(String context)
    {
        if("error".equals(context))
        {
            showError = true;
        }
    }

    public boolean getShowError()
    {
        return showError;
    }

    public String getContextPath()
    {
        return request.getContextPath();
    }
}

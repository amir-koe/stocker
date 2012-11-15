package com.stocker.web.components;

import java.util.Locale;

import javax.inject.Inject;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;

@Import(stylesheet = { "../css/bootstrap.css",
		"../css/bootstrap.min.css",
		"../css/bootstrap-responsive.min.css", 
		"../css/fullcalendar.css",
		"../css/unicorn.main.css",
		"../css/unicorn.grey.css" }, 
		library = { 
		"../js/bootstrap.js",
		"../js/excanvas.min.js", 
		"../js/jquery.min.js",
		"../js/jquery.ui.custom.js", 
		"../js/bootstrap.min.js",
		"../js/jquery.flot.min.js", 
		"../js/jquery.flot.resize.min.js",
		"../js/jquery.peity.min.js", 
		"../js/fullcalendar.min.js",
		"../js/unicorn.js", 
		"../js/unicorn.dashboard.js" })

public class Layout
{
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Inject
    private Locale locale;

    public String getLanguage()
    {
        return locale.getLanguage();
    }

    public String getTitle()
    {
        return title;
    }
}

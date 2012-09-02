package com.stocker.web.components;

import java.util.Locale;

import javax.inject.Inject;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;

@Import(stylesheet = {"../css/bootstrap.css", "../css/bootstrap-responsive.css"},
    library = {"../js/bootstrap.js"})
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

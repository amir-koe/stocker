package com.stocker.web.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;

public class DevelopmentModule
{
    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "de,en");
        configuration.add(SymbolConstants.FILE_CHECK_INTERVAL, "5 s");
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    }
}

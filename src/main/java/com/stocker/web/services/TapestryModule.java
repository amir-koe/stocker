package com.stocker.web.services;

import org.apache.tapestry5.ioc.Configuration;

/**
 * Tapestry IoC configuration.
 */
public class TapestryModule
{
    /**
     * Adds paths to be ignored by Tapestry.
     * 
     * @param configuration .
     */
    public static void contributeIgnoredPathsFilter(Configuration<String> configuration)
    {
        configuration.add("/users.*");
    }
}

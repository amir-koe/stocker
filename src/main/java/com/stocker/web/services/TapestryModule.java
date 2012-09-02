package com.stocker.web.services;

import org.apache.tapestry5.ioc.Configuration;

public class TapestryModule
{
    public static void contributeIgnoredPathsFilter(Configuration<String> configuration)
    {
      configuration.add("/users.*");
    }
}

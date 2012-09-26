package com.stocker;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * Loads configuration properties files from WEB-INF/classes/{environment}/stocker.properties. The environment may be
 * specified by a Java system property (-Denvironment=XXX).
 */
public class StockerApplicationContextInitializer implements
    ApplicationContextInitializer<ConfigurableWebApplicationContext>
{
    private static final Logger LOG = LoggerFactory.getLogger(StockerApplicationContextInitializer.class);

    public void initialize(ConfigurableWebApplicationContext applicationContext)
    {
        CompositePropertySource propertySource = new CompositePropertySource("stocker");
        ResourceLoader resourceLoader = new PathMatchingResourcePatternResolver(ClassUtils.getDefaultClassLoader());

        String environment = System.getProperty("environment");
        if (StringUtils.isNotEmpty(environment))
        {
            Resource environmentSpecificResource =
                resourceLoader.getResource("classpath:environment/" + environment + "/stocker.properties");

            if (environmentSpecificResource.exists())
            {
                try
                {
                    propertySource.addPropertySource(new ResourcePropertySource(environmentSpecificResource));
                }
                catch (IOException e)
                {
                    String msg = "Could not load environment specific properties from resource "
                        + environmentSpecificResource;
                    LOG.error(msg);
                    LOG.info(msg, e);
                }
            }
        }

        Resource globalResource = resourceLoader.getResource("classpath:stocker.properties");

        try
        {
            propertySource.addPropertySource(new ResourcePropertySource(globalResource));
        }
        catch (IOException e)
        {
            String msg = "Could not load global properties from resource " + globalResource;
            LOG.error(msg);
            LOG.info(msg, e);
        }

        applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
    }
}

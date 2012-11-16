package com.stocker;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * Loads configuration properties files from WEB-INF/classes/{environment}/stocker.properties. The environment may be
 * specified by a Java system property (-Denvironment=xxx).
 */
public class StockerApplicationContextInitializer implements
    ApplicationContextInitializer<ConfigurableWebApplicationContext>
{
    private static final Logger LOG = LoggerFactory.getLogger(StockerApplicationContextInitializer.class);

    private final ResourceLoader resourceLoader = new PathMatchingResourcePatternResolver(
        ClassUtils.getDefaultClassLoader());

    public void initialize(ConfigurableWebApplicationContext applicationContext)
    {
        String environment = System.getProperty("environment");

        applicationContext.getEnvironment().getPropertySources().addFirst(createPropertySource(environment));

        configureLogging(environment);
    }

    private PropertySource<Object> createPropertySource(String environment)
    {
        CompositePropertySource propertySource = new CompositePropertySource("stocker");
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

        return propertySource;
    }

    private void configureLogging(String environment)
    {
        Resource environmentSpecificLogConfig =
            resourceLoader.getResource("classpath:environment/" + environment + "/logback.xml");

        if (environmentSpecificLogConfig.exists())
        {
            JoranConfigurator configurator = new JoranConfigurator();
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            try
            {
                LOG.info("Reinitializing logger with " + environmentSpecificLogConfig + " ...");
                InputStream is = environmentSpecificLogConfig.getInputStream();
                configurator.setContext(lc);
                lc.reset();
                configurator.doConfigure(is);
            }
            catch (JoranException e)
            {
                LOG.error("Error initializing logger from " + environmentSpecificLogConfig, e);
                configurator.recallSafeConfiguration();
            }
            catch (IOException e)
            {
                LOG.error("Error reading file " + environmentSpecificLogConfig, e);
            }
        }
    }
}

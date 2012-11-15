package com.stocker;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.stocker.model.auth.spring.TenantUser;

@Configuration
@ComponentScan(basePackageClasses = AppConfig.class)
@EnableTransactionManagement
@ImportResource({"classpath:com/stocker/security.xml", "classpath:com/stocker/data-jpa.xml"})
public class AppConfig
{
    @Inject
    private Environment env;

    @Bean
    public DataSource dataSource() throws PropertyVetoException
    {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(env.getProperty("db.jdbcUrl", "jdbc:h2:data/stocker"));
        dataSource.setDriverClass(env.getProperty("db.driverClass", "org.h2.Driver"));
        dataSource.setUser(env.getProperty("db.user", "sa"));
        dataSource.setPassword(env.getProperty("db.password", ""));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
    {
        return new JpaTransactionManager(emf);
    }

    @Bean
    @Qualifier("local")
    public LocalContainerEntityManagerFactoryBean localeEmf() throws PropertyVetoException
    {
        LocalContainerEntityManagerFactoryBean emfFactoryBean = new LocalContainerEntityManagerFactoryBean();

        emfFactoryBean.setDataSource(dataSource());
        emfFactoryBean.setPersistenceUnitName("stocker");
        emfFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        emfFactoryBean.setPackagesToScan("com.stocker.model");
        Properties jpaProperties = new Properties();
        jpaProperties.put("eclipselink.weaving", "false");
        emfFactoryBean.setJpaProperties(jpaProperties);

        return emfFactoryBean;
    }

    @Bean
    @Primary
    public EntityManagerFactory emf(@Qualifier("local") EntityManagerFactory delegate)
    {
        return new MultiTenantEntityManagerFactory(delegate);
    }

    @Bean
    EntityManager entityManager(EntityManagerFactory emf)
    {
        return emf.createEntityManager();
    }

    public static class MultiTenantEntityManagerFactory implements EntityManagerFactory
    {
        private EntityManagerFactory delegate;

        public MultiTenantEntityManagerFactory(EntityManagerFactory delegate)
        {
            super();
            this.delegate = delegate;
        }

        @Override
        public EntityManager createEntityManager()
        {
            return createEntityManager(new HashMap());
        }

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public EntityManager createEntityManager(Map map)
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() != null)
            {
                TenantUser user = (TenantUser) authentication.getPrincipal();
                map.put("eclipselink.tenant-id", user.getTenant());
            }

            return delegate.createEntityManager(map);
        }

        @Override
        public CriteriaBuilder getCriteriaBuilder()
        {
            return delegate.getCriteriaBuilder();
        }

        @Override
        public Metamodel getMetamodel()
        {
            return delegate.getMetamodel();
        }

        @Override
        public boolean isOpen()
        {
            return delegate.isOpen();
        }

        @Override
        public void close()
        {
            delegate.close();
        }

        @Override
        public Map<String, Object> getProperties()
        {
            return delegate.getProperties();
        }

        @Override
        public Cache getCache()
        {
            return delegate.getCache();
        }

        @Override
        public PersistenceUnitUtil getPersistenceUnitUtil()
        {
            return delegate.getPersistenceUnitUtil();
        }
    }

    JpaVendorAdapter jpaVendorAdapter()
    {
        EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.H2);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setShowSql(true);
        return jpaVendorAdapter;
    }
}

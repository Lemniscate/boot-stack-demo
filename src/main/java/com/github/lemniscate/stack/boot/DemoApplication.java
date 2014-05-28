package com.github.lemniscate.stack.boot;

import com.github.lemniscate.lib.tiered.annotation.EnableApiResources;
import com.github.lemniscate.stack.boot.config.TomcatConfig;
import org.springframework.boot.actuate.autoconfigure.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * A simple dummy example of how you might bootstrap the application.
 */
@Configuration
@ComponentScan
@EnableAsync
@EnableScheduling
@EnableEntityLinks
@EnableApiResources
@EnableAspectJAutoProxy
@EnableJpaRepositories// (repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
@EnableTransactionManagement
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude=ErrorMvcAutoConfiguration.class)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableSpringDataWebSupport
public class DemoApplication extends SpringBootServletInitializer {

    /**
     * Entry point for Servlet 3 initialization.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources( getClass() );
    }

    /**
     * Entry point for embedded / command line initialization.
     */
    public static void main(String[] args) {
        SpringApplicationBuilder sb = new SpringApplicationBuilder(
                DemoApplication.class,
                TomcatConfig.class // include for embedded tomcat...
        );
        sb.run(args);
    }
}

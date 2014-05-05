package com.github.lemniscate.stack.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

import java.net.URISyntaxException;
import java.net.UnknownHostException;

/**
 * Configuration settings for Embedded Tomcat instances.
 */
public class TomcatConfig {

    @Value("${httpPort:8080}")
    private int port;

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory(){
        return new TomcatEmbeddedServletContainerFactory(port);
    }

    @Bean
    public EmbeddedServletContainerCustomizer tomcatCustomizer() throws URISyntaxException, UnknownHostException {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if(container instanceof TomcatEmbeddedServletContainerFactory){
                    TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) container;
                    configureTomcat(containerFactory);
                }
            }
        };
    }

    private void configureTomcat(TomcatEmbeddedServletContainerFactory tomcatFactory) {

    }
}

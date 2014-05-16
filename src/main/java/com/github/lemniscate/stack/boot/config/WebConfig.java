package com.github.lemniscate.stack.boot.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableWebMvc
@ComponentScan
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// registry.addViewController("/").setViewName("home");
	}
//
//    // Add pass-through routes for static resource folders
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        ResourceHandlerRegistration pub = registry
//                .addResourceHandler("/static/**", "/static/mobile/**")
//                .addResourceLocations("/WEB-INF/client/public/", "/WEB-INF/mobile/");
//
//        // If we're not deployed, public the sources for dev and disable caches;
//        if( !envService.isDeployed() ){
//            pub.setCachePeriod(0);
//            registry
//                .addResourceHandler("/static/src/**")
//                .addResourceLocations("/WEB-INF/client/")
//                .setCachePeriod(0);
//        }
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addWebRequestInterceptor(profileInterceptor);
//        registry.addInterceptor(metricInterceptor);
//    }

    @Bean
    public MethodInterceptor ExposeInvocationInterceptor(){
        return ExposeInvocationInterceptor.INSTANCE;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    	SortHandlerMethodArgumentResolver sortResolver = new SortHandlerMethodArgumentResolver();
    	sortResolver.setSortParameter("_sort");
    	
        PageableHandlerMethodArgumentResolver pageResolver = new PageableHandlerMethodArgumentResolver(sortResolver);
        pageResolver.setFallbackPageable(new PageRequest(0, 20));
        pageResolver.setPrefix("_");
        
        resolvers.add(sortResolver);
        resolvers.add(pageResolver);


    }

    @Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean // Only used when running in embedded servlet
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        configurer.ignoreAcceptHeader(true);
    }

    @Override
    protected Map<String, MediaType> getDefaultMediaTypes() {
        return new HashMap<String, MediaType>(){{
            put("json", MediaType.APPLICATION_JSON);
        }};
    }

    // Add Jackson Support for returning JSON
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jackson());
    }

    private MappingJackson2HttpMessageConverter jackson() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();

        // deserialization features
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // serialization features
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        // formatting
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
        converter.setObjectMapper(mapper);
        converter.setPrettyPrint(true);
        return converter;
    }

}
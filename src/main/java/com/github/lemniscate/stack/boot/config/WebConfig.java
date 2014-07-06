package com.github.lemniscate.stack.boot.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.inject.Inject;
import javax.xml.transform.Source;
import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Inject
    private Environment environment;



//    // Add pass-through routes for static resource folders
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration publicResources = registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/static/**");


        ResourceHandlerRegistration compiledAppResources = registry
                .addResourceHandler("/ar/**")
                .addResourceLocations("/WEB-INF/client/public/**");

        // TODO only show when in dev.
        ResourceHandlerRegistration srcAppResources = registry
                .addResourceHandler("/src/**")
                .addResourceLocations("/WEB-INF/client/**")
                .setCachePeriod(0);
        // TODO only disable cache when in dev
        publicResources.setCachePeriod(0);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addWebRequestInterceptor(new AppEnvironmentInterceptor(servletContext, environment));
//        registry.addWebRequestInterceptor(profileInterceptor);
//        registry.addInterceptor(metricInterceptor);
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

//	@Bean // Only used when running in embedded servlet
//	public DispatcherServlet dispatcherServlet() {
//		return new DispatcherServlet();
//	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset(false);

        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(stringConverter);
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<Source>());
        converters.add(new AllEncompassingFormHttpMessageConverter());

        // Add Jackson Support for returning JSON
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
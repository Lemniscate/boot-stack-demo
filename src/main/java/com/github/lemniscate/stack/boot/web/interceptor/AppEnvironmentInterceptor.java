package com.github.lemniscate.stack.boot.web.interceptor;

import com.github.lemniscate.stack.boot.web.util.WebAppFileScanner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * A utility interceptor that prepares global information for all requests.
 *
 * @Author dave 2/23/14 3:37 PM
 */
public class AppEnvironmentInterceptor implements WebRequestInterceptor {

    public static final String PROFILE_DEPLOYED_KEY = "DEPLOYED";
    public static final String PROFILE_ACTIVE_KEY = "ACTIVE_PROFILES";
    public static final String DEV_JS_LIST_KEY = "JS_RESOURCES";
    public static final String DEV_CSS_LIST_KEY = "CSS_RESOURCES";


    private final String[] profiles;

    private final List<String> jsSources;
    private final List<String> cssSources;
    private final boolean deployed;


    // TODO DTW really? constructor logic? clean this up...
    @Inject
    public AppEnvironmentInterceptor(ServletContext ctx, Environment env) {
        profiles = env.getActiveProfiles();
        deployed = Arrays.asList(profiles).contains("deploy");

        if( Arrays.asList(profiles).contains("deploy")){
            jsSources = null;
            cssSources = null;
        }else{
            String path = "/WEB-INF/client/src/";
            try{
                WebAppFileScanner scanner = new WebAppFileScanner(ctx);
                jsSources = scanner.loadGulpConfigLibs();
                cssSources = scanner.loadGulpConfigCss();
            }catch(IOException e){
                throw new RuntimeException("Failed loading gulp resources", e);
            }
        }
    }

    @Override
    public void preHandle(WebRequest request) throws Exception {
        request.setAttribute(PROFILE_ACTIVE_KEY, profiles, RequestAttributes.SCOPE_GLOBAL_SESSION);
        request.setAttribute(PROFILE_DEPLOYED_KEY, deployed, RequestAttributes.SCOPE_GLOBAL_SESSION);
        request.setAttribute(DEV_JS_LIST_KEY, jsSources, RequestAttributes.SCOPE_GLOBAL_SESSION);
        request.setAttribute(DEV_CSS_LIST_KEY, cssSources, RequestAttributes.SCOPE_GLOBAL_SESSION);
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {}

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {}
}

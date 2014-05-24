package com.github.lemniscate.stack.boot.web.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for scanning filesystem resources in a deployed application. It's very hacky and evil.
 *
 * @Author dave 2/23/14 4:30 PM
 */
@RequiredArgsConstructor
public class WebAppFileScanner {

    private final ServletContext ctx;

    @Deprecated
    public List<String> findRelativeFilePaths(String path, String filter) throws IOException {
        List<String> result = new ArrayList<String>();
        ServletContextResource r = new ServletContextResource(ctx, path);
        Collection<File> files = FileUtils.listFiles(r.getFile(), new WildcardFileFilter(filter), TrueFileFilter.INSTANCE);
        for(File f : files){
            String relative = f.getPath().substring(f.getPath().indexOf(path) + path.length());
            result.add(relative);
        }
        return result;
    };

    public List<String> loadGulpConfigLibs() throws IOException {
        List<String> result = loadGulpConfigElement("libs");
        List<String> srcs = findRelativeFilePaths("/WEB-INF/client/src/", "*.js");
        for(String src : srcs){
            if( !src.startsWith("libs/") ){
                result.add( "src/" + src);
            }
        }
        return result;
    }

    public List<String> loadGulpConfigCss() throws IOException {
        List<String> result = loadGulpConfigElement("libsCss");
        List<String> srcs = findRelativeFilePaths("/WEB-INF/client/src/", "*.css");
        for(String src : srcs){
            if( !src.startsWith("libs/") ){
                result.add( "src/" + src);
            }
        }
        return result;
    }

    private List<String> loadGulpConfigElement(String element) throws IOException {
        List<String> result = new ArrayList<String>();
        ServletContextResource r = new ServletContextResource(ctx, "/WEB-INF/client/gulp-config.json");

        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(r.getFile());
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = parser.getCurrentName();
            if (element.equals(fieldname)) {
                parser.nextToken();
                // messages is array, loop until token equal to "]"
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    String lib = parser.getText();
                    result.add(lib);
                }
            }
        }
        return result;
    };

}

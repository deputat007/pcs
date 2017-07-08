package restservice.configurations;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SecurityConfig.class, WebConfig.class, DataSourceConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

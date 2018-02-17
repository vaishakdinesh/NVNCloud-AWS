package edu.neu.csye6225.cloud.configuration;

import edu.neu.csye6225.cloud.filter.XSSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ConfigMVC extends WebMvcConfigurerAdapter{

    @Autowired
    private Environment environment;

    //----Resource handler ---//
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/profilepic/**")
                .addResourceLocations("file://"+environment.getProperty("local.image.path"));

        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    //-----Spring filter to avoid xss attach-----//
    @Bean
    public FilterRegistrationBean xssFilterBean(){
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter(xssCleanFilter());
        frb.addUrlPatterns("/**");
        frb.setName("xssCleanFilter");
        frb.setOrder(1);
        return frb;
    }

    @Bean(name="xssCleanFilter")
    public XSSFilter xssCleanFilter(){
        return new XSSFilter();
    }

    //---------------------------------------------------------------------------------------//

}

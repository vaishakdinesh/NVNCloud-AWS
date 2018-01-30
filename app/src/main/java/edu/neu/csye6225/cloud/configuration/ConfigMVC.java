package edu.neu.csye6225.cloud.configuration;

import edu.neu.csye6225.cloud.filter.XSSFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ConfigMVC extends WebMvcConfigurerAdapter{

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

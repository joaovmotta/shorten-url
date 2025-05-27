package br.com.shorten_url.configs;

import br.com.shorten_url.infra.interceptors.RecaptchaInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final RecaptchaInterceptor recaptchaInterceptor;

    @Autowired
    public InterceptorConfig(RecaptchaInterceptor recaptchaInterceptor) {
        this.recaptchaInterceptor = recaptchaInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(recaptchaInterceptor)
                .addPathPatterns("/api/v1/shorten");
    }
}

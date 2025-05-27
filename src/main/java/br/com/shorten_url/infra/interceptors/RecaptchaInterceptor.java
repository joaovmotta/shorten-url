package br.com.shorten_url.infra.interceptors;

import br.com.shorten_url.services.CaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class RecaptchaInterceptor implements HandlerInterceptor {

    private final CaptchaService captchaService;

    public RecaptchaInterceptor(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        if (HttpMethod.POST.matches(request.getMethod())) {

            String recaptchaToken = request.getHeader("Recaptcha-Token");

            if (recaptchaToken == null || !captchaService.verifyRecaptcha(recaptchaToken)) {

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                return false;
            }
        }
        return true;
    }
}

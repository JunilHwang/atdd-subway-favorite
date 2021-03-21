package nextstep.subway.auth.ui.session;

import nextstep.subway.auth.domain.Authentication;
import nextstep.subway.auth.domain.AuthenticationToken;
import nextstep.subway.auth.exception.InvalidAuthenticationException;
import nextstep.subway.auth.infrastructure.SecurityContext;
import nextstep.subway.auth.ui.base.AuthenticationInterceptor;
import nextstep.subway.member.application.CustomUserDetailsService;
import nextstep.subway.member.domain.LoginMember;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static nextstep.subway.auth.infrastructure.SecurityContextHolder.SPRING_SECURITY_CONTEXT_KEY;

public class SessionAuthenticationInterceptorV2 extends AuthenticationInterceptor {
    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    public SessionAuthenticationInterceptorV2(CustomUserDetailsService userDetailsService) {
        super(userDetailsService);
    }

    @Override
    public void afterAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication == null) {
            throw new InvalidAuthenticationException();
        }

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(SPRING_SECURITY_CONTEXT_KEY, new SecurityContext(authentication));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public AuthenticationToken convert(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        String principal = paramMap.get(USERNAME_FIELD)[0];
        String credentials = paramMap.get(PASSWORD_FIELD)[0];

        return new AuthenticationToken(principal, credentials);
    }
}

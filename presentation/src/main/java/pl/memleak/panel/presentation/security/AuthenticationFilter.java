package pl.memleak.panel.presentation.security;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.memleak.panel.presentation.dto.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static Gson gson = new Gson();

    public AuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        LoginRequest loginRequest = this.getLoginRequest(request);

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        return this.getAuthenticationManager().authenticate(authRequest);

    }

    private LoginRequest getLoginRequest(HttpServletRequest request) {
        LoginRequest loginRequest;
        try (BufferedReader reader = request.getReader()) {
            loginRequest =  gson.fromJson(reader, LoginRequest.class);
        } catch (JsonSyntaxException ex) {
            throw new BadRequestAuthenticationException("Wrong json format", ex);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException("", ex);
        }

        if(loginRequest == null){
            throw new BadRequestAuthenticationException("Unable to parse login request json");
        }

        return loginRequest;
    }

}

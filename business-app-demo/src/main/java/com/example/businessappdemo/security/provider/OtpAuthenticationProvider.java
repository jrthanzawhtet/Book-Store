package com.example.businessappdemo.security.provider;

import com.example.businessappdemo.security.OtpAuthentication;
import com.example.businessappdemo.security.proxy.AuthenticationServerProxy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OtpAuthenticationProvider implements
        AuthenticationProvider {
    private final AuthenticationServerProxy proxy;

    public OtpAuthenticationProvider(AuthenticationServerProxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String code = String.valueOf(authentication.getCredentials());
        boolean result = proxy.sendOTP(username,code);
        if(result){
            return new OtpAuthentication(username,code);
        }
        else{
            throw new BadCredentialsException("Bad Credentials.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class
                .isAssignableFrom(authentication);
    }
}

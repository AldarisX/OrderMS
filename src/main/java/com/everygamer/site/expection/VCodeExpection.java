package com.everygamer.site.expection;

import org.springframework.security.core.AuthenticationException;

public class VCodeExpection extends AuthenticationException {
    public VCodeExpection(String msg) {
        super(msg);
    }
}

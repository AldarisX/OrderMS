package com.everygamer.site.expection;

import org.springframework.security.core.AuthenticationException;

public class SiteLoginExpection extends AuthenticationException {
    public SiteLoginExpection(String msg) {
        super(msg);
    }
}

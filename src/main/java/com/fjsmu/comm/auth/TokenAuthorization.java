package com.fjsmu.comm.auth;

import org.apache.log4j.Logger;

public class TokenAuthorization {

    static final Logger logger = Logger.getLogger( TokenAuthorization.class );

    public TokenAuthorization() {
    }

    public String generateBase64Token(String str1) {
        return org.jboss.resteasy.util.Base64.encodeBytes(str1.getBytes());
    }

}

package com.Beevago.App.utils;

import org.apache.tomcat.util.codec.binary.Base64;

public class JwtDecodeToken {

    @SuppressWarnings("deprecation")
    public static String getEmailByJwtToken(String jwtToken) {

        // DIVIDE ATÉ CHEGAR NO EMAIL:
        String[] chunks = jwtToken.split("\\.");
        String base64EncodedBody = chunks[1];        
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));        
        String[] bodyPiece = body.split(":");
        String[] email = bodyPiece[2].split(",");

        return email[0].replace("\"", "");
    }

}

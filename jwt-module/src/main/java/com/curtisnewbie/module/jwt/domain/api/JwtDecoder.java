package com.curtisnewbie.module.jwt.domain.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * <p>
 * Decoder of JWT
 * </p>
 *
 * @author yongjie.zhuang
 */
public interface JwtDecoder {

    /**
     * Decode JWT
     *
     * @throws JWTVerificationException if token is invalid
     */
    DecodedJWT decode(String jwt) throws JWTVerificationException;

}

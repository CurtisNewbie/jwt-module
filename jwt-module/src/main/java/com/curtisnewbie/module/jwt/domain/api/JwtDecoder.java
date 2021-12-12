package com.curtisnewbie.module.jwt.domain.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * Decoder of JWT
 * </p>
 *
 * @author yongjie.zhuang
 */
@Validated
public interface JwtDecoder {

    /**
     * Decode JWT
     *
     * @throws JWTVerificationException if token is invalid
     */
    DecodedJWT decode(@NotBlank String jwt) throws JWTVerificationException;

}

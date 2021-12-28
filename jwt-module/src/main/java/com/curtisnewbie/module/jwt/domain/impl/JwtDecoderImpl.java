package com.curtisnewbie.module.jwt.domain.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.curtisnewbie.module.jwt.config.JwtModuleConfig;
import com.curtisnewbie.module.jwt.domain.api.JwtDecoder;
import com.curtisnewbie.module.jwt.vo.DecodeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

/**
 * @author yongjie.zhuang
 */
@Component
public class JwtDecoderImpl implements JwtDecoder {

    @Autowired
    private JwtModuleConfig config;

    @Override
    public DecodeResult decode(@NotBlank String jwt) {
        try {

            return DecodeResult.builder()
                    .decodedJWT(config.getVerifier().verify(jwt))
                    .isValid(true)
                    .build();

        } catch (JWTVerificationException e) {

            return DecodeResult.builder()
                    .isValid(false)
                    .isExpired(e instanceof TokenExpiredException)
                    .build();
        }
    }
}

package com.curtisnewbie.module.jwt.domain.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.curtisnewbie.module.jwt.config.JwtModuleConfig;
import com.curtisnewbie.module.jwt.domain.api.JwtDecoder;
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
    public DecodedJWT decode(@NotBlank String jwt) throws JWTVerificationException {
        return config.getVerifier().verify(jwt);
    }
}

package com.curtisnewbie.module.jwt.domain.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.curtisnewbie.module.jwt.vo.DecodeResult;
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
     */
    DecodeResult decode(@NotBlank String jwt);

}

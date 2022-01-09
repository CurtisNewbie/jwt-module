package com.curtisnewbie.module.jwt.vo;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * @author yongjie.zhuang
 */
@Data
public class DecodeResult {

    /** decoded JWT */
    private final DecodedJWT decodedJWT;

    /** whether the token is valid */
    private final boolean isValid;

    /** whether the token is expired */
    private final boolean isExpired;

    @Nullable
    private final JWTVerificationException exception;

    @Builder
    public DecodeResult(DecodedJWT decodedJWT, boolean isValid, boolean isExpired, JWTVerificationException exception) {
        this.decodedJWT = decodedJWT;
        this.isValid = isValid;
        this.isExpired = isExpired;
        this.exception = exception;
    }
}

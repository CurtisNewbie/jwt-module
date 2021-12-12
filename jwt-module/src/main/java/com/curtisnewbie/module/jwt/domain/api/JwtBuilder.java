package com.curtisnewbie.module.jwt.domain.api;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * Builder of JWT
 * </p>
 *
 * @author yongjie.zhuang
 */
@Validated
public interface JwtBuilder {

    /**
     * Encode a JWT
     *
     * @param payload  payload object
     * @param expireAt when the JWT is expired
     * @return JWT in string
     */
    String encode(@NotNull Object payload, @NotNull LocalDateTime expireAt);

    /**
     * Encode a JWT
     *
     * @param claims   key-value pairs in payload
     * @param expireAt when the JWT is expired
     * @return JWT in string
     */
    String encode(@NotEmpty Map<String, String> claims, @NotNull LocalDateTime expireAt);

}

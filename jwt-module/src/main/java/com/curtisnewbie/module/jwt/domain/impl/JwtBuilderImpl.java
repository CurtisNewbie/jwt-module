package com.curtisnewbie.module.jwt.domain.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.curtisnewbie.common.util.JsonUtils;
import com.curtisnewbie.module.jwt.config.JwtModuleConfig;
import com.curtisnewbie.module.jwt.domain.api.JwtBuilder;
import com.curtisnewbie.module.jwt.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @author yongjie.zhuang
 */
@Component
public class JwtBuilderImpl implements JwtBuilder {

    private final ObjectMapper objectMapper = JsonUtils.constructsJsonMapper();

    @Autowired
    private JwtModuleConfig config;

    private String encode(Map<String, String> claims, Object payload, Date expireAt) {
        JWTCreator.Builder builder = JWT.create();
        // load custom claims
        claims.entrySet().forEach(e -> builder.withClaim(e.getKey(), e.getValue()));

        // store payload object in claim "json_payload"
        if (payload != null) {
            try {
                JwtUtil.writePayload(builder, payload);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Unable to serialize payload object", e);
            }
        }

        // special claims
        builder.withExpiresAt(expireAt);
        builder.withIssuer(config.getIssuer());

        // sign
        return builder.sign(config.getAlgorithm());
    }

    @Override
    public String encode(@NotEmpty Map<String, String> claims, @NotNull LocalDateTime expireAt) {
        return encode(claims, null, Date.from(expireAt.atZone(ZoneOffset.systemDefault()).toInstant()));
    }

    @Override
    public String encode(@NotNull Object payload, @NotNull LocalDateTime expireAt) {
        return encode(Collections.emptyMap(), payload, Date.from(expireAt.atZone(ZoneOffset.systemDefault()).toInstant()));
    }
}

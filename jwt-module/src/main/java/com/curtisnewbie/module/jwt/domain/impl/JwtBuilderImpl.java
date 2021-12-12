package com.curtisnewbie.module.jwt.domain.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.curtisnewbie.common.util.JsonUtils;
import com.curtisnewbie.module.jwt.config.JwtModuleConfig;
import com.curtisnewbie.module.jwt.domain.api.JwtBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yongjie.zhuang
 */
@Component
public class JwtBuilderImpl implements JwtBuilder {

    private final ObjectMapper objectMapper = JsonUtils.constructsJsonMapper();

    @Autowired
    private JwtModuleConfig config;

    private String encode(@NotEmpty Map<String, String> claims, @NotNull Date expireAt) {
        JWTCreator.Builder builder = JWT.create();
        claims.entrySet().forEach(e -> builder.withClaim(e.getKey(), e.getValue()));
        builder.withExpiresAt(expireAt);
        builder.withIssuer(config.getIssuer());
        return builder.sign(config.getAlgorithm());
    }

    private String encode(@NotNull Object payload, @NotNull Date expireAt) {
        Map<?, ?> map = objectMapper.convertValue(payload, Map.class);
        Map<String, String> claims = new HashMap<>(map.size());
        map.forEach((k, v) -> claims.put(k.toString(), v.toString()));
        return encode(claims, expireAt);
    }

    @Override
    public String encode(@NotNull Object payload, @NotNull LocalDateTime expireAt) {
        return encode(payload, Date.from(expireAt.atZone(ZoneOffset.systemDefault()).toInstant()));
    }

    @Override
    public String encode(@NotEmpty Map<String, String> claims, @NotNull LocalDateTime expireAt) {
        return encode(claims, Date.from(expireAt.atZone(ZoneOffset.systemDefault()).toInstant()));
    }
}

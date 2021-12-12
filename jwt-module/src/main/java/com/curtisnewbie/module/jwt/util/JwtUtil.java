package com.curtisnewbie.module.jwt.util;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.curtisnewbie.common.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author yongjie.zhuang
 */
public final class JwtUtil {

    private static final String PAYLOAD_CLAIM_NAME = "json_payload";

    private JwtUtil() {
    }

    public static <T> T extractPayload(DecodedJWT jwt, Class<T> clazz) throws JsonProcessingException {
        return JsonUtils.readValueAsObject(jwt.getClaim(PAYLOAD_CLAIM_NAME).asString(), clazz);
    }

    public static void writePayload(JWTCreator.Builder builder, Object o) throws JsonProcessingException {
        builder.withClaim(PAYLOAD_CLAIM_NAME, JsonUtils.writeValueAsString(o));
    }
}

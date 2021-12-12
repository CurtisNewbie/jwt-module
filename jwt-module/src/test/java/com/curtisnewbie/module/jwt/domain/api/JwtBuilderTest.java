package com.curtisnewbie.module.jwt.domain.api;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.curtisnewbie.module.jwt.TestBootstrapHelper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author yongjie.zhuang
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestBootstrapHelper.class)
public class JwtBuilderTest {

    private static final String NAME = "curtis";
    private static final int AGE = 100;

    @Autowired
    private JwtBuilder jwtBuilder;
    @Autowired
    private JwtDecoder jwtDecoder;

    @Test
    public void should_generate_and_parse_jwt() {
        Dummy dummy = Dummy.builder()
                .name(NAME)
                .age(AGE)
                .build();

        String jwt = jwtBuilder.encode(dummy, LocalDateTime.now().plusDays(1));
        Assertions.assertTrue(StringUtils.hasText(jwt), "jwt is empty");
        log.info("jwt: {}", jwt);

        DecodedJWT decoded = jwtDecoder.decode(jwt);
        Assertions.assertNotNull(decoded, "DecodedJWT == null");
        log.info("decoded: {}", decoded);
        log.info("claims: {}", decoded.getClaims());
    }

    @Test
    public void should_detect_jwt_expiration() {
        Dummy dummy = Dummy.builder()
                .name(NAME)
                .age(AGE)
                .build();

        // token expired already
        LocalDateTime tomorrow = LocalDateTime.now().minusDays(1);

        String jwt = jwtBuilder.encode(dummy, tomorrow);
        Assertions.assertTrue(StringUtils.hasText(jwt), "jwt is empty");
        log.info("jwt: {}", jwt);

        Assertions.assertThrows(TokenExpiredException.class, () -> jwtDecoder.decode(jwt), "Failed to validate 'exp' claim");
    }

    @Builder
    @Data
    private static class Dummy {
        private String name;
        private int age;
    }
}


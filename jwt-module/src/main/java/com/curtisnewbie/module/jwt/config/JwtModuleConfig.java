package com.curtisnewbie.module.jwt.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * <p>
 * Configuration of JWT Module
 * </p>
 * <p>
 * This module always use RSA256 algorithm
 * </p>
 *
 * @author yongjie.zhuang
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt-module")
public class JwtModuleConfig implements InitializingBean {

    /** issuer */
    private String issuer = "nil";

    /** Private key used to sign JWT */
    private String privateKey;

    /** Public key used to verify JWT */
    private String publicKey;

    /** Algorithm with keys loaded for JWT encoding, decoding and verification */
    private Algorithm algorithm = null;

    /** Verifier of tokens */
    private JWTVerifier verifier = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.isTrue(StringUtils.hasText(privateKey), "public key is null or empty");
        Assert.isTrue(StringUtils.hasText(publicKey), "secret key is null or empty");

        // build Algorithm object
        algorithm = buildRsaAlgorithm(publicKey, privateKey);
        // build JWTVerifier object
        verifier = buildVerifier(algorithm, issuer);

        Assert.notNull(algorithm, "Failed to construct Algorithm for JWT, algorithm == null");
        Assert.notNull(verifier, "verifier == null");
    }

    // ----------------------------------------- private helper methods

    /** Build RSA based Algorithm */
    private static final Algorithm buildRsaAlgorithm(final String encodedPubKey, final String encodedPriKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        final byte[] pubKeyBytes = Base64.getDecoder().decode(encodedPubKey);
        final byte[] priKeyBytes = Base64.getDecoder().decode(encodedPriKey);
        KeyFactory rsaKeyFac = KeyFactory.getInstance("RSA");

        RSAPublicKey pubKey = (RSAPublicKey) rsaKeyFac.generatePublic(new X509EncodedKeySpec(pubKeyBytes));
        RSAPrivateKey priKey = (RSAPrivateKey) rsaKeyFac.generatePrivate(new PKCS8EncodedKeySpec(priKeyBytes));

        return Algorithm.RSA256(pubKey, priKey);
    }

    /** Builder JWTVerifier */
    private static final JWTVerifier buildVerifier(Algorithm algorithm, String issuer) {
        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

}

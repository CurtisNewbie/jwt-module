package com.curtisnewbie.module.jwt.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
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
@ConfigurationProperties("jwt-module")
public class JwtModuleConfig implements InitializingBean {

    /** issuer */
    private String issuer = "nil";

    /** Private key used to sign JWT */
    private String privateKey;

    /** Public key used to verify JWT */
    private String publicKey;

    /** Algorithm with keys loaded for JWT encoding, decoding and verification */
    private Algorithm algorithm;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.isTrue(StringUtils.hasText(privateKey), "public key is null or empty");
        Assert.isTrue(StringUtils.hasText(publicKey), "secret key is null or empty");

        // build Algorithm object
        algorithm = buildRsaAlgorithm(publicKey, privateKey);
    }

    // ----------------------------------------- private helper methods

    /** Build RSA based Algorithm */
    private static final Algorithm buildRsaAlgorithm(final String encodedPubKey, final String encodedPriKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        final byte[] pubKeyBytes = Base64.getDecoder().decode(encodedPubKey);
        final byte[] priKeyBytes = Base64.getDecoder().decode(encodedPriKey);
        KeyFactory rsaKeyFac = KeyFactory.getInstance("RSA");

        PublicKey pubKey = rsaKeyFac.generatePublic(new X509EncodedKeySpec(pubKeyBytes));
        PrivateKey priKey = rsaKeyFac.generatePrivate(new X509EncodedKeySpec(priKeyBytes));

        return Algorithm.RSA256((RSAPublicKey) priKey, (RSAPrivateKey) pubKey);
    }

}
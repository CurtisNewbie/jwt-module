package com.curtisnewbie.module.jwt.config;

import com.curtisnewbie.module.jwt.TestBootstrapHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yongjie.zhuang
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestBootstrapHelper.class)
@Slf4j
public class JwtModuleConfigTest {

    @Autowired
    private JwtModuleConfig config;

    @Test
    public void should_build_algorithm() {
        Assertions.assertNotNull(config.getAlgorithm(), "algorithm == null");
    }

    @Test
    public void should_build_verifier() {
        Assertions.assertNotNull(config.getVerifier(), "verifier == null");
    }


}

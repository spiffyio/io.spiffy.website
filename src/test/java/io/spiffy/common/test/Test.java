package io.spiffy.common.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class Test {

    protected static final String UNIT_TEST_CONFIG = "classpath:/spring/unit-testing.xml";
    protected static final String FUNCTIONAL_TEST_CONFIG = "classpath:/spring/functional-testing.xml";
}

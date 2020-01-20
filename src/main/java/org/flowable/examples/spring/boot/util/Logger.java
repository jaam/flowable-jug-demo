package org.flowable.examples.spring.boot.util;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Sample class to output to console
 *
 * @author Jose Antonio Alvarez
 */
@Component
public class Logger {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Logger.class);

    public void info(String text) {
        LOG.info(text);
    }

}

package org.flowable.examples.spring.boot.demo;

import org.springframework.stereotype.Component;

/**
 * Sample class to output to console
 */
@Component
public class Logger {

    public void info(String text) {
        System.out.println(text);
    }

}

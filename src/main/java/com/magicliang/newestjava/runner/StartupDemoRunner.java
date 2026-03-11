package com.magicliang.newestjava.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.magicliang.newestjava.feature.Java14to17Features;
import com.magicliang.newestjava.feature.Java21Features;
import com.magicliang.newestjava.feature.Java25Features;
import com.magicliang.newestjava.feature.Java9to11Features;

@Component
public class StartupDemoRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupDemoRunner.class);

    @Override
    public void run(String... args) {
        log.info("\n" + "=".repeat(70));
        log.info("  Java {} New Features Showcase", System.getProperty("java.version"));
        log.info("  Running on: {} ({})", System.getProperty("java.vm.name"), System.getProperty("java.vendor"));
        log.info("=".repeat(70));

        log.info("\n{}", Java9to11Features.runAll());
        log.info("\n{}", Java14to17Features.runAll());
        log.info("\n{}", Java21Features.runAll());
        log.info("\n{}", Java25Features.runAll());

        log.info("\n" + "=".repeat(70));
        log.info("  All demos completed! Visit http://localhost:8080/api/features/all");
        log.info("=".repeat(70));
    }
}

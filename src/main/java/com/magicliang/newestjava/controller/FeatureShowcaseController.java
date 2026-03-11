package com.magicliang.newestjava.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicliang.newestjava.feature.Java14to17Features;
import com.magicliang.newestjava.feature.Java21Features;
import com.magicliang.newestjava.feature.Java25Features;
import com.magicliang.newestjava.feature.Java9to11Features;

@RestController
@RequestMapping("/api/features")
public class FeatureShowcaseController {

    @GetMapping("/all")
    public Map<String, String> showAllFeatures() {
        return Map.of(
                "java9to11", Java9to11Features.runAll(),
                "java14to17", Java14to17Features.runAll(),
                "java21", Java21Features.runAll(),
                "java25", Java25Features.runAll()
        );
    }

    @GetMapping("/java9-11")
    public String java9to11() {
        return Java9to11Features.runAll();
    }

    @GetMapping("/java14-17")
    public String java14to17() {
        return Java14to17Features.runAll();
    }

    @GetMapping("/java21")
    public String java21() {
        return Java21Features.runAll();
    }

    @GetMapping("/java25")
    public String java25() {
        return Java25Features.runAll();
    }

    @GetMapping("/info")
    public Map<String, String> javaInfo() {
        return Map.of(
                "java.version", System.getProperty("java.version"),
                "java.vendor", System.getProperty("java.vendor"),
                "java.vm.name", System.getProperty("java.vm.name"),
                "os.name", System.getProperty("os.name"),
                "os.arch", System.getProperty("os.arch"),
                "virtual.threads.enabled", String.valueOf(Thread.currentThread().isVirtual())
        );
    }
}

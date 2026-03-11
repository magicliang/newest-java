package com.magicliang.newestjava.feature;

import java.util.List;
import java.util.stream.Gatherers;

/**
 * 展示 Java 25 LTS 的新特性（在 Java 25 中正式定稿的功能）
 */
public class Java25Features {

    /**
     * Java 25: Scoped Values (JEP 506)
     * ScopedValue 是 ThreadLocal 的现代替代品，具有不可变性和结构化生命周期。
     * 适用于在调用链中传递上下文，如请求 ID、认证信息等。
     */
    private static final ScopedValue<String> CURRENT_USER = ScopedValue.newInstance();
    private static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

    public static String scopedValues() {
        var result = new StringBuilder("=== Java 25: Scoped Values (JEP 506) ===\n");

        // ScopedValue.where().run() 绑定值到当前作用域
        ScopedValue.where(CURRENT_USER, "Alice")
                .where(REQUEST_ID, "REQ-12345")
                .run(() -> {
                    result.append("  Current user: %s\n".formatted(CURRENT_USER.get()));
                    result.append("  Request ID: %s\n".formatted(REQUEST_ID.get()));
                    result.append("  Nested call result: %s\n".formatted(innerMethod()));
                });

        // ScopedValue.where().call() 带返回值
        try {
            var computedValue = ScopedValue.where(CURRENT_USER, "Bob")
                    .call(() -> "Processed by " + CURRENT_USER.get());
            result.append("  Computed value: %s\n".formatted(computedValue));
        } catch (Exception e) {
            result.append("  Error: %s\n".formatted(e.getMessage()));
        }

        result.append("  (ScopedValue replaces ThreadLocal with immutable, structured lifecycle)\n\n");
        return result.toString();
    }

    private static String innerMethod() {
        // 在嵌套调用中访问 ScopedValue，无需参数传递
        return "inner sees user=%s, request=%s".formatted(CURRENT_USER.get(), REQUEST_ID.get());
    }

    /**
     * Java 25: Flexible Constructor Bodies (JEP 513)
     * 允许在调用 super() 之前执行语句（参数验证、转换等）
     */
    public static String flexibleConstructorBodies() {
        var result = new StringBuilder("=== Java 25: Flexible Constructor Bodies (JEP 513) ===\n");

        // 演示 flexible constructor body
        var validatedObj = new ValidatedEntity("hello world", 42);
        result.append("  ValidatedEntity: name=%s, value=%d\n".formatted(
                validatedObj.getName(), validatedObj.getValue()));

        try {
            new ValidatedEntity("", 10);
        } catch (IllegalArgumentException e) {
            result.append("  Validation caught: %s\n".formatted(e.getMessage()));
        }

        result.append("  (Statements before super() allow validation and transformation)\n\n");
        return result.toString();
    }

    // 基类
    static class BaseEntity {
        private final String name;
        private final int value;

        BaseEntity(String name, int value) {
            this.name = name;
            this.value = value;
        }

        String getName() { return name; }
        int getValue() { return value; }
    }

    // Java 25: 子类构造器中可以在 super() 之前执行语句
    static class ValidatedEntity extends BaseEntity {
        ValidatedEntity(String name, int value) {
            // Java 25: 可以在 super() 调用之前执行验证逻辑
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be blank");
            }
            var normalizedName = name.strip().toLowerCase();
            super(normalizedName, value);
        }
    }

    /**
     * Java 25: Module Import Declarations (JEP 511)
     * 说明：import module 语法允许一次导入整个模块的所有公开包。
     * 在此展示概念（实际使用需要模块化项目）。
     */
    public static String moduleImportDeclarations() {
        return """
                === Java 25: Module Import Declarations (JEP 511) ===
                  Syntax: import module java.base;
                  This single declaration imports all public packages from java.base module.
                  Equivalent to importing java.util.*, java.io.*, java.lang.*, etc.
                  (Requires modular project with module-info.java to use)
                
                """;
    }

    /**
     * Java 24/25: Stream Gatherers (JEP 485)
     * Gatherer 是 Stream API 的中间操作扩展，支持自定义有状态的流转换。
     * 内置 Gatherer：windowFixed, windowSliding, fold, scan, mapConcurrent
     */
    public static String streamGatherers() {
        var result = new StringBuilder("=== Java 24/25: Stream Gatherers (JEP 485) ===\n");

        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // windowFixed: 将流分成固定大小的窗口
        var fixedWindows = numbers.stream()
                .gather(Gatherers.windowFixed(3))
                .toList();
        result.append("  windowFixed(3): %s\n".formatted(fixedWindows));

        // windowSliding: 滑动窗口
        var slidingWindows = numbers.stream()
                .gather(Gatherers.windowSliding(4))
                .toList();
        result.append("  windowSliding(4): %s\n".formatted(slidingWindows));

        // scan: 类似 reduce，但输出每一步的中间结果
        var runningSum = numbers.stream()
                .gather(Gatherers.scan(() -> 0, Integer::sum))
                .toList();
        result.append("  scan (running sum): %s\n".formatted(runningSum));

        // fold: 折叠为单个结果（类似 reduce 但更灵活）
        var concatenated = numbers.stream()
                .gather(Gatherers.fold(() -> "", (acc, n) -> acc.isEmpty() ? n.toString() : acc + "-" + n))
                .toList();
        result.append("  fold (concatenate): %s\n".formatted(concatenated));

        result.append("  (Gatherers extend Stream API with custom stateful intermediate operations)\n\n");
        return result.toString();
    }

    public static String runAll() {
        return scopedValues()
                + flexibleConstructorBodies()
                + moduleImportDeclarations()
                + streamGatherers();
    }
}

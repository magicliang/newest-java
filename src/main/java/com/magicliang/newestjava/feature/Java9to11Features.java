package com.magicliang.newestjava.feature;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 展示 Java 9、10、11 的新特性
 */
public class Java9to11Features {

    /**
     * Java 9: 集合工厂方法 (JEP 269)
     * List.of(), Set.of(), Map.of() 创建不可变集合
     */
    public static String collectionFactoryMethods() {
        // Java 9: 不可变集合工厂方法
        var immutableList = List.of("Java 9", "Java 10", "Java 11");  // var 是 Java 10 特性
        var immutableSet = Set.of("Alpha", "Beta", "Gamma");
        var immutableMap = Map.of(
                "language", "Java",
                "version", "25",
                "framework", "Spring Boot"
        );

        return """
                === Java 9: Collection Factory Methods ===
                List.of(): %s
                Set.of(): %s
                Map.of(): %s
                """.formatted(immutableList, immutableSet, immutableMap);
    }

    /**
     * Java 10: Local Variable Type Inference (JEP 286)
     * 使用 var 进行局部变量类型推断
     */
    public static String localVariableTypeInference() {
        // Java 10: var 关键字，编译器自动推断类型
        var message = "Hello from Java 10!";           // 推断为 String
        var numbers = List.of(1, 2, 3, 4, 5);         // 推断为 List<Integer>
        var sum = numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();                                // 推断为 int

        // Java 11: var 可以用在 lambda 参数中（可以加注解）
        var result = numbers.stream()
                .map((var n) -> n * 2)                 // lambda 中使用 var
                .collect(Collectors.toList());

        return """
                === Java 10: var (Local Variable Type Inference) ===
                var message: %s (type: String)
                var numbers: %s (type: List<Integer>)
                var sum: %d (type: int)
                === Java 11: var in Lambda ===
                doubled: %s
                """.formatted(message, numbers, sum, result);
    }

    /**
     * Java 11: 新的 String 方法
     */
    public static String newStringMethods() {
        var blank = "   ";
        var text = "  Hello, Java 11!  ";
        var multiline = "line1\nline2\nline3";

        return """
                === Java 11: New String Methods ===
                isBlank("   "): %b
                strip("  Hello  "): [%s]
                stripLeading(): [%s]
                stripTrailing(): [%s]
                lines() count: %d
                "Ha".repeat(3): %s
                """.formatted(
                blank.isBlank(),
                text.strip(),
                text.stripLeading(),
                text.stripTrailing(),
                multiline.lines().count(),
                "Ha".repeat(3)
        );
    }

    /**
     * Java 9: Stream 增强 - takeWhile, dropWhile, ofNullable
     */
    public static String streamEnhancements() {
        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Java 9: takeWhile - 取满足条件的前缀元素
        var taken = numbers.stream()
                .takeWhile(n -> n < 5)
                .toList();                             // Java 16: Stream.toList()

        // Java 9: dropWhile - 跳过满足条件的前缀元素
        var dropped = numbers.stream()
                .dropWhile(n -> n < 5)
                .toList();

        return """
                === Java 9: Stream Enhancements ===
                Original: %s
                takeWhile(n < 5): %s
                dropWhile(n < 5): %s
                === Java 16: Stream.toList() ===
                (Above lists created with .toList() instead of .collect(Collectors.toList()))
                """.formatted(numbers, taken, dropped);
    }

    public static String runAll() {
        return collectionFactoryMethods()
                + localVariableTypeInference()
                + newStringMethods()
                + streamEnhancements();
    }
}

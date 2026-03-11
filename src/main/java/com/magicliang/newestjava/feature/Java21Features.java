package com.magicliang.newestjava.feature;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SequencedMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.magicliang.newestjava.model.PersonRecord;
import com.magicliang.newestjava.model.Shape;
import com.magicliang.newestjava.model.Shape.Circle;
import com.magicliang.newestjava.model.Shape.Rectangle;
import com.magicliang.newestjava.model.Shape.Triangle;

/**
 * 展示 Java 21 LTS 的新特性
 */
public class Java21Features {

    /**
     * Java 21: Virtual Threads (JEP 444)
     * 虚拟线程是轻量级线程，由 JVM 管理，可以创建数百万个而不耗尽系统资源。
     * 适用于 I/O 密集型任务。
     */
    public static String virtualThreads() throws InterruptedException {
        var results = new ConcurrentLinkedQueue<String>();

        // 创建虚拟线程 - 方式1: Thread.ofVirtual()
        var thread1 = Thread.ofVirtual().name("vt-1").start(() -> {
            results.add("VirtualThread-1 [isVirtual=%b]".formatted(Thread.currentThread().isVirtual()));
        });

        // 创建虚拟线程 - 方式2: Thread.startVirtualThread()
        var thread2 = Thread.startVirtualThread(() -> {
            results.add("VirtualThread-2 [isVirtual=%b]".formatted(Thread.currentThread().isVirtual()));
        });

        thread1.join();
        thread2.join();

        // 批量创建虚拟线程
        var startTime = System.nanoTime();
        var threads = new ArrayList<Thread>();
        for (int i = 0; i < 10_000; i++) {
            int index = i;
            var vt = Thread.ofVirtual().start(() -> {
                // 模拟轻量任务
                var unused = index * index;
            });
            threads.add(vt);
        }
        for (var t : threads) {
            t.join();
        }
        var elapsed = Duration.ofNanos(System.nanoTime() - startTime).toMillis();

        return """
                === Java 21: Virtual Threads (JEP 444) ===
                %s
                %s
                Created and joined 10,000 virtual threads in %d ms
                (Platform threads would typically require a thread pool for this scale)
                """.formatted(results.poll(), results.poll(), elapsed);
    }

    /**
     * Java 21: Pattern Matching for switch (JEP 441)
     * switch 中使用类型模式和守卫条件（guarded patterns）
     */
    public static String patternMatchingSwitch() {
        var results = new StringBuilder("=== Java 21: Pattern Matching for switch (JEP 441) ===\n");

        Object[] testValues = {"Hello", 42, 3.14, null, List.of(1, 2, 3), new Circle(5.0)};

        for (var value : testValues) {
            // Java 21: switch 中的类型模式匹配 + null 处理 + 守卫条件
            var description = switch (value) {
                case null -> "null value";
                case String s when s.length() > 10 -> "Long string: \"%s\"".formatted(s);
                case String s -> "Short string: \"%s\"".formatted(s);
                case Integer i when i > 0 -> "Positive integer: %d".formatted(i);
                case Integer i -> "Non-positive integer: %d".formatted(i);
                case Double d -> "Double: %.2f".formatted(d);
                case List<?> list -> "List of size %d".formatted(list.size());
                case Shape shape -> "Shape with area: %.2f".formatted(shape.area());
                default -> "Other: %s".formatted(value);
            };
            results.append("  %s -> %s\n".formatted(
                    value == null ? "null" : value.getClass().getSimpleName(), description));
        }

        return results.append("\n").toString();
    }

    /**
     * Java 21: Record Patterns (JEP 440)
     * 在 pattern matching 中解构 Record
     */
    public static String recordPatterns() {
        var results = new StringBuilder("=== Java 21: Record Patterns (JEP 440) ===\n");

        Object[] shapes = {
                new Circle(3.0),
                new Rectangle(4.0, 5.0),
                new Triangle(6.0, 7.0)
        };

        for (var shape : shapes) {
            // Java 21: Record Pattern - 直接在 switch 中解构 record 的组件
            var info = switch (shape) {
                case Circle(var radius) -> "Circle: radius=%.1f, circumference=%.2f".formatted(
                        radius, 2 * Math.PI * radius);
                case Rectangle(var w, var h) -> "Rectangle: %s, perimeter=%.2f".formatted(
                        w + "x" + h, 2 * (w + h));
                case Triangle(var base, var height) -> "Triangle: base=%.1f, height=%.1f".formatted(
                        base, height);
                default -> "Unknown shape";
            };
            results.append("  ").append(info).append("\n");
        }

        // Record Pattern 在 if 中使用
        var person = new PersonRecord("Charlie", 28, "charlie@example.com");
        if (person instanceof PersonRecord(var name, var age, var email)) {
            results.append("  Destructured Person: name=%s, age=%d, email=%s\n".formatted(name, age, email));
        }

        return results.append("\n").toString();
    }

    /**
     * Java 21: Sequenced Collections (JEP 431)
     * 为有序集合提供统一的 first/last 访问 API
     */
    public static String sequencedCollections() {
        // Java 21: SequencedCollection 接口
        var list = new ArrayList<>(List.of("Alpha", "Beta", "Gamma", "Delta"));

        // getFirst() / getLast() 替代 get(0) / get(size()-1)
        var first = list.getFirst();
        var last = list.getLast();

        // reversed() 返回反转视图
        var reversed = list.reversed();

        // SequencedMap
        SequencedMap<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        return """
                === Java 21: Sequenced Collections (JEP 431) ===
                List: %s
                getFirst(): %s
                getLast(): %s
                reversed(): %s
                SequencedMap: %s
                firstEntry(): %s
                lastEntry(): %s
                """.formatted(list, first, last, reversed, map,
                map.firstEntry(), map.lastEntry());
    }

    public static String runAll() {
        try {
            return virtualThreads()
                    + patternMatchingSwitch()
                    + recordPatterns()
                    + sequencedCollections();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Java 21 demo interrupted: " + e.getMessage();
        }
    }
}

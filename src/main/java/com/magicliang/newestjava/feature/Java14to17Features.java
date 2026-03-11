package com.magicliang.newestjava.feature;

import java.util.List;

import com.magicliang.newestjava.model.PersonRecord;
import com.magicliang.newestjava.model.Shape;
import com.magicliang.newestjava.model.Shape.Circle;
import com.magicliang.newestjava.model.Shape.Rectangle;
import com.magicliang.newestjava.model.Shape.Triangle;

/**
 * 展示 Java 14、15、16、17 的新特性
 */
public class Java14to17Features {

    /**
     * Java 14: Switch Expressions (JEP 361)
     * switch 从语句升级为表达式，使用箭头语法，支持返回值
     */
    public static String switchExpressions() {
        var day = "WEDNESDAY";

        // Java 14: Switch Expression with arrow syntax
        var dayType = switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "Weekday";
            case "SATURDAY", "SUNDAY" -> "Weekend";
            default -> "Unknown";
        };

        // Switch expression with yield (多行块)
        var dayLength = switch (day) {
            case "MONDAY", "FRIDAY", "SUNDAY" -> 6;
            case "TUESDAY" -> 7;
            case "WEDNESDAY", "THURSDAY", "SATURDAY" -> {
                var len = day.length();
                yield len;  // yield 用于多行块返回值
            }
            default -> throw new IllegalArgumentException("Invalid day: " + day);
        };

        return """
                === Java 14: Switch Expressions ===
                Day: %s
                Type: %s
                Name length (via yield): %d
                """.formatted(day, dayType, dayLength);
    }

    /**
     * Java 15: Text Blocks (JEP 378)
     * 多行字符串字面量，保留格式，自动管理缩进
     */
    public static String textBlocks() {
        // Java 15: Text Block - 三引号多行字符串
        var json = """
                {
                    "name": "Java 25",
                    "type": "LTS",
                    "features": [
                        "Virtual Threads",
                        "Pattern Matching",
                        "Sealed Classes"
                    ]
                }
                """;

        var html = """
                <html>
                    <body>
                        <h1>Hello, Text Blocks!</h1>
                        <p>No more string concatenation.</p>
                    </body>
                </html>
                """;

        // Java 15: Text Block 中使用 formatted（Java 15 引入的方法）
        var sql = """
                SELECT *
                FROM users
                WHERE age > %d
                  AND name LIKE '%%%s%%'
                ORDER BY name
                """.formatted(18, "Java");

        return """
                === Java 15: Text Blocks ===
                JSON:
                %s
                HTML:
                %s
                SQL (with formatted):
                %s""".formatted(json, html, sql);
    }

    /**
     * Java 16: Pattern Matching for instanceof (JEP 394)
     * instanceof 检查后自动绑定变量，无需显式强制转换
     */
    public static String patternMatchingInstanceof() {
        Object obj1 = "Hello, Pattern Matching!";
        Object obj2 = 42;
        Object obj3 = List.of(1, 2, 3);

        var result1 = describe(obj1);
        var result2 = describe(obj2);
        var result3 = describe(obj3);

        return """
                === Java 16: Pattern Matching for instanceof ===
                describe("Hello..."): %s
                describe(42): %s
                describe(List): %s
                """.formatted(result1, result2, result3);
    }

    private static String describe(Object obj) {
        // Java 16: Pattern variable 直接绑定
        if (obj instanceof String s) {
            return "String of length " + s.length() + ": \"" + s + "\"";
        } else if (obj instanceof Integer i) {
            return "Integer value: " + i + ", doubled: " + (i * 2);
        } else if (obj instanceof java.util.List<?> list) {
            return "List of size " + list.size() + ": " + list;
        }
        return "Unknown type: " + obj.getClass().getSimpleName();
    }

    /**
     * Java 17: Sealed Classes (JEP 409)
     * 展示 sealed interface + record 的组合
     */
    public static String sealedClasses() {
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        Shape triangle = new Triangle(3.0, 8.0);

        return """
                === Java 17: Sealed Classes ===
                Circle(r=5): area = %.2f
                Rectangle(4x6): area = %.2f
                Triangle(b=3, h=8): area = %.2f
                (Shape is a sealed interface, only Circle/Rectangle/Triangle can implement it)
                """.formatted(circle.area(), rectangle.area(), triangle.area());
    }

    /**
     * Java 14/16: Record 类型
     */
    public static String recordDemo() {
        var person1 = new PersonRecord("Alice", 30, "alice@example.com");
        var person2 = new PersonRecord("Bob", 25, "bob@example.com");

        return """
                === Java 14/16: Records ===
                person1: %s
                person1.name(): %s
                person1.greeting(): %s
                person1.equals(person2): %b
                person1.hashCode(): %d
                """.formatted(person1, person1.name(), person1.greeting(),
                person1.equals(person2), person1.hashCode());
    }

    public static String runAll() {
        return switchExpressions()
                + textBlocks()
                + patternMatchingInstanceof()
                + sealedClasses()
                + recordDemo();
    }
}

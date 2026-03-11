package com.magicliang.newestjava.model;

/**
 * Java 17: Sealed Classes / Interfaces (JEP 409)
 *
 * Sealed 类型限制了哪些类可以继承/实现它，提供了比 final 更灵活的封闭层次结构。
 * 与 pattern matching for switch (Java 21) 结合使用时特别强大。
 */
public sealed interface Shape permits Shape.Circle, Shape.Rectangle, Shape.Triangle {

    double area();

    // Java 16: Record 作为 sealed interface 的实现
    record Circle(double radius) implements Shape {
        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }

    record Rectangle(double width, double height) implements Shape {
        @Override
        public double area() {
            return width * height;
        }
    }

    record Triangle(double base, double height) implements Shape {
        @Override
        public double area() {
            return 0.5 * base * height;
        }
    }
}

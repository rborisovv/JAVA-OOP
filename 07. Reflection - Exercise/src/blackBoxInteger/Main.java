package blackBoxInteger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        Class<BlackBoxInt> blackBox = BlackBoxInt.class;
        try {
            Constructor<BlackBoxInt> ctor = blackBox.getDeclaredConstructor();
            ctor.setAccessible(true);
            BlackBoxInt dummy = ctor.newInstance();
            String[] tokens = reader.readLine().split("_");

            while (!tokens[0].equals("END")) {
                String method = tokens[0];
                int value = Integer.parseInt(tokens[1]);
                switch (method) {
                    case "add" -> {
                        Method addMethod = blackBox.getDeclaredMethod("add", int.class);
                        invokeMethod(dummy, value, addMethod);
                    }
                    case "subtract" -> {
                        Method subtractMethod = blackBox.getDeclaredMethod("subtract", int.class);
                        invokeMethod(dummy, value, subtractMethod);
                    }
                    case "multiply" -> {
                        Method multiplyMethod = blackBox.getDeclaredMethod("multiply", int.class);
                        invokeMethod(dummy, value, multiplyMethod);
                    }
                    case "divide" -> {
                        Method divideMethod = blackBox.getDeclaredMethod("divide", int.class);
                        invokeMethod(dummy, value, divideMethod);
                    }
                    case "leftShift" -> {
                        Method leftShiftMethod = blackBox.getDeclaredMethod("leftShift", int.class);
                        invokeMethod(dummy, value, leftShiftMethod);
                    }
                    case "rightShift" -> {
                        Method rightShiftMethod = blackBox.getDeclaredMethod("rightShift", int.class);
                        invokeMethod(dummy, value, rightShiftMethod);
                    }
                }
                Field innerValue = blackBox.getDeclaredField("innerValue");
                innerValue.setAccessible(true);
                System.out.println(innerValue.get(dummy));
                tokens = reader.readLine().split("_");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void invokeMethod(BlackBoxInt dummy, int value, Method method) throws IllegalAccessException, InvocationTargetException {
        method.setAccessible(true);
        method.invoke(dummy, value);
    }
}
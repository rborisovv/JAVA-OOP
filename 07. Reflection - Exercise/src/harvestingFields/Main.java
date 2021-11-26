package harvestingFields;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        Class<RichSoilLand> reflection = RichSoilLand.class;
        Field[] declaredFields = reflection.getDeclaredFields();
        String modifier = reader.readLine();
        Function<Field, String> mapField = field ->
                String.format("%s %s %s", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName());
        while (!modifier.equals("HARVEST")) {
            switch (modifier) {
                case "private":
                    String modifierName = Modifiers.PRIVATE.name().toLowerCase(Locale.ROOT);
                    Arrays.stream(declaredFields)
                            .filter(assertFieldModifier(modifierName))
                            .map(mapField)
                            .forEach(System.out::println);
                    break;
                case "protected":
                    modifierName = Modifiers.PROTECTED.name().toLowerCase(Locale.ROOT);
                    Arrays.stream(declaredFields)
                            .filter(assertFieldModifier(modifierName))
                            .map(mapField)
                            .forEach(System.out::println);
                    break;
                case "public":
                    modifierName = Modifiers.PUBLIC.name().toLowerCase(Locale.ROOT);
                    Arrays.stream(declaredFields)
                            .filter(assertFieldModifier(modifierName))
                            .map(mapField)
                            .forEach(System.out::println);
                    break;
                case "all":
                    Arrays.stream(declaredFields)
                            .map(mapField)
                            .forEach(System.out::println);
                    break;
            }
            modifier = reader.readLine();
        }
    }

    private static Predicate<Field> assertFieldModifier(String modifier) {
        return switch (modifier) {
            case "private" -> field -> Modifier.isPrivate(field.getModifiers());
            case "protected" -> field -> Modifier.isProtected(field.getModifiers());
            case "public" -> field -> Modifier.isPublic(field.getModifiers());
            default -> null;
        };
    }
}
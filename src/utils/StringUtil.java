package utils;

public class StringUtil {
    public static String camelToSnake(String camelCaseStr) {
        if (camelCaseStr == null || camelCaseStr.isEmpty()) {
            return camelCaseStr;
        }
        return camelCaseStr.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}



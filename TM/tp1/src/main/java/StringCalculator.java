import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    private static final Pattern DELIMITER_PATTERN = Pattern.compile("^//(.)(.*)");
    private static final String DEFAULT_DELIMITER = ",";

    /*
    public static Integer add(String str) {
        String[] numbersAsArray = str.split(DEFAULT_DELIMITER);
        List<Integer> numbers = Arrays.stream(numbersAsArray).map(Integer::parseInt).collect(Collectors.toList());
        return null;
    }

    public static Integer add(String str) throws RuntimeException {
        String[] numbersAsArray = str.split(DEFAULT_DELIMITER);

        if (numbersAsArray.length > 2) {
            throw new RuntimeException("Too much number (2 is the maximum)");
        }

        List<Integer> numbers = Arrays.stream(numbersAsArray).map(Integer::parseInt).collect(Collectors.toList());
        return null;
    }

    public static Integer add(String str) throws RuntimeException {
        if (str.isEmpty()) {
            return 0;
        }

        String[] numbersAsArray = str.split(DEFAULT_DELIMITER);
        if (numbersAsArray.length > 2) {
            throw new RuntimeException("Too much number (2 is the maximum)");
        }

        List<Integer> numbers = Arrays.stream(numbersAsArray).map(Integer::parseInt).collect(Collectors.toList());
        if (numbers.size() == 1) {
            return numbers.get(0);
        }

        return null;
    }

    public static Integer add(String str) throws RuntimeException {
        if (str.isEmpty()) {
            return 0;
        }

        String[] numbersAsArray = str.split(DEFAULT_DELIMITER);
        if (numbersAsArray.length > 2) {
            throw new RuntimeException("Too much number (2 is the maximum)");
        }

        return Arrays.stream(numbersAsArray)
                .map(String::trim)
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
    }

    public static Integer add(String str) {
        if (str.isEmpty()) {
            return 0;
        }

        return Arrays.stream(str.split(DEFAULT_DELIMITER))
                .map(String::trim)
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
    }

    public static Integer add(String str) {
        if (str.isEmpty()) {
            return 0;
        }

        return Arrays.stream(str.split("[" + DEFAULT_DELIMITER + "\n]"))
                .map(String::trim)
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
    }
    */

    public static Integer add(String str) {
        if (str.isEmpty()) {
            return 0;
        }

        String delimiter = DEFAULT_DELIMITER;
        String numbersAsString = str;

        Matcher matcher = DELIMITER_PATTERN.matcher(str);
        if (matcher.find()) {
            delimiter = matcher.group(1);
            numbersAsString = matcher.group(2);
        }

        return Arrays.stream(numbersAsString.split("[" + delimiter + "\n]"))
                .map(String::trim)
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
    }
}

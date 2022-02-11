package encryptdecrypt;

import java.util.ArrayList;
import java.util.Arrays;

interface Algorithm {
    String encryption(String message, int key);
    String decryption(String message, int key);
}

class Unicode implements Algorithm {
    @Override
    public String encryption(String message, int key) {
        StringBuilder resultBuilder = new StringBuilder();

        for (char ch : message.toCharArray()) {
            int newValue = ch + key;
            resultBuilder.append((char) newValue);
        }

        return resultBuilder.toString();
    }

    @Override
    public String decryption(String message, int key) {
        StringBuilder resultBuilder = new StringBuilder();

        for (char ch : message.toCharArray()) {
            int newValue = ch - key;
            resultBuilder.append((char) newValue);
        }

        return resultBuilder.toString();
    }
}

class Shift implements Algorithm {
    private static final ArrayList<String> firstCircle = new ArrayList<>(Arrays.asList("a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ")));
    private static final ArrayList<String> secondCircle = new ArrayList<>(Arrays.asList("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ")));

    @Override
    public String encryption(String message, int key) {
        StringBuilder resultBuilder = new StringBuilder();

        for (char ch : message.toCharArray()) {
            if (firstCircle.contains(String.valueOf(ch))) {
                final int currentIndexInCircle = firstCircle.indexOf(String.valueOf(ch));
                final int temp = currentIndexInCircle + (key % firstCircle.size());
                final int newIndex = temp < firstCircle.size() ? temp : temp - firstCircle.size();

                resultBuilder.append(firstCircle.get(newIndex));
            } else if (secondCircle.contains(String.valueOf(ch))) {
                final int currentIndexInCircle = secondCircle.indexOf(String.valueOf(ch));
                final int temp = currentIndexInCircle + (key % secondCircle.size());
                final int newIndex = temp < secondCircle.size() ? temp : temp - secondCircle.size();

                resultBuilder.append(secondCircle.get(newIndex));
            } else {
                resultBuilder.append(ch);
            }
        }

        return resultBuilder.toString();
    }

    @Override
    public String decryption(String message, int key) {
        StringBuilder resultBuilder = new StringBuilder();

        for (char ch : message.toCharArray()) {
            if (firstCircle.contains(String.valueOf(ch))) {
                final int currentIndexInCircle = firstCircle.indexOf(String.valueOf(ch));
                final int temp = currentIndexInCircle - (key % firstCircle.size());
                final int newIndex = temp >= 0 ? temp : temp + firstCircle.size();

                resultBuilder.append(firstCircle.get(newIndex));
            } else if (secondCircle.contains(String.valueOf(ch))) {
                final int currentIndexInCircle = secondCircle.indexOf(String.valueOf(ch));
                final int temp = currentIndexInCircle - (key % secondCircle.size());
                final int newIndex = temp >= 0 ? temp : temp + secondCircle.size();

                resultBuilder.append(secondCircle.get(newIndex));
            } else {
                resultBuilder.append(ch);
            }
        }

        return resultBuilder.toString();
    }
}

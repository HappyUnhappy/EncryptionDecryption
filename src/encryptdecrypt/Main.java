package encryptdecrypt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Process {
    final static String DATA = "-data";
    final static String MODE = "-mode";
    final static String ALG  = "-alg";
    final static String KEY  = "-key";
    final static String OUT  = "-out";
    final static String IN   = "-in";

    final static String DEFAULT_MODE = "enc";
    final static String DEFAULT_DATA = "";
    final static String DEFAULT_OUT  = "";
    final static String DEFAULT_ALG  = "shift";
    final static int    DEFAULT_KEY = 0;

    private static List<String> argsList;
    private static String targetOperation;
    private static String message;
    private static int key;
    private static String out;
    private static Algorithm algorithm;

    public static void getResult(String[] args) {
        setParameters(args);

        switch (targetOperation) {
            case "dec":
                printResult(algorithm.decryption(message, key), out);
                break;
            case "enc":
            default:
                printResult(algorithm.encryption(message, key), out);
        }
    }

    private static void setParameters(String[] args) {
        argsList = new ArrayList<>(Arrays.asList(args));

        targetOperation = getTargetOperation();
        message = getMessage();
        key = getKey();
        out = getOutMode();
        algorithm = getAlgorithm();
    }

    private static String getTargetOperation() {
        final int modeIndex = argsList.indexOf(MODE);

        if (modeIndex != -1) {
            return argsList.get(modeIndex + 1);
        }

        return DEFAULT_MODE;
    }

    private static String getMessage() {
        final int dataIndex = argsList.indexOf(DATA);
        final int inModeIndex = argsList.indexOf(IN);

        if (dataIndex != -1) {
            return argsList.get(dataIndex + 1);
        } else if (inModeIndex != -1) {
            try {
                return readFileAsString(argsList.get(inModeIndex + 1));
            } catch (IOException e) {
                System.out.println("Error. Cannot read file: " + e.getMessage());
            }
        }

        return DEFAULT_DATA;
    }

    private static int getKey() {
        final int keyIndex = argsList.indexOf(KEY);

        if (keyIndex != -1) {
            return Integer.parseInt(argsList.get(keyIndex + 1));
        }

        return DEFAULT_KEY;
    }

    private static String getOutMode() {
        final int outModeIndex = argsList.indexOf(OUT);

        if (outModeIndex != -1) {
            return argsList.get(outModeIndex + 1);
        }

        return DEFAULT_OUT;
    }

    private static Algorithm getAlgorithm() {
        final int algIndex = argsList.indexOf(ALG);

        String alg;
        if (algIndex != -1) {
            alg =  argsList.get(algIndex + 1);
        } else {
            alg = DEFAULT_ALG;
        }

        switch (alg) {
            case "unicode":
                return new Unicode();
            case "shift":
            default:
                return new Shift();
        }
    }

    private static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    private static void printResult(String resultMessage, String out) {
        if (out.equals(DEFAULT_OUT)) {
            System.out.println(resultMessage);
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(out));
                writer.write(resultMessage);
                writer.close();
            } catch (IOException e) {
                System.out.println("Error. Cannot read file: " + e.getMessage());
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Process.getResult(args);
    }
}

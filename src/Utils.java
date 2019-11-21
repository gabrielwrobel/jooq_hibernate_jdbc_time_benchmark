import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class Utils {

    static List<Integer> saveCases = Arrays.asList(100, 500, 1000, 5000, 10000, 30000, 50000,
            70000, 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000,
            1200000, 1400000, 1600000, 1800000, 2000000);
    static List<Integer> testCases = Arrays.asList(100);
    static List<Integer> readCases = Arrays.asList(100, 500, 1000, 5000, 10000, 30000, 50000,
            70000, 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000,
            1200000, 1400000, 1600000, 1800000, 2000000);


    public static String randomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public static String convertToTime(long millis) {
        return String.format("%02d:%02d:%02d.%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
                TimeUnit.MILLISECONDS.toMillis(millis)
                        - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis)));
    }

    public static void writeLog(StringBuilder resultlogs, String name) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(name + ".txt");
        byte[] strToBytes = resultlogs.toString().getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
    }
}



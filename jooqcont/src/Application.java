import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {

        DataService dataService = new DataService();
        StringBuilder resultlogs = new StringBuilder();
        String time = "err";

        dataService.readMagic(1);
        resultlogs.append("____________SAVE_____________\n");
        for (Integer count : Utils.saveCases) {
            time = dataService.saveMagic(count);
            System.out.println("resalt for " + count + " records:" + time);
            resultlogs.append("count: ").append(count).append(", time: ").append(time).append("\n");
        }

        resultlogs.append("____________READ_____________\n");
        for (Integer count : Utils.readCases) {
            time = dataService.readMagic(count);
            System.out.println("resalt for " + count + " records:" + time);
            resultlogs.append("count: ").append(count).append(", time: ").append(time).append("\n");
        }

        Utils.writeLog(resultlogs, "jooqLogs");

    }
}

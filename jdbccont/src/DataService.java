import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DataService {

    private long startTime, endTime;

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/compdb?useSSL=false";

    private static final String USER = "gabb";
    private static final String PASS = "gabb";

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;

    String saveMagic(Integer count) {

        String sql = "INSERT into data_model (s_value2, s_value3, s_value4, s_value5, s_value6, s_value7, tag)" +
                " values (?,?,?,?,?,?,?)";
        List<DataModel> reckords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DataModel dataModel = new DataModel(Utils.randomString(),
                    Utils.randomString(),
                    Utils.randomString(),
                    Utils.randomString(),
                    Utils.randomString(),
                    Utils.randomString(),
                    "jdbc");
            reckords.add(dataModel);
        }

        startTime = System.currentTimeMillis();
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < count; i++) {
                pstmt.setString(1, reckords.get(i).getValue2());
                pstmt.setString(2, reckords.get(i).getValue3());
                pstmt.setString(3, reckords.get(i).getValue4());
                pstmt.setString(4, reckords.get(i).getValue5());
                pstmt.setString(5, reckords.get(i).getValue6());
                pstmt.setString(6, reckords.get(i).getValue7());
                pstmt.setString(7, reckords.get(i).getTag());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
            pstmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        endTime = System.currentTimeMillis();

        return Utils.convertToTime(endTime - startTime);
    }

    String readMagic(Integer count) {
        startTime = System.currentTimeMillis();
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql = "Select * from data_model limit " + count;
            ResultSet rs = stmt.executeQuery(sql);
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        endTime = System.currentTimeMillis();
        return Utils.convertToTime(endTime - startTime);
    }
}

import cont.jooqcont.model.tables.records.DataModelRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import static cont.jooqcont.model.tables.DataModel.DATA_MODEL;

class DataService {
    private String user = "gabb";
    private String password = "gabb";
    private String url = "jdbc:mysql://localhost:3306/compdb?useCursorFetch=true";
    private String driver = "com.mysql.jdbc.Driver";
    private long startTime, endTime = System.currentTimeMillis();

    String saveMagic(int count) throws ClassNotFoundException {
        startTime = 0;
        endTime = 0;
        List<DataModel> reckords = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            DataModel dataModel = new DataModel(Utils.randomString(), Utils.randomString(), Utils.randomString(), Utils.randomString(), Utils.randomString(), Utils.randomString(), "jooq");
            reckords.add(dataModel);
        }

        startTime = System.currentTimeMillis();

        Class.forName(driver);
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            ArrayList<DataModelRecord> list = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                cont.jooqcont.model.tables.records.DataModelRecord dataModelRecord = new DataModelRecord();
                dataModelRecord.setSValue2(reckords.get(i).getValue2());
                dataModelRecord.setSValue3(reckords.get(i).getValue3());
                dataModelRecord.setSValue4(reckords.get(i).getValue4());
                dataModelRecord.setSValue5(reckords.get(i).getValue5());
                dataModelRecord.setSValue6(reckords.get(i).getValue6());
                dataModelRecord.setSValue7(reckords.get(i).getValue7());
                dataModelRecord.setTag(reckords.get(i).getTag());
                list.add(dataModelRecord);

                if (i % 10000 == 0) {
                    dslContext.batchStore(list).execute();
                    list.clear();
                }
            }
            if (!list.isEmpty()) {
                dslContext.batchStore(list).execute();
                list.clear();
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        endTime = System.currentTimeMillis();
        return Utils.convertToTime(endTime - startTime);

    }

    String readMagic(int count) throws ClassNotFoundException {
        startTime = 0;
        endTime = 0;

        startTime = System.currentTimeMillis();

        Class.forName(driver);
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);
            Result<Record> a = dslContext.select().from(DATA_MODEL).limit(count).fetch();
            System.out.println(a.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();

        return Utils.convertToTime(endTime - startTime);
    }


}

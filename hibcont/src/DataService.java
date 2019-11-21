import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

class DataService {

    private static SessionFactory factory;
    long startTime = 0, endTime = 0;
    int MAX_ITEM_IN_BATCH = 100;

    DataService() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    String saveMagic(int dataObjectCount) {
        startTime = 0;
        endTime = 0;
        List<DataModel> dataModels = new ArrayList<>();
        for (int i = 0; i < dataObjectCount; i++) {
            dataModels.add(new DataModel(Utils.randomString(), Utils.randomString(), Utils.randomString(), Utils.randomString(), Utils.randomString(), Utils.randomString(), "hibernate"));
        }

        try {
            startTime = System.currentTimeMillis();
            Session session = factory.openSession();
            addData(session, dataModels);
            session.close();
            endTime = System.currentTimeMillis();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Utils.convertToTime(endTime - startTime);

    }

    String readMagic(int dataObjectCount) {
        startTime = 0;
        endTime = 0;
        Session session;
        try {
            startTime = System.currentTimeMillis();
            session = factory.openSession();
            readData(session, dataObjectCount);
            session.close();
            endTime = System.currentTimeMillis();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return Utils.convertToTime(endTime - startTime);
    }

    private void addData(Session session, List<DataModel> dataModelList) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (int i = 0; i < dataModelList.size(); i++) {
                if (i % MAX_ITEM_IN_BATCH == 0 && i > 0) {
                    session.flush();
                    session.clear();
                }
                session.persist(dataModelList.get(i));
            }
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.close();
        }
    }

    private void readData(Session session, int a) {
        Transaction tx = null;
        String hql = "FROM DataModel E";
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setMaxResults(a);
            query.list();
            System.out.println(query.list().size());
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

}

package volunteersservice.repositories.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.FirstPartOfReport;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.repositories.ReportRepository;
import volunteersservice.utils.HibernateUtil;

import java.util.List;

public class ReportRepositoryHibernate implements ReportRepository {
    private final static Logger LOG = Logger.getLogger(EventRepositoryHibernate.class);

    @Override
    public FirstPartOfReport getReportByID(int reportID)  {
        return HibernateUtil.getSession().get(FirstPartOfReport.class, reportID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public FirstPartOfReport getReportByEvent(Event event){
        return (FirstPartOfReport)HibernateUtil.getSession()
                .createQuery("select firstPartOfReport From FirstPartOfReport as firstPartOfReport where firstPartOfReport.event.eventID = :eventID")
                .setParameter("eventID", event.getEventID())
                .uniqueResult();
    }

    @Override
    public boolean save(FirstPartOfReport report) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();

        try {
            session.save(report);
            tx.commit();
            return true;
        } catch (Exception ex) {
            LOG.error(ex);
            return false;
        }
    }

    @Override
    public boolean update(FirstPartOfReport report) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();

        try {
            session.update(report);
            tx.commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}

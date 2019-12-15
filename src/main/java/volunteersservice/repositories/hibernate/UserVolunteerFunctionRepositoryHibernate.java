package volunteersservice.repositories.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.User;
import volunteersservice.models.entities.UserVolunteerFunction;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.models.enums.UserVolunteerFunctionStatusEnum;
import volunteersservice.repositories.UserVolunteerFunctionRepository;
import volunteersservice.utils.HibernateUtil;

@Repository
public class UserVolunteerFunctionRepositoryHibernate implements UserVolunteerFunctionRepository {

	private static Logger LOG = Logger.getLogger(UserRepositoryHibernate.class);

	@Override
	public UserVolunteerFunction getUserVolunteerFunctionByID(int id) {
		return HibernateUtil.getSession().get(UserVolunteerFunction.class, id);
	}

	@Override
	public UserVolunteerFunction getUserVolunteerFunction(int userID, int volunteerFunctionID) {
		return (UserVolunteerFunction) HibernateUtil.getSession().createQuery(
				"select userfunc from UserVolunteerFucntion userfunc where userfunc.user.userID = :userID and userfunc.volunteerFunction.volunteerFunctionID = :vfID")
				.setParameter("userID", userID).setParameter("vfID", volunteerFunctionID).uniqueResult();
	}

	@Override
	public boolean save(UserVolunteerFunction userVolunteerFunction) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(userVolunteerFunction);
			tx.commit();
			return true;
		} catch (Exception ex) {
			LOG.error(ex);
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean update(UserVolunteerFunction userVolunteerFunction) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(userVolunteerFunction);
			tx.commit();
			return true;
		} catch (Exception ex) {
			return false;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllVolunteersOfFunction(VolunteerFunction volunteerFunction) {
		return (List<User>) HibernateUtil.getSession().createQuery(
				"select user from User as user inner join UserVolunteerFunction as uvf on uvf.user.userID = user.userID where uvf.volunteerFunction.volunteerFunctionID = :vfID")
				.setParameter("vfID", volunteerFunction.getVolunteerFunctionID()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getVolunteersOfFunction(VolunteerFunction volunteerFunction,
			UserVolunteerFunctionStatusEnum status) {
		return (List<User>) HibernateUtil.getSession().createQuery(
				"select user from User as user inner join VolunteerFunction as vf where vf.user.userID = user.userID inner join UserVolunteerFunctionStatus uvfs on uvfs.status.statusID = vf.statusID where uvfs.name = :statusName")
				.setParameter("statusName", status.name().toLowerCase()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserVolunteerFunction> getUserVolunteerFunctionsOfVolunteerFunction(
			VolunteerFunction volunteerFunction) {
		return (List<UserVolunteerFunction>) HibernateUtil.getSession().createQuery(
				"select uvf from UserVolunteerFunction as uvf where uvf.volunteerFunction.volunteerFunctionID = :vfID")
				.setParameter("vfID", volunteerFunction.getVolunteerFunctionID()).list();
	}

	@Override
	public boolean alreadySignedUp(int userID, int volunteerFunctionID) {
		return ((Long) HibernateUtil.getSession().createQuery(
				"select count(*) from UserVolunteerFunction as uvf where uvf.user.userID = :userID and uvf.volunteerFunction.volunteerFunctionID = :vfID")
				.setParameter("userID", userID).setParameter("vfID", volunteerFunctionID).uniqueResult()) > 0;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UserVolunteerFunction> getAllVolunteersOfEvent(int eventId){
		return (List<UserVolunteerFunction>) HibernateUtil.getSession().createQuery(
				"select uvf from UserVolunteerFunction as uvf inner join VolunteerFunction as vf on uvf.volunteerFunction.volunteerFunctionID = vf.volunteerFunctionID where vf.event.eventID = :eventId order by uvf.id")
				.setParameter("eventId", eventId).list();

	}

	@Override
	public Long getHoursOfVolunteer(int userId){
		return ((Long) HibernateUtil.getSession().createQuery(
				"select sum(numberOfHours) from UserVolunteerFunction as uvf where uvf.user.userID = :userID")
				.setParameter("userID", userId).getSingleResult());
	}

	@Override
	public double getAVGRating(int userId){
		Long sum = ((Long) HibernateUtil.getSession().createQuery(
				"select sum(estimation) from UserVolunteerFunction as uvf where uvf.user.userID = :userID")
				.setParameter("userID", userId).getSingleResult());

		Long numOfEvents = ((Long) HibernateUtil.getSession().createQuery(
				"select count(*) from UserVolunteerFunction as uvf where uvf.user.userID = :userID and uvf.status.statusID = :statusID")
				.setParameter("userID", userId).setParameter("statusID", 5).getSingleResult());

		if (numOfEvents == 0)
			return 0;

		return (double)sum / numOfEvents;
	}
}

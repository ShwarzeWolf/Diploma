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
import volunteersservice.utils.HibernateSessionFactoryUtil;

@Repository
public class UserVolunteerFunctionRepositoryHibernate implements UserVolunteerFunctionRepository {

	private static Logger LOG = Logger.getLogger(UserRepositoryHibernate.class);

	@Override
	public UserVolunteerFunction getUserVolunteerFunctionByID(int id) {
		return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(UserVolunteerFunction.class, id);
	}

	@Override
	public UserVolunteerFunction getUserVolunteerFunction(int userID, int volunteerFunctionID) {
		return (UserVolunteerFunction) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
				"select UserVolunteerFunction(function) from UserVolunteerFucntion userfunc where userfunc.user.userID = :userID and userfunc.volunteerFunction.volunteerFunctionID = :vfID")
				.setParameter("userID", userID).setParameter("vfID", volunteerFunctionID).uniqueResult();
	}

	@Override
	public boolean save(UserVolunteerFunction userVolunteerFunction) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
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
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
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
		return (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
				"SELECT new User(user) from User as user inner join UserVolunteerFunction as uvf on uvf.user.userID = user.userID where uvf.volunteerFunction.volunteerFunctionID = :vfID")
				.setParameter("vfID", volunteerFunction.getVolunteerFunctionID()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getVolunteersOfFunction(VolunteerFunction volunteerFunction,
			UserVolunteerFunctionStatusEnum status) {
		return (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
				"SELECT new User(user) from User as user inner join VolunteerFunction as vf where vf.user.userID = user.userID inner join UserVolunteerFunctionStatus uvfs on uvfs.status.statusID = vf.statusID where uvfs.name = :statusName")
				.setParameter("statusName", status.name().toLowerCase()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserVolunteerFunction> getUserVolunteerFunctionsOfVolunteerFunction(
			VolunteerFunction volunteerFunction) {
		return (List<UserVolunteerFunction>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
			"SELECT new UserVolunteerFunction(uvf) from UserVolunteerFunction as uvf where uvf.volunteerFunction.volunteerFunctionID = :vfID"
		).setParameter("vfID", volunteerFunction.getVolunteerFunctionID()).list();
	}
}

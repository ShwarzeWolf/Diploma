package volunteersservice.repositories.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.User;
import volunteersservice.repositories.UserRepository;
import volunteersservice.utils.HibernateUtil;
import org.apache.log4j.Logger;

@Repository
public class UserRepositoryHibernate implements UserRepository {
	private static Logger LOG = Logger.getLogger(UserRepositoryHibernate.class);

	@Override
	public User getUserByID(int id) {
		return HibernateUtil.getSession().get(User.class, id);
	}

	@Override
	public User getUserByEmail(String email) {
		return (User) HibernateUtil.getSession()
				.createQuery("select new User(user) from User as user where user.email = :email")
				.setParameter("email", email).uniqueResult();
	}

	@Override
	public User getUserByLogin(String login) {
		return (User) HibernateUtil.getSession()
				.createQuery("select new User(user) from User as user where user.login = :login")
				.setParameter("login", login).uniqueResult();
	}

	@Override
	public boolean save(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		LOG.info("Saving user " + user);
		try {
			session.save(user);
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
	public boolean update(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(user);
			tx.commit();
			return true;
		} catch (Exception ex) {
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean delete(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.delete(user);
			tx.commit();
			return true;
		} catch (Exception ex) {
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return (List<User>) HibernateUtil.getSession().createQuery("From User").list();
	}
}
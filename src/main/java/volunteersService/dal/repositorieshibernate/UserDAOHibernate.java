package volunteersService.dal.repositorieshibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import volunteersService.models.User;
import volunteersService.utils.HibernateSessionFactoryUtil;
import volunteersService.dal.repositories.UserDAO;

@Repository
public class UserDAOHibernate implements UserDAO {

	@Override
	public User getUserByID(int id) {
		return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
	}

	@Override
	public User getUserByEmail(String email) {
		return (User) HibernateSessionFactoryUtil.getSessionFactory().openSession()
				.createQuery("select new User(user) from User as user where user.email = :email")
				.setParameter("email", email).uniqueResult();
	}

	@Override
	public void save(User user) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(user);
		tx.commit();
		session.close();
	}

	@Override
	public void update(User user) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.update(user);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(User user) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.delete(user);
		tx.commit();
		session.close();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User")
				.list();
	}
}
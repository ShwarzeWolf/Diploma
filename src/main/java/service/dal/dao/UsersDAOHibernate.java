package service.dal.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import service.dal.models.User;
import service.utils.HibernateSessionFactoryUtil;

public class UsersDAOHibernate implements UsersDAO {

	public User getUserByID(int id) {
		return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
	}

	public User getUserByEmail(String email) {
		return (User) HibernateSessionFactoryUtil.getSessionFactory().openSession()
				.createQuery("select new User(user) from User as user where user.email = :email")
				.setParameter("email", email).uniqueResult();
	}

	public void save(User user) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(user);
		tx.commit();
		session.close();
	}

	public void update(User user) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(user);
		tx1.commit();
		session.close();
	}

	public void delete(User user) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		session.delete(user);
		tx1.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		List<User> users = (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
				.createQuery("From Users").list();
		return users;
	}
}
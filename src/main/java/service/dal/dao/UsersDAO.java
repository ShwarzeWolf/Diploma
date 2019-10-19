package service.dal.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import service.dal.models.UserEntity;
import service.utils.HibernateSessionFactoryUtil;

public class UsersDAO {

	public UserEntity getUserByID(int id) {
		return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(UserEntity.class, id);
	}

	public UserEntity getUserByEmail(String email) {
		return (UserEntity) HibernateSessionFactoryUtil.getSessionFactory().openSession()
				.createQuery("select new UserEntity(user) from UserEntity as user where user.email = :email")
				.setParameter("email", email).uniqueResult();
		// return (UserEntity) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from UserEntity").list().get(0);
	}

	public void save(UserEntity userEntity) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(userEntity);
		tx.commit();
		session.close();
	}

	public void update(UserEntity userEntity) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(userEntity);
		tx1.commit();
		session.close();
	}

	public void delete(UserEntity userEntity) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		session.delete(userEntity);
		tx1.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<UserEntity> findAll() {
		List<UserEntity> users = (List<UserEntity>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
				.createQuery("From Users").list();
		return users;
	}
}
package com.samanecorp.secureapp.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samanecorp.secureapp.dto.AccountUserDto;
import com.samanecorp.secureapp.entities.AccountUserEntity;
import com.samanecorp.secureapp.util.HibernateUtil;

public class AccountUserDaoImpl implements AccountUserDao{

    private static final Logger logger = LoggerFactory.getLogger(AccountUserDaoImpl.class);

	@Override
	public int save(AccountUserEntity user) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
			session.save(user);
			transaction.commit();
			
			logger.info("\n\n\tUser saved successfully, User Details=" + user +".\n\n");
			return 1;
		} catch (Exception e) {
			//System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("\n\tFailed to save user", e);
            return 0;
		}
	}

	@Override
	public int update(AccountUserEntity user) {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
			
			logger.info("\n\tUser updated successfully, User Details=" + user);
			return 1;
		} catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("\n\tFailed to update user", e);
			return 0;
		}
	}

	@Override
	public int delete(AccountUserEntity user) {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.delete(user);
			transaction.commit();
			
			logger.info("\n\tUser deleted successfully, User Details=" + user);
			return 1;
		} catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("\n\tFailed to delete user", e);
			return 0;
		}
	}

	@Override
	public AccountUserEntity findById(long id) {
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			AccountUserEntity user = session.get(AccountUserEntity.class, id);

			if(user != null) {
				logger.info("\n\n\tUser ({}) found successfully.\n", user.getEmail());
            } else {
                logger.info("\n\n\tUser not found with id={}.\n", id);
            }
			return user;
		} catch (Exception e) {
            logger.error("\n\n\tFailed to find user\n", e);
            return null;
		}
	}

	@Override
	public AccountUserEntity findByEmailAndPassword(String email, String password) {
		Transaction transaction = null;
		AccountUserEntity user = null;
		
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			
			String hql = "from AccountUserEntity where email = :email";
			Query<AccountUserEntity> query = session.createQuery(hql, AccountUserEntity.class);			
			query.setParameter("email", email);
			
			try 
			{
                user = query.getSingleResult();

                if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
                    transaction.commit();
                    logger.info("\n\n\tUser ({}) found successfully.\n", user.getEmail());
                    return user;
                } else {
                    transaction.rollback();
                    return null;
                }
            } 
			catch (NoResultException e) // No result found, handle accordingly
			{
                transaction.rollback();
                logger.warn("\n\n\tNo result found !!!");
                return null;
            } 
			catch (NonUniqueResultException e) // Multiple results found, handle accordingly
			{
                transaction.rollback();
                logger.warn("\n\tMultiple results found!");
                return null;
            }
		} catch (Exception e) 
		{
            if (transaction != null) {
                transaction.rollback();
            }

			logger.error("An error occurred while finding the user by email and password.", e);
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountUserEntity> findAll() {
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			List<AccountUserEntity> users = session.createQuery("from AccountUserEntity").list();
			logger.info("\n\tUsers found successfully, Users=" + users);
            return users;
		} catch (Exception e) {
            logger.error("\n\tFailed to find users", e);
            return null;
		}
	}

	@Override
	public AccountUserEntity findByEmail(String email) {
		AccountUserEntity user = null;
		Transaction transaction;
		
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			
			String hql = "from AccountUserEntity where email = :email";
			Query<AccountUserEntity> query = session.createNamedQuery(hql, AccountUserEntity.class);
			query.setParameter("email", email);
			
			try {
				user = query.getSingleResult();
				if(user != null) {
                    transaction.commit();
                    logger.info("\n\n\tUser ({}) found successfully.=", user.getEmail());
                    return user;
				} else {
					transaction.rollback();
                    logger.warn("\n\n\tUser with email {} not found",email);
					return null;
				}
			} 
			catch (NoResultException e) // No result found, handle accordingly
			{
                transaction.rollback();
                logger.warn("\n\n\tNo result found !!!");
                return null;
            } 
			catch (NonUniqueResultException e) // Multiple results found, handle accordingly
			{
                transaction.rollback();
                logger.warn("\n\tMultiple results found!");
                return null;
            }
		} catch (Exception e) {
            logger.error("\n\n\tFailed to find user by email {}\n.", email, e);
		}
		return user;
	}
}

package org.eastnets.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eastnets.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LogManager.getLogger(UserRepositoryImpl.class);
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("task-management");

    @Override
    public void signup(User user) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            logger.info("Adding user: {} To Database ");

            if (userIsRegistered(user.getUsername(), em)) {
                throw new Exception("User is Already Registered");
            }

            em.persist(user);
            em.getTransaction().commit();
            logger.info("User added Successfully");
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("{}   {}", ex.getMessage(), ex.getCause());
            throw new Exception("error while sign up");
        } finally {
            em.close();
        }
    }

    public boolean userIsRegistered(String username, EntityManager em) {
        String sql = "SELECT u FROM User u WHERE u.username = :username";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        return !users.isEmpty();
    }

    @Override
    public User login(String enteredUsername, String enteredPassword) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            logger.info("Attempting to log in to DB");

            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            query.setParameter("username", enteredUsername);
            query.setParameter("password", enteredPassword);

            User user = query.getSingleResult();
            em.getTransaction().commit();
            return user;

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("No user found with the provided username and password. Cause: {}", ex.getCause());
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> getAllUsersFromDataBase() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            logger.info("getAllUsersFromDataBase");

            TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
            List<User> users = query.getResultList();
            em.getTransaction().commit();
            logger.info("Users Retrieved {}", users.size());
            return users;

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Error retrieving users. Cause: {}", ex.getCause());
            return null;
        } finally {
            em.close();
        }
    }
}

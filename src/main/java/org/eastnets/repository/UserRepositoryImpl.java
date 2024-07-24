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
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("task-management");
    EntityManager em = emf.createEntityManager();

    @Override
    public void signup(User user) throws Exception {


        try {
            logger.info("Adding user: {} To Database ", user.toString());
            if (userIsRegistered(user.getUsername()))
                throw new Exception("User is Already Registered");
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            logger.info("User added Successfully");

        } catch (Exception ex) {
            logger.error("{}   {}", ex.getMessage(), ex.getCause());
            throw new Exception("error while sign up");
        }
    }

    private boolean userIsRegistered(String username) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        String sql = "SELECT u FROM User u WHERE u.username = :username";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        return !users.isEmpty();
    }



    @Override
    public User login(String enteredUsername, String enteredPassword) {
        try {
            logger.info("Attempting to log in to DB");
            em.getTransaction().begin();


            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            query.setParameter("username", enteredUsername);
            query.setParameter("password", enteredPassword);

            User user = query.getSingleResult();

            em.getTransaction().commit();
            return user;

        } catch (Exception ex) {
            logger.error("No user found with the provided username and password. Cause: {}", ex.getCause());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        }
    }

    @Override
    public List<User> getAllUsersFromDataBase() {
        logger.info("getAllUsersFromDataBase");
        List<User> users;
        em.getTransaction().begin();
        TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
        users = query.getResultList();
        em.getTransaction().commit();
        logger.info("Users Retrieved {}" , users.size() );
        return users;

    }




}

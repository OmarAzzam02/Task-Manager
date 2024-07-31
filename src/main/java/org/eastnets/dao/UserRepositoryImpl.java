//package org.eastnets.repository;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.eastnets.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.TypedQuery;
//import java.util.List;
//
//

//public class UserRepositoryImpl{
//    private static final Logger logger = LogManager.getLogger(UserRepositoryImpl.class);

//    EntityManager em;
//    UserRepository userRepository;
//
//    UserRepositoryImpl(){}
//
//
//    public UserRepositoryImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//
//    @Transactional
//    public void signup(User user) throws Exception {
//
//        try {
//
//            logger.info("Adding user: {} To Database " , user.getUsername());
//            if(userIsRegistered(user))
//                throw new Exception("User Already Registered");
//
//            userRepository.save(user);
//            logger.info("User added Successfully");
//        } catch (Exception ex) {
//            logger.error("{}   {}", ex.getMessage(), ex.getCause());
//            throw new Exception(ex);
//        }
//    }
//
//    @Transactional
//    public void update(User user) {
//
//        logger.info("attempting to update User in DB {} " , user.getUserId());
//        try {
//            userRepository.save(user);
//        } catch (Exception ex) {
//            logger.error("{}   {}", ex.getMessage(), ex.getCause());
//        }
//
//    }
//
//    @Transactional
//    public void updatePrivlage(User user) {
//        update(user);
//    }
//
//    public boolean userIsRegistered(User user) {
//            return userRepository.findById(user.getUserId()).isPresent();
//    }
//
//
//    public User login(String enteredUsername, String enteredPassword) {
//
//        try {
//            logger.info("Attempting to log in to DB");
//            return userRepository.login(enteredUsername , enteredPassword);
//        } catch (Exception ex) {
//            logger.error("No user found with the provided username and password. Cause: {}", ex.getCause());
//            return null;
//        }
//    }
//
//
//    public List<User> getAllUsersFromDataBase() {
//
//        try {
//
//            logger.info("getAllUsersFromDataBase");
//            List<User> users = userRepository.findAll();
//            logger.info("Users Retrieved {}", users.size());
//            return users;
//        } catch (Exception ex) {
//            logger.error("Error retrieving users. Cause: {}", ex.getCause());
//            return null;
//        }
//    }
//}

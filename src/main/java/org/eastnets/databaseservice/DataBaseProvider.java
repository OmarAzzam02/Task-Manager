package org.eastnets.databaseservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eastnets.entity.UserType;
import org.eastnets.entity.User;
import org.eastnets.entity.Task;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DataBaseProvider implements DataBaseService {
    private final static Logger logger = LogManager.getLogger(DataBaseProvider.class);
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("task-management");
    EntityManager em = emf.createEntityManager();

    public DataBaseProvider() {
    }


    @Override
    public List<User> getAllUsersFromDataBase() {
        logger.info("getAllUsersFromDataBase");
        List<User> users = new ArrayList<User>();
        em.getTransaction().begin();
        TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
        users = query.getResultList();
        em.getTransaction().commit();
        logger.info("Users Retrived {}" , users.size() );
        return users;

    }

    @Override
    public void addUser(User user) {


        try {
            logger.info("Adding user: {} To Database ", user.toString());
            if (userIsRegistered(user.getUsername()))
                throw new Exception("User is Already Registered");
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            logger.info("User added Successfully");

        } catch (Exception ex) {
            logger.error(ex.getMessage() + "   " + ex.getCause());
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
            logger.error("No user found with the provided username and password. Cause: " + ex.getCause());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        }
    }

    @Override
    public void insertTask(Task task) {

        try {
            em.getTransaction().begin();
            em.persist(task);
            em.getTransaction().commit();

        } catch (Exception ex) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            logger.error("Error in inserting task", ex.getMessage());
        }

    }

    public void assignTask(Task task, User user) {

        try {
            if (task == null || user == null) throw new Exception("TaskID or UserId is invalid");
            List<User> tasksAssign = task.getAssignedTo();
            if (tasksAssign.contains(user)) throw new Exception("User already assigned to this task");
            tasksAssign.add(user);
            task.setAssignedTo(tasksAssign);
            updateTask(task);
        } catch (Exception ex) {
            logger.error(ex.getMessage() + "   " + ex.getCause());
        }


    }

    @Override
    public void updateTask(Task task) {
        logger.info("attempting to update task in DB");
        try {
            em.getTransaction().begin();
            em.merge(task);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            logger.error(ex.getMessage() + "   " + ex.getCause());
        }


    }

    @Override
    public void deleteTask(Task task) {
        try {
            logger.info("attempting to delete task in DB");
            em.getTransaction().begin();
            em.remove(task);
            em.getTransaction().commit();

            logger.debug("Task deleted");
        } catch (Exception ex) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            logger.error(ex.getMessage() + "   " + ex.getCause());
        }

    }

    // TODO Whats The best and efficient way to filter the tasks without duplicating code ???
    @Override
    public List<Task> getAllTasksFromDB() {
        TypedQuery<Task> query = em.createNamedQuery("Task.findAll", Task.class);
        logger.info("tasks retreived {}", query.getResultList().size());
        return query.getResultList();
    }


    @Override
    public List<Task> getTasksByName(String name) {

        try {
            logger.info("attempting to get tasks By name in DB");
            TypedQuery<Task> sql = em.createQuery("select t from Task t where t.name = :name", Task.class);
            sql.setParameter("name", name);
            List<Task> tasks = sql.getResultList();
            logger.info(" # tasks found {} " ,  tasks.size());

            return tasks;
        } catch (Exception ex) {
            logger.error(ex.getMessage() + "   " + ex.getCause());
            return null;
        }

    }

    @Override
    public List<Task> getTasksByStatus(boolean status) {
        try {
            logger.info("attempting to get tasks By name in DB");
            TypedQuery<Task> sql = em.createQuery("select t from Task t where t.status = :status", Task.class);
            sql.setParameter("status", status);
            List<Task> tasks = sql.getResultList();
            logger.info(" # tasks found {} " ,  tasks.size());
            return tasks;
        } catch (Exception ex) {
            logger.error(ex.getMessage() + "   " + ex.getCause());
            return null;
        }
    }

    @Override
    public List<Task> getTasksByPriority(String priority) {
        try {
            logger.info("attempting to get tasks By name in DB");
            TypedQuery<Task> sql = em.createQuery("select t from Task t where t.priority = :priority", Task.class);
            sql.setParameter("priority", priority);
            List<Task> tasks = sql.getResultList();
            logger.info(" # tasks found {} " ,  tasks.size());
            return tasks;
        } catch (Exception ex) {
            logger.error(ex.getMessage() + "   " + ex.getCause());
            return null;
        }

    }

   public  List<User> getUsersByTaskId(int taskId){
        try {
            logger.info("Fetching users assigned to task ID: {}", taskId);


            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u JOIN u.tasksAssigned t WHERE t.taskId = :taskId",
                    User.class
            );
            query.setParameter("taskId", taskId);


            List<User> users = query.getResultList();

            return users;
        } catch (Exception ex) {
            logger.error("Error fetching users for task ID " + taskId + ": " + ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Task> getTasksByUserId(int userId) {
        try {
            logger.info("Fetching tasks assigned to user with ID: {}", userId);
            TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t JOIN t.assignedTo u WHERE u.userId = :userId", Task.class);
            query.setParameter("userId", userId);
            logger.info("Number of Tasks Retreived {} ",query.getResultList().size());
            return query.getResultList();
        } catch (Exception ex) {
            logger.error("Error fetching tasks for user ID " + userId + ": " + ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

}

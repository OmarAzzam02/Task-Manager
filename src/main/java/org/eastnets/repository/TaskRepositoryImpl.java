package org.eastnets.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eastnets.entity.Task;
import org.eastnets.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private final static Logger logger = LogManager.getLogger(TaskRepositoryImpl.class);
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("task-management");
    EntityManager em  = emf.createEntityManager();

    public TaskRepositoryImpl() {}

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

    public List<Task> getTasksByDueDate(String dueDate) {

        try {
            logger.info("Attempting to get Tasks By due date in DB");
            String jpql = "SELECT t FROM Task t WHERE t.dueDate = :dueDate";
            TypedQuery<Task> query = em.createQuery(jpql, Task.class);
            query.setParameter("dueDate", dueDate);
            return query.getResultList();

        }catch (Exception ex){
            logger.error("error getting tasks By DueDate" + ex.getMessage() + "   " + ex.getCause());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Task> getTaskById(int id) {
        try {
        logger.info("Attempting to get Task by ID: {}", id);
        String jpql = "SELECT t FROM Task t WHERE t.id = :id";
        TypedQuery<Task> query = em.createQuery(jpql, Task.class);
        query.setParameter("id", id);
        return query.getResultList();

        }catch (Exception ex){
            logger.error("{}   {}", ex.getMessage(), ex.getCause());
            return null;
        }
    }
}


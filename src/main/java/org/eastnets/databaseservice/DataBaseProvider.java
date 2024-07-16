package org.eastnets.databaseservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.logging.LoggerManager;
import org.eastnets.entity.UserType;
import org.eastnets.entity.User;
import org.eastnets.entity.Task;
import java.sql.*;
import java.util.Collections;
import java.util.List;

public class DataBaseProvider implements DataBaseService {
    private final static Logger logger = LogManager.getLogger(DataBaseProvider.class);
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;
    public DataBaseProvider() {
    }




    @Override
    public List<User> getAllUsersFromDataBase() {
        return Collections.emptyList();
    }
    @Override
    public void addUser(User user) {


        try {
            logger.info("Adding user: {} To Database "  , user.toString());
            if (!isConnected())
                connectToDataBase();

            if (userExists(user.getUsername()))
                logger.error( "User Already exists" , new Exception("User already exists"));


            String sql = "INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, EMAIL, ROLE) VALUES (users_id_seq.NEXTVAL, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUserType().name());

            if( statement.executeUpdate() <= 0 )
                logger.error("Couldn't add user to DataBase  " , new Exception("Couldn't add user to DataBase"));


        } catch (Exception ex) {
            logger.error(ex.getMessage() + "   " + ex.getCause());
        }


    }
    public void connectToDataBase() {

        String jdbcUrl = "jdbc:oracle:thin:@//localhost:1521/XE";
        String username = "system";
        String password = "manager";

        try {
            // Load the Oracle JDBC driver
            logger.info("attempting to connect to database");
            Class.forName("oracle.jdbc.OracleDriver");

            // Establish the connection
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the Oracle database successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error in connecting to the Oracle database", e);
        }

    }
    @Override
    public User login(String enteredUsername, String enteredPassword) {

        try {
            logger.info("attempting to login in DB");
            String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, enteredUsername);
            statement.setString(2, enteredPassword);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) logger.error( "No such User"  , new Exception("Entered wrong username or password"));
            logger.debug("user exists");
            return createUserFromResultSet(resultSet);

        } catch (Exception ex) {
           logger.error(ex.getMessage() + "   " + ex.getCause());
            return null;
        }

    }
    @Override
    public void insertTask(Task task) {
        try {

            logger.info("attempting to insert task in DB");

            if (!isConnected())
                connectToDataBase();

            String sql = "INSERT INTO TASKS (TASK_NAME, TASK_DESC, PRIORITY, DUE_DATE, COMPLETION_STATUS) VALUES (?, ?, ?, ?, ?) RETURNING TASK_ID";
            statement = connection.prepareStatement(sql);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getPriority().name());
            statement.setDate(4, Date.valueOf(task.getDueDate().toString()));
            statement.setInt(5, task.getStatus() ? 1 : 0);
            resultSet = statement.executeQuery();

            if (!resultSet.next()) logger.error(  "Error adding Task To DB " ,  new Exception("Something went wrong"));

            int taskId = resultSet.getInt("TASK_ID");

            logger.debug("Task Created");
            assignTask(taskId, task.getAssignedTo());

        } catch (Exception ex) {
            logger.error(ex.getMessage() + "   " + ex.getCause());
        }

    }
    public void assignTask(int taskId, List<Integer> userId) {

        try {
            logger.info("attempting to assign task in DB");
            if (!isValidUser(userId) || !isValidTask(taskId) )
             logger.error( "not valid user or not a valid task "  , new Exception("Error assigning task"));

            logger.debug("User and task are valid");
            int i = 0;
            while (i < userId.size()  && !isAssigned(taskId , userId.get(i))) {

                String sql = "INSERT INTO TASKS_ASSIGNED (TASK_ID, USER_ID) VALUES (?, ?)";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, taskId);
                statement.setInt(2, userId.get(i++));
                int row = statement.executeUpdate();
                if(row < 0) logger.error(new Exception("Task  Not Assigned Successfully"));

                logger.debug("Task Assigned Successfully");
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage() + "   " + ex.getCause());

        }


    }
    @Override
    public void updateTask(Task task) {
            logger.info("attempting to update task in DB");
            try {
                if (!isConnected())
                    connectToDataBase();

                String sql = "UPDATE TASK SET  TASK_NAME = ? , TASK_DESC = ? ,  PRIORITY = ?  ,  DUE_DATE = ? ,  COMPLETION_STATUS = ?  ,  WHERE TASK_ID = ? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, task.getName());
                statement.setString(2, task.getDescription());
                statement.setString(3, task.getPriority().name());
                statement.setDate(4, Date.valueOf(task.getDueDate().toString()));
                statement.setInt(5, task.getStatus() ? 1 : 0);
                statement.setInt(6, task.getTaskId());
                int rows = statement.executeUpdate();
                assignTask(task.getTaskId() , task.getAssignedTo());

                if(rows < 0) logger.error(new Exception("Task  Not Assigned Successfully"));

                logger.debug("Task updated");
            }catch (Exception ex){
                logger.error(ex.getMessage() + "   " + ex.getCause());

            }




    }
    @Override
    public void deleteTask(Task task) {

        try {
            logger.info("attempting to delete task in DB");
            if (!isConnected())
                connectToDataBase();

            if(!isValidTask(task.getTaskId())) logger.error("not a valid task" , new Exception("Not A valid task"));

            String sql = "DELETE FROM TASKS WHERE TASK_ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getTaskId());

            int rows = statement.executeUpdate();
            deleteTaskAssignment(task.getTaskId());
            if (rows < 0) logger.error( new Exception("Something went wrong"));
            logger.debug("Task deleted");
        } catch (Exception ex) {
            logger.error(ex.getMessage() + "   " + ex.getCause());
        }

    }
    // TODO Whats The best and efficient way to filter the tasks without duplicating code ???
    @Override
    public List<Task> getAllTasksFromDB() {
      return Collections.emptyList();
    }

    @Override
    public Task getTaskById(int id) {
        return null;
    }
    @Override
    public List<Task> getTasksByName(String name) {
        return Collections.emptyList();
    }
    @Override
    public List<Task> getTasksByStatus(String status) {
        return Collections.emptyList();
    }
    @Override
    public List<Task> getTasksByPriority(String priority) {
        return Collections.emptyList();
    }
    private int getAssignedTo(int taskId) {

        return 0;
    }
    //-------------------------------------------------

    private void deleteTaskAssignment(int taskId) {

        try {
            String sql = "DELETE FROM TASKS_ASSIGNED WHERE TASK_ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taskId);
            int rows = statement.executeUpdate();
            if (rows < 0) throw new Exception("Something went wrong");
            logger.error("Task Deleted Successfully");

        }catch (Exception ex){
           logger.error(ex.getMessage() + "   " + ex.getCause());
        }
    }
    private boolean isAssigned(int taskId, int userId) {

        try {
            String sql = "SELECT * FROM TASKS WHERE TASK_ID = ? AND USER_ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.setInt(2, userId);
            resultSet = statement.executeQuery();
            return resultSet.next();
        }catch (Exception ex){
          logger.error(ex.getMessage() + "   " + ex.getCause());
            return false;
        }
    }
    private boolean isValidTask(int taskId) {
        try {
            if (!isConnected())
                connectToDataBase();

            String sql = "SELECT * FROM TASKS WHERE TASK_ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taskId);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) throw new Exception("No Such Task ");

        } catch (Exception ex) {
            logger.error(ex.getMessage() + "   " + ex.getCause());
            return false;
        }
        return true;
    }
    private boolean isValidUser(List<Integer> userId) {

        logger.info("Attempting to check if user valid ");
        try {

            for (Integer id : userId) {
                String sql = "SELECT * FROM USERS WHERE USER_ID = ?";

                statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    logger.debug("User found (valid )");
                    return true;
                }


            }


        } catch (Exception ex) {
            logger.error(ex.getMessage() + "   " + ex.getCause());

        }
        logger.debug("No User Found in The DataBase ");
        return false;
    }
    private boolean userExists(String username) {

        try {

            logger.info("attempting to check if user exists ");

            String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (Exception e) {
           logger.error(e.getMessage());
            return false;
        }
    }
    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        logger.info("Attempting to create a new user with result set");
        int userId = resultSet.getInt("USER_ID");
        String username = resultSet.getString("USERNAME");
        String password = resultSet.getString("PASSWORD");
        String email = resultSet.getString("EMAIL");
        String userTypeStr = resultSet.getString("ROLE");

        UserType userType = UserType.valueOf(userTypeStr);

        return new User(userId, username, password, email, userType);
    }
    private boolean isConnected() {
        try {
            return connection == null || connection.isClosed();
        } catch (Exception e) {
            return true;
        }
    }
}

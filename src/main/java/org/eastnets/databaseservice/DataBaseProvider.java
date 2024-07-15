package org.eastnets.databaseservice;

import org.eastnets.entity.UserType;
import org.eastnets.entity.User;
import org.eastnets.entity.Task;
import java.sql.*;
import java.util.Collections;
import java.util.List;

public class DataBaseProvider implements DataBaseService {

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

            if (!isConnected())
                connectToDataBase();

            if (userExists(user.getUsername())) throw new Exception("User already exists");

            String sql = "INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, EMAIL, ROLE) VALUES (users_id_seq.NEXTVAL, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUserType().name());

            statement.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "   " + ex.getCause());
        }


    }
    public void connectToDataBase() {

        String jdbcUrl = "jdbc:oracle:thin:@//localhost:1521/XE";
        String username = "system";
        String password = "manager";


        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Establish the connection
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the Oracle database successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Could not connect to the Oracle database!" + e.getCause() + e.getMessage());
        }

    }
    @Override
    public User login(String enteredUsername, String enteredPassword) {

        try {

            String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, enteredUsername);
            statement.setString(2, enteredPassword);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) throw new Exception("Entered wrong username or password");

            return createUserFromResultSet(resultSet);

        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "   " + ex.getCause());
            return null;
        }

    }
    @Override
    public void insertTask(Task task) {
        try {
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

            if (!resultSet.next()) throw new Exception("Something went wrong");

            int taskId = resultSet.getInt("TASK_ID");
            assignTask(taskId, task.getAssignedTo());

        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "   " + ex.getCause());
        }

    }
    public void assignTask(int taskId, List<Integer> userId) {

        try {
            if (isValidUser(userId) && isValidTask(taskId) ) throw new Exception("Error assigning task");
            int i = 0;
            while (i < userId.size()  && !isAssigned(taskId , userId.get(i))) {

                String sql = "INSERT INTO TASKS_ASSIGNED (TASK_ID, USER_ID) VALUES (?, ?)";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, taskId);
                statement.setInt(2, userId.get(i++));
                int row = statement.executeUpdate();
                if(row > 0) System.out.println("Task Assigned Successfully");


            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "   " + ex.getCause());

        }


    }
    @Override
    public void updateTask(Task task) {

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

                if(rows > 0) System.out.println("Task Updated Successfully");

            }catch (Exception ex){
                System.out.println(ex.getMessage() + "   " + ex.getCause());

            }




    }
    @Override
    public void deleteTask(Task task) {

        try {
            if (!isConnected())
                connectToDataBase();

            String sql = "DELETE FROM TASKS WHERE TASK_ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getTaskId());

            int rows = statement.executeUpdate();
            deleteTaskAssignment(task.getTaskId());
            if (rows < 0) throw new Exception("Something went wrong");

        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "   " + ex.getCause());
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
            System.out.println("Task Deleted Successfully");

        }catch (Exception ex){
            System.out.println(ex.getMessage() + "   " + ex.getCause());
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
            System.out.println(ex.getMessage() + "   " + ex.getCause());
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
            System.out.println(ex.getMessage() + "   " + ex.getCause());
            return false;
        }
        return true;
    }
    private boolean isValidUser(List<Integer> userId) {

        try {

            for (Integer id : userId) {
                String sql = "SELECT * FROM USERS WHERE USER_ID = ?";

                statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                if (resultSet.next()) return true;


            }


        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "   " + ex.getCause());

        }
        return false;
    }
    private boolean userExists(String username) {

        try {

            String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
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

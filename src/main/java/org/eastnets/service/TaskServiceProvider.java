package org.eastnets.service;

import org.eastnets.databaseservice.DataBaseProvider;
import org.eastnets.entity.Task;
import org.eastnets.entity.UserType;
import java.util.List;

public class TaskServiceProvider implements TaskService {
    DataBaseProvider db = new DataBaseProvider();

    @Override
    public void addTask(Task task, UserType userType) throws Exception {
        if (!userType.hasCreatePrivlage()) throw new Exception("User Does not have the previlage");

        db.insertTask(task);
    }

    @Override
    public void updateTask(Task task, UserType userType) {

        try {
            if (!userType.hasEditPrivlage()) throw new Exception("you dont have the previlage");
            db.updateTask(task);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void deleteTask(Task task, UserType userType) {
        try {
            if (userType.hasDeletePrivlage())
                db.deleteTask(task);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void assignTask(Task task, int userId, UserType userType) {
        if (userType.hasAssignPrivlage())
            db.assignTask(task.getTaskId(), task.getAssignedTo());

        else System.out.println("You dont have the previlage");
    }

    @Override
    public void addAssignedTo(Task task, int assignedToId, UserType userType) {
        try {
            if (!userType.hasAssignPrivlage()) throw new Exception("User does not have the previlage");

            List<Integer> assigns = task.getAssignedTo();
            assigns.add(assignedToId);
            task.setAssignedTo(assigns);
            db.updateTask(task);


        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + e.getCause());
        }
    }

    @Override
    public void removeAssignedTo(Task task, int assignedToId, UserType userType) {

        try {
            if (!userType.hasAssignPrivlage()) throw new Exception("User does not have the prevlage");

            List<Integer> assigns = task.getAssignedTo();
            assigns.remove(assignedToId);
            task.setAssignedTo(assigns);
            db.updateTask(task);

        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + ex.getCause());
        }

    }


}

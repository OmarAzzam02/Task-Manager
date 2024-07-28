package org.eastnets.entity;

public enum UserType
{

    MANAGER{
        @Override
        public  boolean hasCreatePrivlage(){
            return true;
        }
        @Override
        public  boolean hasEditPrivlage(){
            return true;
        }
        @Override
        public  boolean hasDeletePrivlage(){
            return true;
        }

        @Override
        public  boolean hasAssignPrivlage(){
            return true;
        }
        @Override
        public boolean hasViewAllTasksAndUsersPrivlage(){
            return true;
        }

        @Override
        public boolean hasUpdatePrivlage() {
            return true;
        }

    },

    SENIOR{
        @Override
        public boolean hasViewAllTasksAndUsersPrivlage(){
            return false;
        }
        @Override
        public  boolean hasCreatePrivlage(){
            return false;
        }
        @Override
        public  boolean hasDeletePrivlage(){
            return false;
        }

        @Override
        public  boolean hasEditPrivlage(){
            return true;
        }

        @Override
        public boolean hasUpdatePrivlage() {
            return false;
        }

        @Override
        public  boolean hasAssignPrivlage(){
            return true;
        }
    },

    JUNIOR{

        @Override
        public boolean hasViewAllTasksAndUsersPrivlage(){
            return false;
        }

        @Override
        public  boolean hasCreatePrivlage(){
            return false;
        }
        @Override
        public  boolean hasDeletePrivlage(){
            return false;
        }

        @Override
        public  boolean hasEditPrivlage(){
            return false;
        }

        @Override
        public  boolean hasAssignPrivlage(){
            return false;
        }

        @Override
        public boolean hasUpdatePrivlage() {
            return false;
        }
    };

    public  boolean hasViewOwnTasks(){
        return true;
    }
    public abstract boolean hasViewAllTasksAndUsersPrivlage();
    public abstract boolean hasCreatePrivlage();
    public abstract boolean hasEditPrivlage();
    public abstract boolean hasDeletePrivlage();
    public abstract boolean hasAssignPrivlage();
    public abstract boolean hasUpdatePrivlage();




}

package org.eastnets;
import org.eastnets.databaseservice.DataBaseProvider;

public class Main {
    public static void main(String[] args) {

        DataBaseProvider db = new DataBaseProvider();
        db.connectToDataBase();

    }
}
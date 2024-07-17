package org.eastnets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eastnets.databaseservice.DataBaseProvider;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        System.out.println("Hi im using logger ");
        DataBaseProvider db = new DataBaseProvider();
        db.connectToDataBase();
        logger.info("Start");
        logger.debug("might cause problems ");
        logger.warn("might cause problems ");
        logger.error(" problem ");
    }
}

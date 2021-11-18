package com.milk.restapp.util;

import org.flywaydb.core.Flyway;

/**
 * @author Jack Milk
 */
public class FlywayUtil {

    private static final String URL = "jdbc:mysql://localhost/rest";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void flywayMigrations() {
        System.out.println("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        System.out.println("db migration finished.");
    }

}

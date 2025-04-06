package com.example.oop2025_week09.TestDb;
//we need maven (
import com.example.oop2025_week09.db.DbUtil;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        try{
            conn = DbUtil.getConnection();
            Statement stmt = conn.createStatement();
//            stmt.execute("DROP TABLE IF EXISTS students");
//            System.out.println("Dropping table students");
//            stmt.execute(   "CREATE TABLE IF NOT EXISTS students (" +
//                    "  id INT PRIMARY KEY, " +
//                    "  name VARCHAR(100) NOT NULL, " +
//                    "  email VARCHAR(100) NOT NULL UNIQUE" +
//                    ")"
//            );
//
//            stmt.execute("INSERT INTO students VALUES (" +
//                            "1001, 'Bobby Connolly', 'bob@bob.com')");
//            stmt.execute("INSERT INTO students VALUES (" +
//                            "1001, 'Bobby Connolly', 'bob@bob.com')");

            System.out.println("Student record created");

            ResultSet rs = stmt.executeQuery("select * from students");

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);

                System.out.println(id + " " + name + " " + email);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("Finished.");

    }
}

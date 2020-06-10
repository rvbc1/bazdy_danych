import java.sql.*;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Base {

    private void createTables() {
        String agent = "CREATE TABLE IF NOT EXISTS Agent( " 
            + "tutorial_id INT NOT NULL AUTO_INCREMENT, "
            + "tutorial_title VARCHAR(100) NOT NULL, " 
            + "tutorial_author VARCHAR(40) NOT NULL, "
            + "submission_date DATE, " 
            + "PRIMARY KEY ( tutorial_id )); ";
        SQL.update(agent);
    }

    Base() {
        SQL.open();
        String query = "INSERT INTO Agent "
        + "(tu_id, dane_pers_id, adres_id)"
        + "VALUES "
        + "('1', '1', '1');";
        //SQL.update(query);
      //  searchKlientForm();
    }

    public static ResultSet searchKlient(long pesel){
        String querry = "SELECT " +
            "*" +
            " FROM " +
            "Klient" +
            " WHERE " +
            "pesel=" + pesel + ";";

         return SQL.exe(querry);
    }

    public static ResultSet searchAgent(int id){
        String querry = "SELECT " +
            "*" +
            " FROM " +
            "Agent" +
            " WHERE " +
            "pesel=" + id + ";";

         return SQL.exe(querry);
    }

    // SELECT Orders.OrderID, Customers.CustomerName
    // FROM Orders
    // INNER JOIN Customers ON Orders.CustomerID = Customers.CustomerID; 

    public static ResultSet searchPolisyKlienta(long pesel){
        if(pesel > 0){
            String querry = "SELECT " +
                "concat(Dane_Pers.imie, ' ', Dane_Pers.nazwisko) AS Klient, Polisa.*" +
                " FROM " +
                "Polisa" +
                " INNER JOIN Klient ON Polisa.klient_pesel = Klient.pesel" +
                " INNER JOIN Dane_Pers ON Klient.dane_pers_id = Dane_Pers.id" +
                " WHERE " +
                "klient_pesel=" + pesel + ";";
            return SQL.exe(querry);   
        }
        return null;
    }

    // public static ResultSet searchPolisyKlienta(long pesel){
    //     if(pesel > 0){
    //         String querry = "SELECT " +
    //             "concat(Dane_Pers.imie, ' ', Dane_Pers.nazwisko) AS Klient, Polisa.*" +
    //             " FROM " +
    //             "Polisa" +
    //             " INNER JOIN Klient ON Polisa.klient_pesel = Klient.pesel" +
    //             " INNER JOIN Dane_Pers ON Klient.dane_pers_id = Dane_Pers.id" +
    //             " WHERE " +
    //             "klient_pesel=" + pesel + ";";
    //         return SQL.exe(querry);   
    //     }
    //     return null;
    // }

    public static ResultSet searchDanePersonalne(int id){
        String querry = "SELECT " +
            "*" +
            " FROM " +
            "Dane_Pers" +
            " WHERE " +
            "id=" + id + ";";

         return SQL.exe(querry);
    }

    public static ResultSet searchAdres(int id){
        String querry = "SELECT " +
            "*" +
            " FROM " +
            "Adres" +
            " WHERE " +
            "id=" + id + ";";

         return SQL.exe(querry);
    }
}
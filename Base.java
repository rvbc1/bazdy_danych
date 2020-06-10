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
            "id=" + id + ";";

         return SQL.exe(querry);
    }

    public static ResultSet searchAgenciTU(long regon){
        if(regon > 0){
        String querry = "SELECT " +
            "concat(Dane_Pers.imie, ' ', Dane_Pers.nazwisko) AS Agent, " +
            "Adres.ulica, Adres.miejscowosc, Adres.kod" +
            " FROM " +
            "Agent" +
            " INNER JOIN Dane_Pers ON Agent.dane_pers_id = Dane_Pers.id" +
            " INNER JOIN Adres ON Agent.adres_id = Adres.id" +
            " WHERE " +
            "tu_regon=" + regon + ";";

         return SQL.exe(querry);
        }
        return null;
    }

    public static ResultSet searchAgenciTUWithId(long regon){
        if(regon > 0){
        String querry = "SELECT " +
            "concat(Dane_Pers.imie, ' ', Dane_Pers.nazwisko) AS Agent, Agent.id AS ID, " +
            "Adres.ulica, Adres.miejscowosc, Adres.kod" +
            " FROM " +
            "Agent" +
            " INNER JOIN Dane_Pers ON Agent.dane_pers_id = Dane_Pers.id" +
            " INNER JOIN Adres ON Agent.adres_id = Adres.id" +
            " WHERE " +
            "tu_regon=" + regon + ";";

         return SQL.exe(querry);
        }
        return null;
    }

    public static ResultSet allTU(){
        String querry = "SELECT " +
            "TU.nazwa, Adres.ulica, Adres.miejscowosc, Adres.kod, TU.regon" +
            " FROM " +
            "TU" +
            " INNER JOIN Adres ON TU.adres_id = Adres.id;";

         return SQL.exe(querry);
    }

    public static ResultSet searchTU(long regon){
        String querry = "SELECT " +
            "*" +
            " FROM " +
            "TU" +
            " WHERE " +
            "regon=" + regon + ";";

         return SQL.exe(querry);
    }

    // SELECT Orders.OrderID, Customers.CustomerName
    // FROM Orders
    // INNER JOIN Customers ON Orders.CustomerID = Customers.CustomerID; 

    public static ResultSet searchPolisyKlienta(long pesel){
        if(pesel > 0){
            String querry = "SELECT " +
                "Polisa.nazwa AS Opis, Polisa_Rodzaj.rodzaj AS Rodzaj, " +
                "concat(Dane_Pers.imie, ' ', Dane_Pers.nazwisko) AS Agent, " +
                "TU.nazwa AS Ubezpieczyciel , Polisa.cena AS \"Cena [zł]\", Polisa.start AS Od, Polisa.koniec AS Do" +
                " FROM " +
                "Polisa" +
                " INNER JOIN Polisa_Rodzaj ON Polisa.polisa_rodzaj_id = Polisa_Rodzaj.id" +
                " INNER JOIN Agent ON Polisa.agent_id = Agent.id" +
                " INNER JOIN Dane_Pers ON Agent.dane_pers_id = Dane_Pers.id" +
                " LEFT JOIN TU ON Polisa.tu_regon = TU.regon" +
                " WHERE " +
                "klient_pesel=" + pesel + ";";
            return SQL.exe(querry);   
        }
        return null;
    }

    public static ResultSet searchPolisyAgenta(int id){
        if(id > 0){
            String querry = "SELECT " +
                "concat(Dane_Pers.imie, ' ', Dane_Pers.nazwisko) AS Klient, " +
                "Polisa.nazwa AS Opis, Polisa_Rodzaj.rodzaj AS Rodzaj, " +
                "TU.nazwa AS Ubezpieczyciel , Polisa.cena AS \"Cena [zł]\", Polisa.start AS Od, Polisa.koniec AS Do" +
                " FROM " +
                "Polisa" +
                " INNER JOIN Polisa_Rodzaj ON Polisa.polisa_rodzaj_id = Polisa_Rodzaj.id" +
                " INNER JOIN Klient ON Polisa.klient_pesel = Klient.pesel" +
                " INNER JOIN Dane_Pers ON Klient.dane_pers_id = Dane_Pers.id" +
                " LEFT JOIN TU ON Polisa.tu_regon = TU.regon" +
                " WHERE " +
                "agent_id=" + id + ";";
            return SQL.exe(querry);   
        }
        return null;
    }

    public static ResultSet searchPolisyTU(long regon){
        if(regon > 0){
            String querry = "SELECT " +
                "concat(Dane_Pers.imie, ' ', Dane_Pers.nazwisko) AS Agent, " +
                "Polisa.nazwa AS Opis, Polisa_Rodzaj.rodzaj AS Rodzaj, " +
                "Polisa.cena AS \"Cena [zł]\", Polisa.start AS Od, Polisa.koniec AS Do" +
                " FROM " +
                "Polisa" +
                " INNER JOIN Polisa_Rodzaj ON Polisa.polisa_rodzaj_id = Polisa_Rodzaj.id" +
                " INNER JOIN Klient ON Polisa.klient_pesel = Klient.pesel" +
                " INNER JOIN Dane_Pers ON Klient.dane_pers_id = Dane_Pers.id" +
                " WHERE " +
                "tu_regon=" + regon + ";";
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
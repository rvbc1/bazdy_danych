import java.sql.*;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Base {
    Base() {
        SQL.open();
    }

    public static int addAdres(String ulica, String miejscowosc, String kod){
        int id = 1;
        String query = "SELECT MAX(id) " +
        "FROM Adres;";
        ResultSet rs = SQL.exe(query);
        try {
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        String update = "INSERT INTO Adres "
        + "(id, ulica, miejscowosc, kod)"
        + "VALUES "
        + "('" + id + "', '" + ulica + "', '" + miejscowosc + "', '" + kod + "');";
        SQL.update(update);
        return id;
    }

    public static int addDanePers(String imie, String nazwisko){
        int id = 1;
        String query = "SELECT MAX(id) " +
        "FROM Dane_Pers;";
        ResultSet rs = SQL.exe(query);
        try {
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        String update = "INSERT INTO Dane_Pers "
        + "(id, imie, nazwisko)"
        + "VALUES "
        + "('" + id + "', '" + imie + "', '" + nazwisko + "');";
        SQL.update(update);
        return id;
    }

    public static void addKlient(long pesel, int dane_pers_id, int adres_id){
        String update = "INSERT INTO Klient "
        + "(pesel, dane_pers_id, adres_id)"
        + "VALUES "
        + "('" + pesel + "', '" + dane_pers_id + "', '" + adres_id + "');";
        SQL.update(update);
    }

    public static int addAgent(long tu_regon, int dane_pers_id, int adres_id){
        int id = 1;
        String query = "SELECT MAX(id) " +
        "FROM Agent;";
        ResultSet rs = SQL.exe(query);
        try {
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String update = "INSERT INTO Agent "
        + "(id, tu_regon, dane_pers_id, adres_id)"
        + "VALUES "
        + "('" + id + "', '" + tu_regon + "', '" + dane_pers_id + "', '" + adres_id + "');";
        SQL.update(update);
        return id;
    }

    public static int addPolisa(long klient_pesel, int agent_id, String nazwa, int rodzaj_id, float cena, String start, String koniec){
        int id = 1;
        long tu_regon = -1;
        String query = "SELECT MAX(id) " +
        "FROM Polisa;";
        ResultSet rs = SQL.exe(query);
        try {
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        query = "SELECT Agent.tu_regon " +
        "FROM Agent WHERE Agent.id=" + agent_id + ";";
        rs = SQL.exe(query);
        try {
            if (rs.next()) {
                tu_regon = rs.getLong(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String update = "INSERT INTO Polisa "
        + "(id, klient_pesel, agent_id, tu_regon, nazwa, polisa_rodzaj_id, cena, start, koniec)"
        + "VALUES "
        + "('" + id + "', '" + klient_pesel + "', '" + agent_id + "', '" + tu_regon + "', '" + nazwa + "', '" + rodzaj_id + "', '" + cena + "', '" + start + "', '" + koniec + "');";
        SQL.update(update);
        return id;
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
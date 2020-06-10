public class Agent {
    Adres adres;
    int adres_id;
    String pesel;
    String imie;
    String nazwisko;
    String ost_log;

    void addQuery(){
        String query    = "INSERT INTO Agent "
                        + "(id, tu_id, dane_pers_id, adres_id)"
                        + "VALUES "
                        + "(`1`, `1`, `1`, `1`, `1`, `1`);";
    }
}
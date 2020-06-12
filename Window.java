import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.*;
import java.util.Vector;
import java.awt.event.*;

public class Window extends JFrame implements Runnable {
    public static String wyszukane_imie;
    public static String wyszukane_nazwisko;
    public static String wyszukane_tu;

    private static Window this_pointer;

    @Override
    public void run() {
        init();
    }

    public void init() {
        setTitle("Polisy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(300, 165);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        add(panel);

        JButton klient = new JButton("klient");
        klient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long pesel = searchKlientForm();
                if(pesel > 0){
                    Window.this_pointer.dispose();
                    Base.updateLoginDateKlient(pesel);
                    new Thread(new WindowKlient(pesel)).start();
                }
            }
        });


        JButton agent = new JButton("agent");
        agent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //searchTUForm();
                int id = searchAgentForm();
                if(id > 0){
                    Window.this_pointer.dispose();
                    Base.updateLoginDateAgent(id);
                    new Thread(new WindowAgent(id)).start();
                }
            }
        });

        JButton tu = new JButton("TU");
        tu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //tableGUI(Base.searchAgenciTU(searchTUForm()), "Agenci " + wyszukane_tu);
                long regon = searchTUForm();
                if(regon > 0){
                    Window.this_pointer.dispose();
                    new Thread(new WindowTU(regon)).start();
                }
            }
        });


        panel.add(klient);
        panel.add(agent);
        panel.add(tu);
    }

    public Window() {
        this_pointer = this;
    }

    public static long searchKlientForm(){
        // String[] items = {"One", "Two", "Three", "Four", "Five"};
        // JComboBox combo = new JComboBox(items);
        JTextField pesel_field = new JTextField();
        JTextField hide_pesel_field = new JTextField();

        JTextField imie_field = new JTextField();
        JTextField nazwisko_field = new JTextField();
        JTextField adres_ulica_field = new JTextField();
        JTextField adres_miejscowosc_field = new JTextField();
        JTextField adres_kod_field = new JTextField();

        imie_field.setEditable(false);
        nazwisko_field.setEditable(false);
        adres_ulica_field.setEditable(false);
        adres_miejscowosc_field.setEditable(false);
        adres_kod_field.setEditable(false);

        JButton search_button = new JButton("szukaj");
        search_button.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                if(pesel_field.getText().length() > 0 ){
                    try {
                    long klient_pesel = Long.parseLong(pesel_field.getText());
                    ResultSet rs_klient = Base.searchKlient(klient_pesel);
                        if(rs_klient.next()){
                            hide_pesel_field.setText(Long.toString(klient_pesel));
                            int dane_pers_id = rs_klient.getInt("dane_pers_id");
                            int adres_id = rs_klient.getInt("adres_id");
                            ResultSet rs_dane_pers = Base.searchDanePersonalne(dane_pers_id);
                            if(rs_dane_pers.next()){
                                imie_field.setText(rs_dane_pers.getString("imie"));
                                nazwisko_field.setText(rs_dane_pers.getString("nazwisko"));
                            }

                            ResultSet rs_adres = Base.searchAdres(adres_id);
                            if(rs_adres.next()){
                                adres_ulica_field.setText(rs_adres.getString("ulica"));
                                adres_miejscowosc_field.setText(rs_adres.getString("miejscowosc"));
                                adres_kod_field.setText(rs_adres.getString("kod"));
                            }
                        }
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (NumberFormatException e2){
                        //e2.printStackTrace();
                    }
                }
            } 
          } );

        JPanel panel = new JPanel(new GridLayout(0, 1));
      //  panel.add(combo);
        panel.add(new JLabel("Pesel:"));
        panel.add(pesel_field);
        panel.add(search_button);
        panel.add(new JLabel("Imie:"));
        panel.add(imie_field);
        panel.add(new JLabel("Nazwisko:"));
        panel.add(nazwisko_field);
        panel.add(new JLabel("Adres:"));
        panel.add(adres_ulica_field);
        panel.add(adres_miejscowosc_field);
        panel.add(adres_kod_field);

       int result = JOptionPane.showConfirmDialog(null, panel, "Szukaj Klienta",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println(//combo.getSelectedItem()
                 " " + imie_field.getText()
                + " " + nazwisko_field.getText());
            if(imie_field.getText().length() > 0) {
                wyszukane_imie = imie_field.getText();
                wyszukane_nazwisko = nazwisko_field.getText();
                return Long.parseLong(hide_pesel_field.getText());
            } else
                return -1;
        } else {
            System.out.println("Cancelled");
            return -1;
        }
    }

    public static int searchAgentForm(){
        // String[] items = {"One", "Two", "Three", "Four", "Five"};
        // JComboBox combo = new JComboBox(items);
        JTextField id_field = new JTextField();
        JTextField hide_id_field = new JTextField();

        JTextField imie_field = new JTextField();
        JTextField nazwisko_field = new JTextField();
        JTextField adres_ulica_field = new JTextField();
        JTextField adres_miejscowosc_field = new JTextField();
        JTextField adres_kod_field = new JTextField();

        imie_field.setEditable(false);
        nazwisko_field.setEditable(false);
        adres_ulica_field.setEditable(false);
        adres_miejscowosc_field.setEditable(false);
        adres_kod_field.setEditable(false);

        JButton search_button = new JButton("Szukaj Agenta");
        search_button.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                if(id_field.getText().length() > 0 ){
                    try {
                    int agent_id = Integer.parseInt(id_field.getText());
                    ResultSet rs_agent = Base.searchAgent(agent_id);
                        if(rs_agent.next()){
                            hide_id_field.setText(Integer.toString(agent_id));
                            int dane_pers_id = rs_agent.getInt("dane_pers_id");
                            int adres_id = rs_agent.getInt("adres_id");
                            ResultSet rs_dane_pers = Base.searchDanePersonalne(dane_pers_id);
                            if(rs_dane_pers.next()){
                                imie_field.setText(rs_dane_pers.getString("imie"));
                                nazwisko_field.setText(rs_dane_pers.getString("nazwisko"));
                            }

                            ResultSet rs_adres = Base.searchAdres(adres_id);
                            if(rs_adres.next()){
                                adres_ulica_field.setText(rs_adres.getString("ulica"));
                                adres_miejscowosc_field.setText(rs_adres.getString("miejscowosc"));
                                adres_kod_field.setText(rs_adres.getString("kod"));
                            }
                        }
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (NumberFormatException e2){
                        //e2.printStackTrace();
                    }
                }
            } 
          } );

        JPanel panel = new JPanel(new GridLayout(0, 1));
      //  panel.add(combo);
        panel.add(new JLabel("ID:"));
        panel.add(id_field);
        panel.add(search_button);
        panel.add(new JLabel("Imie:"));
        panel.add(imie_field);
        panel.add(new JLabel("Nazwisko:"));
        panel.add(nazwisko_field);
        panel.add(new JLabel("Adres:"));
        panel.add(adres_ulica_field);
        panel.add(adres_miejscowosc_field);
        panel.add(adres_kod_field);

       int result = JOptionPane.showConfirmDialog(null, panel, "Szukaj Klienta",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println(//combo.getSelectedItem()
                 " " + imie_field.getText()
                + " " + nazwisko_field.getText());
            if(imie_field.getText().length() > 0) {
                wyszukane_imie = imie_field.getText();
                wyszukane_nazwisko = nazwisko_field.getText();
                return Integer.parseInt(hide_id_field.getText());
            } else
                return -1;
        } else {
            System.out.println("Cancelled");
            return -1;
        }
    }

    public static long searchTUForm(){
        JTextField regon_field = new JTextField();
        JTextField hide_regon_field = new JTextField();

        JTextField nazwa_field = new JTextField();
        JTextField adres_ulica_field = new JTextField();
        JTextField adres_miejscowosc_field = new JTextField();
        JTextField adres_kod_field = new JTextField();

        nazwa_field.setEditable(false);
        adres_ulica_field.setEditable(false);
        adres_miejscowosc_field.setEditable(false);
        adres_kod_field.setEditable(false);

        JButton search_button = new JButton("Szukaj TU");
        search_button.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                if(regon_field.getText().length() > 0 ){
                    try {
                    long tu_regon = Long.parseLong(regon_field.getText());
                    ResultSet rs_tu = Base.searchTU(tu_regon);
                        if(rs_tu.next()){
                            hide_regon_field.setText(Long.toString(tu_regon));

                            nazwa_field.setText(rs_tu.getString("nazwa"));

                            int adres_id = rs_tu.getInt("adres_id");
                            ResultSet rs_adres = Base.searchAdres(adres_id);
                            if(rs_adres.next()){
                                adres_ulica_field.setText(rs_adres.getString("ulica"));
                                adres_miejscowosc_field.setText(rs_adres.getString("miejscowosc"));
                                adres_kod_field.setText(rs_adres.getString("kod"));
                            }
                        }
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (NumberFormatException e2){
                        //e2.printStackTrace();
                    }
                }
            } 
          } );

        JPanel panel = new JPanel(new GridLayout(0, 1));
      //  panel.add(combo);
        panel.add(new JLabel("Regon:"));
        panel.add(regon_field);
        panel.add(search_button);
        panel.add(new JLabel("Nazwa:"));
        panel.add(nazwa_field);
        panel.add(new JLabel("Adres:"));
        panel.add(adres_ulica_field);
        panel.add(adres_miejscowosc_field);
        panel.add(adres_kod_field);

       int result = JOptionPane.showConfirmDialog(null, panel, "Szukaj TU",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if(nazwa_field.getText().length() > 0) {
                wyszukane_tu = nazwa_field.getText();
                return Long.parseLong(hide_regon_field.getText());
            } else
                return -1;
        } else {
            System.out.println("Cancelled");
            return -1;
        }
    }

    public static void tableGUI(ResultSet rs, String title) {
        if (rs != null) {
            JFrame polsy_window = new JFrame();
            polsy_window.setTitle(title);

            polsy_window.setSize(800, 150);
            
            try {
                JTable table = new JTable(buildTableModel(rs));

                JScrollPane scrollPane = new JScrollPane(table);
            

                polsy_window.add(scrollPane, BorderLayout.CENTER);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            



            polsy_window.setLocationRelativeTo(null);

            polsy_window.setVisible(true);
        }
    }

    
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnLabel(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        rs.beforeFirst();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    public static long addKlientForm(){
        JTextField pesel_field = new JTextField();

        JTextField imie_field = new JTextField();
        JTextField nazwisko_field = new JTextField();
        JTextField adres_ulica_field = new JTextField();
        JTextField adres_miejscowosc_field = new JTextField();
        JTextField adres_kod_field = new JTextField();

        JButton search_button = new JButton("Dane klienta");
        search_button.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                if(pesel_field.getText().length() > 0 ){
                    try {
                    long klient_pesel = Long.parseLong(pesel_field.getText());
                    ResultSet rs_klient = Base.searchKlient(klient_pesel);
                        if(rs_klient.next()){
                            int dane_pers_id = rs_klient.getInt("dane_pers_id");
                            int adres_id = rs_klient.getInt("adres_id");
                            ResultSet rs_dane_pers = Base.searchDanePersonalne(dane_pers_id);
                            if(rs_dane_pers.next()){
                                imie_field.setText(rs_dane_pers.getString("imie"));
                                nazwisko_field.setText(rs_dane_pers.getString("nazwisko"));
                            }

                            ResultSet rs_adres = Base.searchAdres(adres_id);
                            if(rs_adres.next()){
                                adres_ulica_field.setText(rs_adres.getString("ulica"));
                                adres_miejscowosc_field.setText(rs_adres.getString("miejscowosc"));
                                adres_kod_field.setText(rs_adres.getString("kod"));
                            }
                        }
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (NumberFormatException e2){
                        //e2.printStackTrace();
                    }
                }
            } 
          } );

        JPanel panel = new JPanel(new GridLayout(0, 1));
      //  panel.add(combo);
        panel.add(new JLabel("Pesel:"));
        panel.add(pesel_field);
        panel.add(search_button);
        panel.add(new JLabel("Imie:"));
        panel.add(imie_field);
        panel.add(new JLabel("Nazwisko:"));
        panel.add(nazwisko_field);
        panel.add(new JLabel("Adres:"));
        panel.add(new JLabel("ulica:"));
        panel.add(adres_ulica_field);
        panel.add(new JLabel("miejscowosc:"));
        panel.add(adres_miejscowosc_field);
        panel.add(new JLabel("kod:"));
        panel.add(adres_kod_field);

       int result = JOptionPane.showConfirmDialog(null, panel, "Szukaj Klienta",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if(pesel_field.getText().length() > 0 && imie_field.getText().length() > 0 && nazwisko_field.getText().length() > 0
                && adres_ulica_field.getText().length() > 0 && adres_miejscowosc_field.getText().length() > 0 && adres_kod_field.getText().length() > 0) {
                
                Base.addKlient(Long.parseLong(pesel_field.getText()),
                Base.addDanePers(imie_field.getText(), nazwisko_field.getText()),
                Base.addAdres(adres_ulica_field.getText(), adres_miejscowosc_field.getText(), adres_kod_field.getText()));

                wyszukane_imie = imie_field.getText();
                wyszukane_nazwisko = nazwisko_field.getText();
                return Long.parseLong(pesel_field.getText());
            } else
                return -1;
        } else {
            System.out.println("Cancelled");
            return -1;
        }
    }

    public static long addAgentForm(long tu_regon){

        JTextField imie_field = new JTextField();
        JTextField nazwisko_field = new JTextField();
        JTextField adres_ulica_field = new JTextField();
        JTextField adres_miejscowosc_field = new JTextField();
        JTextField adres_kod_field = new JTextField();


        JPanel panel = new JPanel(new GridLayout(0, 1));
      //  panel.add(combo);
        panel.add(new JLabel("Imie:"));
        panel.add(imie_field);
        panel.add(new JLabel("Nazwisko:"));
        panel.add(nazwisko_field);
        panel.add(new JLabel("Adres:"));
        panel.add(new JLabel("ulica:"));
        panel.add(adres_ulica_field);
        panel.add(new JLabel("miejscowosc:"));
        panel.add(adres_miejscowosc_field);
        panel.add(new JLabel("kod:"));
        panel.add(adres_kod_field);

       int result = JOptionPane.showConfirmDialog(null, panel, "Dane Agenta",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if(imie_field.getText().length() > 0 && nazwisko_field.getText().length() > 0
                && adres_ulica_field.getText().length() > 0 && adres_miejscowosc_field.getText().length() > 0 && adres_kod_field.getText().length() > 0) {
                
                int id = Base.addAgent(tu_regon,
                    Base.addDanePers(imie_field.getText(), nazwisko_field.getText()),
                    Base.addAdres(adres_ulica_field.getText(), adres_miejscowosc_field.getText(), adres_kod_field.getText()));

                wyszukane_imie = imie_field.getText();
                wyszukane_nazwisko = nazwisko_field.getText();
                return id;
            } else
                return -1;
        } else {
            System.out.println("Cancelled");
            return -1;
        }
    }

    public static long addPolisaForm(long klient_pesel, int agent_id){
        String[] polisa_rodzaj = { "OC Auto", "OC+AC Auto", "DOM", "OC zawodowe", "Zdrowotne" };
        JComboBox polisaList = new JComboBox(polisa_rodzaj);

        JTextField nazwa_field = new JTextField();
        JTextField start_field = new JTextField();
        JTextField koniec_field = new JTextField();

        SpinnerModel model = new SpinnerNumberModel(999.99, 0.01, 10000, 0.01);     
        JSpinner spinner = new JSpinner(model);


        JPanel panel = new JPanel(new GridLayout(0, 1));
      //  panel.add(combo);
        panel.add(new JLabel("Nazwa:"));
        panel.add(nazwa_field);
        panel.add(new JLabel("Rodzaj:"));
        panel.add(polisaList);
        panel.add(new JLabel("Początek RRRR-MM-DD:"));
        panel.add(start_field);
        panel.add(new JLabel("Koniec RRRR-MM-DD:"));
        panel.add(koniec_field);
        panel.add(new JLabel("cena:"));
        panel.add(spinner);

       int result = JOptionPane.showConfirmDialog(null, panel, "Dane Polisy",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if(nazwa_field.getText().length() > 0 && start_field.getText().length() > 0 && 
                koniec_field.getText().length() > 0) {
                
                int id = Base.addPolisa(klient_pesel, agent_id, nazwa_field.getText(), polisaList.getSelectedIndex() + 1, ((Double)spinner.getValue()).floatValue(), start_field.getText(), koniec_field.getText());

                return id;
            } else
                return -1;
        } else {
            System.out.println("Cancelled");
            return -1;
        }
    }
}
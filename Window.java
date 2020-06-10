import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.*;
import java.util.Vector;
import java.awt.event.*;

public class Window extends JFrame implements Runnable {

    @Override
    public void run() {
        init();
    }

    public void init() {
        setTitle("Hello World");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(300, 165);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        add(panel);

        JButton search_button = new JButton("szukaj klienta");
        search_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchKlientForm();
            }
        });

        JButton search_polisy = new JButton("polisy Klienta");
        search_polisy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                polisyKlienta(Base.searchPolisyKlienta(searchKlientForm()));
            }
        });

        panel.add(search_button);
        panel.add(search_polisy);
    }

    public Window() {

    }

    public void polisyKlienta(ResultSet rs_polisy) {
        if (rs_polisy != null) {
            JFrame polsy_window = new JFrame();
            polsy_window.setTitle("klient_window");

            polsy_window.setSize(600, 150);
            polsy_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            try {
                JTable table = new JTable(buildTableModel(rs_polisy));

                JScrollPane scrollPane = new JScrollPane(table);
            

                polsy_window.add(scrollPane, BorderLayout.CENTER);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            


            //add(scrollPane, BorderLayout.CENTER);


            polsy_window.setLocationRelativeTo(null);

            polsy_window.setVisible(true);
        }
    }

    public long searchKlientForm(){
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
                long klient_pesel = Long.parseLong(pesel_field.getText());
                ResultSet rs_klient = Base.searchKlient(klient_pesel);

                try {
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

       int result = JOptionPane.showConfirmDialog(null, panel, "Test",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println(//combo.getSelectedItem()
                 " " + imie_field.getText()
                + " " + nazwisko_field.getText());
            if(imie_field.getText().length() > 0)
                return Long.parseLong(hide_pesel_field.getText());
            else
                return -1;
        } else {
            System.out.println("Cancelled");
            return -1;
        }
    }
    
    public static DefaultTableModel buildTableModel(ResultSet rs)
        throws SQLException {

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
}
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class WindowKlient extends JFrame implements Runnable {
    public long klient_pesel;
    @Override
    public void run() {
        init();
    }

    public void init() {
        setTitle("Klient");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(300, 165);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        add(panel);


        JButton search_polisy = new JButton("moje polisy");
        search_polisy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.tableGUI(Base.searchPolisyKlienta(klient_pesel), "Polisy klienta " + Window.wyszukane_imie + " " + Window.wyszukane_nazwisko);
            }
        });

        JButton search_tu = new JButton("szukaj Ubezpieczalni");
        search_tu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.searchTUForm();
            }
        });

        JButton search_agents = new JButton("szukaj Agentów Ubezpieczalni");
        search_agents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.tableGUI(Base.searchAgenciTU(Window.searchTUForm()), "Agenci " + Window.wyszukane_tu);
            }
        });

        JButton all_tu = new JButton("Wszystkie Ubezpieczalnie");
        all_tu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.tableGUI(Base.allTU(), "Ubezpieczalnie");
            }
        });


        panel.add(search_polisy);
        panel.add(search_tu);
        panel.add(search_agents);
        panel.add(all_tu);
    }

    public WindowKlient(long pesel) {
        this.klient_pesel = pesel;
    }
}
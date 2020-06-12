import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class WindowTU  extends JFrame implements Runnable{
    public long tu_regon;
    @Override
    public void run() {
        init();
    }

    public void init() {
        setTitle("TU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(300, 165);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        add(panel);

        JButton search_button = new JButton("szukaj klienta");
        search_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.searchKlientForm();
            }
        });

        JButton search_polisy = new JButton("polisy Klienta");
        search_polisy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.tableGUI(Base.searchPolisyKlientaTU(Window.searchKlientForm(), tu_regon), "Polisy klienta " + Window.wyszukane_imie + " " + Window.wyszukane_nazwisko);
            }
        });

        JButton search_polisy_agenta = new JButton("moje polisy");
        search_polisy_agenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.tableGUI(Base.searchPolisyTU(tu_regon), "Polisy TU");
            }
        });

        JButton search_agents = new JButton("wyświetl Agentów");
        search_agents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.tableGUI(Base.searchAgenciTUWithId(tu_regon), "Agenci " + Window.wyszukane_tu);
            }
        });

        JButton add_agent = new JButton("dodaj agenta");
        add_agent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.addAgentForm(tu_regon);
            }
        });


        panel.add(search_button);
        panel.add(search_polisy);
        panel.add(search_polisy_agenta);
        panel.add(search_agents);
        panel.add(add_agent);
    }

    public WindowTU(long regon) {
        this.tu_regon = regon;
    }
}
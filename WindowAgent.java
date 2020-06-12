import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class WindowAgent extends JFrame implements Runnable{
    public int agent_id;
    @Override
    public void run() {
        init();
    }

    public void init() {
        setTitle("Agent");
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
                Window.tableGUI(Base.searchPolisyKlientaTU(Window.searchKlientForm(), Base.searchAgentTU(agent_id)), "Polisy klienta " + Window.wyszukane_imie + " " + Window.wyszukane_nazwisko);
            }
        });

        JButton search_polisy_agenta = new JButton("moje polisy");
        search_polisy_agenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.tableGUI(Base.searchPolisyAgenta(agent_id), "Polisy agenta");
            }
        });

        JButton add_klient = new JButton("dodaj klienta");
        add_klient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.addKlientForm();
            }
        });

        JButton add_polisa = new JButton("dodaj polise");
        add_polisa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.addPolisaForm(Window.searchKlientForm(), agent_id);
            }
        });



        panel.add(search_button);
        panel.add(search_polisy);
        panel.add(search_polisy_agenta);
        panel.add(add_klient);
        panel.add(add_polisa);
    }

    public WindowAgent(int id) {
        this.agent_id = id;
    }
}
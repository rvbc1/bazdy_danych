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
                Window.searchKlientForm();
            }
        });

        JButton search_polisy = new JButton("polisy Klienta");
        search_polisy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.tableGUI(Base.searchPolisyKlienta(Window.searchKlientForm()), "Polisy klienta " + Window.wyszukane_imie + " " + Window.wyszukane_nazwisko);
            }
        });

        JButton search_polisy_agenta = new JButton("moje polisy");
        search_polisy_agenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window.tableGUI(Base.searchPolisyAgenta(agent_id), "Polisy klienta " + Window.wyszukane_imie + " " + Window.wyszukane_nazwisko);
            }
        });


        panel.add(search_button);
        panel.add(search_polisy);
        panel.add(search_polisy_agenta);
    }

    public WindowAgent(int id) {
        this.agent_id = id;
    }
}
package pojo;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SnakeCell {

    private final JPanel body;
    private final JLabel number;


    public SnakeCell(int number) {
        body = new JPanel();
        this.number = new JLabel("" + number);
        this.number.setForeground(Color.WHITE);
        body.setPreferredSize(new Dimension(25, 25));
        body.setSize(25, 25);
        body.setVisible(true);
        body.setBackground(Color.white);
        body.add(this.number);
    }

    public JPanel getBody() {
        return body;
    }

}

package message;


import javax.swing.*;

public class Message {

    public Message() {
    }

    public void upozorneni(String upozorneni) {
        JOptionPane frame = new JOptionPane();
        JOptionPane.showMessageDialog(frame,
                upozorneni);
    }

    public String vyber(String upozorneni, String title, Object[] moznosti, String prvni) {

        JOptionPane frame = new JOptionPane();

        return (String) JOptionPane.showInputDialog(frame, upozorneni, title, JOptionPane.PLAIN_MESSAGE, null, moznosti, prvni);
    }

    public String napis() {
        final JPanel panel = new JPanel();
        final JRadioButton button1 = new JRadioButton("1");
        final JRadioButton button2 = new JRadioButton("2");

        panel.add(button1);
        panel.add(button2);

        JOptionPane.showMessageDialog(null, panel);
       return "as";




    }


}


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class infoFrame extends JFrame {
    public infoFrame(String id){
        super(id + "'s info");
        ImageIcon img = new ImageIcon("C:/Logo_Icon.png");
        setIconImage(img.getImage());
        setVisible(true);
        JTextArea text = new JTextArea(4, 20);
        JScrollPane scroll = new JScrollPane(text);
        add(scroll);
        text.append(id);
        text.append("\nName: " + defaults.SQL.getName(id));
        text.append("\nEmail: " + defaults.SQL.getEmail(id));
        text.append("\nExtension: " + defaults.SQL.getExt(id));
        text.setEditable(false);
        pack();
    }
}

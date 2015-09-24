
import java.awt.Dimension;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class defaults {
    //static final String imgPath = "/resources/File_Icon.png";
    static final ImageIcon img = new ImageIcon("resources/File_Icon.png");
    static final int minHeight = 35;
    static final int minWidth = 100;
    static final int msgLoglimit = 25;
    public static InetAddress IP = null;
    public static iThread input = null;
    public static oThread output = null;
    public static userListFrame ULF = null;
    public static HashMap CFHashes = new HashMap();
    public static HashMap NPHashes = new HashMap();
    public static int idx = 0;
    public static String uid = null;
    public static SQLAgent SQL = null;;

   
}

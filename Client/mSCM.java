import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class mSCM {
    static final String imgPath = "/File_Icon.png";
	public static void main(String args[]){
		boolean connected = false;
		Socket cSocket = null;
		File file = new File("C:/Messenger/");
                file.mkdirs();
                file = new File("C:/Messenger/ipPref.lol");
                
                
                
                
                try{
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    InetAddress IP = (InetAddress) ois.readObject();
                    ois.close();
                    cSocket = new Socket(IP, 9376);
                }
                catch(Exception f){
		while(connected == false){
                    
                    
		try{
			String IP = (String) JOptionPane.showInputDialog("Please enter an IP address");
			if(IP == null){
				System.exit(1);
			}
			cSocket = new Socket(InetAddress.getByName(IP), 9376);
			connected = true;
                        FileOutputStream fos = new FileOutputStream(file);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(InetAddress.getByName(IP));
                        oos.close();
		}
		catch(IOException e){
			System.out.println(e);
		}
		}
                }
		defaults.output = new oThread(cSocket);
		defaults.output.start();
		defaults.input = new iThread(cSocket);
                defaults.IP = cSocket.getInetAddress();
		loginFrame login = new loginFrame(cSocket, defaults.input, defaults.output, defaults.IP);
		defaults.input.linkLogin(login);
		defaults.input.start();
		
		
	}
	
	
	
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class oThread extends Thread {
	ObjectOutputStream output = null;
	
	public oThread(Socket Sock){
		try {
			output = new ObjectOutputStream(Sock.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(Message M){
		try {
			output.writeObject(M);
                        output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
        public void send(ArrayList AL){
		try {
			output.writeObject(AL);
                        output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

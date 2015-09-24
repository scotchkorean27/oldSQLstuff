import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class sThread extends Thread {
        Message last = null;
	Socket cSocket = null;
	ObjectInputStream input = null;
	ObjectOutputStream output = null;
	int loggedin = 2;
	int ID = 0;
	zero3 admin = null;
	DBHandler SQLAgent = null;
	HashMap OnlineList = null;
	boolean clientAlive = true;
	Message mess = null;
	boolean stable = true;
	InetAddress IP = null;
	public sThread(zero3 z3, HashMap Hashes, Socket Sock, DBHandler SQL){
		admin = z3;
		SQLAgent = SQL;
		OnlineList = Hashes;
		cSocket = Sock;
	}
	
	public void run(){
		try{
		IP = cSocket.getInetAddress();
		input = new ObjectInputStream(cSocket.getInputStream());
		output = new ObjectOutputStream(cSocket.getOutputStream());}
		catch(Exception e){
			e.printStackTrace();
		}
		while(stable){
			try {
                                last = mess;
				mess = (Message) input.readObject();
                                
			}
			catch(Exception e){
				kill();
			}
			
			
			if(loggedin == 3){
                                int sender = mess.getSender();
                                int group = mess.getRecipient();
                                String message = mess.getMessage();
                                if(sender == -5){
                                    SQLAgent.recordFile(ID, group, message);
                                }
                                else if(sender== -3){
                                	System.out.println("Request to delete received");
                                    SQLAgent.deleteFile(mess);
                                }
                                else if(sender == -4){
                                    ArrayList<Integer> idxs = null;
                                    try {
                                        idxs = (ArrayList<Integer>) input.readObject();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    
                                    SQLAgent.createGroup(idxs);
                                    sendNot(25);
                                }
                                else{
                                    //M.setSender(ID);
                                    if(mess.equals(last)){
                                    System.out.println("Last word intercepted");
                                    }
                                    else{
                                    System.out.println("Grp: " +mess.getRecipient());
                                    SQLAgent.recordMessage(mess);}}
			}
			else{
                            int sender = mess.getSender();
                                int group = mess.getRecipient();
                                String message = mess.getMessage();
                                String uid = null;
				try {
					if(sender == -1){
                                        uid = mess.getMessage();
                                        Message M2 = (Message) input.readObject();
                                        String pwd = M2.getMessage();
					loggedin = SQLAgent.verifyLogin(uid, pwd, IP);}
					else if(sender == -2){
						uid = mess.getMessage();
                                                mess = (Message) input.readObject();
                                                String pwd = mess.getMessage();
                                                mess = (Message) input.readObject();
                                                String name = mess.getMessage();
                                                mess = (Message) input.readObject();
                                                String email = mess.getMessage();
                                                mess = (Message) input.readObject();
                                                String ext = mess.getMessage();
						loggedin = SQLAgent.registerLogin(
                                                        uid, pwd, name, email, ext, IP);
					}
					output.writeInt(loggedin);
					output.flush();
				} catch(Exception e){
					kill();
					stable = false;
					System.out.println(e);
				}
				if(loggedin == 3){
					ID = SQLAgent.getIDX(uid);
                                        sendNot(ID);
					OnlineList.put("" + ID, this);
					admin.announceLogin(ID, IP);
					sendNot(7);
				}
			}
			
		}
		
		
	}
	
	public void kill(){
		OnlineList.remove("" +ID);
		stable = false;
		admin.announceLogout(ID, IP);
		try {
			output.close();
			input.close();
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println();
		}
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			System.out.println(ID + " has logged out");
		}
	}
	
	public void sendNot(int code){
		try {
			output.writeInt(code);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendObj(Object obj){
		try {
			output.writeObject(obj);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getID(){
		return ID;
	}
	
        public void waiting (int n){
        
        long t0, t1;

        t0 =  System.currentTimeMillis();

        do{
            t1 = System.currentTimeMillis();
        }
        while ((t1 - t0) < (n * 1000));
    }
}

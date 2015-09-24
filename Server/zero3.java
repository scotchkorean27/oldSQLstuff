import java.net.*;
import java.io.*;
import java.util.*;
public class zero3 {
	HashMap usrList = null;
	DBHandler DataBaser = null;
	ArrayList<String> onlineList = null;
	public zero3(HashMap Hashes){
		usrList = Hashes;
	}
	
	
        
        public void relayMessage(int i){
            try{
            sThread S = (sThread) usrList.get("" + i);
            S.sendNot(7);}
            catch(Exception e){
                System.out.println(e);
            }
        }
        
	
	public void specialCommands(){
		
	}
	
	public void massDisconnect(){
		Set St = usrList.entrySet();
		Iterator It = St.iterator();
		while(It.hasNext()){
			Map.Entry E = (Map.Entry) It.next();
			sThread P = (sThread) E.getValue();
			P.kill();}
		System.exit(1);
	}
	
	public void specificDisconnect(int ID, int code){
		sThread P = (sThread) usrList.get("" +ID);
		P.sendNot(code);
		P.kill();
		
	}
	
	public void announceLogin(int ID, InetAddress IP){
		int name = ID;
		System.out.println("Zero3: " + ID + " has logged in!");
		Set St = usrList.entrySet();
		Iterator It = St.iterator();
		onlineList = new ArrayList();
		while(It.hasNext()){
			Map.Entry E = (Map.Entry) It.next();
			sThread P = (sThread) E.getValue();
			onlineList.add("" + P.getID());
			P.sendNot(22);
			P.sendNot(name);
			}
		((sThread) usrList.get("" + ID)).sendNot(24);
		String S = "";
		for(String p : onlineList){
			S = S + p + ",";
		}
                System.out.println("Usr List: " + S);
		((sThread) usrList.get("" +ID)).sendObj(S);
		DataBaser.recordLogin(ID, IP);
	}
	
	public void announceLogout(int ID, InetAddress IP){
		int name = ID;
		System.out.println("Zero3: "  + ID + " has logged out.");
		Set St = usrList.entrySet();
		Iterator It = St.iterator();
		while(It.hasNext()){
			Map.Entry E = (Map.Entry) It.next();
			sThread P = (sThread) E.getValue();
			P.sendNot(23);
			P.sendNot(name);
			}
		DataBaser.recordLogout(ID, IP);
	}
	
	
	public boolean checkLogStatus(int idx){
		if(usrList.containsKey("" + idx)){
			return false;
		}
		return true;
	}
	
	public void assignSQLH(DBHandler DBH){
		DataBaser = DBH;
	}
	
}

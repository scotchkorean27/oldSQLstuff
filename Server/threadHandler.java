import java.net.*;
import java.io.*;
import java.util.*;
public class threadHandler {
	ServerSocket sSocket = null;
	ServerSocket fsSocket = null;
	HashMap onlineList = null;
	zero3 Z3 = null;
	DBHandler SQLAgent = null;
	controlPanel cP = null;
	public threadHandler(){
		try {
			sSocket = new ServerSocket(9376);
			fsSocket = new ServerSocket(9377);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		onlineList = new HashMap();
		Z3 = new zero3(onlineList);
		SQLAgent = new DBHandler(Z3);
		Z3.assignSQLH(SQLAgent);
		cP = new controlPanel(Z3);
		newSThread();
	}
	
	
	public void newSThread(){
		Socket cSocket = null;
			try{
				cSocket = sSocket.accept();
				sThread S = new sThread(Z3, onlineList, cSocket, SQLAgent);
				S.start();
				newSThread();
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
public class iThread extends Thread {
	ObjectInputStream input = null;
	loginFrame login = null;
	registerFrame register = null;
	public iThread(Socket cSocket){
		try {
			input = new ObjectInputStream(cSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	public void run(){
		while(true){
			int code = 0;
			try {
				code = input.readInt();
				System.out.println(code);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				kill("Error.  Close the program and start again.");
				break;
			}
			
			if(code == 2){
				login.relog("Invalid Username/Password");
			}
			else if(code == 3){
                            try {
                                defaults.idx = input.readInt();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
				login.success();
                                
			}
			else if(code == 7){
				defaults.SQL.retrieveMessages(defaults.idx);
			}
			else if(code == 9){
				kill("You have been disconnected from the server.");
			}
			else if(code == 12){
				register.success();
			}
			else if(code == 13){
				register.relog("Error. Registration failed");
                                
			}
			else if(code == 14){
				register.relog("Username already in use");
			}
			else if(code == 15){
				register.relog("Invalid username/password");
			}
			else if(code == 16){
				login.relog("Already logged in. Please log out first");
			}
			else if(code == 17){
				login.relog("Account not activated.  Please contact Administrator.");
			}
			else if(code == 18){
				login.relog("Login failed. Please see server administrator.");
			}
			else if(code == 19){
				kill("You have logged in from a separate location.\nYou have been disconnected.");
			}
			else if(code == 21){
			}
			else if(code == 22){
				int name = 0;
				try {
					name = input.readInt();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                                try{
                                System.out.println(name);
				((namePlate) defaults.NPHashes.get("" +name)).login();}
                                catch(Exception e){
                                    
                                }
			}
			else if(code == 23){
				int name = 0;
				try {
					name = input.readInt();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                                System.out.println(name);
				((namePlate) defaults.NPHashes.get("" +name)).logout();
			}
			else if(code == 24){
				String oList = null;
				try {
					oList = (String) input.readObject();
                                        System.out.println("oList: " + oList);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				while(oList.length() > 0){
					try{
						String sub = oList.substring(0, oList.indexOf(","));
						oList = oList.replaceFirst(sub, "");
						oList = oList.replaceFirst(",", "");
                                                System.out.println(oList);
						((namePlate) defaults.NPHashes.get("" +sub)).login();
					}
					
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
                        else if(code == 25){
                            defaults.ULF.confirmG();
                        }
		}
	}
	
	public void linkLogin(loginFrame log){
		login = log;
	}
	
	public void linkReg(registerFrame reg){
		register = reg;
	}
	
	
	public void kill(String info){
		try{
		input.close();}
		catch(Exception e){
			System.out.println(e);
		}
		
		JOptionPane.showMessageDialog(new JFrame(), info);
		
		
	}
	
	
	
	
        
        public void relay(int grp, String message){
            if(defaults.CFHashes.containsKey(""+ grp)){
			((convoFrame) defaults.CFHashes.get("" + grp)).print(message);
			((convoFrame) defaults.CFHashes.get("" + grp)).setVisible(true);
		}
		else{
                    ArrayList<String> AL = defaults.SQL.getGroupUID(grp);
                    defaults.ULF.newConvo(grp, AL);
                    
			//((convoFrame) defaults.CFHashes.get("" + grp)).print(message);
			((convoFrame) defaults.CFHashes.get("" + grp)).setVisible(true);
		}
        }
        
        
}

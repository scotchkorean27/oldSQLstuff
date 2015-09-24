import javax.swing.*;

import java.awt.GridLayout;
import java.io.*;
import java.net.*;
import java.awt.event.*;

public class loginFrame extends JFrame {
	JLabel usrL = new JLabel("ID");
	JTextArea usrT = new JTextArea(1, 10);
	JPanel usrP = new JPanel();
	JLabel pwdL = new JLabel("Password");
	JPasswordField pwdT = new JPasswordField(10);
	JButton login = new JButton("Log in");
	JButton register = new JButton("Register");
        JButton change = new JButton("Change IP");
	JPanel butP = new JPanel();
	iThread ithread = null;
	registerFrame reg = null;
	oThread output = null;
	Socket socks;
	loginFrame lF = null;
	public loginFrame(Socket Sock, iThread thread, oThread out, InetAddress IP){
                setIconImage(defaults.img.getImage());
		ithread = thread;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		output = out;
		setTitle("Login (" + IP + ")");
		setResizable(false);
		usrP.add(usrL);
		usrP.add(usrT);
		usrP.add(pwdL);
		usrP.add(pwdT);
		butP.add(login);
		butP.add(register);
                butP.add(change);
		add(usrP);
		add(butP);
		setVisible(true);
		ithread = thread;
		socks = Sock;
		lF = this;
		setLayout(new GridLayout(2,1));
		usrP.setLayout(new GridLayout(2,2));
		pack();
		
		
		
		usrT.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == 9){
					pwdT.requestFocusInWindow();
					e.consume();
				}
				else if(e.getKeyCode() ==  10){
					send();
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		pwdT.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() ==  10){
					send();
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		login.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				send();
			}
		});
		
                change.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				cIP();
			}
		});
                
		register.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				setVisible(false);
				reg = new registerFrame(lF);
				reg.setVisible(true);
				ithread.linkReg(reg);
			}
		});
		
	}
	
	public void send(){
                output.send(new Message(usrT.getText(), -1, 0));
		output.send(new Message(new String(pwdT.getPassword()), -1, 0));
		
	}
	
	public void relog(String info){
		JOptionPane.showMessageDialog(new JFrame(), info);
		pwdT.setText("");
	}
	
	public void success(){
		JOptionPane.showMessageDialog(new JFrame(), "Login successful!");
		setVisible(false);
                defaults.IP = socks.getInetAddress();
                defaults.uid = usrT.getText();
		userListFrame ULF = new userListFrame();
	}
	
	public void cIP(){
            File file = new File("C:/Messenger/ipPref.lol");
            boolean unconnected = true;
            while(unconnected){
            try{
			String IP = (String) JOptionPane.showInputDialog("Please enter an IP address");
                        if(IP == null){
				System.exit(1);
			}
			socks = new Socket(InetAddress.getByName(IP), 9376);
                        FileOutputStream fos = new FileOutputStream(file);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(InetAddress.getByName(IP));
                        oos.close();
                        unconnected = false;
                        setTitle("Login (" + IP + ")");
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(new JFrame(), "Could not connect to the server");
		}}
                defaults.output = new oThread(socks);
		defaults.output.start();
		defaults.input = new iThread(socks);
		defaults.input.linkLogin(this);
		defaults.input.start();
		}
              
		
        
	
}

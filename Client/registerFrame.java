import javax.swing.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.*;
import java.net.*;
import java.awt.event.*;

public class registerFrame extends JFrame {
	oThread output;
	JLabel usrL = new JLabel("ID");
	JTextArea usrT = new JTextArea(1, 10);
	JPanel usrP = new JPanel();
	JLabel pwdL = new JLabel("Password");
	JPasswordField pwdT = new JPasswordField(10);
	JLabel pwdL2 = new JLabel("Confirm");
	JPasswordField pwdT2 = new JPasswordField(10);
	JButton cancel = new JButton("Cancel");
	JButton register = new JButton("Register");
	JPanel butP = new JPanel();
	iThread ithread = null;
	loginFrame log = null;
	JLabel fnL = new JLabel("Full name (optional");
	JTextArea fnT = new JTextArea(1, 30);
	JLabel emL = new JLabel("Work Email Address");
	JTextArea emT = new JTextArea(1, 30);
	JLabel exL = new JLabel("Extension");
	JTextArea exT = new JTextArea(1, 30);
	JPanel opP = new JPanel();
	boolean optional  = false;
        ImageIcon img = new ImageIcon("/Logo_Icon.png");
	public registerFrame(loginFrame login){
                setIconImage(img.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		usrT.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		fnT.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		emT.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		exT.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		output = defaults.output;
		setTitle("Register");
		setResizable(false);
		usrP.add(usrL);
		usrP.add(usrT);
		usrP.add(pwdL);
		usrP.add(pwdT);
		usrP.add(pwdL2);
		usrP.add(pwdT2);
		butP.add(register);
		butP.add(cancel);
		opP.add(fnL);
		opP.add(fnT);
		opP.add(emL);
		opP.add(emT);
		opP.add(exL);
		opP.add(exT);
		add(usrP);;
		add(opP);
		add(butP);
                
		setVisible(false);
		log = login;
		
		setLayout(new GridLayout(3,1));
		usrP.setLayout(new GridLayout(3,2));
		opP.setLayout(new GridLayout(3,2));
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
		
		fnT.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == 9){
					emT.requestFocusInWindow();
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
		
		emT.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == 9){
					exT.requestFocusInWindow();
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
		
		pwdT2.addKeyListener(new KeyListener(){
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
		
		exT.addKeyListener(new KeyListener(){
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
		
		register.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				send();
			}
		});
		
		cancel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				setVisible(false);
				log.setVisible(true);
			}
		});
		
	}
	
	public void send(){
		
		if((new String(pwdT.getPassword()).equals(new String(pwdT2.getPassword()))) == true && optional == false){
		output.send(new Message(usrT.getText(), -2, 0));
                output.send(new Message(new String(pwdT.getPassword()), -2, 0));
                output.send(new Message(fnT.getText(), -2, 0));
                output.send(new Message(emT.getText(), -2, 0));
                output.send(new Message(exT.getText(), -2, 0));}
		else if((new String(pwdT.getPassword()).equals(new String(pwdT2.getPassword()))) == false && optional == false){
			relog("Passwords do not match!");}
		
		
	}
	
	public void relog(String info){
		JOptionPane.showMessageDialog(new JFrame(), info);
                usrT.setText("");
		pwdT.setText("");
		pwdT2.setText("");
		optional = false;
		validate();
		repaint();
		pack();
	}
	
	public void success(){
		JOptionPane.showMessageDialog(new JFrame(), "Registration successful!");
		setVisible(false);
		log.setVisible(true);
	}
	
	
	
	
}

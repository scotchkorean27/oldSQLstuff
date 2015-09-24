import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class userListFrame extends JFrame {
        boolean GConfirmed = false;
	ArrayList<Message> SNameList = new ArrayList();
	JButton start = new JButton("Start");
        JButton Mailbox = new JButton("Open FileBox");
	int ID = 0;
        SQLAgent SQL = new SQLAgent();
        JPanel window = new JPanel();
        JScrollPane scroll = new JScrollPane(window);
        fileFrame fF = null;
        int coldis = 0;
	public userListFrame(){
                super(defaults.uid);
                defaults.ULF = this;
                defaults.SQL = SQL;
                ID = defaults.idx;
                setIconImage(defaults.img.getImage());
                add(scroll);
                SNameList.add(new Message(defaults.uid, ID, 0));
                window.setLayout(new BoxLayout(window, BoxLayout.PAGE_AXIS));
                window.setMaximumSize(new Dimension(100, 1000));
                fF = new fileFrame();
		setResizable(false);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		setVisible(true);
		SQL.retrieveUserList();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                add(Mailbox);
		add(start);
		start.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
                                if(SNameList.size() > 0){
                                String idxs = "";
                                ArrayList<String> uids = new ArrayList();
                                ArrayList<Integer> idxList = new ArrayList();
                                for(Message M: SNameList){
                                    idxs += M.getSender() + ",";
                                    uids.add(M.getMessage());
                                    idxList.add(M.getSender());
                                }
                                idxs = idxs.substring(0, idxs.length()- 1);
                                int group = SQL.findGroup(idxs, SNameList.size());
                                System.out.println("SQLResult:" + group);
                                if(group == 0){
                                    defaults.output.send(new Message("", -4, 0));
                                    defaults.output.send(idxList);
                                    waitForGCon();
                                    group = SQL.findGroup(idxs, SNameList.size());
                                }
				newConvo(group, uids);}
                                else{
                                    JOptionPane.showMessageDialog(new JFrame(), "You must select a user!");
                                }
			}
		});
                Mailbox.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				fF.setVisible(true);
			}
		});
		
		
		
		setSize(110, 500);
		repaint();
		validate();
	}
	
	public void addCList(ArrayList<Message> names, String title ){
		collapList cList = new collapList(title, names, SNameList);
		cList.setLocation(coldis, 0);
		coldis = coldis + 100;
		window.add(cList);
		validate();
		repaint();
		pack();
	}
	
	
        
       
        
        public void newConvo(int group, ArrayList<String> uidList){
            if(defaults.CFHashes.containsKey("" + group) == false){
            convoFrame cF = new convoFrame(group, uidList);
            defaults.CFHashes.put("" + group, cF);
            defaults.SQL.retrieveHistory(group);} 
            else{
                ((convoFrame) defaults.CFHashes.get("" + group)).setVisible(true);
            }
            
            
        }
	
	public void setButSize(int i){
		start.setSize(i, 50);
	}
	
        public int getUIDX(){
            return ID;
        }
        
        public void waitForGCon(){
            waiting(50);
            if(GConfirmed == false){
                waitForGCon();
            }
            GConfirmed = false;
        }
        
        public void confirmG(){
            GConfirmed = true;
        }
        
        public void waiting (int n){
        
        long t0, t1;

        t0 =  System.currentTimeMillis();

        do{
            t1 = System.currentTimeMillis();
        }
        while ((t1 - t0) < (n));
    }
        
}

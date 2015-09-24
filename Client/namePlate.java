import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;


public class namePlate extends JButton {
	boolean selected = false;
	ArrayList<Message> selectedNames = null;
	String title = null;
	boolean loggedin = false;
        Message info = null;
        int uidx = 0;
	public namePlate(Message M, ArrayList<Message> Names, SQLAgent SQL, userListFrame ULF){
		super(M.getMessage());
                uidx = ULF.getUIDX();
                info = M;
                uidx = M.getSender();
                setToolTipText("Click to select user. Right click to view information."
                        + " Red indicates offline. Green indicates online.");
		setMaximumSize(new Dimension(90, 30));
		setSize(90, 30);
		selectedNames = Names;
		setOpaque(true);
                title = info.getMessage();
		setBackground(Color.RED);
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
                            if (e.getClickCount() == 2 && !e.isConsumed()) {
                                doubleClick();
                                 e.consume();
                            }
                            else if(e.getButton() == 1){
				click();}                           
                            else if(e.getButton() == 3){
                                infoFrame iF = new infoFrame(title);
                            }
			}
		});
	}
	
	public void login(){
		if(selected == false){
		setBackground(Color.GREEN);}
		loggedin = true;
	}
	
	public void logout(){
		if(selected == false){
			setBackground(Color.RED);
		}
		loggedin = false;
	}
	
	public void click(){
		if(selected == true){
			deselect();
		}
		else{
			select();
		}
	}
        
        public void doubleClick(){
            String idxs = uidx + "," + defaults.idx;
            ArrayList<Integer> idxList = new ArrayList();
            idxList.add(uidx);
            idxList.add(defaults.idx);
            int group = defaults.SQL.findGroup(idxs, 2);
            if(group == 0){
                                    defaults.output.send(new Message("", -4, 0));
                                    defaults.output.send(idxList);
                                    defaults.ULF.waitForGCon();
                                    group = defaults.SQL.findGroup(idxs, 2);
                                }
            ArrayList<String> AL = new ArrayList();
            AL.add(title);
            defaults.ULF.newConvo(group, AL);
            
        }
	
	public void deselect(){
		selected = false;
		selectedNames.remove(info);
		if(loggedin){
			setBackground(Color.GREEN);
		}
		else{
			setBackground(Color.RED);
		}
	}
	
	public void select(){
		selected = true;
		selectedNames.add(info);
		setBackground(Color.YELLOW);
	}
	
}

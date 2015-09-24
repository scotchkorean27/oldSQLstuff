import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class collapList extends JPanel {
	
	JButton collapse = null;
	JPanel butPan = new JPanel();
	JPanel listPan = new JPanel();
	JScrollPane scroll = new JScrollPane(listPan);
        
	ArrayList<Message> selectedList = null;
	boolean expanded = false;
	userListFrame frame = null;
        int NPCount = 0;
	public collapList(String title, ArrayList<Message> nameList,
			ArrayList<Message> SNL){
		int i = nameList.size();
                scroll.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
		//setLayout(null);
                
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//setBorder(BorderFactory.createMatteBorder(0, 4,0, 0, Color.BLACK));
		setOpaque(true);
		setPreferredSize( new Dimension(100,35));
		setMaximumSize( new Dimension(100,35));
		//setSize(100, 100);
		frame = defaults.ULF;
		selectedList = SNL;
		collapse = new JButton(title);
		listPan.setVisible(true);
		listPan.setLayout(new BoxLayout(listPan, BoxLayout.PAGE_AXIS));
		for(Message M : nameList){
			namePlate nP = new namePlate(M, selectedList, defaults.SQL, frame);
                        System.out.println(M.getRecipient());
			defaults.NPHashes.put("" +M.getSender(), nP);
			listPan.add(nP);
			listPan.add(Box.createRigidArea(new Dimension(0,5)));

			
			NPCount = NPCount + 35;
                        
                        listPan.setSize(100, NPCount);
		}
		collapse.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(expanded == false){
					add(scroll);
					//setPreferredSize( new Dimension(100,200));
					setMaximumSize( new Dimension(100,NPCount + 50));
					frame.validate();
					validate();
					frame.repaint();
					expanded = true;
				}
				else{
					remove(scroll);
                                     	setPreferredSize( new Dimension(100,35));
					setMaximumSize( new Dimension(100,35));
					frame.validate();
					validate();
					frame.repaint();
					expanded = false;
				}
				validate();
				frame.validate();
				frame.repaint();
			}
		});
		
		butPan.add(collapse);
		add(butPan);
		add(scroll);
		
		butPan.setSize(90, 30);
		butPan.setLocation(5, 5);
		scroll.setSize(90, 300);
		scroll.setLocation(5, 40);
		
		validate();
		
		
	}
	
	
	
}

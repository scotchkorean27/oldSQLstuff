import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class convoFrame extends JFrame {
        int grp = 0;
	String recipients = null;
	JTextArea output = new JTextArea(3, 50);
	JTextArea input = new JTextArea(15, 50);
	JPanel Top = new JPanel();
	JPanel Bottom = new JPanel();
	JScrollPane scroll = new JScrollPane(input);
	JScrollPane scroll2 = new JScrollPane(output);
        ImageIcon img = new ImageIcon("C:/Logo_Icon.png");
        String uids = "";
        ArrayList<String> idList = new ArrayList();
        public convoFrame(int group, ArrayList<String> uidList){
                System.out.println("GRP: " + group);
                grp = group;
                idList = uidList;
                for(String s: uidList){
                    uids += s + ", ";
                }
                setTitle(uids);
                setIconImage(img.getImage());
		setVisible(true);
		//setLayout(new GridLayout(2, 1));
		Top.add(scroll);
		Top.add(scroll2);
		input.setEditable(false);
		input.setLineWrap(true);
		output.setLineWrap(true);
		add(Top);
		//add(Bottom);
		input.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		output.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		//pack();
		setSize(610, 410);
		
		output.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() ==  10){
					send();
					output.setText("");
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
                
                
    output.setDropTarget(new DropTarget() {
        public synchronized void drop(DropTargetDropEvent evt) {
            try {
            evt.acceptDrop(DnDConstants.ACTION_COPY);
            List<File> droppedFiles = (List<File>) evt
                        .getTransferable().getTransferData(
                                DataFlavor.javaFileListFlavor);


            for (File file : droppedFiles) {
                Path source = Paths.get(file.getAbsolutePath());
                Path destination = null;
                for(String uid: idList){
                    try{
                        String destDir = "//" + defaults.IP + "/Messenger/Mailbox/" +
                                uid + "/";
                        //String dest = "//" + IPA + "/Users/Public/" +
                                //"/" + sub + "/"+ source.getFileName();
                        String fileName = (source.getFileName()).toString();
                        String dest = "//" + defaults.IP + "/Messenger/Mailbox/" +
                                uid + "/" + fileName;
                        input.append("\nSending...");
                        int i = 1;
                        while(true){
                            File tempFile = new File(dest);
                            if(tempFile.exists()){
                                String tempFN = fileName.substring(0, fileName.lastIndexOf("."))
                                        + " (" + i + ")" + 
                                        fileName.substring(fileName.lastIndexOf("."),
                                        fileName.length());
                                dest = destDir + tempFN;
                                i++;
                            }
                            else{
                                break;
                            }
                        }
                        
                        destination = Paths.get(dest);
                        System.out.println(destination.getFileName());
                        
                        
                        Files.copy(source, destination); 
                    	}
			catch(Exception e){
				break;
			}
                }
                Message Notification = new Message("I sent you " + source.getFileName(),
                                defaults.idx, grp);
                
                
                send(Notification);
                
                Message ServerRequest = new Message((destination.getFileName()).toString(), 
                                -5, grp);
                send(ServerRequest);
               }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
            
        
    }
});            
            //waiting(1);
            //defaults.SQL.retrieveHistory(group);
        }
        
        
        
        
        public void waiting (int n){
        
        long t0, t1;

        t0 =  System.currentTimeMillis();

        do{
            t1 = System.currentTimeMillis();
        }
        while ((t1 - t0) < (n));
    }
        
	
	
	public void print(String s){
		input.append("\n" + s);
                repaint();
	}
	
	public void send(){
		defaults.output.send(new Message(output.getText(), defaults.idx, grp));
	}
	
	public void send(Message M){
		defaults.output.send(M);
	}
      
}

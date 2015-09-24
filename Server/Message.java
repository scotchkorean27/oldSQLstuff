import java.io.Serializable;


public class Message implements Serializable {
	private String Message;
	private int Sender;
	private int Recipient;
	public Message(String M, int S, int R){
		Message = M;
		Sender = S;
		Recipient = R;
	}
	public Message(){}
	
	public void setMessage(String S){
		Message = S;
	}
	public void setSender(int S){
		Sender = S;
	}
	public void setRecipient(int S){
		Recipient = S;
	}
	
	public String getMessage(){
		return Message;
	}
	public int getSender(){
		return Sender;
	}
	public int getRecipient(){
		return Recipient;
	}
	public void clear(){
		Message = "";
		Sender = 0;
		Recipient = 0;
	}
}

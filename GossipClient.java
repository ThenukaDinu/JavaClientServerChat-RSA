import java.io.*;
import java.net.*;
public class GossipClient
{
	// public static void initiateConnection()
	// {
	// 	Socket sock = new Socket("127.0.0.1", 3000);
		
	// 	// reading from keyboard (keyRead object)
	// 	BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
		
    //     // sending to client (pwrite object)
	// 	OutputStream ostream = sock.getOutputStream(); 
	// 	PrintWriter pwrite = new PrintWriter(ostream, true);
 
    //     // receiving from server ( receiveRead  object)
	// 	InputStream istream = sock.getInputStream();
	// 	BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
	// }
	
	public static void chat() throws Exception
	{
		try
		{
			Socket sock = new Socket("127.0.0.1", 3000);
			
			// reading from keyboard (keyRead object)
			BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
			
			// sending to client (pwrite object)
			OutputStream ostream = sock.getOutputStream(); 
			PrintWriter pwrite = new PrintWriter(ostream, true);
	
			// receiving from server ( receiveRead  object)
			InputStream istream = sock.getInputStream();
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
		

			String receiveMessage, sendMessage, encryptedString, decryptedString;  
			final String secretKeyED = "ssshhhhhhhhhhh!!!!";

			System.out.println("Start the chitchat, type and press Enter key");
			System.out.println("******This Chat is Encrypted Using AES Algorithm******\n");
		
			while(true)
			{	
				// keyboard reading
				sendMessage = keyRead.readLine();  
				//encrypt the message before send to server	
				encryptedString = AESExample.encrypt(sendMessage); 
				// sending to server
				pwrite.println(encryptedString);   
				// flush the data    
				pwrite.flush();                    
			
				//receive from server
				if((receiveMessage = receiveRead.readLine()) != null) 
				{
				//System.out.println("Server: " + receiveMessage); // This is encrypted message
				
				//decrypt the message receiving from server
				decryptedString = AESExample.decrypt(receiveMessage); 
				// displaying at DOS prompt
				System.out.println("Server: " + decryptedString); 
				}         
			} 
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	 
	public static void main(String[] args) throws Exception
	{
		//GossipClient.initiateConnection();
		GossipClient.chat();    
	}                    
}            
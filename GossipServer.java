import java.io.*;
import java.net.*;
public class GossipServer
{	
	// public static void initiateConnection()
	// {
	// 	ServerSocket sersock = new ServerSocket(3000);
	// 	System.out.println("Server  ready for chatting");
	// 	System.out.println("******This Chat is Encrypted Using AES Algorithm******\n");
	  
	// 	Socket sock = sersock.accept( );    
		
    //     // reading from keyboard (keyRead object)
	// 	BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
		
	//     // sending to client (pwrite object)
	// 	OutputStream ostream = sock.getOutputStream(); 
	// 	PrintWriter pwrite = new PrintWriter(ostream, true);
 
    //     // receiving from server ( receiveRead  object)
	// 	InputStream istream = sock.getInputStream();
		
	// 	BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
	// }
	
	public static void chat()
	{
		try{
			ServerSocket sersock = new ServerSocket(3000);
			System.out.println("Server  ready for chatting");
			System.out.println("******This Chat is Encrypted Using AES Algorithm******\n");
		
			Socket sock = sersock.accept( );    
			
			// reading from keyboard (keyRead object)
			BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
			
			// sending to client (pwrite object)
			OutputStream ostream = sock.getOutputStream(); 
			PrintWriter pwrite = new PrintWriter(ostream, true);
	
			// receiving from server ( receiveRead  object)
			InputStream istream = sock.getInputStream();
			
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
			
			String receiveMessage, sendMessage, decryptedString, encryptedString;  
			final String secretKey = "ssshhhhhhhhhhh!!!!";
	
			while(true)
			{
				if((receiveMessage = receiveRead.readLine()) != null)  
				{
					//System.out.println("Client: " + receiveMessage); // This is encrypted message
				
					//decrypt the message receiving from client
					decryptedString = AESExample.decrypt(receiveMessage, secretKey);
					System.out.println("Client: " + decryptedString);         
				}  
			
				sendMessage = keyRead.readLine();
				
				//encrypt the message before send to client	
				encryptedString = AESExample.encrypt(sendMessage, secretKey);	
				pwrite.println(encryptedString);             
				pwrite.flush();
			}  
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	  
	public static void main(String[] args) throws Exception
	{
		
		//GossipServer.initiateConnection();
		GossipServer.chat();
    }                    
}                        
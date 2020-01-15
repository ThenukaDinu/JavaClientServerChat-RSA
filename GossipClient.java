import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import Algorithm.RSAExample;
public class GossipClient
{
	public static void chat()
	{
		try
		{

			//read server public key
			Path path = Paths.get("F:\\NIBM\\DOC\\Software security\\CourseWork02\\HNDSE Software security Chat App\\Client Server Chat With RSA\\GeneratePublicPrivateKeys\\client\\privateKey");
			byte[] myPrivateKeyByte = Files.readAllBytes(path);
			path = Paths.get("F:\\NIBM\\DOC\\Software security\\CourseWork02\\HNDSE Software security Chat App\\Client Server Chat With RSA\\GeneratePublicPrivateKeys\\server\\publicKey");
			byte[] serverPublicKeyByte = Files.readAllBytes(path);

			PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(myPrivateKeyByte);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PrivateKey myPrivateKey = kf.generatePrivate(ks);

			/* Generate public key. */
			X509EncodedKeySpec ks2 = new X509EncodedKeySpec(serverPublicKeyByte);
			KeyFactory kf2 = KeyFactory.getInstance("RSA");
			PublicKey serverPublicKey = kf2.generatePublic(ks2);
			

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
			

			System.out.println("Start the chitchat, type and press Enter key");
			System.out.println("******This Chat is Encrypted Using RSA Algorithm******\n");
		
			while(true)
			{	
				// keyboard reading
				sendMessage = keyRead.readLine();  

				//encrypt the message before send to server	
				encryptedString = RSAExample.encrypt(sendMessage, serverPublicKey); 
				// sending to server
				pwrite.println(encryptedString);   
				// flush the data    
				pwrite.flush();                    
			
				//receive from server
				if((receiveMessage = receiveRead.readLine()) != null) 
				{
				System.out.println("Server: " + receiveMessage); // This is encrypted message
				
				//decrypt the message receiving from server
				decryptedString = RSAExample.decrypt(receiveMessage, myPrivateKey); 
				// displaying at DOS prompt
				System.out.println("\nServer: " + decryptedString + "\n"); 
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
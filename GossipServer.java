import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import Algorithm.RSAExample;
public class GossipServer
{	
	public static void chat()
	{
		try{
			Path path = Paths.get("F:\\NIBM\\DOC\\Software security\\CourseWork02\\HNDSE Software security Chat App\\Client Server Chat With RSA\\GeneratePublicPrivateKeys\\Server\\privateKey");
			byte[] myPrivateKeyByte = Files.readAllBytes(path);
			Path path2 = Paths.get("F:\\NIBM\\DOC\\Software security\\CourseWork02\\HNDSE Software security Chat App\\Client Server Chat With RSA\\GeneratePublicPrivateKeys\\client\\publicKey");
			byte[] clientPublicKeyByte = Files.readAllBytes(path2);

			/* Generate private key. */
			PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(myPrivateKeyByte);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PrivateKey myPrivateKey = kf.generatePrivate(ks);

			/* Generate public key. */
			X509EncodedKeySpec ks2 = new X509EncodedKeySpec(clientPublicKeyByte);
			KeyFactory kf2 = KeyFactory.getInstance("RSA");
			PublicKey clientPublicKey = kf2.generatePublic(ks2);

			ServerSocket sersock = new ServerSocket(3000);
			System.out.println("Server  ready for chatting");
			System.out.println("******This Chat is Encrypted Using RSA Algorithm******");
		
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
			
	
			while(true)
			{
				if((receiveMessage = receiveRead.readLine()) != null)  
				{
					// This is encrypted message
					System.out.println("\nClient: " + receiveMessage); 
				
					//decrypt the message receiving from client
					decryptedString = RSAExample.decrypt(receiveMessage, myPrivateKey);

					// displaying at DOS prompt
					System.out.println("Client: " + decryptedString + "\n");         
				}  
			
				sendMessage = keyRead.readLine();
				
				//encrypt the message before send to client	
				encryptedString = RSAExample.encrypt(sendMessage, clientPublicKey);	
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
		GossipServer.chat();
    }                    
}                        
import java.io.*;
import java.net.*;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.InvalidMarkException;
import java.nio.ReadOnlyBufferException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import Algorithm.RSAExample;
public class GossipClient
{
	PrivateKey myPrivateKey;
	PublicKey serverPublicKey;
	String receiveMessage, sendMessage, encryptedString, decryptedString;  
	PKCS8EncodedKeySpec ks;
	X509EncodedKeySpec ks2;
	KeyFactory kf, kf2;
	Path path, path2;

	public void readKeys() throws Exception
	{
		//read server public key
		path = Paths.get("F:\\NIBM\\DOC\\Software security\\CourseWork02\\HNDSE Software security Chat App\\Client Server Chat With RSA\\GeneratePublicPrivateKeys\\client\\privateKey");
		byte[] myPrivateKeyByte = Files.readAllBytes(path);
		path2 = Paths.get("F:\\NIBM\\DOC\\Software security\\CourseWork02\\HNDSE Software security Chat App\\Client Server Chat With RSA\\GeneratePublicPrivateKeys\\server\\publicKey");
		byte[] serverPublicKeyByte = Files.readAllBytes(path2);

		/* Generate private key. */
		ks = new PKCS8EncodedKeySpec(myPrivateKeyByte);
		kf = KeyFactory.getInstance("RSA");
		myPrivateKey = kf.generatePrivate(ks);

		/* Generate public key. */
		ks2 = new X509EncodedKeySpec(serverPublicKeyByte);
		kf2 = KeyFactory.getInstance("RSA");
		serverPublicKey = kf2.generatePublic(ks2);
	}

	public void chat() throws Exception
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
			// This is encrypted message
			System.out.println("\nServer: " + receiveMessage); 

			//decrypt the message receiving from server
			decryptedString = RSAExample.decrypt(receiveMessage, myPrivateKey); 

			// displaying at DOS prompt
			System.out.println("Server: " + decryptedString + "\n"); 
			}         
		} 
	}
	 
	public static void main(String[] args)
	{
		try {
			GossipClient GC = new GossipClient();
			GC.readKeys();
			GC.chat();
		}
		catch(ClassNotFoundException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(FileNotFoundException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(UTFDataFormatException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(UnsupportedEncodingException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(InterruptedException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(GeneralSecurityException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(BufferOverflowException exception)
		{
			System.out.println(exception.getMessage());
		} 
		catch(BufferUnderflowException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(InvalidMarkException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(ReadOnlyBufferException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(PortUnreachableException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(ProtocolException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(SocketTimeoutException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(ObjectStreamException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(SocketException exception) 
		{
			System.out.println(exception.getMessage());
		}
		catch(IOException exception)
		{
			System.out.println(exception.getMessage());
		}
		catch(Exception exception)
		{
			System.out.println(exception.getMessage());
		}
	}                    
}            
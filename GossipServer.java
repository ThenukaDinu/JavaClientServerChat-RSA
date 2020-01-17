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
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import Algorithm.RSAExample;
public class GossipServer
{	
	private PrivateKey myPrivateKey;
	private PublicKey clientPublicKey;
	private String receiveMessage, sendMessage, decryptedString, encryptedString;  
	private PKCS8EncodedKeySpec ks;
	private X509EncodedKeySpec ks2;
	private KeyFactory kf, kf2;
	private Path path, path2;
	private static Logger logger;
	private FileHandler fh;
	private ServerSocket sersock;
	private SimpleFormatter formatter;

	public GossipServer() throws Exception {
		sersock = new ServerSocket(3000);

		logger = Logger.getLogger("MyLog");
		fh = new FileHandler("F:/NIBM/DOC/Software security/CourseWork02/HNDSE Software security Chat App/Client Server Chat With RSA/logs/ServerLogFile.log");
		logger.addHandler(fh);
		formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
		logger.setUseParentHandlers(false);
		logger.info("Server Connected.\n");
	}

	public void readKeys() throws Exception
	{
		path = Paths.get("F:\\NIBM\\DOC\\Software security\\CourseWork02\\HNDSE Software security Chat App\\Client Server Chat With RSA\\GeneratePublicPrivateKeys\\Server\\privateKey");
		byte[] myPrivateKeyByte = Files.readAllBytes(path);
		path2 = Paths.get("F:\\NIBM\\DOC\\Software security\\CourseWork02\\HNDSE Software security Chat App\\Client Server Chat With RSA\\GeneratePublicPrivateKeys\\client\\publicKey");
		byte[] clientPublicKeyByte = Files.readAllBytes(path2);

		/* Generate private key. */
		ks = new PKCS8EncodedKeySpec(myPrivateKeyByte);
		kf = KeyFactory.getInstance("RSA");
		myPrivateKey = kf.generatePrivate(ks);

		/* Generate public key. */
		ks2 = new X509EncodedKeySpec(clientPublicKeyByte);
		kf2 = KeyFactory.getInstance("RSA");
		clientPublicKey = kf2.generatePublic(ks2);
	}

	public void chat() throws Exception
	{
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

			// log
			logger.info("Original Message: " + sendMessage + "\n" + "Encrypted Message: " + encryptedString + "\n");

			pwrite.println(encryptedString);             
			pwrite.flush();
		}  
	}
	  
	public static void main(String[] args)
	{
		try{
			GossipServer GS = new GossipServer();
			GS.readKeys();
			GS.chat();
		}
		catch (ClassNotFoundException exception) {
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(FileNotFoundException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(UTFDataFormatException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(UnsupportedEncodingException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(InterruptedException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(GeneralSecurityException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(BufferOverflowException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		} 
		catch(BufferUnderflowException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(InvalidMarkException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(ReadOnlyBufferException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(PortUnreachableException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(ProtocolException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(SocketTimeoutException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(ObjectStreamException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(SocketException exception) 
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(IOException exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
		catch(Exception exception)
		{
			System.out.println(exception.getMessage());
			logger.severe(exception.getMessage());
		}
    }                    
}                        
package server.net;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


import java.net.Socket;
import java.net.SocketException;


import server.controller.Controller;
import server.model.Game;
import shared.GameData;


class RequestHandler implements Runnable
{
	
	private Socket clientSock;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	Controller contr;
	private boolean runAgain;

    RequestHandler(Socket clientSock)
    {
        this.clientSock = clientSock;
        this.contr = new Controller(new Game());
		try {
			toClient = new ObjectOutputStream(clientSock.getOutputStream());
			fromClient = new ObjectInputStream(clientSock.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			this.runAgain = false;
			return;
		}

		this.runAgain = true;
    }

    
    public void run()
    {
    	
    	while(runAgain)
    	{
	    	try
	    	{
	    		executeClientRequest(readClientRequest());
	    		respondClientRequest();
			} 
	    	catch (IOException e)
	    	{
				if (e instanceof SocketException) {
					disconnect();
					continue;
				}

				e.printStackTrace();
			} catch (ClassNotFoundException e)
	    	{
				e.printStackTrace();
	    	}
    	}
    	

    }

	private void disconnect() {
		runAgain = false;

		try {
			clientSock.close();
		} catch (IOException e) {
		}
	}

	/**
     * Listens for client request and returns it in the form of a String
     * @return
     * @throws ClassNotFoundException 
     * @throws IOException 
     */
    private String readClientRequest() throws ClassNotFoundException, IOException
    {
    	String clientRequest;
    	ObjectInputStream objIn;

    	int length = -1;
    	byte[] byteArr;
			
    	
    	while(length == -1)
    	{
    		length = fromClient.read();
    	}

		byteArr = new byte [length];
		for(int i = 0; i < length; i++)
			byteArr[i] = fromClient.readByte();
			
				
		objIn = new ObjectInputStream(new ByteArrayInputStream(byteArr));
			
		clientRequest = (String) objIn.readObject();
		objIn.close();		
				
		return clientRequest;
		
    }
    

    private void executeClientRequest(String string) throws IOException
    {
    	
    	if (string.equals(".stop"))
    	{
			disconnect();
    	}
    	else if (!string.equals(".start")) {
			contr.receiveGuess(string);
		}

    }
    
    private void respondClientRequest() throws IOException
    {
       
        GameData gameData = contr.getGameData();
    	
    	ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream ();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream );
		
		objectOutputStream.writeObject(gameData); 
		objectOutputStream.flush();
		objectOutputStream.close();
		
		byte[] byteArr = byteOutputStream.toByteArray();
		int length = byteArr.length;

		
		toClient.write(length);
		toClient.write(byteArr);
		toClient.flush();
	
		
		/*
		fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintWriter pw = new PrintWriter(toClient);
        
        pw.println(gameData.get(0));
        pw.println(gameData.get(1));
        pw.println(gameData.get(2));
        pw.println(gameData.get(3));
        
        pw.println("stop");

        pw.flush();*/
    }
    


}
package startup;

import java.io.IOException;

import client.net.Client;
import server.net.Server;

/**
 * Starts the server thread as well as three client threads
 */
public class Main {

	public static void main(String[] args)
	{

		new Server().run();

	}

}

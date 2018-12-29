package startup;

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

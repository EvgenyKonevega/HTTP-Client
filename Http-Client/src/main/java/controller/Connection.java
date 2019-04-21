package controller;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exception.BsuirException;
import model.HttpRequest;

public class Connection {

    private final String fileName = "communicationProtocol.txt";

	final Logger lOGGER = LogManager.getLogger(Connection.class.getName());

	public String connection(String method, HttpRequest httpRequest, String requesttext) throws BsuirException {
		//System.out.println(method + " \n" +requesttext);
		String result = httpRequest.request(requesttext, method);
		System.out.println("____");
		
		System.out.println(result);
		Socket socket = null;
		StringBuilder serverAnswer = new StringBuilder();
		try {
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            //String out = result;
            //System.out.println(method);
            /*if(!method.equals("POST")){
                out = "";
                String[] request = result.split("\n");
                for(int i=0; i<request.length-2; i++){
                    out += request[i] + "\n";
                }
                System.out.println("________________________________");
                System.out.println(out);
            }*/
            bufferWriter.write(result + "\n");
            //
//            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
//            out.print(result);
//            out.close();

			socket = new Socket(httpRequest.getHost(), Integer.parseInt(httpRequest.getPort()));
			socket.getOutputStream().write(result.getBytes());
			Scanner scanner = new Scanner(socket.getInputStream());
			while (scanner.hasNextLine()) {
				serverAnswer.append(new String(scanner.nextLine().getBytes(), "utf-8"));
				serverAnswer.append(new String("\n"));
			}
			socket.close();

            bufferWriter.write("Response: \n "+ serverAnswer.toString() + "\n");
            bufferWriter.close();

			lOGGER.info("answer read successfuly");


		} catch (IOException e) {
			lOGGER.debug("Exception in connection class");
			e.printStackTrace();
			throw new BsuirException("B suirIOException" + e);
		}
		lOGGER.info("answer read successfuly");
        System.out.println("________");
        System.out.println(serverAnswer.toString());

		return serverAnswer.toString();
	}

}

package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exception.BsuirException;
import model.HttpRequest;

public class Connection {

	private final String fileName = "communicationProtocol.txt";

	final Logger lOGGER = LogManager.getLogger(Connection.class.getName());
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;

	public String connection(String method, String params, HttpRequest httpRequest, String requesttext) throws BsuirException {
        String result = httpRequest.request(requesttext, params, method);
        System.out.println("____");
        System.out.println(result);
        System.out.println("____");
        StringBuilder response = new StringBuilder();
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fwriter = new FileWriter(file, true);
            BufferedWriter bufferWriter = new BufferedWriter(fwriter);
            bufferWriter.write(result + "\n");

            socket = new Socket(httpRequest.getHost(), Integer.parseInt(httpRequest.getPort()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            if (socket == null) {
                throw new BsuirException("no internet connection");
            }

            writer.println(result);
            writer.flush();
            String str;
            while ((str = reader.readLine()) != null) {
                response.append(str + "\n");
            }

            writer.close();
            reader.close();
            socket.close();

            bufferWriter.write("Response: \n " + response.toString() + "\n");
            bufferWriter.close();

            lOGGER.info("answer read successfuly");

        } catch (IOException e) {
            lOGGER.debug("Exception in connection class");
            e.printStackTrace();
            throw new BsuirException("B suirIOException" + e);
        }
        lOGGER.info("answer read successfuly");
        System.out.println("________");
        System.out.println("response \n" + response.toString());

        return response.toString();
	}
}

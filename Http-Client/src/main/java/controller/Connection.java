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
		long start = System.nanoTime();
		// поиск смысла жизни ...
		String result = httpRequest.request(requesttext, params, method);
		long finish = System.nanoTime();
		long timeConsumedMillis = finish - start;
		System.out.println("Time for writing request____" + timeConsumedMillis);
		
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

            long start2 = System.nanoTime();
            socket = new Socket(httpRequest.getHost(), Integer.parseInt(httpRequest.getPort()));
            long finish2 = System.nanoTime();
    		long timeConsumedMillis2 = finish2 - start2;
    		System.out.println("Time for socket = new Socket____" + timeConsumedMillis2);
            
            
    		long start3 = System.nanoTime();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            long finish3 = System.nanoTime();
    		long timeConsumedMillis3 = finish3 - start3;
    		System.out.println("Time for reader = new BufferedReader(new InputStreamReader(socket.getInputStream())__" + timeConsumedMillis3);
            
    		long start4 = System.nanoTime();
            writer = new PrintWriter(socket.getOutputStream(), true);
            long finish4 = System.nanoTime();
    		long timeConsumedMillis4 = finish4 - start4;
    		System.out.println("Time for writer = new PrintWriter(socket.getOutputStream(), true)__" + timeConsumedMillis4);
            
            
            
            if (socket == null) {
                throw new BsuirException("no internet connection");
            }

            writer.println(result);
            writer.flush();
            String str;
            
            long start5 = System.nanoTime();
            while ((str = reader.readLine()) != null) {
                response.append(str + "\n");
            }
            long finish5 = System.nanoTime();
    		long timeConsumedMillis5 = finish5 - start5;
    		System.out.println("Time for while__" + timeConsumedMillis5);
            
            
            writer.close();
            reader.close();
            socket.close();

            
            long start6 = System.nanoTime();
            bufferWriter.write("Response: \n " + response.toString() + "\n");
            long finish6 = System.nanoTime();
    		long timeConsumedMillis6 = finish6 - start6;
    		System.out.println("Time for bufferWriter.write(\"Response: \\n \" + response.toString()__" + timeConsumedMillis6);
            
            
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

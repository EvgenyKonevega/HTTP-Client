//package connectiontest;
//
//import static org.testng.Assert.assertEquals;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.text.ParseException;
//import java.util.Scanner;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import controller.Connection;
//import exception.BsuirException;
//import model.HttpRequest;
//
//public class ConnectionTest {
//	private Logger logger = LogManager.getLogger(ConnectionTest.class.getName());
//	private static String firstRequest, firstFormedRequest;
//	private static String secondRequest, secondFormedRequest;
//	private static String port, method;
//	private static String wrongrequest;
//	private static String Host;
//	private static String expectedResult1;
//	private static String expectedResult2;
//	private HttpRequest request;
//	private Connection connection;
//
//	 /**
//     * Initialize.
//     */
//    @BeforeClass
//    public void initialize() {
//    	firstFormedRequest = "POST /welcome\n" +
//				"Host: 127.0.0.1:8080\n" +
//				"Content-Length: 28\n" +
//				"Content-Type: application/x-www-form-urlencoded\n" +
//				"\n" +
//				"id=112858265069&user=Polinka";
//    	secondFormedRequest = "GET /welcome1\n" +
//				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
//				"Referer: 127.0.0.1/welcome\n" +
//				"Host: 127.0.0.1:8080\n" +
//				"Accept-Encoding: gzip, deflate\n" +
//				"Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7\n" +
//				"\n" +
//				"id=112858265069&user=Polinka";
//    	firstRequest = "127.0.0.1/welcome\n" +
//				"Params:{\n" +
//				"\tuser = Polinka;\n" +
//				"\tid = 112858265069;\n" +
//				"}";
//    	secondRequest = "127.0.0.1/welcome";
//    	port = "8080";
//    	Host = "127.0.0.1";
//    	expectedResult1 = "\t\t<title>Welcome message</title>";
//    	expectedResult2 = "\t\t<title>Get request</title>";
//    	wrongrequest ="GET ht//wwww.bycarddddd.by/ HTTP/1.0\n" +
//    			"Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
//    			"Accept-Encoding:gzip, deflate\n" +
//    			"Accept-Language:ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7\n" +
//    			"Host:www.bycard.by\n" +
//    			"Referer:http://www.bycard.by/";;
//    	request = new HttpRequest();
//    	connection = new Connection();
//    	}
//
//
//	/**Provider.
//	 * @return object yes
//	 *  */
//	    @DataProvider(name = "test")
//	    public static Object[][] connection1() {
//	        return new Object[][] {{firstFormedRequest, Host, port, "POST",expectedResult1},
//	        	{secondFormedRequest, Host, port, "GET",expectedResult2}};
//	    }
//
//	    /**Provider.
//		 * @return object yes
//		 *  */
//		    @DataProvider(name = "test2")
//		    public static Object[][] connection2() {
//		        return new Object[][] {{"POST", firstRequest, expectedResult1},
//		        	{"GET", secondRequest, expectedResult2}};
//		    }
//
//	    /** Test.
//	     *@param request
//	     *@param host
//	     *@param port
//	     *@param expectedResult
//	     * @throws UnknownHostException
//	     * @throws NumberFormatException
//	     * @throws IOException
//	    */
//	    @Test(dataProvider = "test")
//	    public void testConnection(final String request,
//	    		final String host, final String port, String method ,final String expectedResult) throws IOException {
//	    	Socket socket = new Socket(host, Integer.parseInt(port));
//			socket.getOutputStream().write(request.getBytes());
//			StringBuilder serverAnswer = new StringBuilder();
//			Scanner scanner = new Scanner(socket.getInputStream());
//			while (scanner.hasNextLine()) {
//				serverAnswer.append(new String(scanner.nextLine().getBytes(), "utf-8"));
//				serverAnswer.append(new String("\n"));
//			}
//			serverAnswer = new StringBuilder(serverAnswer.toString());
//			socket.close();
//			if (serverAnswer.toString() == null) {
//			}
//			String[] result = serverAnswer.toString().split("\n");
//			logger.debug("result lenth "+ result[4]);
//			assertEquals(result[3], expectedResult);
//	    }
//
//	    /** Test.
//	     *@param request
//	     *@param host
//	     *@param expectedResult
//	     * @throws BsuirException
//	    */
//	    @Test(dataProvider = "test2")
//	    public void testConnection2(final String method, final String requesttext, final String expectedResult) throws BsuirException {
//	    	String[] result = (connection.connection(method, request,requesttext)).split("\n");
//	    	System.out.println("---------------------------------------------------------------------");
//	    	System.out.println(result[3]);
//	    	System.out.println("---------------------------------------------------------------------");
//	    	System.out.println(expectedResult);
//		//	assertEquals(result[3], expectedResult);
//	    }
//
//	    /** Test.
//	     * @throws BsuirException new exception class
//	     */
//	  /*  @Test(expectedExceptions =
//	            exception.BsuirException.class)
//	    public void testConnectionException() throws BsuirException {
//	    	String[] result = (connection.connection(method,request,wrongrequest)).split("\n");
////	    	try {
////				Socket socket = new Socket(firstHost, Integer.parseInt("90"));
////			} catch (NumberFormatException | IOException e) {
////				throw new BsuirException("BsuirIOException");
////			}
//	    }*/
//}

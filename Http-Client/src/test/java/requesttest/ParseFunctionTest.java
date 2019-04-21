//package requesttest;
//
//import static org.testng.Assert.assertEquals;
//
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import exception.BsuirException;
//import model.HttpRequest;
//
//public class ParseFunctionTest {
//	private static String postrequest;
//	private static String getrequest;
//	private static String headrequest;
//	private HttpRequest httpRequest;
//	private static String postFormedRequest, getFormedRequest, headFormedRequest;
//
//	@BeforeClass
//	public void initialize() {
//		postrequest = "127.0.0.1/welcome\n" +
//				"Params:{\n" +
//				"\tuser = Polinka;\n" +
//				"\tid = 112858265069;\n" +
//				"}";
//		getrequest = "127.0.0.1/welcome";
//		headrequest = "127.0.0.1/welcome";
//		postFormedRequest = "POST /welcome\n" +
//				"Host: 127.0.0.1:8080\n" +
//				"Content-Length: 28\n" +
//				"Content-Type: application/x-www-form-urlencoded\n" +
//				"\n" +
//				"id=112858265069&user=Polinka";
//		getFormedRequest = "GET /welcome\n" +
//				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
//				"Referer: 127.0.0.1/welcome\n" +
//				"Host: 127.0.0.1:8080\n" +
//				"Accept-Encoding: gzip, deflate\n" +
//				"Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7\n" +
//				"\n" +
//				"id=112858265069&user=Polinka";
//		headFormedRequest = "HEAD /welcome\n" +
//				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
//				"Referer: 127.0.0.1/welcome\n" +
//				"Host: 127.0.0.1:8080\n" +
//				"Accept-Encoding: gzip, deflate\n" +
//				"Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7\n" +
//				"\n" +
//				"id=112858265069&user=Polinka";
//		 httpRequest = new HttpRequest();
//	}
//
//	@DataProvider(name = "testsetNewRequest")
//	public static Object[][] testNewRequest() {
//		return new Object[][] { { postrequest,"POST", "Host", "127.0.0.1" },
//				{ getrequest,"GET", "Host", "127.0.0.1" },
//				{ headrequest, "HEAD","Host", "127.0.0.1" }};
//	}
//
//	@DataProvider(name = "testRequest")
//	public static Object[][] testRequest() {
//		return new Object[][] { { postrequest,"POST", postFormedRequest },
//				{ getrequest, "GET", getFormedRequest },
//				{ headrequest, "HEAD", headFormedRequest }};
//	}
//
//	/**
//	 * Test.
//	 * @param text
//	 * @param expectedResult
//	 * @throws BsuirException
//	 */
//	@Test(dataProvider = "testRequest")
//	public void testRequest(final String text, final String method, final String expectedResult) throws BsuirException {
//		String result = httpRequest.request(text, method);
//		assertEquals(result, expectedResult);
//	}
//
//	/**
//	 * Test.
//	 * @param request
//	 * @param expectedResult
//	 * @throws BsuirException
//	 */
//	@Test(dataProvider = "testsetNewRequest")
//	public void testsetNewRequest(final String text, String method, String key, final String expectedResult)
//			throws BsuirException {
//		HttpRequest request = new HttpRequest();
//		request.setNewRequest(text, method);
//		assertEquals(request.getRequestFields().get(key), expectedResult);
//	}
//}

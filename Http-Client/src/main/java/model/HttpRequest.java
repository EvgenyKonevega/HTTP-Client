//revo uninstaller
//
//dpkg remove

package model;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exception.BsuirException;

/**
 * This class represents http-request model (�����, ��������������� ������ http �������).
 *
 * @author Yuzvenko Polina and Konevega Evgeny
 */
public class HttpRequest {

	/**
	 * Logger for logging.
	 */
	private static Logger lOGGER = LogManager.getLogger(HttpRequest.class.getName());

	/**
	 * Default request value.
	 */
	private String request = "";
	/**
	 * List for request parameters.
	 **/
	private List<Field> fields = new ArrayList<>();

	/**
	 * Map for request parameters.
	 **/
	private Map<String, String> requestFields = createMap();

	public Map<String, String> getRequestFields() {
		return requestFields;
	}
	private boolean userPort = false;
	public String usPortVal = "";

	private static Map<String, String> createMap() {
		Map<String, String> requestFields = new HashMap<String, String>();
		requestFields.put("Method", "");
		requestFields.put("URL", "");
		requestFields.put("Host", "");
		requestFields.put("Accept", "*/*");
        requestFields.put("Cache-Control", "no-cache");
		requestFields.put("Accept-Encoding", "gzip, deflate");
		requestFields.put("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
		requestFields.put("Content-Type", "application/x-www-form-urlencoded");
		requestFields.put("Content-Length", "");
		requestFields.put("Port8080", "8080");
		requestFields.put("Port80", "80");
		requestFields.put("Connection", "Keep-Alive");
		requestFields.put("User-Agent", "Chrome/73.0.3683.86 (Windows 10 Pro 10.0.17134)");
		//requestFields.put("Connection", "close");

		return requestFields;
	}

	//public Map<String, String> getMap(){}

	/**
	 * Public model constructor.
	 */
	public HttpRequest() {
		for (Field field : HttpRequest.class.getDeclaredFields()) {
			if (field.getType() == String.class && field.getModifiers() == Modifier.PRIVATE) {
				field.setAccessible(true);
				fields.add(field);
			}
		}
	}

	/**
	 * Method set new http-request text.
	 *
	 * @param newRequest
	 * @param method
	 * @throws BsuirException new users exception
	 */
	public void setNewRequest(String newRequest, String method) throws BsuirException {
		try {
			String[] request = newRequest.split("\n");
			String url = request[0];
			//System.out.println("request[0] " + url);
			requestFields.put("Method", method);
			requestFields.put("URL", url);
			if (method != "POST") {
				if (url.contains("http")) {
					String host = newRequest.substring(newRequest.indexOf("//") + 2, newRequest.length());
					host = host.substring(0, host.indexOf("/"));
					requestFields.put("Host", host);
				} else if(url.startsWith("1")) {
				    if (newRequest.contains("/")){
					requestFields.put("Host", newRequest.substring(0, newRequest.indexOf("/")));
					requestFields.put("URL", url.substring(url.indexOf("/"), url.length()));}
					else {
                        requestFields.put("Host", newRequest.substring(0, newRequest.indexOf(":")));
                        requestFields.put("URL", "/");
					}
				} else {
					requestFields.put("Host", newRequest.substring(0, newRequest.indexOf("/")));
					requestFields.put("URL", "http://"+url);
				}
				
			} else {
				requestFields.put("Host", url.substring(0, url.indexOf("/")));
				requestFields.put("URL", url.substring(url.indexOf("/"), url.length()));
			}

			int st = url.indexOf(":");
			int end = url.indexOf("/");

			if ((end - st > 1) && (st > -1)){
				String usPort = url.substring(st+1, end);
				userPort = true;
				usPortVal = usPort;

			}

            if (url.contains("8080")) {
                userPort = true;
                usPortVal = "8080";

            }

			if (method == "POST") {
				int len = 0;
				for (int index = 1; index < request.length; index++) {
					if (request[index].split(":")[0].equals("Params")) {
						index++;
						while (!request[index].equals("}")) {
							String[] params = request[index].split("\\s*(\\s|,|!|\\.)\\s*");
							index++;
							requestFields.put("Param:" + params[1], params[3].substring(0, params[3].length() - 1));
							len += (params[1].length() + params[3].length());

						}
					}
				}
				requestFields.put("Content-Length", String.valueOf(len + 1));
			}

		} catch (IllegalArgumentException e) {
			lOGGER.debug("IllegalArgumentException in setNewRequest function");
			throw new BsuirException("IllegalArgumentException in setNewRequest function");
		}
	}

	/**
	 * Method forms new htto-request text for socket.
	 *
	 * @param text - text, which enter user
	 * @return new request
	 * @throws BsuirException user exception
	 */
	public String request(String text, String method) throws BsuirException {
		setNewRequest(text, method);

		List<String> keys = new ArrayList<String>(requestFields.keySet());
//		for (int i = 0; i < keys.size(); i++) {
//			System.out.println("key: " + keys.get(i) + " value: " + requestFields.get(keys.get(i)));
//		}

		String newLine = "\r\n";
		String space = ": ";
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<String> paramValues = new ArrayList<String>();
		request = requestFields.get("Method") + " " + requestFields.get("URL") + " HTTP/1.1" + newLine;
//		request = String.format("%s%s%s%s", requestFields.get("Method"), " ", requestFields.get("URL"), "\r\n");
		try {
			for (int i = 0; i < keys.size(); i++) {
				String key = keys.get(i);
				if (key.equals("Host")) {
					request += key + space + requestFields.get(key) + ":" + getPort() + newLine;
//					request = String.format("%s%s", request, String.format("%s%s%s%s%s%s", key, space,
//							requestFields.get(key), ":", getPort(), "\r\n"));
				} else if (key.split(":")[0].equals("Param")) {
					params.add(key.split(":")[1]);
					paramValues.add(requestFields.get(key));
				} else if (!key.equals("Method") && !key.equals("URL") && !key.equals("Port80")
						&& !key.equals("Port8080")) {
					if (method == "POST") {
						if (!key.equals("Accept") && !key.equals("Accept-Encoding") && !key.equals("Accept-Language")) {
							request += key + space + requestFields.get(key) + newLine;
//							request = String.format("%s%s", request,
//									String.format("%s%s%s%s", key, space, requestFields.get(key), "\r\n"));
						}
					}else if(method == "HEAD") {
						if(!key.equals("Accept-Encoding") 	&& !key.equals("Accept-Language") && !key.equals("Content-Length")) {
							request += key + space + requestFields.get(key) + newLine;
//							request = String.format("%s%s", request,
//									String.format("%s%s%s%s", key, space, requestFields.get(key), "\r\n"));
						}
					}else {
						if (!key.equals("Content-Length") && !key.equals("Param:user") && !key.equals("Param:id")) {
							request += key + space + requestFields.get(key) + newLine;
// 							request = String.format("%s%s", request,
//									String.format("%s%s%s%s", key, space, requestFields.get(key), "\r\n"));
						}
					}
				}
			}
            if(method.equals("POST")) {
                for (int i = 0; i < params.size(); i++) {
                    if (i == 0) {
                    	request += newLine + URLEncoder.encode(params.get(i), "UTF-8") + "="
								+ URLEncoder.encode(paramValues.get(i), "UTF-8");
//                        request = String.format("%s%s%s", request, "\n", URLEncoder.encode(params.get(i), "UTF-8") + "="
//                                + URLEncoder.encode(paramValues.get(i), "UTF-8"));
                    } else {
                    	request += "&" + URLEncoder.encode(params.get(i), "UTF-8") + "="
								+ URLEncoder.encode(paramValues.get(i), "UTF-8");
//                        request = String.format("%s%s", request, "&" + URLEncoder.encode(params.get(i), "UTF-8") + "="
//                                + URLEncoder.encode(paramValues.get(i), "UTF-8"));
                    }
                }
            }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//System.out.println(request);
		return request;
	}

	public String testFunc(String text, HttpRequest httpRequest)
			throws IllegalArgumentException, IllegalAccessException {
		String result;
		for (int index = 0; index < fields.size(); index++) {
			if (fields.get(index).getName().equals(text)) {
				result = (String) fields.get(index).get(httpRequest);
			}
		}
		return text;
	}

	/**
	 *
	 * @return port
	 */
	public String getPort() {

		if (requestFields.get("Host").equals("127.0.0.1")) {
			if (userPort){
				return usPortVal;
			} else return requestFields.get("Port8080");
		} else {
			if (userPort){
				return usPortVal;
			} else return requestFields.get("Port80");
		}
	}

	/**
	 *
	 * @return host
	 */
	public String getHost() {
		return requestFields.get("Host");
	}
}
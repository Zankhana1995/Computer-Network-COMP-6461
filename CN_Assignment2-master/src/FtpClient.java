
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This class is the entry point of FTP Client Library Implementation.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
public class FtpClient {

	private static HttpClientRequest clientRequest = new HttpClientRequest();
	private static List<String> headerLst = null;
	static Socket socket = null;
	static ObjectOutputStream oos = null;
	static ObjectInputStream ois = null;
	static HttpClientResponse serverResponse;

	/**
	 * This method id the entry point and when user run this class in console used
	 * need to provide valid request URL
	 * 
	 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
	 */
	public static void main(String[] args)
			throws UnknownHostException, IOException, EOFException, URISyntaxException, ClassNotFoundException {
		String dir = System.getProperty("user.dir");
		File file = new File("attachment");
		file.mkdir();
		while (true) {
			String request = "";
			System.out.print("Please Enter File transfer command ==> ");
			Scanner sc = new Scanner(System.in);
			request = sc.nextLine();
			if (request.isEmpty() || request.length() == 0 || (request.contains("post") && !request.contains("-d"))) {
				System.out.println("Invalid Command or Please enter POST url with inline data");
				continue;
			}

			String[] requestArray = request.split(" ");
			requestArray[0] = "httpfs";
			 List<String> dataList = Arrays.asList(requestArray);
			String url = "";
			if (request.contains("post")) {
				url = dataList.get(2);

			} else {
				url = dataList.get(dataList.size() - 1);
			}

			if (url.contains("\'")) {
				url = url.replace("\'", "");
			}
			clientRequest.setHttpRequest(request);
			parseInputRequest(dataList);
		
			URI uri = new URI(clientRequest.getRequestUrl());
			String hostName = uri.getHost();
			// establish socket connection to server
			socket = new Socket(hostName, uri.getPort());
			// write to socket using ObjectOutputStream
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("Sending request to Socket Server");
			oos.writeObject(clientRequest);

			oos.writeObject("Test");

			String method = clientRequest.getRequestMethod();

			// read the server response message
			serverResponse = (HttpClientResponse) ois.readObject();

			// System.out.println("serverResponse ==> "+serverResponse);

			if (method.equalsIgnoreCase("get/")) {

				System.out.println(serverResponse.getResponseHeaders());
				System.out.println(serverResponse.getBody());

			} else if (!method.endsWith("/") && method.contains("get/")) {
				if (request.contains("Content-Disposition:attachment")) {

					String statusCode = serverResponse.getResponseCode();

					if (!statusCode.equals("404")) {

						String fileData = serverResponse.getBody();
						String fileName = serverResponse.getRequestFileName();

						file = new File(dir + "/attachment/" + fileName);
						file.createNewFile();

						FileWriter fw = new FileWriter(file);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter pw = new PrintWriter(bw);

						pw.print(fileData);
						pw.flush();
						pw.close();
					}

					System.out.println(serverResponse.getResponseHeaders());
					System.out.println(serverResponse.getBody());

					if (!statusCode.equals("404"))
						System.out.println("File downloaded in " + dir + "/attachment");
				} else {

					System.out.println(serverResponse.getResponseHeaders());
					System.out.println(serverResponse.getBody());
				}
			}
			else if (!method.endsWith("/") && method.contains("post/")) {

				System.out.println(serverResponse.getResponseHeaders());
				System.out.println(serverResponse.getBody());
			}

			oos.flush();
			oos.close();

		}
	}

	/**
	 * This method will parse the user request URL and set different value in
	 * Request class based on different conditions
	 * 
	 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
	 * @param dataList space separated request element
	 */
	private static void parseInputRequest(List<String> dataList)
			throws URISyntaxException, UnknownHostException, IOException {

		headerLst = new ArrayList<String>();

		// Collecting user request elements
		for (int i = 0; i < dataList.size(); i++) {

			if (dataList.get(i).equals("-v")) {
				clientRequest.setVerbosePreset(true);

			} else if (dataList.get(i).startsWith("http://") || dataList.get(i).startsWith("https://")) {
				clientRequest.setRequestUrl(dataList.get(i));

			} else if (dataList.get(i).equals("-h")) {

				headerLst.add(dataList.get(i + 1));

				clientRequest.setHttpHeader(true);
				clientRequest.setHeaderLst(headerLst);

			} else if (dataList.get(i).equals("-d") || dataList.get(i).equals("--d")) {

				clientRequest.setInlineData(true);
				clientRequest.setInlineData(dataList.get(i + 1));

			} else if (dataList.get(i).equals("-f")) {

				clientRequest.setFilesend(true);
				clientRequest.setFileSendPath(dataList.get(i + 1));

			} else if (dataList.get(i).equals("-o")) {

				clientRequest.setFileWrite(true);
				clientRequest.setFileWritePath(dataList.get(i + 1));

			}
		}
		String str = clientRequest.getHttpRequest();
		
		String[] strArray = str.split("\\s+");
		for(int i=0;i<strArray.length;i++)
		{
			if(strArray[i].startsWith("http://"))
			{
				String[] methodarray = strArray[i].split("/");
				if(methodarray.length==4)
				{
					clientRequest.setClientType(dataList.get(0));
					clientRequest.setRequestMethod(methodarray[3]+"/");
				}
				else if(methodarray.length==5)
				{
					clientRequest.setClientType(dataList.get(0));
					String a = methodarray[3]+"/"+methodarray[4];
					clientRequest.setRequestMethod(a);
				}
			}
		}

		// for -d inline data
		if (clientRequest.isInlineData()) {
			if (clientRequest.getInlineData().contains("\'")) {

				clientRequest.setInlineData(clientRequest.getInlineData().replace("\'", ""));

			}

		}

	}

}

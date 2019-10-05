
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
 * This class is the entry point of HTTP Client Library Implementation.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
public class HttpClient {

	private static final String HTTPC = "httpc";

	private static HttpClientRequest request = new HttpClientRequest();
	private static List<String> headerLst = null;
	private static StringBuilder fileData = null;

	private static final String NEW_LINE = "\r\n";

	private static final String LEFT_BRACKET = "{";
	private static final String RIGHT_BRACKET = "}";

	private static Socket clientSocket = null;

	
	/**
	 * This method id the entry point and when user run this class in console used need to provide valid request URL
	 * 
	 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
	 */
	public static void main(String[] args) throws Exception {

		int count = 0;

		while (true) {

			try {

				headerLst = new ArrayList<String>();
				fileData = new StringBuilder();

				//checking for redirection
				if (request.isRedirect() && count <= 3) {
					count++;

					request.setHttpRequest(
							HTTPC + " " + request.getRequestMethod() + " -v " + request.getRedirectLocation());
					request.setRedirect(false);

				} else {
					count = 0;
					request = new HttpClientRequest();
					System.out.print("Please Enter command ==> ");
					Scanner scanner = new Scanner(System.in);
					request.setHttpRequest(scanner.nextLine());

					if (request.getHttpRequest() == null || request.getHttpRequest().isEmpty()) {
						System.out.println("Invalid URL please try again");
						continue;
					}
				}

				String[] dataArray = request.getHttpRequest().split(" ");
				List<String> dataList = Arrays.asList(dataArray);

				if (dataList.contains("help")) {

					if (dataList.contains("post")) {
						System.out.println(
								"usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\\nPost executes a HTTP ");
					} else if (dataList.contains("get")) {
						System.out
								.println("usage: httpc get [-v] [-h key:value] URL\\nGet executes a HTTP GET request ");
					} else {
						System.out.println("httpc is a curl-like application but supports HTTP protocol only.\n");
					}
				}

				//validation start
				if (dataList.get(0).contains("httpc")
						&& (dataList.get(1).contains("get") || dataList.get(1).contains("post"))) {

					if (dataList.get(1).contains("get")
							&& (dataList.contains("--d") || dataList.contains("-d") || dataList.contains("-f"))) {
						System.out.println("-f or -d are not allowed in GET Request");
						continue;
					}

					if (dataList.get(1).contains("post")
							&& ((dataList.contains("--d") || dataList.contains("-d")) && dataList.contains("-f"))) {
						System.out.println(
								"your command is not Valid ==> -f and -d both are not allowed in POST request");
						continue;
					}

					parseInputRequest(dataList);

					BufferedReader responseReader = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
					String t;
					String statusCode = responseReader.readLine();

					//validate if redirection or not
					String[] strArr = statusCode.split(" ");
					if (strArr[1].contains("3")) {
						request.setRedirect(true);

						while ((t = responseReader.readLine()) != null) {
							if (t.startsWith("Location:")) {

								request.setRedirectLocation(t.split(" ")[1]);
								System.out.println("redirectLocation ==> " + request.getRedirectLocation());
								break;
							}

						}

					}
					
					if (request.isFileWrite()) {

						//Method call for write response in file
						writetoFile(responseReader, statusCode);

						if (request.isRedirect()) {
							continue;
						}

					} else {

						//Method call for printing response in console
						printresult(responseReader, statusCode);

						if (request.isRedirect()) {
							continue;
						}
					}

					if (responseReader != null) {
						responseReader.close();
					}
					clientSocket.close();

				} else {
					System.out.println("Invalid URL please. Provide valid httpc get or httpc post URL");
				}

			} catch (Exception e) {
				System.out.println("Invalid URL please. Provide valid httpc get or httpc post URL");
				continue;
			}

		}

	}

	/**
	 * This method will write response in specific file location which user has provided while sending the request
	 * 
	 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
	 * @param reader response in bufferreader
	 * @param statusCode response statusCode in String
	 */
	private static void writetoFile(BufferedReader reader, String statusCode) throws IOException {

		System.out.println("=============================================================================>>");

		FileWriter fileWriter = new FileWriter(request.getFileWritePath(), true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(bufferedWriter);
		String line;
		if (request.isVerbosePreset()) {
			printWriter.println("=============================================================================>>");
			printWriter.println(statusCode);
			while ((line = reader.readLine()) != null) {
				printWriter.println(line);
				if (line.equals(RIGHT_BRACKET)) {
					break;
				}

			}
		} else {
			printWriter.println("=============================================================================>>");
			boolean isJson = false;
			while ((line = reader.readLine()) != null) {
				if (line.trim().equals(LEFT_BRACKET))
					isJson = true;
				if (isJson) {
					printWriter.println(line);
					if (line.equals(RIGHT_BRACKET)) {
						break;
					}
				}
			}
		}
		System.out
				.println("Response has been successfully written in ==> " + request.getFileWritePath() + "  File path");

		printWriter.flush();
		printWriter.close();

	}

	/**
	 * This method will write response in Console
	 * 
	 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
	 * @param reader response in bufferreader
	 * @param statusCode response statusCode in String
	 */
	private static void printresult(BufferedReader reader, String statusCode) throws IOException {

		System.out.println("=============================================================================>>");

		String line;
		if (request.isVerbosePreset()) {
			System.out.println(statusCode);
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				if (line.equals(RIGHT_BRACKET))
					break;
			}
		} else {

			boolean isJson = false;
			while ((line = reader.readLine()) != null) {

				if (line.trim().equals(LEFT_BRACKET))
					isJson = true;
				if (isJson) {
					System.out.println(line);
					if (line.equals(RIGHT_BRACKET))
						break;
				}
			}

		}

	}

	/**
	 * This method will parse the user request URL and set different value in Request class based on different conditions
	 * 
	 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
	 * @param dataList space separated request element
	 */
	private static void parseInputRequest(List<String> dataList)
			throws URISyntaxException, UnknownHostException, IOException {
		
		//Collecting user request elements 
		for (int i = 0; i < dataList.size(); i++) {

			if (dataList.get(i).equals("-v")) {
				request.setVerbosePreset(true);

			} else if (dataList.get(i).startsWith("http://") || dataList.get(i).startsWith("https://")) {
				request.setRequestUrl(dataList.get(i));

			} else if (dataList.get(i).equals("-h")) {

				headerLst.add(dataList.get(i + 1));

				request.setHttpHeader(true);
				request.setHeaderLst(headerLst);

			} else if (dataList.get(i).equals("-d") || dataList.get(i).equals("--d")) {

				request.setInlineData(true);
				request.setInlineData(dataList.get(i + 1));

			} else if (dataList.get(i).equals("-f")) {

				request.setFilesend(true);
				request.setFileSendPath(dataList.get(i + 1));

			} else if (dataList.get(i).equals("-o")) {

				request.setFileWrite(true);
				request.setFileWritePath(dataList.get(i + 1));

			}
		}

		request.setRequestMethod(dataList.get(1));

		URI uri = new URI(request.getRequestUrl());
		String hostName = uri.getHost();
		clientSocket = new Socket(hostName, 80);
		OutputStream output = clientSocket.getOutputStream();

		String path = uri.getPath();
		String query = uri.getQuery();

		if (path != null && query != null) {

			if (query.length() > 0 || path.length() > 0) {
				path = path + "?" + query;
			}
		}

		PrintWriter writer = new PrintWriter(output);

		if (path.length() == 0) {
			writer.println(request.getRequestMethod().toUpperCase() + " / HTTP/1.1");
		} else {
			writer.println(request.getRequestMethod().toUpperCase() + " " + path + " HTTP/1.1");
		}

		writer.print("Host: " + hostName + NEW_LINE);

		//for -d inline data
		if (request.isInlineData()) {
			if (request.getInlineData().contains("\'")) {

				request.setInlineData(request.getInlineData().replace("\'", ""));

			}
			writer.print("Content-Length: " + request.getInlineData().length() + NEW_LINE);

		// -f for sending file data	
		} else if (request.isFilesend()) {

			File filetoSend = new File(request.getFileSendPath());
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filetoSend));
			String string;
			while ((string = bufferedReader.readLine()) != null) {
				fileData.append(string);
			}
			writer.println("Content-Length: " + fileData.length() + NEW_LINE);

			bufferedReader.close();

		}

		// -h for http header
		if (request.isHttpHeader()) {
			if (!headerLst.isEmpty()) {

				for (int z = 0; z < headerLst.size(); z++) {
					String[] headerKeyValue = headerLst.get(z).split(":");
					writer.write(headerKeyValue[0] + ":" + headerKeyValue[1] + NEW_LINE);
				}
			}
		}

		if (request.isInlineData()) {
			writer.print(NEW_LINE);
			writer.print(request.getInlineData());
		} else if (request.isFilesend()) {
			writer.print(fileData);
			writer.print(NEW_LINE);
		} else {
			writer.print(NEW_LINE);
		}

		writer.flush();

	}
}
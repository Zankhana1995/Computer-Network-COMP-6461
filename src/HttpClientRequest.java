import java.util.ArrayList;
import java.util.List;

/**
 * Pojo class for collecting user request URL elements
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
public class HttpClientRequest {

	private String requestUrl;
	private String inlineData;
	private String redirectLocation;
	private String requestMethod;
	private String httpRequest;
	private String fileSendPath;
	private String fileWritePath;
	private boolean isVerbosePreset;
	private boolean isHttpHeader;
	private boolean isInlineData;
	private boolean isFilesend;
	private boolean isFileWrite;
	private boolean isRedirect;
	
	List<String> headerLst = new ArrayList<String>();

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getInlineData() {
		return inlineData;
	}

	public void setInlineData(String inlineData) {
		this.inlineData = inlineData;
	}

	public String getRedirectLocation() {
		return redirectLocation;
	}

	public void setRedirectLocation(String redirectLocation) {
		this.redirectLocation = redirectLocation;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(String httpRequest) {
		this.httpRequest = httpRequest;
	}

	public boolean isVerbosePreset() {
		return isVerbosePreset;
	}

	public void setVerbosePreset(boolean isVerbosePreset) {
		this.isVerbosePreset = isVerbosePreset;
	}

	public boolean isHttpHeader() {
		return isHttpHeader;
	}

	public void setHttpHeader(boolean isHttpHeader) {
		this.isHttpHeader = isHttpHeader;
	}

	public boolean isInlineData() {
		return isInlineData;
	}

	public void setInlineData(boolean isInlineData) {
		this.isInlineData = isInlineData;
	}

	public boolean isFilesend() {
		return isFilesend;
	}

	public void setFilesend(boolean isFilesend) {
		this.isFilesend = isFilesend;
	}

	public boolean isFileWrite() {
		return isFileWrite;
	}

	public void setFileWrite(boolean isFileWrite) {
		this.isFileWrite = isFileWrite;
	}

	public String getFileSendPath() {
		return fileSendPath;
	}

	public void setFileSendPath(String fileSendPath) {
		this.fileSendPath = fileSendPath;
	}

	public String getFileWritePath() {
		return fileWritePath;
	}

	public void setFileWritePath(String fileWritePath) {
		this.fileWritePath = fileWritePath;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public List<String> getHeaderLst() {
		return headerLst;
	}

	public void setHeaderLst(List<String> headerLst) {
		this.headerLst = headerLst;
	}
	
	
}

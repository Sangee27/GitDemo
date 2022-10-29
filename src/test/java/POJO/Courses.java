package POJO;

import java.util.List;

public class Courses {
	
	private List<webAutomation> webAutomation;
	private List<Api> api;
	private List<Mobile> mobile;
	
	public void getWebAutomation(List<POJO.webAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	
	public List<POJO.webAutomation> getWebAutomation() {
		return webAutomation;
	}
	public void getApi(List<Api> api) {
		this.api = api;
	}
	
	public List<Api> getApi() {
		return api;
	}
	public void getMobile(List<Mobile> mobile) {
		this.mobile = mobile;
	}
	
	public List<Mobile> getMobile() {
		return mobile;
	}
}

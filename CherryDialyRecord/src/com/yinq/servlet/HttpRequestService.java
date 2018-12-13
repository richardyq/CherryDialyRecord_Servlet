package com.yinq.servlet;

public enum HttpRequestService {
	UserService("userService"),
	SituationService("situationService"),
	HistoryService("historyService"),
	FunnyService("funnyService"),
	UnKnownService("Error.");
	
	private String name;
	
	private HttpRequestService(String service) {
		this.name = service;
	}
	
	public static HttpRequestService fromString(String serviceName) {
		HttpRequestService requestService = UnKnownService;
		for (HttpRequestService service : HttpRequestService.values()) {
			if (service.getName().equals(serviceName) == true) {
				requestService = service;
				break;
			}
		}
		
		return requestService;
	}

	public String getName() {
		return name;
	}

	
}

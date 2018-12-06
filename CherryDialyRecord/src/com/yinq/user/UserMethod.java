package com.yinq.user;


public enum UserMethod {
	LoginMethod("login"),
	UserInfoMethod("getUserInfo"),
	KidInfoMethod("getKidInfo"),
	UnkonwnMethod("Unkonwn");
	
	private String name;
	
	private UserMethod(String name) {
		this.name = name;
	}

	public static UserMethod fromString(String name) {
		UserMethod requestMethod = UnkonwnMethod;
		for (UserMethod method : UserMethod.values()) {
			if (method.getName().equals(name) == true) {
				requestMethod = method;
				break;
			}
		}
		
		return requestMethod;
	}

	public String getName() {
		return name;
	}

	
}

package com.yinq.funny.dispatcher;


public enum FunnyMethod {
	AddFunnyMethod("addFunny"),
	FunnyListMethod("funnyList"),
	UnkonwnMethod("Unkonwon");
	
	private String name;
	
	private FunnyMethod(String name) {
		System.out.println("SituationMethod -> " + name);
		this.setName(name);
	}

	public static FunnyMethod fromString(String name) {
		FunnyMethod requestMethod = UnkonwnMethod;
		for (FunnyMethod method : FunnyMethod.values()) {
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

	public void setName(String name) {
		this.name = name;
	}
}

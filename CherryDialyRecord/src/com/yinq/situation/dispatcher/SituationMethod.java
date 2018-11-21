package com.yinq.situation.dispatcher;



public enum SituationMethod {
	AddMealSituationMethod("addMealSituation"),
	TodayMealSituationMethod("todayMealSituation"),
	AddSleepSituationMethod("addSleepSituation"),
	TodaySleepSituationMethod("todaySleepSituation"),
	InterestCateList("getAllInterestList"),
	TodayInterestSituationMethod("todayInterestSituation"),
	AddInterestSituationMethod("addInsertstSituation"),
	UnkonwnMethod("Unkonwon");
	
	private String name;
	
	private SituationMethod(String name) {
		System.out.println("SituationMethod -> " + name);
		this.setName(name);
	}

	public static SituationMethod fromString(String name) {
		SituationMethod requestMethod = UnkonwnMethod;
		for (SituationMethod method : SituationMethod.values()) {
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

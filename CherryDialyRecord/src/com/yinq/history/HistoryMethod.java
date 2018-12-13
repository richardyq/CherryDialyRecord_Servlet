package com.yinq.history;


public enum HistoryMethod {

	RecordListMethod("recordList"),
	StatisticsMethod("statistics"),
	MealStatisticsMethod("mealStatistics"),
	SleepStatisticsMethod("sleepStatistics"),
	InterestStatisticsMethod("interestStatistics"),
	RecordTestMethod("test"),
	UnkonwnMethod("Unkonwon");
	
	private String name;
	
	private HistoryMethod(String name) {
		System.out.println("SituationMethod -> " + name);
		this.setName(name);
	}

	public static HistoryMethod fromString(String name) {
		HistoryMethod requestMethod = UnkonwnMethod;
		for (HistoryMethod method : HistoryMethod.values()) {
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

package com.yinq.datamodel;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonModel {
	
	public JsonModel() {
		
	}

	public String toJson() {
		Gson gson = createGson();
		String json = gson.toJson(this);
		return json;
	}
	
	 public JsonModel fromJson(String json)  {
		 
		 Gson gson = createGson();
		 JsonModel retModel = gson.fromJson(json, this.getClass());
		 
		 return retModel;
	}
	
	protected Gson createGson() {
//		Gson gson = new Gson();
//		return gson;
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		return builder.create();
	}
}

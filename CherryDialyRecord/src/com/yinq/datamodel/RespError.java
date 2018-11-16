package com.yinq.datamodel;



public class RespError {
	
	public static int urlServiceError = 0x10001;
	public static int urlMethodError = 0x10002;
	public static int urlInvalidParamError = 0x10003;
	
	public static int databaseError = 0x20001;
	
	//账号没有找到
	public static int userAccountNotFound = 0x30001;
	//登录密码不正确
	public static int userPasswordError = 0x30002;
}

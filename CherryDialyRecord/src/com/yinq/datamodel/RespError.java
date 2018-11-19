package com.yinq.datamodel;


public class RespError {
	
	public static final int urlServiceError = 0x10001;
	public static final int urlMethodError = 0x10002;
	public static final int urlInvalidParamError = 0x10003;
	
	public static final int databaseError = 0x20001;
	
	//账号没有找到
	public static final int userAccountNotFound = 0x30001;
	//登录密码不正确
	public static final int userPasswordError = 0x30002;
	//用户信息没有找到
	public static final int userNotFound = 0x30003;
	
	public static final int situationHasBeenExisted = 0x40001;
}

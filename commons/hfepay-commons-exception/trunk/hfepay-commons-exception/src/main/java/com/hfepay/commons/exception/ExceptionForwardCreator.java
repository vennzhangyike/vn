package com.hfepay.commons.exception;


public interface ExceptionForwardCreator {
	
	public ExceptionForward create(ErrorMessage err,Throwable th);
	
	public static final ExceptionForwardCreator DEFAULA_CREATOR = new ExceptionForwardCreator() {
		public ExceptionForward create(ErrorMessage err,Throwable th) {
			return ExceptionForward.create(th, err);
		}
		
	};
}

package com.wyndham.redesign.core.exception;

import org.slf4j.Logger;

/**
 * @Type Class
 * This is a base class about exception
 */

public class BaseException {
	
	/**
	 * This static function is used to handle the exception while facing in the code
	 * @param e
	 * @param logger
	 * @param message
	 */
	public static <T> void logException(T e,Logger logger,String message){
//		if(e instanceof PathNotFoundException){
//			logger.info("e"+e.toString());
//		}else if(e instanceof RepositoryException){
//			
//		}
		logger.info("Exception: "+message);
		logger.debug("Exception: "+message);
	}

}

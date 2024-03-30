package org.apache.commons.mail;

import java.util.Map;

import javax.mail.internet.MimeMessage;

public class EmailConcrete extends Email {
	
	@Override
	public Email setMsg(String msg) throws EmailException
	{
		if (msg != null)
		{
			//todo: set MimeMessage to message?
			//this.message = msg;
	        return this;
	    } 
		else
		{
	       return null;
	    }
	}
	
	// @return headers
	
	public Map<String, String> getHeaders()
	{
		return this.headers;
	}
	
	public String getContentType()
	{
		return this.contentType;
	}

}

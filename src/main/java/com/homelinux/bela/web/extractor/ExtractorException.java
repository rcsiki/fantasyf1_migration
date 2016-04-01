/*
 * Created on 11-Feb-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.homelinux.bela.web.extractor;

import java.io.PrintStream;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExtractorException extends Exception {

    private static final long serialVersionUID = -996313252590891431L;
    private Exception innerException;
	
	public ExtractorException() { super(); innerException = null; }
	public ExtractorException(String message) { super(message); innerException = null; }
	public ExtractorException(String message, Exception innerException) {
		super(message);
		this.innerException = innerException;
	}
	
	public String getMessage() {
		String message = super.getMessage() + "\n" + 
			   (innerException == null ? "" : innerException.getMessage());
		return message;
	}
	
	public void printStackTrace(PrintStream ps) {
		super.printStackTrace(ps);
		innerException.printStackTrace(ps);
	}
}

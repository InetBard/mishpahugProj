package telran.ashkelon2018.mishpahug.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserConflictException extends RuntimeException {
	
	 public UserConflictException() {
	        super();
	    }
	    public UserConflictException(String message) {
	        super(message);
	    }
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}

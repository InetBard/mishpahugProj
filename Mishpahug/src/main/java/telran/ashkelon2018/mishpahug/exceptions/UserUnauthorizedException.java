package telran.ashkelon2018.mishpahug.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserUnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String code = "401";
	String message = "Wrong login or password!";
}

package telran.ashkelon2018.mishpahug.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
@NoArgsConstructor
@Getter
public class UserUnprocessableEntity extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String code = "422";
	String message = "Invalid data!";
}

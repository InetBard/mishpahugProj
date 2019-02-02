package telran.ashkelon2018.mishpahug.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Address implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String city;
    String place_id;
//    "location":   {
//         "lat": "[Double]",
//         "lng":"[Double]"
	
}

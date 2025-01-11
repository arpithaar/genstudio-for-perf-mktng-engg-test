package com.adobe.roman_numeral_conversion;

import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Spring boot application to convert integer to its roman equivalent
 */
@SpringBootApplication
@Configuration
public class RomanNumeralConversionApplication implements WebMvcConfigurer{

	private static final Logger logger = LoggerFactory.getLogger(RomanNumeralConversionApplication.class);

	public static void main(String[] args) {

		logger.debug("Initializing RomanNumeralConversionApplication");
		SpringApplication.run(RomanNumeralConversionApplication.class, args);
	}

	/**
	 * Enabled CORS to avoid browser blocking requests b/w different
	 * origins by default since react app is running on a different port
     */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000")  // Allow React app
				.allowedMethods("GET");
	}

}

/**
 * Controller to expose the API endpoint
 */
@RestController
class RomanNumeralConversionController {

	private static final Logger logger = LoggerFactory.getLogger(RomanNumeralConversionController.class);

	// Storing roman values of digits from 0-9 when placed at different places
	String[] m = { "", "M", "MM", "MMM" };
	String[] c = { "",  "C",  "CC",  "CCC",  "CD",
			"D", "DC", "DCC", "DCCC", "CM" };
	String[] x = { "",  "X",  "XX",  "XXX",  "XL",
			"L", "LX", "LXX", "LXXX", "XC" };
	String[] i = { "",  "I",  "II",  "III",  "IV",
			"V", "VI", "VII", "VIII", "IX" };

	/**
	 *
	 * @param query Input
	 * @return json response containing input and output
	 */
	@GetMapping("/romannumeral")
	public ResponseEntity<Object> convertToRoman(@RequestParam int query) {
		logger.debug("Invoked endpoint /romannumeral for the input {}", query);
		String errorMessage = "";
		if(query < 1 || query > 3999){
			errorMessage = "Invalid! Input must be between 1 and 3999";
			logger.error("Input {} ::" + errorMessage, query);
		}else if (!String.valueOf(query).matches("^[0-9]+$")) {
			errorMessage="Invalid! Input must be a whole number";
		}
		if(StringUtils.isNotEmpty(errorMessage)){
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		String thousands = m[query / 1000];
		String hundreds = c[(query % 1000) / 100];
		String tens = x[(query % 100) / 10];
		String ones = i[query % 10];
		String roman =  thousands + hundreds + tens + ones;
		logger.info("Roman numeral for the input {} is {}", new Object[]{query, roman});
		RomanNumeralResponse romanNumeralResponse =  new RomanNumeralResponse(String.valueOf(query),roman);
		return new ResponseEntity<>(romanNumeralResponse, HttpStatus.OK);
	}

	@ExceptionHandler
	public ResponseEntity<String> handleException(Exception e) {
		logger.error("Exception" ,e);
		String errorMessage = e.getMessage();
		if(e.getCause() instanceof NumberFormatException){
			errorMessage = "Input must be a whole number";
		}
		return ResponseEntity.badRequest().body("Error: " + errorMessage);
	}
}

class RomanNumeralResponse{
	private String input;

	private String output;


	public RomanNumeralResponse(String input, String output) {
		this.input = input;
		this.output = output;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}


	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}

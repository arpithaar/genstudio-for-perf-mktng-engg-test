package com.adobe.roman_numeral_conversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
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

	public static void main(String[] args) {
		SpringApplication.run(RomanNumeralConversionApplication.class, args);
	}

	/**
	 * Enabled CORS to avoid browser blocking requests b/w different
	 * origins by default since react app is running on a different port
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000")  // Allow React app
				.allowedMethods("GET", "POST", "PUT", "DELETE");
	}

}

@RestController
class RomanNumeralConversionController {

	String[] m = { "", "M", "MM", "MMM" };
	String[] c = { "",  "C",  "CC",  "CCC",  "CD",
			"D", "DC", "DCC", "DCCC", "CM" };
	String[] x = { "",  "X",  "XX",  "XXX",  "XL",
			"L", "LX", "LXX", "LXXX", "XC" };
	String[] i = { "",  "I",  "II",  "III",  "IV",
			"V", "VI", "VII", "VIII", "IX" };

	@GetMapping("/romannumeral")
	public ResponseEntity<RomanNumeralResponse> convertToRoman(@RequestParam int query) {
		String thousands = m[query / 1000];
		String hundreds = c[(query % 1000) / 100];
		String tens = x[(query % 100) / 10];
		String ones = i[query % 10];
        String roman =  thousands + hundreds + tens + ones;
		RomanNumeralResponse romanNumeralResponse =  new RomanNumeralResponse(query,roman);
		return ResponseEntity.ok(romanNumeralResponse);
	}
}

class RomanNumeralResponse{
	private int input;

	private String output;


	public RomanNumeralResponse(int input, String output) {
		this.input = input;
		this.output = output;
	}

	public int getInput() {
		return input;
	}

	public void setInput(int input) {
		this.input = input;
	}


	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}

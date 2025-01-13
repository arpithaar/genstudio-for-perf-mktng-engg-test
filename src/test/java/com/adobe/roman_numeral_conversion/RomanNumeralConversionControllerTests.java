package com.adobe.roman_numeral_conversion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class RomanNumeralConversionControllerTests {

	private MockMvc mockMvc;

	@InjectMocks
	private RomanNumeralConversionController romanNumeralConversionController;

	@BeforeEach
	public void setup(WebApplicationContext webApplicationContext) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testConvertToRoman_validInput() throws Exception {
		// Arrange
		int query = 1987;
		RomanNumeralResponse expectedResponse = new RomanNumeralResponse("1987", "MCMLXXXVII");

		// Act & Assert
		mockMvc.perform(get("/romannumeral")
						.param("query", String.valueOf(query)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.input", is(expectedResponse.getInput())))
				.andExpect(jsonPath("$.output", is(expectedResponse.getOutput())));
	}

	@Test
	public void testConvertToRoman_invalidInput_low() throws Exception {
		// Arrange
		int query = 0;
		String expectedErrorMessage = "Invalid! Input must be between 1 and 3999";

		// Act & Assert
		mockMvc.perform(get("/romannumeral")
						.param("query", String.valueOf(query)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$", is(expectedErrorMessage)));
	}

	@Test
	public void testConvertToRoman_invalidInput_high() throws Exception {
		int query = 4000;
		String expectedErrorMessage = "Invalid! Input must be between 1 and 3999";
		mockMvc.perform(get("/romannumeral")
						.param("query", String.valueOf(query)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$", is(expectedErrorMessage)));
	}

	@Test
	public void testConvertToRoman_exceptionHandling() throws Exception {
		int query = -1;
		String expectedErrorMessage = "Invalid! Input must be between 1 and 3999";

		// Act & Assert
		mockMvc.perform(get("/romannumeral")
						.param("query", String.valueOf(query)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$", is(expectedErrorMessage)));
	}
}
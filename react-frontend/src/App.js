import React, { useState } from 'react';
import { Provider, defaultTheme, darkTheme } from '@adobe/react-spectrum';
import {TextField, Form, Button, Text} from '@adobe/react-spectrum';
import axios from 'axios';
import './App.css';

function App() {
    const [isDarkMode, setIsDarkMode] = useState(false);
    const [value, setValue] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [isValid, setIsValid] = useState(true);
    const [successMessage, setSuccessMessage] = useState('');

    // Function to display error messages
    const validateValue = (value) => {
        if (value === '') {
            setErrorMessage('This field is required.');
            setIsValid(false);
        } else if (isNaN(value) || value.trim() !== parseFloat(value).toString()) {
            setErrorMessage('Please enter a valid integer');
            setIsValid(false);
        } else if(value % 1 !== 0){
            setErrorMessage('Please enter a whole number');
            setIsValid(false);
        } else if (value < 1 || value > 3999) {
            setErrorMessage('Value must be between 1 and 3999.');
            setIsValid(false);
        } else {
            setErrorMessage('');
            setIsValid(true);
        }
    };

    // Toggle between themes
    const toggleTheme = () => {
        setIsDarkMode((prev) => !prev);
    };

    const handleChange = (value) => {
        setSuccessMessage('');
        setValue(value);
        validateValue(value);
    };
    const convertToRoman = async (event) => {
        event.preventDefault();
        if (value === "") {
            setErrorMessage('Input field is mandatory');
            setIsValid(false);
            return;
        }

        axios.get(`http://localhost:8080/romannumeral?query=${value}`)  // Java backend endpoint
            .then(response => {
                setSuccessMessage('Roman Numeral : '+response.data.output);  // Set the response message
            })
            .catch(error => {
                console.error('Backend error !', error);
                setErrorMessage('Something went wrong, please try again later');
            });
    };

    return (
        (
            <Provider theme={isDarkMode ? darkTheme : defaultTheme}>
                <div style={{ padding: '20px' }}>
                    <Button variant="cta" onPress={toggleTheme}>
                        Toggle Theme
                    </Button>

                    <h3 id="roman-numeral-converter">Roman Numeral Converter</h3>
                    <Form width={500} aria-labelledby="roman-numeral-converter" onSubmit={convertToRoman}>
                        <TextField label="Enter a number"
                                   value={value}
                                   onChange={handleChange}
                                   isRequired
                                   validationState={isValid ? 'valid' : 'invalid'}
                                   errorMessage={errorMessage}/>
                        <br/>
                        {isValid && <Text UNSAFE_className="boldGreenText">{successMessage}</Text>}
                        <Button variant="cta" type="submit" isDisabled={!!errorMessage}>Convert to Roman Numeral</Button>
                    </Form>
                </div>
            </Provider>

        )
    );
}
export default App;
import {useState} from "react";

function RegisterUserInput({typeText, holderText, pattern, onInput, id, passwordsMatch, reference}) {
    // Define state variables for the input value
    const [value, setValue] = useState("");
    const [isBlurred, setIsBlurred] = useState(false);
    // Handle changes to the input value
    const handleChange = (event) => {
        setValue(event.target.value);
    };
    // Handle when the input loses focus
    const handleBlur = () => {
        setIsBlurred(true);
    };

    let isValid = true;
    if (isBlurred) {
        isValid = value.match(pattern);
    }

    let feedback = null;
    let className = "bg-dark text-white border-white rounded";
    if (!isValid) {
        className += " is-invalid";
        feedback = (
            <div className="invalid-feedback">
                Please enter a valid {typeText === "password" ? "password" : "value"}.
            </div>
        );
    }
    if (isBlurred && passwordsMatch === false) {
        className += " is-invalid";
        feedback = (
            <div className="invalid-feedback">
                Passwords do not match.
            </div>
        );
    }

    return (
        <div className="mb-4">
            <input
                type={typeText}
                className={className}
                placeholder={holderText}
                onChange={handleChange}
                onBlur={handleBlur}
                onInput={onInput}
                ref={reference}
                id={id}
            />
            {feedback}
        </div>
    );
}

export default RegisterUserInput;

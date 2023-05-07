import { useState } from "react";

function UserInput({ typeText, holderText, pattern }) {
    const [value, setValue] = useState("");
    const [isTouched, setIsTouched] = useState(false);

    const handleChange = (event) => {
        setValue(event.target.value);
    };

    const handleBlur = () => {
        setIsTouched(true);
    };

    const isValid = !isTouched ? true : value.match(pattern);

    return (
        <div className="mb-4">
            <input
                type={typeText}
                className={`bg-dark text-white border-white rounded ${
                    !isValid ? "is-invalid" : ""
                }`}
                placeholder={holderText}
                value={value}
                onChange={handleChange}
                onBlur={handleBlur}
            />
            {!isValid && isTouched && (
                <div className="invalid-feedback">
                    Please enter a valid {typeText === "password" ? "password" : "value"}.
                </div>
            )}
        </div>
    );
}

export default UserInput;
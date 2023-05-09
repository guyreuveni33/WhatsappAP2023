import { useState } from "react";
import './LoginRegister.css';
import UserInput from "./login/userInput/UserInput";
import PageStruct from "./login/pageStruct/PageStruct";
import ImageUpload from "./login/imageUpload/ImageUpload";

function Register() {
    const [passwordsMatch, setPasswordsMatch] = useState(false);

    const handleFirstPassword = (event) => {
        const firstPassword = event.target.value;
        const secondPassword = document.getElementById("secondPassword").value;
        setPasswordsMatch(firstPassword === secondPassword);
    };

    const handleSecondPassword = (event) => {
        const secondPassword = event.target.value;
        const firstPassword = document.getElementById("firstPassword").value;
        setPasswordsMatch(firstPassword === secondPassword);
    };

    return (
        <PageStruct
            cardSetting="card-body text-center"
            margins="mb-md-5 mt-md-4"
            title="REGISTER"
            registerStatus="Already registered?"
            currentStatusLink="login.html"
        >
            <UserInput
                holderText=" Username"
                pattern={/^[a-zA-Z\s]+$/}
            />
            <UserInput
                typeText="password"
                holderText=" Password"
                pattern={/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/}
                onInput={handleFirstPassword}
                id="firstPassword"
            />
            <UserInput
                typeText="password"
                holderText=" Re-Enter Password"
                pattern={/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/}
                onInput={handleSecondPassword}
                id="secondPassword"
                passwordsMatch={passwordsMatch}
            />
            <UserInput
                holderText=" Display Name"
                pattern={/^[a-zA-Z\s]+$/}
            />
            <ImageUpload />
            <button className="btn btn-outline-light btn-lg px-4" type="submit">
                Register
            </button>
        </PageStruct>
    );
}

export default Register;

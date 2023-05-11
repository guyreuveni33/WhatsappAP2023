import {useRef, useState} from "react";
import './LoginRegister.css';
import RegisterUserInput from "./login/userInput/RegisterUserInput";
import PageStruct from "./login/pageStruct/PageStruct";
import ImageUpload from "./login/imageUpload/ImageUpload";
import {Link} from "react-router-dom";
import users from "./UsersDatabase";
import myImage from "./myImage.jpg"

function Register() {
    const username = useRef("");
    const [password, setPassword] = useState('');
    const [displayName, setDisplayName] = useState('');
    const [image, setImage] = useState(myImage);
    const [passwordsMatch, setPasswordsMatch] = useState(false);

    const handleFirstPassword = (event) => {
        const firstPassword = event.target.value;
        const secondPassword = document.getElementById("secondPassword").value;
        setPasswordsMatch(firstPassword === secondPassword);
        setPassword(firstPassword);
    };

    const handleSecondPassword = (event) => {
        const secondPassword = event.target.value;
        const firstPassword = document.getElementById("firstPassword").value;
        setPasswordsMatch(firstPassword === secondPassword);
        setPassword(firstPassword);
    };

    const handleDisplayName = () => {
        const registerDisplayName = document.getElementById("displayName").value;
        setDisplayName(registerDisplayName);
    }
    const handleImage = (image) => {
        const reader = new FileReader();
        reader.onload = () => {
            const dataUrl = reader.result;
            setImage(dataUrl);
        };
        reader.readAsDataURL(image);
    };

    const validateInputs = () => {
        const isValidUsername = username.current.value.match(/^[a-zA-Z\s]+$/);
        const isValidPassword = password.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/);
        const isValidDisplayName = displayName.match(/^[a-zA-Z\s]+$/);
        if (!isValidUsername || !isValidPassword || !isValidDisplayName || !passwordsMatch) {
            alert('One or more fields are missing or incorrect.');
            return false;
        }
        if ( username.current.value in users ){
            alert('Username is already taken.');
            return false;
        }
        return true;
    }

    const handleRegister = (e) => {
        if (validateInputs()) {
            users[username.current.value] = {
                password: password,
                displayName: displayName,
                image: image
            };
        } else {
            e.preventDefault();
        }
    };

    return (
        <PageStruct
            cardSetting="card-body text-center"
            margins="mb-md-5 mt-md-4"
            title="REGISTER"
            registerStatus="Already registered?"
            currentStatusLink="/"
        >
            <RegisterUserInput
                holderText=" Username"
                pattern={/^[a-zA-Z\s]+$/}
                reference={username}
                id="username"
            />
            <RegisterUserInput
                typeText="password"
                holderText=" Password"
                pattern={/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/}
                onInput={handleFirstPassword}
                id="firstPassword"
            />
            <RegisterUserInput
                typeText="password"
                holderText=" Re-Enter Password"
                pattern={/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/}
                onInput={handleSecondPassword}
                id="secondPassword"
                passwordsMatch={passwordsMatch}
            />
            <RegisterUserInput
                holderText=" Display Name"
                pattern={/^[a-zA-Z\s]+$/}
                onInput={handleDisplayName}
                id="displayName"
            />
            <ImageUpload
                onInput={handleImage}
                id="image"
            />
            <Link to="/"
                  className="btn btn-outline-light btn-lg px-4"
                  type="submit"
                  onClick={handleRegister}>
                Register
            </Link>
        </PageStruct>
    );
}

export default Register;


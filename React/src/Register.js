
import {useRef, useState} from "react";
import './LoginRegister.css';
import RegisterUserInput from "./login/userInput/RegisterUserInput";
import PageStruct from "./login/pageStruct/PageStruct";
import ImageUpload from "./login/imageUpload/ImageUpload";
import {Link} from "react-router-dom";
import users from "./UsersDatabase";
import myImage from "./myImage.jpg"
import {useNavigate} from "react-router-dom";

function Register() {
    // State variables to store the user's name, password, display name, and image
    const username = useRef("");
    const [password, setPassword] = useState('');
    const [displayName, setDisplayName] = useState('');
    const [image, setImage] = useState(myImage);
    // State variable to indicate whether the two password inputs match
    const [passwordsMatch, setPasswordsMatch] = useState(false);
    const navigate = useNavigate();

    // Event handler for the first password input field
    const handleFirstPassword = (event) => {
        const firstPassword = event.target.value;
        const secondPassword = document.getElementById("secondPassword").value;
        setPasswordsMatch(firstPassword === secondPassword);
        setPassword(firstPassword);
    };
    // Event handler for the second password input field
    const handleSecondPassword = (event) => {
        const secondPassword = event.target.value;
        const firstPassword = document.getElementById("firstPassword").value;
        setPasswordsMatch(firstPassword === secondPassword);
        setPassword(firstPassword);
    };
    // Event handler for the display name input field
    const handleDisplayName = () => {
        const registerDisplayName = document.getElementById("displayName").value;
        setDisplayName(registerDisplayName);
    }
    // Event handler for the image upload input field
    const handleImage = (image) => {
        const reader = new FileReader();
        reader.onload = () => {
            const dataUrl = reader.result;
            setImage(dataUrl);
        };
        reader.readAsDataURL(image);
    };
    // Function to validate user inputs
    const validateInputs = () => {
        const isValidUsername = username.current.value.match(/^[a-zA-Z\s]+$/);
        const isValidPassword = password.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/);
        const isValidDisplayName = displayName.match(/^[a-zA-Z\s]+$/);
        //this show message in case of invalid input on one or more of the fields
        if (!isValidUsername || !isValidPassword || !isValidDisplayName || !passwordsMatch) {
            alert('One or more fields are missing or incorrect.');
            return false;
        }
        if (username.current.value in users) {
            alert('Username is already taken.');
            return false;
        }
        return true;
    }
    //This insert the values of the user in case of valid inputs to the Database
    const handleRegister = async (e) => {
        e.preventDefault();
        if (validateInputs()) {
            const user = {
                username: username.current.value,
                password: password,
                displayName: displayName,
                profilePic: image,
            };
            await console.log(user)
            const response = await fetch('http://localhost:5001/api/Users', {
                'method': 'post',
                'headers': {
                    'Content-Type': 'application/json',
                },
                'body': JSON.stringify(user),
            });
            await console.log(await response)
            if (response.ok) {
                navigate("/")
                // User registration successful, handle the response or perform any additional actions
                console.log('User registered successfully');
            } else {
                // Error occurred during user registration, handle the error
                console.log('User registration failed');
            }
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
                pattern={/^\S+$/}
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
                pattern={/^[\w\s-]+$/}
                onInput={handleDisplayName}
                id="displayName"
            />
            <ImageUpload
                onInput={handleImage}
                id="image"
            />
            <button
                  className="btn btn-outline-light btn-lg px-4"
                  type="submit"
                  onClick={handleRegister}>
                Register
            </button>
        </PageStruct>
    );
}

export default Register;
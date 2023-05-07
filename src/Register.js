import './LoginRegister.css';
import UserInput from "./login/userInput/UserInput";
import PageStruct from "./login/pageStruct/PageStruct";

function Register() {
    return (
        <PageStruct cardSetting="card-body text-center" margins="mb-md-5 mt-md-4" title="REGISTER">
                            <div className="card-body">
                                <h1 className="card-title">REGISTER</h1>
                                <br></br>
                                <br></br>
                                <UserInput holderText=" Username"/>
                                <UserInput typeText="password" holderText=" Password"/>
                                <UserInput typeText="password" holderText=" Re-Enter Password"/>
                                <UserInput holderText=" Display Name"/>
                            <div className="mb-4">
                                <input className="form-control bg-dark text-white border-white rounded " type="file"
                                       id="formFile"></input>
                            </div>
                            <img
                                src="https://images.maariv.co.il/image/upload/f_auto,fl_lossy/c_fill,g_faces:center,h_533,w_758/569046"
                                alt="Image Description" width="175" height="150"></img>
                            <br></br>
                            <br></br>
                            <button className="btn btn-outline-light btn-lg px-4" type="submit">Register</button>
                        </div>
            <p>Already registered? <a href="login.html">Click here</a></p>

        </PageStruct>
)
    ;
}

export default Register;

import './LoginRegister.css';

function Register() {
    return (
        <section className="mainBG">
            <div className="d-flex justify-content-center align-items-center">
                <div className="card bg-dark text-white" style={{borderRadius: '1rem'}}>
                    <div className="card-body p- text-center">
                        <div className="mb-md-5 mt-md-4">
                            <div className="card-body">
                                <h1 className="card-title">REGISTER</h1>
                                <br></br>
                            <br></br>
                        <div className="mb-4"><input className="bg-dark text-white border-white rounded"
                                                 placeholder=" Username"></input>
                        </div>
                        <div className="mb-4"><input type="password" className="bg-dark text-white border-white rounded"
                                                 placeholder=" Password"></input>
                        </div>
                        <div className="mb-4"><input type="password" className="bg-dark text-white border-white rounded"
                                                 placeholder=" Re-Enter Password"></input>
                        </div>
                        <div className="mb-4"><input className="bg-dark text-white border-white rounded"
                                                 placeholder=" Display Name"></input>
                        </div>
                        <div className="mb-4">
                            <input className="form-control bg-dark text-white border-white rounded " type="file" id="formFile"></input>
                        </div>
                        <img src="https://images.maariv.co.il/image/upload/f_auto,fl_lossy/c_fill,g_faces:center,h_533,w_758/569046" alt="Image Description" width="175" height="150"></img>
                        <br></br>
                            <br></br>
                    <button className="btn btn-outline-light btn-lg px-4" type="submit">Register</button>
                </div>
                <p >Already registered? <a href="login.html">Click here</a></p>
            </div>
        </div>
</div>
</div>
</section>
    );
}

export default Register;

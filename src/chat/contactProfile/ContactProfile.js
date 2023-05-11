function ContactProfile({name,lastMessage,date,profilePicture}){
    return(
        <li className=" p-0.5 mt-2 chat-borders">
            <div className="d-flex justify-content-between">
                <div className="d-flex flex-row">
                    <div>
                        <img className="profilePic d-flex align-self-center me-3 "
                             src={profilePicture}
                             alt="avatar"  width="50"></img>
                    </div>
                    <div>
                        <p className="fw-bold mb-0 ms-1 me-1">{name}</p>
                        <p className="small ms-1">{lastMessage}</p>
                    </div>
                </div>
                <p className="small mb-1  px-1" >{date}</p>
            </div>
        </li>
    );
}
export default ContactProfile;
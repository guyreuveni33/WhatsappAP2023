import "./ContactProfile.css";

function ContactProfile({ name, lastMessage, date, profilePicture }) {
    return (
        <li className="p-0.5 mt-2 chat-borders">
            <div className="contactProfile">
                <div>
                    <img
                        className="profilePic"
                        src={profilePicture}
                        alt="avatar"
                        width="50"
                    />
                </div>
                <div className="content">
                    <p className="name fw-bold">{name}</p>
                    <p className="lastMessage small">{lastMessage}</p>
                </div>
                <p className="date mb-1 px-1">{date}</p>
            </div>
        </li>
    );
}

export default ContactProfile;

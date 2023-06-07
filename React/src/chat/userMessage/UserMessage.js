import "./UserMessage.css"

function UserMessage({text, time}) {
    const getTimeString = () => {
        const date = new Date(time);
        const hours = date.getHours().toString().padStart(2, "0");
        const minutes = date.getMinutes().toString().padStart(2, "0");
        return `${hours}:${minutes}`;
    };

    return (
        <div className="clearfix">
            <div className="message userTextMessage">
                <div className="message-content">
                    <p className="message-text">{text}</p>
                    <p className="message-time">{getTimeString()}</p>
                </div>
            </div>
        </div>
    );
}

export default UserMessage;
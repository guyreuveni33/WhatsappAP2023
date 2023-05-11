import ReceivedMessage from "../../receivedMessage/ReceivedMessage";
import UserMessage from "../../userMessage/UserMessage";
import './ChatMessages.css';
import MessageDB from "../../dataBase/MessagesDB";

function ChatMessages({messages}) {
    // Define messages state and setMessages state updater function

    return (
        <div className="chat-messages overflow-scroll hover">
            <div>

                {/* Map over messages state and render the appropriate component */}
                {messages.map((message, index) => {
                     if (message.type === "received") {
                        return <ReceivedMessage key={index} text={message.text} />;
                    } else if (message.type === "user") {
                        return <UserMessage key={index} text={message.text} />;
                    } else {
                        return null;
                    }
                })}
            </div>
        </div>
    );
}

export default ChatMessages;

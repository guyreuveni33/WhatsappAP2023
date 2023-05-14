import ReceivedMessage from "../../receivedMessage/ReceivedMessage";
import UserMessage from "../../userMessage/UserMessage";
import './ChatMessages.css';
import MessageDB from "../../dataBase/MessagesDB";

function ChatMessages({messages}) {
    // Define messages state and setMessages state updater function

    return (
        <div className="chat-messages overflow-scroll hover">
            <div>
                {messages.map((message, index) => {
                    if (message.type === "received") {
                        return (
                            <div key={index} className="message-container">
                                <ReceivedMessage text={message.text} />
                            </div>
                        );
                    } else if (message.type === "user") {
                        return (
                            <div key={index} className="message-container">
                                <UserMessage text={message.text} time={message.time} />
                            </div>
                        );
                    } else {
                        return null;
                    }
                })}
            </div>
        </div>
    );
}

export default ChatMessages;
import {useState} from "react";
import MessageDB from "../../dataBase/MessagesDB";

function SendMessageBar({setMessages, selectedContact, contacts, setLastMessage}) {

    // Define a state for the message input field
    const [message, setMessage] = useState("");

    // Event handler for when the user types in the message input field
    function handleMessageChange(event) {
        setMessage(event.target.value);
    }

    // Event handler for when the user clicks the send button
    function handleSendMessage() {
        setLastMessage(message);
        const trimmedMessage = message.trim();

        if (!trimmedMessage) {
            return;
        }

        // Get the current time
        const now = new Date();
        const hours = now.getHours().toString().padStart(2, '0');
        const minutes = now.getMinutes().toString().padStart(2, '0');
        const time = `${hours}:${minutes}`;

        // Add the user's message to the messages array
        setMessages(prevMessages => [
            ...prevMessages,
            {
                type: "user",
                text: trimmedMessage,
                time: time
            }
        ]);

        MessageDB[selectedContact] = Array.isArray(MessageDB[selectedContact]) ? [...MessageDB[selectedContact], {
            type: "user",
            text: trimmedMessage,
            time: time
        }] : [{
            type: "user",
            text: trimmedMessage,
            time: time
        }];

        // Clear the message input field
        setMessage("");

        for (let i = 0; i < contacts.length; i++) {
            if (contacts[i].name === selectedContact) {
                contacts[i].lastMessage = trimmedMessage;
            }
        }
    }

    // When the user hits the enter key the message wil be sent
    function handleKeyDown(event) {
        if (event.keyCode === 13 && !event.shiftKey) {
            event.preventDefault();
            handleSendMessage();
        }
    }

    return (
        <div className="chat-input">
            <input type="text"
                   className="text-white"
                   placeholder="Type your message here..."
                   value={message}
                   onChange={handleMessageChange}
                   onKeyDown={handleKeyDown}
                   disabled={selectedContact === "0"}
            ></input>
            <button className="bg-dark"
                    onClick={handleSendMessage}
                    disabled={!message || selectedContact === "0"}>Send
            </button>
        </div>
    );
}

export default SendMessageBar;
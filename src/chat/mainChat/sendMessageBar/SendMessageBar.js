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


        console.log(selectedContact);
        // Add the user's message to the messages array
        setMessages(prevMessages => [
            ...prevMessages,
            {
                type: "user",
                text: trimmedMessage,
                time: time // Add the time to the message object
            }
        ]);

        MessageDB[selectedContact] = Array.isArray(MessageDB[selectedContact]) ? [...MessageDB[selectedContact], {
            type: "user",
            text: trimmedMessage,
            time: time // Add the time to the message object
        }] : [{
            type: "user",
            text: trimmedMessage,
            time: time // Add the time to the message object
        }];
        // Clear the message input field
        setMessage("");
        console.log("first");
        for (let i = 0; i < contacts.length; i++) {
            if (contacts[i].name === selectedContact) {
                contacts[i].lastMessage = trimmedMessage;
            }
        }
    }

    function handleKeyDown(event) {
        if (event.keyCode === 13 && !event.shiftKey) {
            event.preventDefault(); // Prevent default enter key behavior (e.g. adding a new line)
            handleSendMessage();
        }
    }

    // Render the component
    return (
        <div className="chat-input">
            <input type="text"
                   className="text-white"
                   placeholder="Type your message here..."
                   value={message}
                   onChange={handleMessageChange}
                   onKeyDown={handleKeyDown}
            ></input>
            <button className="bg-dark"
                    onClick={handleSendMessage}
                    disabled={!message || selectedContact === "0"}>Send
            </button>
        </div>
    );
}

export default SendMessageBar;
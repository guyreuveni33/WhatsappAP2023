import { useState } from "react";
import MessageDB from "../../dataBase/MessagesDB";

function SendMessageBar({ setMessages,selectedContact,contacts }) {

    // Define a state for the message input field
    const [message, setMessage] = useState("");

    // Event handler for when the user types in the message input field
    function handleMessageChange(event) {
        setMessage(event.target.value);
    }

    // Event handler for when the user clicks the send button
    function handleSendMessage() {
        console.log(selectedContact);
        // Add the user's message to the messages array
        setMessages(prevMessages => [
            ...prevMessages,
            {
                type: "user",
                text: message
            }
        ]);

        MessageDB[selectedContact] = Array.isArray(MessageDB[selectedContact]) ? [...MessageDB[selectedContact], {
            type: "user",
            text: message
        }] : [{
            type: "user",
            text: message
        }];
        // Clear the message input field
        setMessage("");
        console.log("first");
        for (let i = 0; i < contacts.length; i++) {
            if (contacts[i].name === selectedContact) {
                contacts[i].lastMessage=message;
            }
        }
    }

    // Render the component
    return (
        <div className="chat-input">
            <input type="text" className="text-white" placeholder="Type your message here..." value={message} onChange={handleMessageChange}></input>
            <button className="bg-dark" onClick={handleSendMessage} disabled={!message ||   selectedContact === "0"}>Send</button>
        </div>
    );
}

export default SendMessageBar;
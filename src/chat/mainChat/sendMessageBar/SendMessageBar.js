import { useState } from "react";
import MessageDB from "../../dataBase/MessagesDB";

function SendMessageBar({ setMessages,selectedContact }) {

    // Define a state for the message input field
    const [message, setMessage] = useState("");

    // Event handler for when the user types in the message input field
    function handleMessageChange(event) {
        setMessage(event.target.value);
    }

    // Event handler for when the user clicks the send button
    function handleSendMessage() {

        // Add the user's message to the messages array
        setMessages(prevMessages => [
            ...prevMessages,
            {
                type: "user",
                text: message
            }
        ]);

      MessageDB[selectedContact]= [ ...MessageDB[selectedContact], {
            type: "user",
            text: message
        }];
        // Clear the message input field
        setMessage("");
        console.log("first");
    }

    // Render the component
    return (
        <div className="chat-input">
            <input type="text" className="text-white" placeholder="Type your message here..." value={message} onChange={handleMessageChange}></input>
            <button className="bg-dark" onClick={handleSendMessage} disabled={!message}>Send</button>
        </div>
    );
}

export default SendMessageBar;

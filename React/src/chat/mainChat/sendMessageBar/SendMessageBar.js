import {useState} from "react";
import MessageDB from "../../dataBase/MessagesDB";

function SendMessageBar({
                            messages, setLastMessage, lastMessage, selectedContact, socket,
                            token, fetchSelectedUserMessages, selectedUsername, username
                        }) {

    // Define a state for the message input field
    const [message, setMessage] = useState("");

    // Event handler for when the user types in the message input field
    function handleMessageChange(event) {
        setMessage(event.target.value);
    }

    const handleSendMessage = async (e) => {
        setMessage("");
        const trimmedMessage = message.trim();
        if (!trimmedMessage) {
            return;
        }

        const sentMessage = {msg: message};
        const response = await fetch(`http://localhost:5000/api/Chats/${selectedContact}/Messages`, {
            'method': 'post',
            'headers': {
                'Content-Type': 'application/json',
                authorization: token,
            },
            'body': JSON.stringify(sentMessage),
        });
        if (response.ok) {
            const currentDate = new Date();
            const formattedDate = currentDate.toISOString();
            const msg = {
                type: "received",
                text: trimmedMessage,
                time: formattedDate
            }
            const fullMsg = {
                receiver: await selectedUsername,
                sender: username,
                msg: msg,
                id: selectedContact
            }
            socket.current.emit('receiveMessage', fullMsg);
            await fetchSelectedUserMessages();
            setLastMessage(!lastMessage);
            // User registration successful, handle the response or perform any additional actions
            console.log('message sent successfully');
        } else {
            // Error occurred during user registration, handle the error
            console.log('sending of message failed');
        }
    };


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
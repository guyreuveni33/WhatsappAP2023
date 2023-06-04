import { useState, useEffect } from "react";
import "./Chat.css";
import Sidebar from "./chat/sidebar/Sidebar";
import MainChat from "./chat/mainChat/MainChat";
import ModalScreen from "./chat/modalScreen/ModalScreen";
import plaster from "./chat/mainChat/mainChatHeader/plaster.png"
import MessageDB from "./chat/dataBase/MessagesDB";

function Chat({ username, token }) {
    const currentUser = username;
    const userToken = token;
    const [contacts, setContacts] = useState([]);
    const [contactId, setContactId] = useState("0");
    const [lastMessage, setLastMessage] = useState(false);
    const [displayName, setDisplayName] = useState("");
    const [selectedDisplayName, setSelectedDisplayName] = useState("");
    const [selectedProfilePic, setSelectedProfilePic] = useState(plaster);
    const [profilePic, setProfilePic] = useState("");
    const [messages, setMessages] = useState([]);


    const fetchSelectedUserMessages = async () => {
        try {
            const response = await fetch(`http://localhost:5000/api/Chats/${contactId}`, {
                method: "get",
                headers: {
                    "Content-Type": "application/json",
                    authorization: userToken,
                },
            });

            if (response.ok) {
                const data = await response.json();
                //setMessages(data);
                setMessages(ADAPTER_messageList(data));
                //console.log(messages)
                //console.log(messages.profilePic);
                // console.log("response working");
                //const adaptedContacts = adaptContactsData(data);
                //setContacts(adaptedContacts);
            } else {
                console.error("Error fetching selected user messages:", response.status);
            }
        } catch (error) {
            console.error("Error fetching user messages:", error);
        }
    };

    useEffect(() => {
        if (contactId !== "0") {
            fetchSelectedUserMessages();
        }
    }, [contactId, userToken]);


    function ADAPTER_messageList(data) {
        var newData = [];
        setSelectedProfilePic(data["users"][0]["profilePic"]);
        for (let message of data["messages"]) {
            let messageType = message["sender"]["username"] === currentUser ? "user" : "received";
            const newMessage = {
                type: messageType,
                text: message["content"],
                id: message["id"],
                // profilePic: {
                //     [data["users"][0]["username"]] : data["users"][0]["profilePic"],
                //     [data["users"][1]["username"]] : data["users"][1]["profilePic"]
                // },
                time: message === null ? "" : message["created"],
            };
            //console.log("11111111111111111111111111");
            //console.log(newMessage.profilePic);
            newData = [...newData, newMessage];
        }
        return newData;
    }

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await fetch(`http://localhost:5000/api/Users/${currentUser}`, {
                    method: 'get',
                    headers: {
                        "Content-Type": "application/json",
                        authorization: userToken,
                    },
                });

                if (response.ok) {
                    console.log("check");
                    console.log(userToken);
                    const data = await response.json();
                    setDisplayName(data.displayName);
                    setProfilePic(data.profilePic);
                } else {
                    console.log(userToken);
                    console.log(currentUser);
                    console.error("Error fetching user data:", response.status);
                }
            } catch (error) {
                console.log(userToken);
                console.error("Error fetching user data:", error);
            }
        };
        fetchUserData();
    }, [currentUser, userToken]);
    const handleAddContact = (newContact) => {
        setContacts([...contacts, { ...newContact, lastMessage: "" }]);
    };

    const handleContactClick = (contactId, selectedDisplayName) => {
        setSelectedDisplayName(selectedDisplayName);
        setContactId(contactId);
        //console.log(selectedDisplayName);
        //console.log(contactId);
    };


    return (
        <div className="mainBG d-flex justify-content-center p-4">
            <div className="chat-container">
                <Sidebar
                    contacts={contacts}
                    handleAddContact={handleAddContact}
                    handleContactClick={handleContactClick}
                    selectedContact={contactId}
                    currentUser={currentUser}
                    lastMessage={lastMessage}
                    displayName={displayName}
                    profilePic={profilePic}
                    userToken={userToken}
                    selectedDisplayName = {selectedDisplayName}
                />
                <MainChat
                    initialMessages={
                        contactId === null ? "" : messages
                    }
                    selectedContact={contactId}
                    //contacts={contacts}
                    displayName = {selectedDisplayName}
                    profilePic = {selectedProfilePic}
                    token = {userToken}
                    fetchSelectedUserMessages={fetchSelectedUserMessages}
                    setLastMessage={setLastMessage}
                    lastMessage={lastMessage
                    }                />
            </div>
            <ModalScreen></ModalScreen>
        </div>
    );

}

export default Chat;
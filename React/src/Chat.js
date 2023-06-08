import {useState, useEffect, useRef} from "react";
import "./Chat.css";
import Sidebar from "./chat/sidebar/Sidebar";
import MainChat from "./chat/mainChat/MainChat";
import ModalScreen from "./chat/modalScreen/ModalScreen";
import plaster from "./chat/mainChat/mainChatHeader/plaster.png"
import MessageDB from "./chat/dataBase/MessagesDB";
import {io} from "socket.io-client";
import Login from "./Login";

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
    const socket = useRef(null);
    const [contactsSidebar, setContactsSidebar] = useState([]);
    const [newMessage, triggerNewMessage] = useState(null);
    const [selectedUsername, setSelectedUsername] = useState({});
    const fetchSelectedUserMessages = async () => {
        try {
            const response = await fetch(`http://localhost:5001/api/Chats/${contactId}`, {
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

    useEffect(()=> {
        if(newMessage === null || newMessage === undefined){
            return;
        }
        console.log("CONTACTS SIDEBAR " + JSON.stringify(contactsSidebar))
        const index = contactsSidebar.findIndex(c => c.username === newMessage.sender)
        console.log("INDEX " + index)
        setContactsSidebar((prev) => {
            var savedContact;
            if(index > -1){
                savedContact = prev.at(index)
                console.log("SAVED CONTACT " + savedContact)
                prev.splice(index, 1);
            }
            return [savedContact, ...prev];
        })
        console.log(JSON.stringify(newMessage))

        console.log("SELECTED USERNAME IN EFFECT " + JSON.stringify(selectedUsername))
        if(Object.keys(selectedUsername).length === 0)
            return;
        if(selectedUsername.id === newMessage.id) {
            setMessages([...messages, newMessage.msg]);
        }
    },[newMessage]);

    useEffect(()=> {
        socket.current = io('http://localhost:5001');
        console.log(socket.current)
        socket.current.emit("connecting", username);
        socket.current.on('addContact', ()=> {
            fetchChats();
        });
        socket.current.on('receiveMessage',(msg)=>{
            fetchChats().then(() => {
                triggerNewMessage(msg);
            });



        })
    },[username]);

    function ADAPTER_contactList (data) {
        var newData = [];
        for (let contact of data) {
            const newContact = {
                id: contact["id"],
                username: contact["user"]["username"],
                name: contact["user"]["displayName"],
                profilePicture: contact["user"]["profilePic"],
                lastMessage: contact[ "lastMessage"] === null ? "" : contact["lastMessage"]["content"],
                //TODO IN MONGO WE WILL SAVE THE LAST MESSAGE DATE!
                date: formatDate(contact["lastMessage"] === null ? "" : contact["lastMessage"]["created"]),
            };
            newData = [...newData, newContact]
        }
        return newData;
    }

    function formatDate(dateString) {
        const date = new Date(dateString);
        const formattedDate = date.toLocaleDateString("en-GB", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
        });
        return `${formattedDate}`;
    }


    const fetchChats = async () => {
        try {
            const response = await fetch("http://localhost:5001/api/Chats", {
                method: "get",
                headers: {
                    "Content-Type": "application/json",
                    authorization: userToken,
                },
            });

            if (response.ok) {
                const data = await response.json();
                //setContacts(data);
                await setContactsSidebar(ADAPTER_contactList(await data));
                await console.log("CON SIDE BAR " + contactsSidebar)
                //const adaptedContacts = adaptContactsData(data);
                //setContacts(adaptedContacts);
            } else {
                console.error("Error fetching chats:", response.status);
            }
        } catch (error) {
            console.error("Error fetching chats:", error);
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
                const response = await fetch(`http://localhost:5001/api/Users/${currentUser}`, {
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
        console.log("ADDED HANDLE ADD CXONTACTS " + newContact)
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
                    handleAddContact={handleAddContact}
                    handleContactClick={handleContactClick}
                    selectedContact={contactId}
                    currentUser={currentUser}
                    lastMessage={lastMessage}
                    displayName={displayName}
                    username={username}
                    profilePic={profilePic}
                    setSelectedUsername={setSelectedUsername}
                    contactsSidebar={contactsSidebar}
                    setContactsSidebar={setContactsSidebar}
                    userToken={userToken}
                    selectedDisplayName = {selectedDisplayName}
                    socket={socket}
                />
                <MainChat
                    initialMessages={
                        contactId === null ? "" : messages
                    }
                    username={username}
                    socket={socket}
                    selectedContact={contactId}
                    selectedUsername={selectedUsername}
                    //contacts={contacts}
                    displayName = {selectedDisplayName}
                    profilePic = {selectedProfilePic}
                    setMessage={setMessages}
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
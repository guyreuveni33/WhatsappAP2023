import avatar1 from "../avatars/1.png";
import avatar2 from "../avatars/2.png";
import avatar3 from "../avatars/3.png";
import avatar4 from "../avatars/4.png";
import avatar5 from "../avatars/5.png";
import avatar6 from "../avatars/6.png";
import avatar8 from "../avatars/8.png";
import avatar9 from "../avatars/9.png";
import './ModalScreen.css';
import {useState} from "react";

function ModalScreen({handleAddContact, token, fetchChats, contacts, socket, currentUser}) {
    // Setting up state for the name input field
    const [name, setName] = useState('');

    // Handling changes to the name input field
    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleAddClick = async () => {
        setName('');
        const existingContact = contacts.find(contact => contact.name === name);
        if (existingContact) {
            alert(`User '${name}' is already in the chat list.`);
            return;
        }
        const user = {
            "username" : `${name}`,
        };
        try {
            const response = await fetch("http://localhost:5001/api/Chats", {
                method: "post",
                headers: {
                    "Content-Type": "application/json",
                    authorization: token,
                },
                'body': JSON.stringify(user),
            });

            if (response.ok) {
                socket.current.emit('addContact', name);
                fetchChats();
                console.log("contact added in post");

            } else {
                console.error("Error adding contact:", response.status);
            }
        } catch (error) {
            console.error("Error fetching post request:", error);
        }
    };

    return (
        <div className="modal fade" id="exampleModal" tabIndex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalLabel">
                            Add new contact
                        </h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <div className="form-group">
                            <input type="text" className="form-control" placeholder="Contact's name" value={name}
                                   onChange={handleNameChange}/>
                        </div>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">
                            Close
                        </button>
                        <button type="button" className="btn btn-primary" onClick={handleAddClick}
                                data-bs-dismiss="modal">
                            Add
                        </button>

                    </div>
                </div>
            </div>
        </div>
    );
}

export default ModalScreen;

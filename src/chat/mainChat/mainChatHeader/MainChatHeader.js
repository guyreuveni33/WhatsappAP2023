import HeaderProfiles from "../../headerProfiles/HeaderProfiles";
import plaster from "./plaster.png"
import {Link} from "react-router-dom";
import MessageDB from "../../dataBase/MessagesDB";

function MainChatHeader(props) {
    function contactIden() {
        for (let i = 0; i < props.contacts.length; i++) {
            if (props.contacts[i].name === props.selectedContact) {
                console.log(i);
                return i;
            }
        }
        return -1;
    }
    // profilePicture="https://i.postimg.cc/BvrXRGr5/IMG-3411dvdv.jpg"

    const index = contactIden();

    function handleLogout() {
        for (let contact in MessageDB) {
            if (MessageDB.hasOwnProperty(contact)) {
                MessageDB[contact] = []; // set the array for the contact to an empty array
            }
        }
    }

    return (
        <div className="chat-header ">
            {/* <!-- Contact name or chat title will be displayed here -->*/}
            <HeaderProfiles pictureSetting="profileCurrentChatPic d-flex align-self-center me-3"
                            profilePicture={index ===-1 ? plaster :props.contacts[index].profilePicture}
                            textPosition="col text-white align-self-center"
                            textSetting="fw-bold mb-0 ms-1 " name={index===-1 ? " " :props.selectedContact}></HeaderProfiles>
            <div className="chat-input">
                <Link
                    to="/"
                    className="bg-dark text-white btn link-underline-opacity-0 link-underline"
                    onClick={handleLogout}
                >Logout
                </Link>
            </div>
        </div>
    );
}

export default MainChatHeader;
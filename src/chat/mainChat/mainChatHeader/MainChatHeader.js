import HeaderProfiles from "../../headerProfiles/HeaderProfiles";

function MainChatHeader(props) {
    function contactIden() {
        for (let i = 0; i < props.contacts.length; i++) {
            if (props.contacts[i].name === props.selectedContact) {
                console.log(i);
                return i;
            }
        }
        return 1;
    }
   // profilePicture="https://i.postimg.cc/BvrXRGr5/IMG-3411dvdv.jpg"

    const index = contactIden();
    return (
        <div className="chat-header overflow-hidden">
            {/* <!-- Contact name or chat title will be displayed here -->*/}
            <HeaderProfiles pictureSetting="profileCurrentChatPic d-flex align-self-center me-3"
                            profilePicture={props.contacts[index].profilePicture ==null ? "https://i.postimg.cc/BvrXRGr5/IMG-3411dvdv.jpg":props.contacts[index].profilePicture}
                            textPosition="col text-white align-self-center"
                            textSetting="fw-bold mb-0 ms-1 " name={props.selectedContact}></HeaderProfiles>
            <div className="chat-input">
                <button className="bg-dark">Log Out</button>
            </div>
        </div>
    );
}

export default MainChatHeader;
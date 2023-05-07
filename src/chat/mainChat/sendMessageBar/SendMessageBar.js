function SendMessageBar(){
    return(
        <div className="chat-input ">
            <input type="text" className="text-white" placeholder="Type your message here..."></input>
            <button className="bg-dark">Send</button>
        </div>
    );
}
export default SendMessageBar;
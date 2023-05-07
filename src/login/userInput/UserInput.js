function UserInput({typeText,holderText}){
    return(
        <div className="mb-4"><input type={ typeText }
                                     className="bg-dark text-white border-white rounded"
                                     placeholder={ holderText }></input>
        </div>
    );
}
export default UserInput;
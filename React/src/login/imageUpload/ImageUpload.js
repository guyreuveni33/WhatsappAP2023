import {useState} from "react";

function ImageUpload(props) {
    // Define a state variable to hold the selected image
     const [image, setImage] = useState("");
    // This function handles the image selection event
    const handleImageChange = (event) => {
        const file = event.target.files[0];
        const validImageTypes = ["image/gif", "image/jpeg", "image/png", "image/jpg"]; // Define valid image types
        if (!validImageTypes.includes(file.type)) {
            console.log("Invalid image type");
            return;
        }
        setImage(file);
        props.onInput(file);
    };

    return (
        <div className="mb-4">
            <input
                className="form-control bg-dark text-white border-white rounded" type="file" id="formFile"
                onChange={handleImageChange}/>
            <br/>
            {image && <img src={URL.createObjectURL(image)} alt="Preview" width="175" height="150"/>}
        </div>
    );
}

export default ImageUpload;

// ImageUpload.js
import { useState } from "react";

function ImageUpload() {
    const [image, setImage] = useState("");

    const handleImageChange = (event) => {
        const file = event.target.files[0];
        setImage(file);
    };

    return (
        <div className="mb-4">
            <input
                className="form-control bg-dark text-white border-white rounded" type="file" id="formFile"
                onChange={handleImageChange}/>
            <br />
            {image && <img src={URL.createObjectURL(image)} alt="Preview" width="175" height="150"/>}
        </div>
    );
}

export default ImageUpload;
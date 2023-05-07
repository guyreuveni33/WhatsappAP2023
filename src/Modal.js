import React, { useEffect } from "react";
import $ from "jquery";

function Modal() {
    useEffect(() => {
        $('[data-toggle="tooltip"]').tooltip();
    }, []);

    return (
        <div>
            {/* your modal content here */}
        </div>
    );
}

export default Modal;

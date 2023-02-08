function showAlert(){
    console.log("entered in to showAlert function");
    alert("showing alert from js in view.js");
}

function getImage() {
    alert("hi");
    console.log("entered into the function");
    var img = new Image();
    img.src ='/css/downloads.jpg';
    document.getElementById('addon').appendChild(img);
    down.innerHTML = "Image Element Added.";
}
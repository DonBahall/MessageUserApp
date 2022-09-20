let xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function (){
    if(this.readyState == 4 && this.status == 200){
        myFunction(this.responseText)
    }
}
xhttp.open("GET","http://localhost:8080",true)
xhttp.send();
document.getElementById("demo").innerHTML = xhttp.responseText;
function myFunction(){
    console.log(data);
}
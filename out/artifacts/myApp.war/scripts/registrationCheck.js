/**
 * Created by sergey on 07.03.15.
 */
function getMessage(){
    var text = document.location.href.split("?")[1].split("=")[1];
    if (text != "" && text.toString() != "null")
        alert(text.replace(/%20/g, " "));
}
function checkPass(){
    var pass1 = document.getElementById('pass');
    var pass2 = document.getElementById('confPass');
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    if(pass1.value == pass2.value){
        pass2.style.backgroundColor = goodColor;
        document.getElementById("ok").disabled = 0;
    }else{
        pass2.style.backgroundColor = badColor;
        document.getElementById("ok").disabled = 1;

    }
}
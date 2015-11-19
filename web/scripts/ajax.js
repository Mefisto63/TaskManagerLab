/**
 * Created by sergey on 29.03.15.
 */
var req;
function f(){
    setInterval(function(){
        sendRequest()
    }, 10000);
}
function callback(){
    if (req.readyState == 4){
        if (req.status == 200){
            var text = req.responseText;
            if (text != "") {
                alert(text);
            }
        }
    }
}
function sendRequest(){
    var url = "/myApp/time";
    req = new XMLHttpRequest();
    req.open("GET", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}
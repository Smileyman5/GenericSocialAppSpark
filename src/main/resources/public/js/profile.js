/**
 * Created by alex on 5/7/2017.
 */
window.onload = function WindowLoad(event) {
    //getPosts()
};

function getComments(post) {
    console.log(post);
    let comments = post['comments'];
    let html = "";
    for (index in comments) {
        html += "               <div style='padding: 0; margin: 0; height: 2px' class='gray divider'></div>" +
            "               <div style='padding: 0 0 0 5px; margin: 0; text-align: left'>" + comments[index]['username'] + "</div>\n" +
            "               <div style='text-align: left; padding: 0 0 0 20px; font-size:18px'>" + comments[index]['comment'] + "</div>\n ";
    }
    return html;
}
function getPosts() {
    let req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if (req.readyState === 4 && req.status === 200) {
            let res = JSON.parse(req.responseText);
            document.getElementById('profilePosts').innerHTML = "<br/><br/><div class='container'>" +
                "   <div class='row'>\n";

            for (index in res['posts'])
                document.getElementById('profilePosts').innerHTML += "   <div style='margin-left: 10%; width: 80%; margin-top: 30px' class='z-depth-2 center'>" +
                    "           <img src='https://www.drupal.org/files/profile_default.jpg' alt='' class='circle left' style='padding: 5px 0 0 5px' width='24' height='24'>\n" +
                    "           <h5 style='padding-left: 32px; margin: 0; text-align: left' class='icon_prefix'>" + res['posts'][index].user + "</h5>\n" +
                    "               <div style='padding: 0; margin: 0; height: 1px' class='black divider'></div>" +
                    "           <p class='center'>" + res['posts'][index].msg + "</p>\n" +
                    "           <p class='bottom' style='font-size: 8px; text-align: right; padding-right: 3px'> Posted at: " + res['posts'][index].timestamp + "</p>\n" +
                    "               <div style='padding: 0; margin: 0; height: 2px' class='gray divider'></div>" +
                    '           <g:form class="col s12" controller="posting" action="addCommentOrLike" method="post">'+
                    '               <div class="input-field col s12 row">' +
                    '                   <input type="hidden" name="postId" value="' + res['posts'][index].id + '"/>'+
                    '                   <g:textField id="icon_prefix2" autocomplete="off" spellcheck="on" class="materialize-textarea" style="overflow: hidden; padding: 0 .5em 0 0; width: 90%; font-size: 20px" name="comment">Post your thoughts...</g:textField>'+
                    '                   <label for="icon_prefix2"><i class="tiny material-icons">comment</i>Comment</label>' +
                    '               </div>'+
                    '           </g:form>' +
                    "           <div class='comment' style='inline-block'> " +
                    getComments(res['posts'][index]) +
                    "           </div>\n" +
                    "   </div>\n";

            document.getElementById('profilePosts').innerHTML += "   </div>\n" +
                "</div>\n";
        }
    };
    req.open("GET", "http://localhost:8080/profile/post/postForm", true);
    req.accept = "application/json";
    req.send(null);
}

function search(name, item) {
    let req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if (req.readyState === 4 && req.status === 200) {
            let res = JSON.parse(req.responseText);
            document.getElementsByClassName("search-results").item(item).innerHTML = "";
            for (index in res)
                document.getElementsByClassName("search-results").item(item).innerHTML += "<a style='z-index: 5' href='#!'>" + res[index] + "</a>\n"
        }
    };
    req.open("GET", "http://localhost:8080/restful/search/" + name, true);
    req.send(null);
}

function post() {
    let msg = document.getElementById('icon_prefix2').value;
    if (msg.length > 0) {
        let req = new XMLHttpRequest();
        req.onreadystatechange = function () {
            if (req.readyState === 4 && req.status === 200) {
                let res = JSON.parse(req.responseText);
                getPosts()
            }
        };
        req.open("POST", "http://localhost:8080/post", true);
        let obj = {
            "username": "${session['username']}",
            "message": msg
        };
        req.send(JSON.stringify(obj));
        document.getElementById('icon_prefix2').value = ""
    }
}
/*<div className="news" id='id'>
    <div className="uname">
        <img src="">
            USER_NAME
    </div>
    <div className="tags">fdsffsdfsdf</div>
    <div className="postImage"></div>
    <div className="postBody">fdsfsfsfs</div>

</div>
<div className="news" id='1id'>
    <div className="uname">
        <img src="">
            USER_NAME
    </div>
</div>*/

function hasImage(scr){
    if (scr != null){
        return `<img src = ${scr}>`
    }
}

$.get(
    '/news',
    function (data) {
        newslist = document.getElementById("newslist");

        data.forEach(element => {
            newslist.innerHTML += `
            <div class = "news" id='${element.id}+id'>
                <div class = "uname">
                    <img src = "${element.author.profilePic}">
                        ${element.author.login}
                </div>
                <div class = "tags">${element.tags}</div>
                <div class = "head">${element.header}</div>
                <div class = "postImage">${hasImage(element.image)}</div>
                <div class = "postBody">${element.body}</div>
            </div>`
        })
    }
)
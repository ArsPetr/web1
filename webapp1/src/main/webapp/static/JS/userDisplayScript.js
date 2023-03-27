
$.get(
    '/usersession',
    function (data) {
        gr = document.getElementById("username");
        gr.innerHTML = `${data.login}`;
        navbar = document.getElementById("navPages")
        if (data.role == "MODERATOR") {
            navbar.innerHTML += `<a href="main/postnews">Post news</a>`
        }
        if (data.role == "ADMIN"){
            navbar.innerHTML += `
                <a href="/main/postnews">Post news</a>
                <a href="/main/userlist">User List</a>
                `
        }
        avatar = document.getElementById("avatar");
        avatar.src = data.profilePic;
        console.log(data.profilePic)
    }
)
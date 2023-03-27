
/*
<div class = "user" id = "1id">
    <div class = "uname">
    <img src="avatar.jpg" class = "min">
     USER_NAME
    <button class = "edit" id = "1edit">Изменить</button>
    <button class = "save" id = "1save">Сохранить</button>
    </div>

</div>
<div class = "roles">
    <input type="radio" name = "USER_NAME" class = "USER"> User
    <input type="radio" name = "USER_NAME" class = "MODERATOR"> Moderator
    <input type="radio" name = "USER_NAME" class = "ADMIN"> Admin
</div>
*/


list = document.getElementById("List");


$.get('/users',function (data){
    data.forEach(element => {
        console.log("printing")
        list.innerHTML += `
    <div class = "user ${element.role}" id = "${element.id + "id"}">
        <div class = "uname">
            <img src="${element.profilePic}" class = "min">
            ${element.login}
            <button class = "edit" id = "${element.id + "edit"}">Изменить</button>
            <button class = "save" id = "${element.id + "save"}">Сохранить</button>
        </div>     
        <div class = "roles" id = "${element.id + "eButtons"}">       
        </div>
    </div>`
    });
});


function changeRole(newRole, id) {
    console.log(newRole)
    data.forEach (el => {
        if (el.id == id){
            el.role = newRole;
        }
    })
}

function findById(id){
    var rl;
    data.forEach(element => {
        if (element.id == id) {
            rl = element}
    })
    return rl;
}
var inputs = new Map();

function isUser(element){
    if (element.contains("USER")) return `checked`
}

function isAdmin(element){
    if (element.contains("ADMIN")) return `checked`
}

function isModerator(element){
    if (element.contains("MODERATOR")) return `checked`
}






document.body.addEventListener("click", function (event){
    if (event.target.className == "edit") {
        id = event.target.id.replace("edit", "");
        var usr = document.getElementById(id + "id");
        ud = usr.classList;
        roleBox = document.getElementById(id + "eButtons")
        if (!usr.classList.contains("edited")){
            roleBox.innerHTML = `
            
            
                <input type="radio" name = "${id + "name"}" id = "${id + "USER"}" class = "role" ${isUser(ud)}> User
                <input type="radio" name = "${id + "name"}" id = "${id + "MODERATOR"}" class = "role" ${isModerator(ud)}> Moderator
                <input type="radio" name = "${id + "name"}" id = "${id + "ADMIN"}" class = "role" ${isAdmin(ud)}> Admin
            
        `

            usr.classList.add("edited");
        }
    }
    if (event.target.className == "save") {
        id = event.target.id.replace("save", "");

        var usr = document.getElementById(id + "id");
        if (usr.classList.contains("edited")) {
            if (inputs.has(id)) {

                console.log("!!!");
                if (!usr.classList.contains(inputs.get(id))){
                    usr.classList.replace("ADMIN",inputs.get(id));
                    usr.classList.replace("MODERATOR",inputs.get(id));
                    usr.classList.replace("USER",inputs.get(id));
                    data = {
                        id: id,
                        login: null,
                        password: null,
                        role: inputs.get(id),
                        profilePic: null,
                    }
                    $.ajax({
                        url: '/users/'+ id,
                        type: 'PUT',
                        data: JSON.stringify(data),
                        datatype:"json",
                        contentType: 'application/json',
                        success: function (data){
                            alert("Success");
                        }
                    })

                    console.log("Change to " + inputs.get(id))
                }
            }
        }
        usr.classList.remove("edited");


        edits = document.getElementById(id + "eButtons")
        edits.innerHTML = ``;
        inputs.delete(id);
    }
})

document.body.addEventListener("input",function(event) {
    if (event.target.classList.contains("role")) {
        id = event.target.name.replace("name", "");
        inputs.set(id, event.target.id.replace(id, ""));
        console.log(inputs);
    }
})
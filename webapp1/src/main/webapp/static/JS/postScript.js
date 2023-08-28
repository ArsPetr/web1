
input = {
    tags: "",
    header: "",
    body: "",
    author:{
        login: "",
    },
}


document.body.addEventListener("input", function(event){
    if (event.target.id == "tags"){
        input.tags = event.target.value;
    }
    else if (event.target.id == "textHeader"){
        input.header = event.target.value;
    }
    else if (event.target.id == "mainText"){
        input.body = event.target.value;
    }
})

document.getElementById("submit").addEventListener("click",function () {
    let file = document.getElementById('image').files[0];
    let formData = new FormData();

    json = JSON.stringify(input);

    blob = new Blob([json],{type:'application/json'});
    input.author.login = document.getElementById("username").innerHTML;
    formData.append('image', file);
    formData.append("inputs",blob);
    console.log(blob)
    $.ajax({
        url: '/news',
        method: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        header: {
            'Accept': 'application/json',
            'Content-Type': 'multipart/form-data',
        },
        success: (response) => {
            console.log(input);
            alert("success")
        },
    })
})
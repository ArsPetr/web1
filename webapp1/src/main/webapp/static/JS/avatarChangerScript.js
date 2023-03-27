
$(document).on('click', '#submit', function(){

    login = document.getElementById("username").innerHTML;

    let file = document.getElementById('file').files[0];
    let formData = new FormData();
    formData.append('stringKeyToGetValue', file);

    $.ajax({
        url: '/users/' + login,
        method: 'PUT',
        data: formData,
        contentType: false,
        processData: false,
        success: (response) => {
            alert("success")
        },
    })

});
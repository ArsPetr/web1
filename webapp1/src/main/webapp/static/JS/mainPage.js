$.get(
    'usersession',
    function (data) {
            gr = document.getElementById("userName")
            gr.innerHTML = `HI ${data.login}!`
    }
)

var btn = document.getElementsByClassName("collapse");
btn[0].classList.toggle("active");
c = btn[0].nextElementSibling;
c.style.display = "none";
btn[0].addEventListener("click", function () {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    
    if (content.style.display === "block") {
        content.style.display = "none";
    } else {
        content.style.display = "block";
    }
});

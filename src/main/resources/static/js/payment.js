

var radios = document.querySelectorAll('input[type=radio]');
var checked = document.querySelectorAll('input[type=radio]:checked');
var error_not_selected = document.getElementById('error_not_selected');
var btn = document.querySelector('[type=submit]');
var btn_div = document.getElementById('order-basket');

if(!checked.length){
    btn.setAttribute("disabled", "disabled");
    btn_div.classList.add("add-to-cart-empty")
}

btn_div.addEventListener('click', function () {
    if (btn_div.classList.contains("add-to-cart-empty")) {
        error_not_selected.style.display = "block";
    }
});

radios.forEach(function(el){
    el.addEventListener('click', function(){
        checked = document.querySelectorAll('input[type=radio]:checked');

        if(checked.length){
            error_not_selected.style.display = "none";
            btn.removeAttribute("disabled");
            btn_div.classList.remove("add-to-cart-empty")
        }
    });
});


history.pushState(null, null, document.URL);
window.addEventListener('popstate', function () {
    history.pushState(null, null, document.URL);
});
window.onload = function (){
    function check() {
        var need = document.getElementById('gon-need');
        var good = document.getElementById('gon-good');
        var imgNeed = document.getElementById('need-img');
        var imgGood = document.getElementById('good-img');
        if(need.checked == true )
            imgNeed.innerHTML= "<img  src=\"../../market/images/need3.png\" style=\"margin-left: 4px\" width=\"20px\" height=\"20px\" alt=\"\"/>";
        else
            imgNeed.innerHTML= "<img  src=\"../../market/images/need.png\" style=\"margin-left: 4px\" width=\"20px\" height=\"20px\" alt=\"\"/>";
        if(good.checked == true)
            imgGood.innerHTML= "<img  src=\"../../market/images/goods2.png\" style=\"margin-left: 4px\" width=\"20px\" height=\"20px\" alt=\"\"/>";
        else
            imgGood.innerHTML= "<img  src=\"../../market/images/goods.png\" style=\"margin-left: 4px\" width=\"20px\" height=\"20px\" alt=\"\"/>";
        need.onclick = function () {
            if(need.checked == true)
            {
                imgNeed.innerHTML= "<img  src=\"../../market/images/need3.png\" style=\"margin-left: 4px\" width=\"20px\" height=\"20px\" alt=\"\"/>";
                //console.log(imgNeed);
                imgGood.innerHTML= "<img src=\"../../market/images/goods.png\" style=\"margin-left: 4px\" width=\"20px\" height=\"20px\" alt=\"\"/>";
            }
            else
                imgNeed.innerHTML= "<img  src=\"../../market/images/need.png\" style=\"margin-left: 4px\" width=\"20px\" height=\"20px\" alt=\"\"/>";
        }
        good.onclick = function () {
            if(good.checked == true)
            {
                imgGood.innerHTML= "<img  src=\"../../market/images/goods2.png\" style=\"margin-left: 4px\" width=\"20px\" height=\"20px\" alt=\"\"/>";
                console.log(imgNeed);
                imgNeed.innerHTML= "<img  src=\"../../market/images/need.png\" style=\"margin-left: 4px\" width=\"20px\" height=\"20px\" alt=\"\"/>";
            }else
                imgGood.innerHTML= "<img  src=\"../../market/images/goods.png\" style=\"margin-left: 4px\" width=\"20px\" height=\"20px\" alt=\"\"/>";
        }
    }
    check();
}
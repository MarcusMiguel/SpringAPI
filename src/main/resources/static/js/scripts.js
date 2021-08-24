$(document).ready(function () {
    var activeTab = document.getElementsByClassName("shop-tab")[0]
    activeTab.classList.toggle("active")
    var activeTabContent = document.getElementsByClassName("tab-pane")[0]
    activeTabContent.classList.add("in", "active", "show")
      
    $(document).on("click", "#cancelbtn", function (e) {
        cancelCart(e.currentTarget.attributes.shopcode.value, e.currentTarget.attributes.productcode.value)
    })

    $(document).on("click", ".cartbutton", function (e) {
        var idproduct = e.currentTarget.attributes.idproduct.value
        var idshop = e.currentTarget.attributes.idshop.value
        var quantity = document.getElementById(e.currentTarget.attributes.shopname.value + 'input' + idproduct).value
        addToCart(idshop, idproduct, quantity)
    })

    $(document).on("click", "#myLink", function (e) {
        openMenu(e, e.currentTarget.attributes.shopname.value)
    })

    $(document).on("click", ".shop-tab", function (e) {
        activeTab.classList.toggle("active")
        activeTab = e.currentTarget
        e.currentTarget.classList.toggle("active") 
       })
})


function cancelCart(shopid, productid) {
    console.log("removing productid: " + productid + " shop: " + shopid);
    $.ajax({
        type: "POST",
        url: "https://marcusmiguel-restapi.herokuapp.com/api/carts/removeUI/" + shopid + "/" + productid,
        complete: function (data) {
            var shopproductsrow = '#shop-products-row'+shopid
            setTimeout(() => { console.log("product removed from cart"); }, 500)
            $("#cartmenu").load(location.href + " #cartmenu>*", "")
            $("#total-cost").load(location.href + " #total-cost>*", "")
             $(shopproductsrow).load(location.href + ' ' + shopproductsrow + ">*", "")
        }
    })
}

function addToCart(shopid, productid, quantity) {
    console.log("adding product, shopid: " + shopid + " productid: " + productid + " quantity: " + quantity);
    $.ajax({
        type: "POST",
        url: "https://marcusmiguel-restapi.herokuapp.com/api/carts/insertUI/" + shopid + "/" + productid + "/" + quantity,
        complete: function (data) {
            var shopproductsrow = '#shop-products-row'+shopid
            setTimeout(() => { console.log("product added to cart"); }, 500)
            $("#cartmenu").load(location.href + " #cartmenu>*", "")
            $("#total-cost").load(location.href + " #total-cost>*", "")
              $(shopproductsrow).load(location.href + ' ' + shopproductsrow + ">*", "")
        }
    })
}


$(document).on('click', '.nav-link', function (event) {
    event.preventDefault();

    $('html, body').animate({
        scrollTop: $($.attr(this, 'href')).offset().top-90
    }, 500);
});
// aplly coupon
let tongTienHangBill = document.querySelector("#tongTienHangBill");
let tienGiamGiaBill = document.querySelector("#tienGiamGiaBill");
let tongTienThanhToanBill = document.querySelector("#tongTienThanhToanBill");
let flagUseCoupon = 0;
console.log(tongTienHangBill.textContent);

jQuery(document).ready(function ($) {
  $("#apply_coupon").click(function (e) {
    e.preventDefault();
    flagUseCoupon = 1;
    tongTienThanhToanBill.textContent =
      parseInt($(tongTienHangBill).text()) -
      parseInt($(tienGiamGiaBill).text());
  });
});

// minus products
let minusProducts = document.querySelectorAll("#minusSanPham");
let sumProducts = document.querySelectorAll("#soluongSanPham");
let listSanPham = document.querySelectorAll("#tenSanPham");

let giaMotSanPham = document.querySelectorAll("#giaMotSanPham");
let giaNSanPham = document.querySelectorAll("#giaNSanPham");
let valueTemp = 0;

for (let i = 0; i < minusProducts.length; i++) {
  minusProducts[i].addEventListener("click", () => {
    // console.log('minus clicked');

    if (sumProducts[i].value > 1) {
      sumProducts[i].value -= 1;
    } else {
      sumProducts[i].value = 1;
    }
    giaNSanPham[i].textContent =
      parseInt($(giaMotSanPham[i]).text()) * sumProducts[i].value;
    updateBill();
  });
}

// add products
let addProducts = document.querySelectorAll("#plusSanPham");

for (let i = 0; i < addProducts.length; i++) {
  addProducts[i].addEventListener("click", () => {
    // console.log('add clicked');

    sumProducts[i].value = parseInt(sumProducts[i].value) + 1;
    // console.log(sumProducts[i].value);

    valueTemp = parseInt($(giaMotSanPham[i]).text()) * sumProducts[i].value;
    giaNSanPham[i].textContent = valueTemp;
    updateBill();
  });
}

function updateBill() {
  tongTienThanhToanBill.textContent = 0;
  tongTienHangBill.textContent = 0;
  for (let i = 0; i < giaNSanPham.length; i++) {
    tongTienHangBill.textContent =
      parseInt(tongTienHangBill.textContent) +
      parseInt($(giaNSanPham[i]).text());

    tongTienThanhToanBill.textContent = parseInt(tongTienHangBill.textContent);

    // if(flagUseCoupon=1){
    //     tongTienThanhToanBill.textContent =
    //     parseInt(tongTienHangBill.textContent) - parseInt($(tienGiamGiaBill).text());;
    // } else{
    //     tongTienThanhToanBill.textContent =
    //     parseInt(tongTienHangBill.textContent);
    // }
  }
}

// Merge 1
function arrMerge(key, value) {
  var object = {};
  for (var i = 0; i < key.length; i++) {
    object[key[i]] = value[i];
  }
  return object;
}

// Process Data
$("#updateButton").click(function (e) {
  e.preventDefault();

  // Convert to array
  const listReturnProducts = [];
  const listReturnAmountProducts = [];
  for (var i = 0; i < listSanPham.length; i++) {
    listReturnProducts.push(listSanPham[i].text);
    listReturnAmountProducts.push(sumProducts[i].value);
  }

  // Merge 2
  var data = [];
  for (var x = 0; x < listSanPham.length; x++) {
    var element = {
      'name': listReturnProducts[x],
      'amount': listReturnAmountProducts[x],
    };
    data.push(element);
  }

  // Merge and stringify
//   var mergeString = arrMerge(listReturnProducts, listReturnAmountProducts);
  var finalString = JSON.stringify(data);
  console.log(data);

  $.ajax({
    type: "post",
    url: "/cart/saveCart",
    data: finalString,
    dataType: "json"
  }).done(function (result) {
    console.log(result);
  });
});

// $(numberProducts.forEach).click(function(e){
//     console.log($('#soluongSanPham').val() -=1);
//     // document.querySelector('#tongTienThanhToan') = ;

// })

// alert("Hello")

// const numberProducts = document.querySelectorAll('#soluongSanPham')

// $(numberProducts.forEach).click(function(e){
//     e.preventDefault()
//     var soluongSanPham = $('#soluongSanPham').val()
//     console.log('soluongSanPham: ' + soluongSanPham)

//     $.ajax({
//         // url: '/cart',
//         // type: 'get',
//         // data:{

//         // }
//     })
// })

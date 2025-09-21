$(document).ready(function () {
    loadProducts();
    loadCategoriesForSelect();

    // Thêm product
    $("#formAddProduct").submit(function (e) {
        e.preventDefault();
        let formData = new FormData(this);

        $.ajax({
            url: "/api/product/addProduct",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function () {
                alert("Thêm sản phẩm thành công!");
                loadProducts();
            },
            error: function () {
                alert("Thêm thất bại!");
            }
        });
    });
});

// Load danh sách sản phẩm
function loadProducts() {
    $.get("/api/product", function (res) {
        let rows = "";
        res.body.forEach(p => {
            rows += `
                <tr>
                    <td>${p.productId}</td>
                    <td>${p.productName}</td>
                    <td>${p.quantity}</td>
                    <td>${p.unitPrice}</td>
                    <td>${p.category ? p.category.categoryName : ""}</td>
                    <td><img src="/uploads/${p.images}" width="50"/></td>
                    <td>
                        <button onclick="deleteProduct(${p.productId})">Xóa</button>
                    </td>
                </tr>`;
        });
        $("#productTable tbody").html(rows);
    });
}

// Xóa product
function deleteProduct(id) {
    $.ajax({
        url: "/api/product/deleteProduct?productId=" + id,
        type: "DELETE",
        success: function () {
            alert("Xóa thành công!");
            loadProducts();
        },
        error: function () {
            alert("Xóa thất bại!");
        }
    });
}

// Load category cho dropdown khi thêm product
function loadCategoriesForSelect() {
    $.get("/api/category", function (res) {
        let options = "";
        res.body.forEach(c => {
            options += `<option value="${c.categoryId}">${c.categoryName}</option>`;
        });
        $("#categoryId").html(options);
    });
}

$(document).ready(function () {
    loadCategories();

    // Thêm category
    $("#formAddCategory").submit(function (e) {
        e.preventDefault();
        let formData = new FormData(this);

        $.ajax({
            url: "/api/category/addCategory",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function () {
                alert("Thêm thành công!");
                loadCategories();
            },
            error: function () {
                alert("Thêm thất bại!");
            }
        });
    });
});

// Load danh sách
function loadCategories() {
    $.get("/api/category", function (res) {
        let rows = "";
        res.body.forEach(c => {
            rows += `
                <tr>
                    <td>${c.categoryId}</td>
                    <td>${c.categoryName}</td>
                    <td><img src="/uploads/${c.icon}" width="50"/></td>
                    <td>
                        <button onclick="deleteCategory(${c.categoryId})">Xóa</button>
                    </td>
                </tr>`;
        });
        $("#categoryTable tbody").html(rows);
    });
}

// Xóa category
function deleteCategory(id) {
    $.ajax({
        url: "/api/category/deleteCategory?categoryId=" + id,
        type: "DELETE",
        success: function () {
            alert("Xóa thành công!");
            loadCategories();
        },
        error: function () {
            alert("Xóa thất bại!");
        }
    });
}

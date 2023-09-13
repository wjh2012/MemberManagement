$(document).ready(function () {
    $("#masterCheckbox").click(function () {
        var subCheckboxes = $(".subCheckbox");
        subCheckboxes.prop("checked", $(this).prop("checked"));
    });
});
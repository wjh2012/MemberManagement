function submitForm(page) {
    let formData = {
        "offsetPagingRequest": {
            "page": page,
            "pageSize": $('#pageSize').val() || null,
        },
        "memberSearchCondition": {
            "isOauth": $('#isOauth').val() || null,
            "authType": $('#authType').val() || null,
            "nickname": $('#nickname').val() || null,
            "role": $('#role').val() || null
        }
    };

    $.ajax({
        url: location.pathname,
        type: 'POST',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        success: function (response) {
            console.log("ajax");
            drawList(response.list)

            const pageInfo = {
                currentPage: page,
                totalRecord: response.recordInfo.totalRecordCount,
                pageSize: formData.offsetPagingRequest.pageSize,
                pageGroupSize: 5
            };

            renderPagination(pageInfo);
        }
    });



}
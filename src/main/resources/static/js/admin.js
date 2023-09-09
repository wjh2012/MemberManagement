function addDummyRequest() {
    $.ajax({
        url: '/admin/addDummyMember',
        method: 'GET',
        success: function (response) {
            alert('더미 데이터 생성에 성공했습니다.');
            location.reload();
        },
        error: function () {
            alert('더미 데이터 생성에 실패했습니다.');
        }
    });
}

function addDummysRequest() {
    $.ajax({
        url: '/admin/addDummyMembers',
        method: 'GET',
        success: function (response) {
            alert('더미 데이터 생성에 성공했습니다.');
            location.reload();
        },
        error: function () {
            alert('더미 데이터 생성에 실패했습니다.');
        }
    });
}
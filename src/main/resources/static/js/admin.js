function addDummyRequest() {
    $.ajax({
        url: '/admin/addDummyMember',
        method: 'Post',
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
        method: 'Post',
        success: function (response) {
            alert('더미 데이터 생성에 성공했습니다.');
            location.reload();
        },
        error: function () {
            alert('더미 데이터 생성에 실패했습니다.');
        }
    });
}

function deleteMember() {
    const selectedIds = [];

    // 모든 체크박스를 선택합니다.
    const checkboxes = document.querySelectorAll('.subCheckbox');

    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            // 체크된 체크박스의 부모 <tr> 요소를 찾아서 해당 회원의 ID를 가져옵니다.
            const memberId = checkbox.closest('tr').querySelector('td:nth-child(2)').textContent;
            selectedIds.push(memberId);
        }
    });

    if (selectedIds.length > 0) {
        const request = {
            selectedIds: selectedIds
        };
        console.log(request);
        fetch('/admin/deleteMembers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(request),
        })
            .then(response => {
                console.log(response);
                if (response.ok) {
                    alert('선택된 회원이 성공적으로 삭제되었습니다.');
                    location.reload();
                } else {
                    response.text().then(errorMessage => {
                        alert('삭제 중 오류 발생: ' + errorMessage);
                    });
                }
            })
            .catch(error => {
                console.error('POST 요청 중 오류 발생:', error);
            });
    } else {
        alert('선택된 회원이 없습니다.');
    }
}
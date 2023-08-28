function renderPagination(pageInfo) {
    // ----- 페이지네이션 요소 계산 ----- //
    let totalPage = Math.ceil(pageInfo.totalRecord / pageInfo.pageSize);
    let currentPageGroup = Math.ceil(pageInfo.currentPage / pageInfo.pageGroupSize);

    let firstPageInGroup = ((currentPageGroup - 1) * pageInfo.pageGroupSize) + 1;
    let lastPageInGroup = ((currentPageGroup * pageInfo.pageGroupSize) > totalPage) ? totalPage : (currentPageGroup * pageInfo.pageGroupSize);

    let prev = firstPageInGroup - 1;
    let next = lastPageInGroup + 1;

    // 페이지네이션 요소 생성
    let fragmentPage = document.getElementsByClassName("pagination")[0];
    fragmentPage.innerHTML = '';

    // 페이지네이션이 필요 없다면 종료
    if (pageInfo.totalRecord <= pageInfo.pageSize) return;

    const paginationList = document.createElement('ul');
    paginationList.classList.add('pagination-list');

    if (prev >= 1) {
        const prevButton = createPaginationLink(prev, 'Previous');
        const prevItem = createPaginationListItem(prevButton);
        paginationList.appendChild(prevItem);
    }

    for (let i = firstPageInGroup; i <= lastPageInGroup; i++) {
        const pageButton = createPaginationLink(i, i.toString());
        const pageItem = createPaginationListItem(pageButton, i === pageInfo.currentPage);
        paginationList.appendChild(pageItem);
    }

    if (next <= totalPage) {
        const nextButton = createPaginationLink(next, 'Next');
        const nextItem = createPaginationListItem(nextButton);
        paginationList.appendChild(nextItem);
    }

    fragmentPage.appendChild(paginationList);
}

function createPaginationLink(page, label) {
    const anchor = document.createElement('a');
    anchor.setAttribute('role', 'button');
    anchor.innerText = label;
    anchor.href = '#';
    anchor.onclick = function() {
        submitForm(page)
    };
    return anchor;
}


function createPaginationListItem(button, isActive = false) {
    const listItem = document.createElement('li');
    if (isActive) {
        listItem.classList.add('active');
    }
    listItem.appendChild(button);
    return listItem;
}

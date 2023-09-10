function drawList(list) {
    const tbody = document.querySelector('tbody');
    tbody.innerHTML = '';
    if (!list.isEmpty) {
        console.log(list)
        list.forEach(row => {
            const tr = document.createElement('tr');

            const tdCheckbox = document.createElement('td');
            const checkbox = document.createElement('input');
            checkbox.className = 'subCheckbox'
            checkbox.type = 'checkbox';
            tdCheckbox.appendChild(checkbox);
            tr.appendChild(tdCheckbox);

            const tdId = document.createElement('td');
            tdId.textContent = row.id;
            tr.appendChild(tdId);

            const tdIsOauth = document.createElement('td');
            tdIsOauth.textContent = row.oauth;
            tr.appendChild(tdIsOauth);

            const tdAuthType = document.createElement('td');
            tdAuthType.textContent = row.authType;
            tr.appendChild(tdAuthType);

            const tdOauthId = document.createElement('td');
            tdOauthId.textContent = row.oauthId;
            tr.appendChild(tdOauthId);

            const tdNickname = document.createElement('td');
            tdNickname.textContent = row.nickname;
            tr.appendChild(tdNickname);

            const tdUsername = document.createElement('td');
            tdUsername.textContent = row.username;
            tr.appendChild(tdUsername);

            const tdCreatedDatetime = document.createElement('td');
            tdCreatedDatetime.textContent = new Date(row.createdDatetime).toLocaleString('en-US', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit'
            });
            tr.appendChild(tdCreatedDatetime);

            const tdRoles = document.createElement('td');
            const ul = document.createElement('ul');
            row.roles.forEach(role => {
                const li = document.createElement('li');
                li.textContent = role;
                ul.appendChild(li);
            });
            tdRoles.appendChild(ul);
            tr.appendChild(tdRoles);

            tbody.appendChild(tr);
        });
    }
}
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

            const tdNickname = document.createElement('td');
            const link = document.createElement('a');
            link.textContent = row.nickname;
            link.href = `members/${row.id}`;
            tdNickname.appendChild(link);
            tr.appendChild(tdNickname);


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
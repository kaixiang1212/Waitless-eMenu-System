const API_URL = window.location.host;

// const root = document.getElementById('root');

document.body.addEventListener('click', (event) => {
	const target = event.target;
	if (target.classList.contains('navbut')) {
		switch (target.textContent) {
/*			case 'Add Category':
				buildAddCat();
				break;*/
			case 'Edit Category':
				buildEditCat();
				break;
			case 'Edit Item':
				buildEditItem();
				break;
			case 'Reorder Menu':
				buildReorder();
				break;
		}
	}
});

/*function buildAddCat() {
	make_active('add-cat');

	const content = clearContent();

	// make form
	const form = document.createElement('form');
	form.id = 'category-form';
	content.appendChild(form);
	// heading
	const heading = document.createElement('h3');
	heading.textContent = 'Add a new menu category:';
	form.appendChild(heading);
	// category name input
	const name = document.createElement('input');
	name.id = 'name-field';
	name.placeholder = 'CATEGORY NAME';
	form.appendChild(name);
	// button
	const button = document.createElement('button');
	button.type = 'submit';
	button.textContent = 'Confirm';
	form.appendChild(button);

	// form listener
	form.addEventListener('submit', async (event) => {
		event.preventDefault();
		// header
		const head = {
			'Content-Type': 'application/json',
			'Access-Control-Allow-Origin': `${API_URL}/category/add`
		};
		// make JSON
		const data = {
			"name": `${name.value}`
		};
		// fetch
		const response = await fetch(`${API_URL}/category/add`, {
			method: 'POST',
			headers: head,
			body: JSON.stringify(data)
		});
		const json = await response.json();
		//switch (response.status) {
		// DO STUFF HERE
		//};
		console.log(json);
		const newC = clearContent();
		const msg = document.createElement('p');
		msg.textContent = `${response.status}: ${json.message}`;
		newC.appendChild(msg);
	});
}*/

function buildEditCat() {
	make_active('edit-cat');

	const content = clearContent();

	// placeholder
	const ph = document.createElement('h3');
	ph.textContent = 'Edit categories:';
	content.appendChild(ph);
}

function buildEditItem() {
	make_active('edit-item');

	const content = clearContent();

	// placeholder
	const ph = document.createElement('h3');
	ph.textContent = 'Edit menu items:';
	content.appendChild(ph);
}

function buildReorder() {
	make_active('reorder');

	const content = clearContent();

	// placeholder
	const ph = document.createElement('h3');
	ph.textContent = 'Reorder menu categories and items:';
	content.appendChild(ph);
}

function clearContent() {
	const root = document.getElementById('root');

	const old = document.getElementById('content');
	old.remove();

	const content = document.createElement('div');
	content.id = 'content';
	root.appendChild(content);

	return content;
}

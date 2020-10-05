const board = document.getElementById('category_list');

const b1 = document.getElementById('add-cat');
const b2 = document.getElementById('view-cat');
const b3 = document.getElementById('add-item');
const b4 = document.getElementById('view-item');

const navbars = [b1,b2,b3,b4];
let navbars_category = [];
let sortable;

function make_active(navbar_name){
    navbars.forEach((navbar) => {
        navbar.classList.remove('active');
    });
    navbars_category.forEach((navbar) => {
        navbar.classList.remove('active');
    });
    document.getElementById(navbar_name).classList.add('active');
}

function init_navbar_drag() {
    sortable = Sortable.create(board, {
        'store' : {
            set: async function(sortable) {
                const order = sortable.toArray();
                console.log(order);
                let data = {'category_order': order};
                const head = {'Content-Type': 'text/plain'};
                try {
                    const response = await fetch('/management/update_category_order', {
                        method: 'POST',
                        headers: head,
                        body: JSON.stringify(data)
                    });
                    if (response.status !== 200) {
                        if (!alert(`Server Error ${response.status}\nFailed saving category priority`)){
                            window.location.reload();
                        }
                    }
                    console.log(response);
                } catch (e) {
                    if (!alert(`Error ${e}\nFailed saving category priority`)){
                        window.location.reload();
                    }
                    save_btn.disabled = false;
                }
            }
        }
    });
}


function add_navbar_category(category_name) {
    const li = document.createElement('li');
    li.className = ('nav-item');
    li.id = category_name;
    li.setAttribute('data-id', category_name);

    const a = document.createElement('a');
    a.className = 'nav-link';
    a.href = '/management/categories/' + category_name;

    const span = document.createElement('span');
    span.innerText = category_name;

    board.appendChild(li);
    li.appendChild(a);
    a.appendChild(span);
    navbars_category.push(li);
}

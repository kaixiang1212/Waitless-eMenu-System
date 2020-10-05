var grid1;
var grid2;

// initCategoryGrid();
initMenuItemGrid();


window.addEventListener('load', function () {
    // grid1.refreshItems().layout();
    grid2.refreshItems().layout();
});

function initCategoryGrid(){
    grid1 = new Muuri('.grid-1', {
        items: ".category",
        dragEnabled: true,
        dragContainer: document.body,
        dragSort: function () {
            return [grid1]
        }
    });
}

function initMenuItemGrid(){
    grid2 = new Muuri('.grid-2', {
        items: ".item",
        dragEnabled: true,
        dragContainer: document.body,
        dragSort: function () {
            return [grid2]
        }
    });
}

function addMenuItemCard(){
    const title = "Menu Item Custom";
    const price = "$19.99";
    const image_src = "http://placehold.it/700x400";
    const description = "nothing to say";

    const item = document.createElement("div");
    item.className = "menu-item card";
    item.style.width = "250px";

    const card_body = document.createElement('div');
    card_body.className = "card-body";

    const card_title = document.createElement('h5');
    card_title.className = "card-title";

    const price_tag = document.createElement('h5');
    price_tag.textContent = price;

    card_title.innerText = title;

    const description_tag = document.createElement('p');
    description_tag.className = "card-text";
    description_tag.textContent = description;

    const edit_button = document.createElement("a");
    edit_button.className = "btn btn-primary";
    edit_button.style.float = "right";
    edit_button.innerText = "Edit";
    edit_button.href = "#";

    const image = document.createElement('img');
    image.src = image_src;
    image.className = "card-img-top";

    item.appendChild(image);
    item.appendChild(card_body);
    card_title.appendChild(edit_button);
    card_body.appendChild(card_title);
    card_body.appendChild(price_tag);
    card_body.appendChild(description_tag);

    grid2.add(item);
    grid2.show(item);

    return item;
}

function newCategory() {

    const category_name = 'New Category';

    const item = document.createElement("div");
    item.className = "item card category";

    const item_content = document.createElement('div');
    item_content.style.width = "200px";

    const category = document.createElement('a');
    category.className = "list-group-item";
    category.href = '#';
    category.innerText = category_name;

    item.appendChild(item_content);
    item_content.appendChild(category);

    grid1.add(item);

    return item;
}

function clearMenuItem(){
    const items = grid2.getItems();
    grid2.destroy(true);
    // delete items;
    initMenuItemGrid();
}
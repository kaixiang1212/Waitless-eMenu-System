import time

from src.database import MenuDB
from src.MenuManager import MenuManager

mDB = MenuDB()
MM = MenuManager(mDB)

""" Menu Setup """
mDB.drop_db()
#MM.add_category("Sushi")
#MM.add_category("Ramen")
#MM.add_category("Fish")

MM.add_category("Salads")
MM.add_category("Burgers")
MM.add_category("Steak")
MM.add_category("Pizza")
MM.add_category("Dessert")

MM.add_item(name="Veg Green Salad", description="Vegetable Green Salad", cost="9.99", cat_names=["Salads"],
                     imagePath="veg_green_salad.jpg", ingredients=["Spinach", "Tomatoes", "Purple Cabbage"])

MM.add_item(name="Greek Salad", description="Meditteranean Greek Salad", cost="9.99", cat_names=["Salads"],
                     imagePath="greek_salad.jpg", ingredients=["Feta Cheese", "Capsicum", "Black Olives"])

MM.add_item(name="Beet Salad", description="Meditteranean Greek Salad", cost="9.99", cat_names=["Salads"],
                     imagePath="beet_salad.jpg", ingredients=["Beet", "Kale", "Tomatoes"])

MM.add_item(name="Fried Chicken Burger", description="Fried Chicken Burger with Coleslaw", cost="11.99", cat_names=["Burgers"],
                     imagePath="fried_chicken_burger.jpg", ingredients=["Chicken", "Cheese", "Coleslaw"])

MM.add_item(name="Wagyu Burger", description="Wagyu Beef Burger with Onion", cost="14.99", cat_names=["Burgers"],
                     imagePath="wagyu_beef_burger.jpg", ingredients=["Beef", "Cheese"])

MM.add_item(name="Chicken Steak", description="Chicken Steak", cost="13.99", cat_names=["Steak"],
                     imagePath="chicken_steak.jpg", ingredients=["Chicken"])

MM.add_item(name="Sirloin Steak", description="Sirloin Steak", cost="16.99", cat_names=["Steak"],
                     imagePath="sirloin_steak.jpg", ingredients=["Beef"])

MM.add_item(name="Pepperoni Pizza", description="Pepperoni Pizza", cost="14.99", cat_names=["Pizza"],
                     imagePath="pepperoni_pizza.jpg", ingredients=["Pork"])

MM.add_item(name="Margherita Pizza", description="Margherita Pizza", cost="19.99", cat_names=["Pizza"],
                     imagePath="margherita_pizza.jpg", ingredients=["Cheese"])

MM.add_item(name="Vegetarian Pizza", description="Vegetarian Pizza", cost="13.99", cat_names=["Pizza"],
                     imagePath="veg_pizza.jpg", ingredients=["Cheese"])

MM.add_item(name="Chicken Supreme Pizza", description="Chicken Supreme Pizza", cost="16.99", cat_names=["Pizza"],
                     imagePath="chicken_supreme_pizza.jpg", ingredients=["Chicken"])
                    
MM.add_item(name="Tiramisu", description="Tiramisu", cost="10.99", cat_names=["Dessert"],
                     imagePath="tiramisu.jpg", ingredients=["Cream"])

MM.add_item(name="Strawberry Cheesecake", description="Strawberry Cheesecake", cost="10.99", cat_names=["Dessert"],
                     imagePath="strawberry_cheesecake.jpg", ingredients=["Cheese"])    

MM.add_item(name="Brownie", description="Brownie", cost="9.99", cat_names=["Dessert"],
                     imagePath="brownie.jpg", ingredients=["Chocolate"])                                                                

#MM.add_item(name="Sushi", description="Looks nice", cost="5", cat_names=["Sushi", "Fish"],
#                     imagePath="800px-Salmon_sushi_cut.jpg", ingredients=["Salmon", "Rice"])

#MM.add_item(name="Ramen", description="Looks delicious", cost="10", cat_names=["Ramen"],
#                     imagePath="8e0b7eff68b5d1977c844ee1580934f9.jpg", ingredients=[])

#MM.add_item(name="Egg Sushi", description="Looks ok", cost="10", cat_names=["Sushi"],
#                     imagePath="Sweet-Egg-Nigiri.png", ingredients=[])


""" Order Setup """
#                (name,      cost, quantity, table,  time,        status)
#MM.add_order_item("Ramen",      "10",   "2", "5", time.time() - 1,  "New")
#MM.add_order_item("Sushi",      "3",    "2", "2", time.time(),      "New")
#MM.add_order_item("Ramen",      "10",   "2", "3", time.time() + 1,  "New")
#MM.add_order_item("Egg Sushi",  "3",    "2", "2", time.time() + 2,  "Preparing")
#MM.add_order_item("Ramen",      "10",   "2", "1", time.time() + 3,  "Ready")


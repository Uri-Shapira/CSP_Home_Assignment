CSP Home Assignment README

1. Class Structure:
	a. User
		- Represnts a "user" in the system
		- Abstract class with the general properties of every user in the mall
		  (name, id, address, etc.) 
	b. Customer
		- A type of User which has a PaymentMethod and BankDetails and a shopping cart
		- Customer is able to add items to his shopping cart and to make purchases (assuming a valid payment method)
		- Customer has a shoppingHistory map which can in the future enable to make recommendations to the Customer
	c. Owner
		- A type of User which has a list of none or one or many Store objects which he owns
		- in these Store objects, the owner can add/remove/update catalogs of items
	d. Store
		- A store object which contains list of Catalog of items, a list of owners, and a map of prices to be discounted
		- Store also has a "blacklist" list of Users which can in the future enable "problematic" users from making purchases
	e. Catalog
		- Catalog has a list of Item and a map of updated discounts (if any) for the items it holds
	f. Item
		- the purchase item itself which Customer can buy
		- Item has a price denoting its current cost
	g. PaymentMethod
		- A sort of credit card with a number, cvv, expiration date
		- PaymentMethod can be used to make purchases by Customer, but only if it is valid (for now I only check the 
		  expiration but in the future can be extended to check with a bank API).
	h. BankDetails
		- The Customer bank details
		- Includes bankId, branchId, and account number
		- This can be used to refund Customer which is not satisfied, etc.
	i. ShoppingCart
		- Includes a Store and a list of ShoppingCartEntity to be bought by a Customer
		- Also has a totalPrice denoting the amount to be paid by the user
	j. ShoppingCartEntity
		- Has a Catalog and an Item to be bought as well as the quantity (the amount of it the user wants)
	k. Mall
		- Includes some general static methods to populate the Users and Stores DB files
		- In the future can be extended to manage the operation of many stores
	l. Logger
		- Utility  singleton class used to add a logging of all events in the application
		- Events are written into file log.txt
	m. Test
		- Testing class with an entry point method to enable testing of the different application functionalities
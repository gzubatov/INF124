window.onload = run;

let errors = '';

function run() {
	const onFormSubmit = (event) => {
		const identifier = document.querySelector('#prod_id').value;
		const qty = document.querySelector('#quantity').value;
		const firstName = document.querySelector('#fname').value;
		const lastName = document.querySelector('#lname').value;
		const shipAddress = document.querySelector('#addr').value;
		const creditCardNum = document.querySelector('#ccn').value;
		const ccv = document.querySelector('#security').value;
		const phoneNum = document.querySelector('#phonenum').value;
		const city = document.querySelector('#city').value;
		const zip = document.querySelector("#zipcode").value;
		
		verifyIdentifier(identifier);
		verifyQuantity(qty);
		verifyName(firstName, lastName);
		verifyPhoneNumber(phoneNum);
		verifyAddress(shipAddress, city, zip);
		verifyCreditCard(creditCardNum, ccv);

		if (errors !== '') {
			alert(errors);
			event.preventDefault();
		}
	};

	const form = document.querySelector('form');
	form.addEventListener('submit', onFormSubmit);
}

/*
Functions to verify the form input
*/
function verifyIdentifier(identifier) {
	if(identifier === '')
	{
		errors += 'Product Identifier field is empty\n';
	}
}

function verifyQuantity(qty) {
	if (qty.length === 0) {
		errors += 'Must enter a quantity\n';
	}
	else if (isNaN(qty) || parseInt(qty) <= 0) {
		errors += 'Cannot enter Quantity Value below 1\n';
	}
}

function verifyName(firstName, lastName) {
	if (firstName === '') {
		errors += 'First Name Field is empty\n';
	}
	if (lastName === '') {
		errors += 'Last Name Field is empty\n';
	}
}

function verifyPhoneNumber(phoneNum) {
	if (phoneNum === '') {
		errors += 'Phone Number Field is empty\n';
	}
}

function verifyAddress(shipAddress, city, zip) {
	if (shipAddress === '') {
		errors += 'Shipping Address Field is empty\n';
	}
	if (city === '') {
		errors += 'City cannot be empty\n';
	}
	if (isNaN(zip) || zip.length !== 5) {
		errors += 'Zip code is not valid\n';
	}
}

function verifyCreditCard(creditCardNum, ccv) {
	if (isNaN(creditCardNum) || creditCardNum.length !== 16) {
		errors += 'Credit card number is invalid. Should be 16 digits.\n';
	}

	if (isNaN(ccv) || ccv.length !== 3) {
		errors += 'CCV is invalid. Should be 3 digits.\n';
	}
}

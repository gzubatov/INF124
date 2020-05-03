window.onload = run;
let errors = '';

let PRICE;

function run() {
	PRICE = document.querySelector('#price').value.substring(1);
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
		const zip = document.querySelector('#zipcode').value;

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
		errors = '';
	};

	const form = document.querySelector('form');
	form.addEventListener('submit', onFormSubmit);

	document.querySelector('#zipcode').addEventListener('blur', getZip);
	document.querySelector('#quantity').addEventListener('keyup', updatePrice);
}

/*
Functions to verify the form input
*/
function verifyIdentifier(identifier) {
	if (identifier === '') {
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

function getZip(event) {
	let zipcode = event.target.value;

	if (zipcode !== '' && !isNaN(zipcode)) {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				var result = xhr.responseText;
				const res = JSON.parse(result);
				if (res['city'] !== -1) {
					const qty = document.querySelector('#quantity').value;
					document.querySelector('#city').value = res['city'];
					document.querySelector('#state').value = res['state'];
					document.querySelector('#tax').value = res['combined_rate'];
					const price = document.querySelector('#price').value.substring(1);
					const tax = 1 + parseFloat(document.querySelector('#tax').value);
					const total = price * tax;
					document.querySelector('#total').value = '$' + total.toFixed(2);
				}
			}
		};

		xhr.open('GET', '../pages/getZip.php?zip=' + zipcode, true);
		xhr.send();
	}
}

function updatePrice(event) {
	console.log(event);
	let quantity = event.target.value;
	if (!isNaN(quantity) && quantity >= 0) {
		document.querySelector('#price').value = '$' + (PRICE * quantity).toFixed(2);
		let tax_tag = document.querySelector('#tax').value;
		if (tax_tag !== '') {
			//let tax = document.querySelector('#tax').value;
			let tax = 1 + parseFloat(document.querySelector('#tax').value);
			let total = PRICE * quantity * tax;
			document.querySelector('#total').value = '$' + total.toFixed(2);
		}
	}
	else if (quantity < 0) {
		document.querySelector('#price').value = '$' + (0 * quantity).toFixed(2);
	}
}

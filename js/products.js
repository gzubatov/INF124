window.onload = run;

function run() {
	const onFormSubmit = (event) => {
		console.log('swan');
		const qty = document.querySelector('#quantity').value;
		const firstName = document.querySelector('#fname').value;
		const lastName = document.querySelector('#lname').value;
		const shipAddress = document.querySelector('#addr').value;
		const creditCardNum = document.querySelector('#ccn').value;
		const ccv = document.querySelector('#security').value;
		const phoneNum = document.querySelector('#phonenum').value;

		if (isNaN(qty) === false && parseInt(qty) <= 0) {
			alert('Cannot enter Quantity Value below 1');
			event.preventDefault();
		}

		if (firstName === '') {
			alert('First Name Field is empty');
			event.preventDefault();
		}
		if (lastName === '') {
			alert('Last Name Field is empty');
			event.preventDefault();
		}
		if (phoneNum === '') {
			alert('Phone Number Field is empty');
			event.preventDefault();
		}
		if (shipAddress === '') {
			alert('Shipping Address Field is empty');
			event.preventDefault();
		}

		if (isNaN(creditCardNum) === false && creditCardNum.length !== 16) {
			alert('Credit card number is invalid. Should be 16 digits.');
			event.preventDefault();
		}

		if (isNaN(ccv) === false && ccv.length !== 3) {
			alert('CCV is invalid. Should be 3 digits.');
			event.preventDefault();
		}
	};

	const form = document.querySelector('form');
	form.addEventListener('submit', onFormSubmit);
}

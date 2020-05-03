window.addEventListener('load', function(event) {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		const table = document.querySelector('#table');
		table.innerHTML = this.response;
	};
	xhr.open('GET', './product_search.php', true);
	xhr.send();
});

// product search
const search = document.querySelector('#search');

search.addEventListener('keyup', function(event) {
	const table = document.querySelector('#table');
	table.innerHTML = '';
	const text = event.target.value;
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		table.innerHTML = this.response;
	};
	xhr.open('GET', './product_search.php?search=' + text, true);
	xhr.send();
});

/*
 From MDN Website on mutation observers
 https://developer.mozilla.org/en-US/docs/Web/API/MutationObserver
*/
// product table
const table = document.querySelector('#table');
// Options for the observer (which mutations to observe)
const config = { attributes: true, childList: true, subtree: true };

// Callback function to execute when mutations are observed
const callback = function(mutationsList, observer) {
	// Use traditional 'for loops' for IE 11
	for (let mutation of mutationsList) {
		if (mutation.type === 'childList') {
			console.log('A child node has been added or removed.');
			console.dir(mutation);

			const products = document.querySelectorAll('.product');
			const filters = Array.from(document.querySelectorAll('input[name="filter"]'));
			console.log(products);
			// image hover zoom
			products.forEach((product) => {
				product.addEventListener('mouseover', function(event) {
					product.classList.add('product-scale');
				});

				product.addEventListener('mouseleave', function(event) {
					product.classList.remove('product-scale');
				});

				product.addEventListener('click', function(event) {
					const pid = event.currentTarget.id;
					var xhr = new XMLHttpRequest();
					xhr.onreadystatechange = function() {
						if (xhr.readyState == 4 && xhr.status == 200) {
							location.href = './pages/product.php?pid=' + pid;
						}
					};
					xhr.open('GET', './pages/product.php?pid=' + pid, true);
					xhr.send();
				});
			});

			const categories = {
				floral             : 'floral',
				'crafts-hobbies'   : 'crafts-hobbies',
				'home-office'      : 'home-office',
				'knitting-crochet' : 'knitting-crochet'
			};

			let activeFilters = [];

			filters.forEach((filter, index) => {
				filter.addEventListener('click', () => {
					// if checked, add to active filters
					if (filter.checked) {
						activeFilters.push(filter);
					}
					else if (!filter.checked && activeFilters.includes(filter)) {
						activeFilters = activeFilters.filter((f) => f !== filter);
					}

					products.forEach((product) => {
						// flag = false
						// loop over activeFilters
						// if classList contains filter, flag = true, we remove hidden
						let flag = false;
						for (let i = 0; i < activeFilters.length; ++i) {
							if (product.classList.contains(activeFilters[i].id)) {
								flag = true;
								break;
							}
						}

						if (!flag) {
							// Product doesn't include any active filer
							product.classList.add('hidden'); // hide
						}
						else {
							product.classList.remove('hidden'); // show
						}
					});

					if (activeFilters.length === 0) {
						products.forEach((product) => {
							product.classList.remove('hidden');
						});
					}
				});
			});
		}
		else if (mutation.type === 'attributes') {
			console.log('The ' + mutation.attributeName + ' attribute was modified.');
		}
	}
};

// Create an observer instance linked to the callback function
const observer = new MutationObserver(callback);

// Start observing the target node for configured mutations
observer.observe(table, config);

// Later, you can stop observing
//observer.disconnect();

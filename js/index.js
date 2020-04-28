const products = document.querySelectorAll('.product');
const filters = Array.from(document.querySelectorAll('input[name="filter"]'));

// image hover zoom
products.forEach((product) => {
	product.addEventListener('mouseover', function(event) {
		product.classList.add('product-scale');
	});

	product.addEventListener('mouseleave', function(event) {
		product.classList.remove('product-scale');
	});
})


//const categories = [ 'floral', 'crafts-hobbies', 'home-office', 'knitting-crochet' ];
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

/*

if (filter.checked) {
			products.forEach((product) => {
				if (product.classList.contains(categories[index])) {
					product.classList.add('hidden');
				}
			});
		}
		else {
			products.forEach((product) => {
				if (product.classList.contains(categories[index])) {
					product.classList.remove('hidden');
				}
            });
            
            */

/*
filters[0].addEventListener('click', () => {
	if (filters[0].checked) {
		products.forEach((product) => {
			if (product.classList.contains('floral')) {
				product.classList.add('hidden');
			}
		});
	}
	else {
		products.forEach((product) => {
			if (product.classList.contains('floral')) {
				product.classList.remove('hidden');
			}
		});
	}
});
*/

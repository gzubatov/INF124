<?php
	$html = new DOMDocument(); 
	libxml_use_internal_errors(true);
	$html->loadHTMLFile('./index.html'); 
	$src = $html->getElementById('table');
	$fragment = $html->createDocumentFragment();	
	echo $html->saveHTML();
?>
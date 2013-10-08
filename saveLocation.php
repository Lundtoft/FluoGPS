<?php
	
	//Decode the json string from the Android app
	$timestamp = date('Y-m-d H:i:s', $_POST['timestamp']);
	$lon = $_POST['lon'];
	$lat = $_POST['lat'];
	$type = $_POST['type'];
	$title = date('Y-m-d H:i:s', $_POST['title']) . '-' . $type;
	
	$filename = $title.'.kml';
	
	//Check if the file exists, so the program knows if it has to create a new file, or extend an existing.
	if(file_exists($filename)){
		$kml = simplexml_load_file($filename);
		
		$kml->registerXPathNamespace('c', 'http://www.opengis.net/kml/2.2');
		
		$lineCoordinates = $kml->xpath('/c:kml/c:Document/c:Placemark/c:LineString/c:coordinates');
		$lineCoordinates[0][0] = $lineCoordinates[0][0] . ' ' . $lon . ',' . $lat;
			
		$document = $kml->Document;
		
	} else{
		//Create new xml object
		$kml = new SimpleXMLElement('<kml></kml>');
		
		//Create kml child, and add the namespace attribute
		$kml->addAttribute('xmlns', 'http://www.opengis.net/kml/2.2'); //Add the namespace
		
		$document = $kml->addChild('Document');
				
		//Add linesegment
		$placemark = $document->addChild('Placemark');
		$placemark->addChild('name', 'Path');
		$lineString = $placemark->addChild('LineString');
		$lineString->addChild('coordinates', $lon . ',' . $lat);
	}
	
	//Gets the number of points, and saves it as a fix number
	$count = count( $document->children() );
	
	//Add point
	$placemark = $document[0]->addChild('Placemark');
	$placemark->addChild('name', $timestamp);
	$placemark->addChild('description', 'Fix #'.$count);
	
	$point = $placemark->addChild('Point');
	$point->addChild('coordinates', $lon . ',' . $lat);
	
	//Save the file
	$kml->asXML($filename);
	
	echo('success');
	
?>

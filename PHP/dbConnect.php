<?php


	define('HOST','localhost');
	define('USER','root');
	define('PASS','');
	define('DB','application');
	
	$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	echo "successfully\n";
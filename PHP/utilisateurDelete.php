<?php
	$id =$_GET['id'];
	
	require_once('dbConnect.php');
	
	$sql = "DELETE FROM utilisateur WHERE id=$id;";
	
	if(mysqli_query($con,$sql)){
		echo'utilisateur Deleted Successfully';
	}else{
		echo 'Could Not Delete Utilisateur try Again';
	}

mysqli_close($con); 
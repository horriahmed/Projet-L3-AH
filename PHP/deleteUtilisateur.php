<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
	
	$id = $_POST['id'];
  
	

    require_once('dbConnect.php');

    $sql = "DELETE FROM `utilisateur` WHERE id='$id' ";

    $result = mysqli_query($con,$sql);
		if($result){

			// successfully inserted into database
			$response["success"] = "1";
			$response["message"] = "success";
			
			// echoing JSON response
			echo json_encode($response);
		}else{
			 // required field is missing
			$response["success"] = "0";
			$response["message"] = "Required field(s) is missing";
		 
			// echoing JSON response
			echo mysql_errno($response);
		}
		
		mysqli_close($con);
	}
		
		

?>
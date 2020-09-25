<?php

		
	    if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$id_utilisateur=$_POST['id_utilisateur'];
		$id_parcours=$_POST['id_parcours'];
		$latitude = $_POST['latitude'];
		$longitude = $_POST['longitude'];
		$position = $_POST['position'];
				
	
		
				
        require_once('dbConnect.php');
        $sql = "INSERT INTO `point`(`id_utilisateur`, `id_parcours`, `latitude`, `longitude`, `position`)
         		VALUES ('$id_utilisateur','$id_parcours','$latitude','$longitude','$position')";
		
		$result = mysqli_query($con,$sql);
		if($result){
			
			// successfully inserted into database
			$response["success"] = "1";
			$response["message"] = "Parcours successfully added.";
			
			// echoing JSON response
			echo json_encode($response);
		}else{
			 // required field is missing
			$response["success"] = "0";
			$response["message"] = "Required field(s) is missing";
		 
			// echoing JSON response
			echo json_encode($response);
		}
		
		mysqli_close($con);
	}
	
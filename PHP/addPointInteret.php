<?php

		
	    if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$id_utilisateur=$_POST['id_utilisateur'];
		$nom=$_POST['nom_point_interet'];
		$latitude = $_POST['latitude'];
		$longitude = $_POST['longitude'];
		$description = $_POST['description_point_interet'];
				
	
		
				
        require_once('dbConnect.php');
        $sql = "INSERT INTO `point_interet`(`id_utilisateur`, `nom`, `latitude`, `longitude`, `descreption`)
         		VALUES ('$id_utilisateur','$nom','$latitude','$longitude','$description')";
		
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
	
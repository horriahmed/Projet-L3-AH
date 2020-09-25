<?php

		
	    if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$id=$_POST['id_utilisateur'];
		$nom = $_POST['nom_parcours'];
		$dat = $_POST['date_parcours'];
		$description = $_POST['description_parcours'];
				
	
		
				
        require_once('dbConnect.php');
        $sql = "INSERT INTO `parcours`(`id_utilisateur`, `nom`, `dat`, `descreption`)
		        VALUES ('$id','$nom','$dat','$description')";
		       
		
		$result = mysqli_query($con,$sql);
		if($result){
			
			$sql ="SELECT MAX(id) as 'max' FROM `parcours` WHERE `id_utilisateur`=$id";
			$result = mysqli_query($con,$sql);
		    if($result){
				
				$max = mysqli_fetch_array($result);
								
				// successfully inserted into database
				
				$response["success"] = "1";
				$response["max"] = $max['max'];
				$response["message"] = "Parcours successfully added.";
				
				// echoing JSON response
			    echo json_encode($response);
			}
			
			else{
				 // required field is missing
				$response["success"] = "0";
				$response["message"] = "Required field(s) is missing";
			 
				// echoing JSON response
				echo json_encode($response);
		}}
		else{
				 // required field is missing
				$response["success"] = "0";
				$response["message"] = "Required field(s) is missing";
			 
				// echoing JSON response
				echo json_encode($response);
		}
		
		mysqli_close($con);
	}
	
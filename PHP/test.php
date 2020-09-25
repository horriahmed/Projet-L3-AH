<?php

		
	    if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$id=1;
		$nom = "kkk";
		$dat = '1996-04-24';
		$description = "lkjfdglkdglkjfdlj";
				
	
		
				
        require_once('dbConnect.php');
        $sql = "INSERT INTO `parcours`(`id_utilisateur`, `nom`, `dat`, `descreption`)
		        VALUES ('$id','$nom','$dat','$description')";
		
		
		$result = mysqli_query($con,$sql);
		if($result){
						
			$sql ="SELECT MAX(id) as 'max' FROM `parcours` WHERE `id_utilisateur`=$id";
			$result = mysqli_query($con,$sql);
		    if($result){
				
				$row = mysqli_fetch_array($result);
				
				echo $row['max'];
				
				// successfully inserted into database
				$response["success"] = "1";
				$response["message"] = "Parcours successfully added.";
				
				// echoing JSON response
			    echo json_encode($response);
			}
			
		}else{
			 // required field is missing
			$response["success"] = "0";
			$response["message"] = "Required field(s) is missing";
		 
			// echoing JSON response
			echo json_encode($response);
		}
		
		mysqli_close($con);
	}

?>
	
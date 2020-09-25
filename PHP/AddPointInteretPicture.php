<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
	
	
	$id_utilisateur = $_POST['id_utilisateur'];
    $nom = $_POST['nom_point_interet'];
    $latitude = $_POST['latitude'];
    $longitude = $_POST['longitude'];
    $photo = $_POST['src_image'];
	

    require_once('dbConnect.php');
	
	
    $sql = "INSERT INTO `image`(`id_utilisateur`,`nom`, `latitude`, `longitude`) 
	        VALUES ($id_utilisateur,$nom,$latitude,$longitude)";

    $result = mysqli_query($con,$sql);
	if($result){
		
		$sql_max="SELECT MAX(id) as 'max' FROM image WHERE latitude=$latitude AND longitude=$longitude";
	    $result = mysqli_query($con,$sql);
		
		if($result){
			
			$row=mysqli_fetch_array($result);
			$max=$row['max'];
			$path = "PointsInteretsImages/$max.jpeg";
	        $finalpath = "http://10.30.10.164/randonner/".$path;
			$sql = "UPDATE `image` SET `src`='$finalpath' WHERE id='$max' ";
            $result = mysqli_query($con,$sql); 
			
			if($result){
			
				if(file_put_contents($path,base64_decode($photo))){
					
					// successfully inserted into database
					$response["success"] = "1";
					$response["message"] = "success";
					// echoing JSON response
					echo json_encode($response);
					
				}else{
					
					 // required field is missing
					$response["success"] = "0";
					$response["message"] = "PICTURE INSERTION PROBLEM";
					// echoing JSON response
					echo json_encode($response);
					
				}
			}else{
			
			 // required field is missing
			$response["success"] = "0";
			$response["message"] = "UPDATE PROBLEM";
			// echoing JSON response
			echo json_encode($response);
			
	        }
		}else{
		
			 // required field is missing
			$response["success"] = "0";
			$response["message"] = "MAX PROBLEM"; 
			// echoing JSON response
			echo json_encode($response);
			
        }}else{
			
			// required field is missing
			$response["success"] = "0";
			$response["message"] = "Required field(s) is missing";		 
			// echoing JSON response
			echo json_encode($response);
			
		}
}

?>
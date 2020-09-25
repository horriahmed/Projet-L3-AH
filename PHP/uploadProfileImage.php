<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
    $photo = $_POST['src_image'];
    
	$path = "profile_image/$id.jpeg";
	$finalpath = "http://10.30.10.164/randonner/".$path;

    require_once('dbConnect.php');

    $sql = "UPDATE `utilisateur` SET `src_image`='$finalpath' WHERE id='$id' ";

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
			$response["message"] = "Required field(s) is missing";
		 
			// echoing JSON response
			echo json_encode($response);
		}}
		
		mysqli_close($con);
	}
		
		

?>
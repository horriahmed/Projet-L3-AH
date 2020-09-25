<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
    $nom = $_POST['nom'];
    $prenom = $_POST['prenom'];
    $mail = $_POST['mail'];
    $tel = $_POST['tel'];
    $dateDeNaissance = $_POST['dateDeNaissance'];
    $passWord = $_POST['motDePasse'];
	
	

    require_once('dbConnect.php');

    $sql = "UPDATE `utilisateur` SET `nom`='$nom',`prenom`='$prenom',`dateDeNaissance`='$dateDeNaissance',
	       `mail`='$mail',`tel`='$tel',`motDePasse`='$passWord' WHERE id='$id' ";

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
			echo json_encode($response);
		}
		
		mysqli_close($con);
	}
		
		

?>
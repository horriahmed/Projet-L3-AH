<?php

		
	    if($_SERVER['REQUEST_METHOD']=='POST'){
		
		
		$nom = $_POST['nom'];
		$prenom = $_POST['prenom'];
		$dateDeNaissance = $_POST['dateDeNaissance'];
		$mail = $_POST['mail'];
		$tel = $_POST['tel'];
		$motDePasse = $_POST['motDePasse'];
		
	
		
				
        require_once('dbConnect.php');
        $sql = "INSERT INTO `utilisateur`(`nom`, `prenom`, `dateDeNaissance`, `mail`, `tel`, `motDePasse`) 
		VALUES ('$nom','$prenom','$dateDeNaissance','$mail','$tel','$motDePasse')";
		$result = mysqli_query($con,$sql);
		if($result){
			
			$sql = "SELECT id FROM utilisateur WHERE mail='$mail' ";    
			    
			$reponse = mysqli_query($con, $sql);

			
			
			if ( mysqli_num_rows($reponse) === 1 ) {
				
				$row = mysqli_fetch_assoc($reponse);
			$response["id"] = $row['id'];}
			
			// successfully inserted into database
			$response["success"] = "1";
			$response["message"] = "Product successfully created.";
			
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
		
		
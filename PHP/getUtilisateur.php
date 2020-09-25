<?php
	 $id = $_GET['id'];
	 
	 require_once('dbConnect.php');
	 $sql = "SELECT * FROM utilisateur WHERE id=$id";
	 $r = mysqli_query($con,$sql);
	 
	 $result = array();
	 
	 $row = mysqli_fetch_array($r);
	 array_push($result,array(
			"id"=>$row['id'],
			"nom"=>$row['nom'],
			"prenom"=>$row['prenom'],
			"dateDeNaissance"=>$row['dateDeNaissance'],
			"mail"=>$row['mail'],
			"tel"=>$row['tel'],
			"motDePasse"=>$row['motDePasse'],
			));
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
			
			
			
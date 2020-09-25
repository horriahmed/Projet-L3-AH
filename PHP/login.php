<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {

    $mail = $_POST['mail'];
    $motDePasse = $_POST['motDePasse'];

    require_once('dbConnect.php');

    $sql = "SELECT * FROM utilisateur WHERE mail='$mail' ";

    $response = mysqli_query($con, $sql);

    
    if ( mysqli_num_rows($response) === 1 ) {
		$result = array();
	        
        $row = mysqli_fetch_assoc($response);

        if ( $motDePasse===$row['motDePasse'] ) {
            $index['id'] = $row['id'];
            $index['nom'] = $row['nom'];
            $index['prenom'] = $row['prenom'];
			$index['mail'] = $row['mail'];
            $index['tel'] = $row['tel'];
			$index['dateDeNaissance'] = $row['dateDeNaissance'];
			$result['login'] = array();

            array_push($result['login'], $index);

            $result['success'] = "1";
            $result['message'] = "login success";
            echo json_encode($result);

            mysqli_close($con);

        } else {

            $result['success'] = "0";
            $result['message'] = "PASSWORD INCORRECT";
            echo json_encode($result);

            mysqli_close($con);

        }

    }
	else{
		    $result['success'] = "0";
            $result['message'] = "USER NOT FOUND";
            echo json_encode($result);

            mysqli_close($con);
	}
	

}

?>
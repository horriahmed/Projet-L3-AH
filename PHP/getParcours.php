<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {


    $id = $_POST['id'];


    require_once('dbConnect.php');

    $sql = "SELECT `id`, `nom`, `dat`, `descreption` FROM parcours WHERE id_utilisateur='$id' ";

    $response = mysqli_query($con, $sql);

    
    if ( mysqli_num_rows($response) >= 1 ) {
		$result = array();
		$result['num_rows'] = mysqli_num_rows($response);
	        
        
		$i=0;
        while($row = mysqli_fetch_assoc($response)){ 
            $index['id'] = $row['id'];
            $index['nom'] = $row['nom'];
			$index['date'] = $row['dat'];
			$index['descreption'] = $row['descreption'];
			$result[$i] = array();
		    array_push($result[$i], $index);
			$i++;
		}

            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);

            mysqli_close($con);


    }
	else{
		    $result['success'] = "0";
            $result['message'] = "PARCOURS NOT FOUND";
            echo json_encode($result);

            mysqli_close($con);
	}
	

}

?>
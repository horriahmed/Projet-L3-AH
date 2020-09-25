<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {


    $id_parcours = $_POST['id_parcours'];

    require_once('dbConnect.php');

    $sql = "SELECT `latitude`, `longitude`, `position` FROM point WHERE `id_parcours`='$id_parcours' ";
	
    $response = mysqli_query($con, $sql);

    
    if ( mysqli_num_rows($response) >= 1 ) {
		$result = array();
		$result['num_rows'] = mysqli_num_rows($response);
	        
        
		$i=0;
        while($row = mysqli_fetch_assoc($response)){ 
            $index['latitude'] = $row['latitude'];
            $index['longitude'] = $row['longitude'];
			$index['position'] = $row['position'];
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
            $result['message'] = "PARCOURS POINTS NOT FOUND";
            echo json_encode($result);

            mysqli_close($con);
	}
	

}

?>
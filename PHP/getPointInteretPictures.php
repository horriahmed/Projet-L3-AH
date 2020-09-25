<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {


    $id = $_POST['id'];


    require_once('dbConnect.php');

    $sql = "SELECT `id`, `nom`,  FROM `image` WHERE latitude=$latitude AND longitude=$longitude ";

    $response = mysqli_query($con, $sql);

    
    if ( mysqli_num_rows($response) >= 1 ) {
		$result = array();
		$result['num_rows'] = mysqli_num_rows($response);
	        
        
		$i=0;
        while($row = mysqli_fetch_assoc($response)){ 
            $index['id'] = $row['id'];
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
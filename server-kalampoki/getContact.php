<?php 
	include "connection.php";
	$mysqli = new mysqli($db_host, $db_user, $db_pass, $db_name);
	if ($mysqli->connect_errno) {
	    printf("Connect failed: %s\n", $mysqli->connect_error);
	    exit();
	}
	if ($result = $mysqli->query("SELECT * FROM value WHERE name IN ('contact', 'location')")) {
		if($result->num_rows > 0){
			$return = array();
			$return['status'] = 1;
			$data = array();
			while ($row = mysqli_fetch_array($result)) {
				$data[] = array(
						'id'				=>	$row['kd_value'],
						'name'				=>	$row['name'],
						'value'				=>	$row['value']
					);
			};
			$return['data'] = $data;
		    $result->close();
		    echo json_encode($return);
		}else{
			$result->close();
			echo json_encode(array("status"=>0));
		}
	}else{
		$result->close();
		echo json_encode(array("status"=>0));
	}
	$mysqli->close();
?>
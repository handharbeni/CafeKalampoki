<?php 
	include "connection.php";
	$mysqli = new mysqli($db_host, $db_user, $db_pass, $db_name);
	$mysqli2 = new mysqli($db_host, $db_user, $db_pass, $db_name);
	if ($mysqli->connect_errno) {
	    printf("Connect failed: %s\n", $mysqli->connect_error);
	    exit();
	}
	$params = $_POST;
	if(isset($params)){
		$meja = $params['meja'];
		$tanggal = date('Y-m-d');
		$atasnama = $params['atasnama'];
		$queryInsert = "INSERT INTO m_order (meja, tanggal, atas_nama) VALUES ('$meja', '$tanggal', '$atasnama')";
		$insert = $mysqli->query($queryInsert);
		if ($insert) {
			$querySelect = $mysqli2->query("SELECT * FROM m_order WHERE meja='$meja' AND tanggal='$tanggal' AND atas_nama='$atasnama'");
			if ($querySelect->num_rows > 0) {
				$return = array();
				$return['status'] = 1;
				$data = array();
				while ($row = mysqli_fetch_array($querySelect)) {
					$data[] = array(
							'id' =>	$row['id']
						);
				};
				$return['data'] = $data;
			    $querySelect->close();
			    echo json_encode($return);
			}else{
				echo json_encode(array("status"=>0));
			}
		}else{
			echo json_encode(array("status"=>0));
		}
	}else{
		echo json_encode(array("status"=>0));
	}
?>
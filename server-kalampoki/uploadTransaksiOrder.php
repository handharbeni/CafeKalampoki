<?php 
	include "connection.php";
	$mysqli = new mysqli($db_host, $db_user, $db_pass, $db_name);
	if ($mysqli->connect_errno) {
	    printf("Connect failed: %s\n", $mysqli->connect_error);
	    exit();
	}
	$params = $_POST;
	if(isset($params)){
		/*kd_order*/
		/*kd_menu*/
		/*qty*/
		/*total_harga*/
		$kd_order = $params['kd_order'];
		$kd_menu = $params['kd_menu'];
		$qty = $params['qty'];
		$total_harga = $params['total_harga'];
		$queryInsert = "INSERT INTO t_order (kd_order, kd_menu, qty, total_harga) VALUES ($kd_order, $kd_menu, $qty, $total_harga)";
		$insert = $mysqli->query($queryInsert);
		if($insert){
			echo json_encode(array("status"=>1));
		}else{
			echo json_encode(array("status"=>0));
		}
	}else{
		echo json_encode(array("status"=>0));
	}
?>
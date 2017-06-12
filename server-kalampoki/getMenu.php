<?php 
	include "connection.php";
	$mysqli = new mysqli($db_host, $db_user, $db_pass, $db_name);
	if ($mysqli->connect_errno) {
	    printf("Connect failed: %s\n", $mysqli->connect_error);
	    exit();
	}
	$params = $_GET;
	if(!empty($params['kategori'])){
		$ktgr = $params['kategori']==1?"food":"baverage";
		if ($result = $mysqli->query("SELECT * FROM menu WHERE kategori='".$ktgr."'")) {
			if($result->num_rows > 0){
				$return = array();
				$return['status'] = 1;
				$data = array();
				while ($row = mysqli_fetch_array($result)) {
					$data[] = array(
							'id'				=>	$row['kd_menu'],
							'nama'				=>	$row['nama_menu'],
							'kategori'			=>	$row['kategori'],
							'deskripsi'			=>	$row['deskripsi'],
							'foto'				=>	$row['foto'],
							'harga'				=>	$row['harga']
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
	}else{
		if ($result = $mysqli->query("SELECT * FROM menu")) {
			if($result->num_rows > 0){
				$return = array();
				$return['status'] = 1;
				$data = array();
				while ($row = mysqli_fetch_array($result)) {
					$data[] = array(
							'id'				=>	$row['kd_menu'],
							'nama'				=>	$row['nama_menu'],
							'kategori'			=>	$row['kategori'],
							'deskripsi'			=>	$row['deskripsi'],
							'foto'				=>	$row['foto'],
							'harga'				=>	$row['harga']
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
	}
	$mysqli->close();
?>
<?php
	include "db.inc.php";
	$sql="SELECT * FROM `articles` ORDER BY `date` DESC";
	$result=mysqli_query($connection,$sql);
	$response= array();
	while($array=mysqli_fetch_array($result)){
		$authorID=$array['authorID'];
		$sql="SELECT `name` FROM `accounts` WHERE `id` LIKE '$authorID'";
		$res=mysqli_query($connection,$sql);
		$stuff=mysqli_fetch_array($res);
		array_push($response,
				array('articleID'=>$array['articleID'],
					'authorName'=>$stuff['name'],
				'articleHeader'=>$array['introText'],
				'date'=>$array['date']
				));

		}
	echo json_encode(array("data"=>$response));
					
?>
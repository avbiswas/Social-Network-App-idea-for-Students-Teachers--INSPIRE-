<?php
	include "db.inc.php";
	$articleID=urldecode($_POST['id']);
	$sql="SELECT * FROM `comments` WHERE `articleID` LIKE '$articleID' ORDER BY `date` DESC";
	$query=mysqli_query($connection,$sql);
	$comments=array();
	while ($result=mysqli_fetch_array($query)){
		$userID=$result['userID'];
		$authorNameSQL="SELECT `name` FROM `accounts` WHERE `id` LIKE '$userID'";
		$resultName=mysqli_query($connection,$authorNameSQL);
		$nameArray=mysqli_fetch_array($resultName);
		$name=$nameArray['name'];
		array_push($comments,
				array('commentID'=>$result['commentID'],
					'commentText'=>$result['commentText'],
					'name'=>$name,	
				'date'=>$result['date'],
				
				));
		}
	if (mysqli_num_rows($query)==0){
		echo "No Comments Yet";
	}
	else{
	echo json_encode(array("data"=>$comments));
	}		


?>
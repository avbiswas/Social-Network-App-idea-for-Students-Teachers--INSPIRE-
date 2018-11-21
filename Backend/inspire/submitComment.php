<?php
	include "db.inc.php";
	$articleID=urldecode($_POST['articleID']);
	$userID=urldecode($_POST['userID']);
	$comment=urldecode($_POST['comment']);
	//$articleID="17";
	//$userID="1";
	//$comment=" fa";
	date_default_timezone_set("Asia/Kolkata");
	
	$date=date("y-m-d h:i:sa");
	
	$sql="INSERT INTO `comments` (`userID` ,`articleID` ,`commentText` ,`date`)VALUES ('$userID',  '$articleID',  '$comment',  '$date')";
	mysqli_query($connection,$sql);
	echo "Submitted.. Refresh to view updates..";
?>
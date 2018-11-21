<?php
	include "db.inc.php";
	define('UPLOAD_DIR', 'images/');
	$image = $_POST['image'];
	$id= $_POST['id'];
	$heading=$_POST['articleHeading'];
	$image = str_replace('data:image/png;base64,', '', $image);
	$image = str_replace(' ', '+', $image);
	$data = base64_decode($image);
	$filename=uniqid();
	$file = UPLOAD_DIR . $filename . '.png';
	$success = file_put_contents($file, $data);
	date_default_timezone_set("Asia/Kolkata");
	$date=date("y-m-d h:i:sa");
	$sql="INSERT INTO  `inspire`.`articles` (
	`articleID` ,
	`authorID` ,
	`introText` ,
	`fileName` ,
	`date`)
	VALUES (
	NULL ,  '$id',  '$heading',  '$filename',  '$date')";
	mysqli_query($connection,$sql);
	print $success ? $file : 'Unable to save the file.';
?>
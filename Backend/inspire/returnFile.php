<?php
	include "db.inc.php";
	$articleID=urldecode($_POST['articleID']);
	//$articleID=13;
	$sql="SELECT `fileName` FROM `articles` WHERE `articleID` LIKE '$articleID'";
	$result=mysqli_query($connection,$sql);
	$file=mysqli_fetch_array($result);
	$dir="images/";
	opendir($dir);
	$filename= "images/".$file['fileName'].".png";
	$image=file_get_contents($filename);
	echo $image;
?>
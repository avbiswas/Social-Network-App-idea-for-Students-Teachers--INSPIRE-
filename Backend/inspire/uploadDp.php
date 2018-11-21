<?php	
	include "db.inc.php";
	define('UPLOAD_DIR', 'dp/');
	$image = urldecode($_GET['image']);
	$id= urldecode($_GET['id']);
	$image = str_replace('data:image/png;base64,', '', $image);
	$image = str_replace(' ', '+', $image);
	$data = base64_decode($image);
	$file = UPLOAD_DIR . $id . '.jpg';
	$success = file_put_contents($file, $data);

?>
<?php
include "db.inc.php";
$user=$_GET['id'];
$password=$_GET['password'];
$query="SELECT * FROM  `accounts` WHERE  `id` LIKE  '$user' AND  `password` LIKE  '$password'";
			$result=mysqli_query($connection,$query);
			$row=mysqli_num_rows($result);
			$response= array();
			if($row==1)
			{
				while($row = mysqli_fetch_array($result)){
				array_push($response,
				array('id'=>$row[0],
					'name'=>$row[1],
				'password'=>$row[2],
				'email'=>$row[3]
				));
			}
 
			echo json_encode(array("data"=>$response));
			}
			else{
				echo "Error";
			}
?>


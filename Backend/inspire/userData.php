<?php
include "db.inc.php";
$user=$_GET['id'];
$password=$_GET['password'];
$query="SELECT * FROM  `accounts` WHERE  `id` LIKE  '$user' AND  `password` LIKE  '$password'";
			$result=mysqli_query($connection,$query);
			$row=mysqli_num_rows($result);
			$response= "";
			if($row==1)
			{
				while($row = mysqli_fetch_array($result)){
				array_push($response,
				array('name'=>$row[0],
				'password'=>$row[1],
				'email'=>$row[2]
				));
			}
 
			echo json_encode($response);
			}
			else{
				echo "Error";
			}
?>


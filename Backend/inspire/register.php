<?php
include "db.inc.php";
if($connection)		//if database gets connected right
	{
			$name=$_GET['name'];
			$email=$_GET['email'];
			$batch=$_GET['batch'];
			$password=$_GET['password'];
			$query="SELECT * FROM  `accounts` WHERE  `email` LIKE  '$email' LIMIT 0 , 30";	
			$result=mysqli_query($connection,$query);	
			$row=mysqli_num_rows($result);	
			if ($row==1){
				echo "Already registered";
			}
			else
			{
				$query1 ="INSERT INTO `accounts` (`name`, `email`, `batch`, `password`) VALUES ('$name', '$email', '$batch', '$password')";		

				if(mysqli_query($connection,$query1))	
				{
					echo "Done";
				}
				else
				{
					echo "Bad Luck";
				}
			}	

		}
		else
		{
			echo "Fill all the required fields";
		}
	

?>
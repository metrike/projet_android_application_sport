<?php
if(isset($_POST['id']) && isset($_POST['longitude']) && isset($_POST['latitude'])&& isset($_POST['ordre'])){   // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $longitude = validate($_POST['longitude']);
    $latitude = validate($_POST['latitude']);
    $ordre = intval($_POST['ordre']);

    $id = validate($_POST['id']);
        // Create the SQL query string. We'll use md5() function for data security. It calculates and returns the MD5 hash of a string
        $sql = "INSERT INTO parcours values('$id','$longitude','$latitude','$ordre')";
        // Execute the query. Print "success" on a successful execution, otherwise "failure".
        if(!$conn->query($sql)){
            echo "failure";
        }else{
            echo "success"; 
        }
    }
	

?>

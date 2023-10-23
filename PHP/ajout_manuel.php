<?php
if(isset($_POST['login']) && isset($_POST['distance']) && isset($_POST['id']) && isset($_POST['vitesse']) && isset($_POST['date'])&& isset($_POST['temps'])&& isset($_POST['note'])&& isset($_POST['sport'])){
    // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $login = validate($_POST['login']);
    $distance = validate($_POST['distance']);
    $vitesse = validate($_POST['vitesse']);
    $date = validate($_POST['date']);
    $temps = validate($_POST['temps']);
    $sport = validate($_POST['sport']);
    $note = intval($_POST['note']);
    $id = intval($_POST['id']);

    
        // Create the SQL query string. We'll use md5() function for data security. It calculates and returns the MD5 hash of a string
        $sql = "INSERT into activity values('$id','$login','$distance', '$vitesse','$date','$temps','$sport','$note','NULL','NULL','NULL')";
        // Execute the query. Print "success" on a successful execution, otherwise "failure".
        if(!$conn->query($sql)){
            echo "failure".$conn->error;
        }else{
            echo "success";   
        }
    }
?>
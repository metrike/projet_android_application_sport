<?php
if(isset($_POST['id']) && isset($_POST['login']) && isset($_POST['rounds']) && isset($_POST['temps_on']) && isset($_POST['temps_off']) && isset($_POST['date'])&& isset($_POST['temps'])&& isset($_POST['note'])&& isset($_POST['sport'])){   // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $login = validate($_POST['login']);
    $temps_on = validate($_POST['temps_on']);
    $temps_off = validate($_POST['temps_off']);
    $rounds = validate($_POST['rounds']);
    $date = validate($_POST['date']);
    $temps = validate($_POST['temps']);
    $sport = validate($_POST['sport']);
    $note = intval($_POST['note']);
    $id = validate($_POST['id']);


        // Create the SQL query string. We'll use md5() function for data security. It calculates and returns the MD5 hash of a string
        $sql = "INSERT into activity values('$id','$login','NULL', 'NULL','$date','$temps','$sport','$note','$rounds','$temps_on','$temps_off')";
        // Execute the query. Print "success" on a successful execution, otherwise "failure".
        if(!$conn->query($sql)){
            echo "failure";
        }else{
            echo "success";
        }
    }

?>

<?php
// Check if email and password are set
if(isset($_POST['login'])){
    // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $login = validate($_POST['login']);
    
    // Create the SQL query string
    $sql = "SELECT * FROM personne_connecter WHERE login='$login'";
    // Execute the query
    $result = $conn->query($sql);

    $sqldelete = "delete from personne_connecter where login= '$login'";

    //$resultadd = $conn->query($sqladd);
    // If number of rows returned is greater than 0 (that is, if the record is found), we'll print "success", otherwise "failure"

        if ($result->num_rows > 0) {
            echo "test";
            if($conn->query($sqldelete)){
                echo "success";
            }else{
                echo "connect";
            }

        } else{
            // If no record is found, print "failure"
            echo "failure";
        }
}
?>
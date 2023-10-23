<?php
// Check if email and password are set
if(isset($_POST['login']) && isset($_POST['password'])){
    // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $login = validate($_POST['login']);
    $password = validate($_POST['password']);
    // Create the SQL query string
    $sql = "select * from utilisateur where id='$login' and mdp='" . md5($password) . "'";
    // Execute the query
    $result = $conn->query($sql);

    $sqladd = "insert into personne_connecter values('$login')";

    //$resultadd = $conn->query($sqladd);
    // If number of rows returned is greater than 0 (that is, if the record is found), we'll print "success", otherwise "failure"

        if ($result->num_rows > 0) {
            if($conn->query($sqladd)){
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
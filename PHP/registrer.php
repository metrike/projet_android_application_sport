<?php
if(isset($_POST['login']) && isset($_POST['noms']) && isset($_POST['prenoms']) && isset($_POST['password']) && isset($_POST['email'])){
    // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $login = validate($_POST['login']);

    $recupLogin = "select id from utilisateur where id = '$login'";  // nom de table changer -- users

    $test = $conn->query($recupLogin);

    $count = 0;
    foreach($test as $a){
        $count++;
    }
    
    if($count==0){
        $email = validate($_POST['email']);
        $password = validate($_POST['password']);
        $nom = validate($_POST['noms']);
        $prenom = validate($_POST['prenoms']);
        
        // Create the SQL query string. We'll use md5() function for data security. It calculates and returns the MD5 hash of a string

        $sql = "insert into utilisateur values('$login','$nom','$prenom','$email', '" . md5($password) . "',null,null,null,null)"; 
        // Execute the query. Print "success" on a successful execution, otherwise "failure".
        
        if($conn->query($sql)){
            echo "marche";
        }else{
            echo "failure";   
        }
    }
    else{
        echo "echec";
    }
    
}
?>

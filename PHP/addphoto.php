<?php
if(isset($_POST['login']) && isset($_POST['image'])){
    // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $login = validate($_POST['login']);
    $image = validate($_POST['image']);

    $sqlverif = "SELECT * FROM photo_de_profil WHERE login = '$login'";

    $result = $conn->query($sqlverif);

    if ($result->num_rows > 0) {
        $sqlupdate = "UPDATE `photo_de_profil` SET login='$login', photo='$image' WHERE login='$login'";

        if($conn->query($sqlupdate)){
            echo "marche";
        }else{
            echo "failure_update";   
        }
    }
    else{
        $sql = "INSERT INTO photo_de_profil VALUES ('$login','$image')";  
        // Execute the query. Print "success" on a successful execution, otherwise "failure".
            
        if($conn->query($sql)){
            echo "marche";
        }else{
            echo "failure";   
        }
    }
}
else {
    echo "echec";
}

//VERIF QUE LA PERSONNE EST CO!!!!!!!!!!
?>

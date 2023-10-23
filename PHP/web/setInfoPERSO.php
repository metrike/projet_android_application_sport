<?php

if(isset($_GET['login']) && isset($_GET['age']) && isset($_GET['poids']) && isset($_GET['taille']) && isset($_GET['sexe'])){
    
    require_once "conn.php";
    require_once "validate.php";
    
    $login = validate($_GET['login']);
    $age = intval($_GET['age']);
    $poids = intval($_GET['poids']);
    $taille = intval($_GET['taille']);
    $sexe = validate($_GET['sexe']);

    if($age == 0){
        $age = -1;
    }
    if($poids == 0){
        $poids = -1;
    }
    if($taille == 0){
        $taille = -1;
    }

    $sql = "UPDATE `utilisateur` SET `age`='$age',`poids`='$poids',`taille`='$taille',`sexe`='$sexe' WHERE id = '$login'";
    
    if($conn->query($sql)){
        echo "marche";
    }else{
        echo "failure ". $conn->error;   
    }
}

?>
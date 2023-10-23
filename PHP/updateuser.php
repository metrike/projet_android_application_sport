<?php
if(isset($_POST['login']) && isset($_POST['age']) && isset($_POST['poids']) && isset($_POST['taille']) && isset($_POST['sexe'])){
    
    require_once "conn.php";
    require_once "validate.php";
    
    $login = validate($_POST['login']);
    $age = intval($_POST['age']);
    $poids = intval($_POST['poids']);
    $taille = intval($_POST['taille']);
    $sexe = validate($_POST['sexe']);

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


//VERIF QUE LA PERSONNE EST CO!!!!!!!!!!
?>
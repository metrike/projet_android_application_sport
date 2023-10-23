<?php
if(isset($_POST['login'])){
    require_once "conn.php";
    require_once "validate.php";

    $login = validate($_POST['login']);

    $sql = "SELECT * FROM `utilisateur` WHERE id='$login'";

    $result = $conn->query($sql);

    if($result->num_rows > 0){
        foreach($result as $row){
            $arr = array(
                "contenue" => array(
                'age' => $row["age"],
                'poids' => $row["poids"],
                'taille' => $row["taille"],
                'sexe' => $row["sexe"]
                )
            );
        }
        echo  json_encode($arr);
        
    }
    else{
        echo "failure";
    }
}
else {
    echo "echec";
}

?>
<?php
if(isset($_GET["login"])){

    require_once "conn.php";
    require_once "validate.php";

    $login  = validate($_GET["login"]);

    $sql = "SELECT * FROM `utilisateur` WHERE id='$login'";

    $result = $conn->query($sql);

    if($result->num_rows > 0){
        foreach($result as $row){
            $arr = array(
                $row["age"],
                $row["poids"],
                $row["taille"],
                $row["sexe"]
            );
        }
        echo  json_encode($arr);
        
    }
}

?>
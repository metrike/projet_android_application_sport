<?php
if(isset($_POST['login'])){
    require_once "conn.php";
    require_once "validate.php";

    $login = validate($_POST['login']);

    $sql = "SELECT prenom FROM `utilisateur` WHERE id='$login'";

    $result = $conn->query($sql);

    if($result->num_rows > 0){
        foreach($result as $row){
            echo $row["prenom"];
        }
    }
    else{
        echo "failure";
    }
}
else {
    echo "echec";
}

?>
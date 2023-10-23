<?php
if(isset($_POST['login'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $login = validate($_POST['login']);

    $sql = "SELECT * FROM photo_de_profil WHERE login = '$login'";

    $result = $conn->query($sql);

    if($result->num_rows > 0){
        foreach($result as $row){
            echo $row["photo"];
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
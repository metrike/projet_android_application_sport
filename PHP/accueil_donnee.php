<?php 

if(isset($_POST['login'])){

require_once "conn.php";
require_once "validate.php";

    $login = validate($_POST['login']);
    

    $sql = "SELECT * FROM activity WHERE login='$login' ORDER BY `activity`.`date` DESC LIMIT 5";


$r = mysqli_query($conn,$sql);


 $result = array();
 $resultfin = array();
 while($row = mysqli_fetch_array($r)){
     array_push($result,array(
        $row['date'],
        $row['distance'],
        $row['vitesse'],
        $row['temps'],
        $row['sport']
 
     ));
     
 }

 echo json_encode($result);

}

?>
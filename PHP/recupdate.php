<?php 

if(isset($_POST['login']) && isset($_POST['ordre'])){

require_once "conn.php";
require_once "validate.php";

    $login = $_POST["login"];
    $ordre = $_POST["ordre"]
    

    $sql = "SELECT * FROM activity WHERE login='$login' ORDER BY `activity`.`date` $ordre";


$r = mysqli_query($conn,$sql);


 $result = array();
 $resultfin = array();
 while($row = mysqli_fetch_array($r)){
     array_push($result,array(
        $row['date']
 
     ));
     
 }

 echo json_encode($result);
 //echo "\n";

}

?>
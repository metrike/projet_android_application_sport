<?php 

//if(isset($_POST['login']) && isset($_POST['donne']) && isset($_POST['date'])){

require_once "conn.php";
require_once "validate.php";

    // $login = validate($_POST['login']);
    // $donne = validate($_POST['donne']);
    // $date = validate($_POST['date']);

    $login ="a" ;
    $donne ="vitesse" ;
    $date = "2022-03-16";


    

    $sql = "SELECT * FROM activity WHERE login='$login' AND date='$date'";


$r = mysqli_query($conn,$sql);


 $result = array();
 while($row = mysqli_fetch_array($r)){
     array_push($result,array(
        $row[$donne]
     ));
     
 }

 echo json_encode($result);

//}

?>
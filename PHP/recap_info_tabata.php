<?php

if(isset($_POST['login']) && isset($_POST['date']) && isset($_POST['sport']) && isset($_POST['temps'])){

require_once "conn.php";
require_once "validate.php";

    $login = $_POST["login"];
    $date = $_POST["date"];
    $sport = $_POST["sport"];
    $temps = $_POST["temps"];



    $sql = "SELECT * FROM activity WHERE login='$login' and date = '$date' and sport = '$sport' and temps = '$temps' and id!='' " ;


$r = mysqli_query($conn,$sql);


$result = array();
 while($row = mysqli_fetch_array($r)){
     array_push($result,array(
        $row['rounds'],
        $row['temps'],
        $row['temps_on'],
        $row['temps_off'],
        $row['date'],
        $row['note']

     ));

 }

 echo json_encode($result);


}

?>
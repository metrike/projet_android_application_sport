<?php 

if(isset($_POST['id'])){

require_once "conn.php";
require_once "validate.php";

    $id = validate($_POST['id']);

    $sql = "SELECT * FROM parcours WHERE id='$id' ORDER BY ordre";
    $countsql = "SELECT count(id) FROM `parcours` WHERE id = '$id'";


    $r = mysqli_query($conn,$sql);
    $rcount = mysqli_query($conn,$countsql);

    $result = array();

    while($ligne = mysqli_fetch_array($rcount)){
        array_push($result,array(
            $ligne['count(id)']
    
        ));
    }

    while($row = mysqli_fetch_array($r)){
            array_push($result,array(
                $row['longitude'],
                $row['latitude']
        
            ));
    }

    
    
    echo json_encode($result);
}

?>
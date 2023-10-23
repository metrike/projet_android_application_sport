<?php 
if(isset($_GET['login'])){  
         
    require_once "conn.php";
    require_once "validate.php";

    $login = validate($_GET['login']); 

    $dates = "SELECT date FROM activity where login = '$login' ORDER BY date ASC ";

    $r = $conn->query($dates);

    $result = array(); 

    while($row = mysqli_fetch_array($r)){
        array_push($result, $row['date']);
    }  

    if(sizeof($result) == 0){
        echo "Pas d'activités";
    } else {
    
        $tab_final = array();
        foreach($result as $res){
            $km = "SELECT distance FROM activity WHERE login = '$login' AND date='$res'";

            $r = $conn->query($km);

            $val = 0;

            while($row = mysqli_fetch_array($r)){
                $val = (double) $row['distance'];
            }

            $tab_final[$res] = $val;
        }
    }
   
    echo json_encode($tab_final);
}
    
?>
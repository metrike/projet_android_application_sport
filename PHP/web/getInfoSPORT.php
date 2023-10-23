<?php
if(isset($_GET["login"])){

    require_once "conn.php";
    require_once "validate.php";

    $login  = validate($_GET["login"]);

    $listsport = ["Course","Vélo","Basket","Football","Natation","Musculation","Aviron","Ski","Handball","Golf"];

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
            $tab = array();
            foreach($listsport as $sport){
                $nb_sport = "SELECT count(*) FROM activity WHERE login = '$login' AND date = '$res' AND sport = '$sport' ";

                $ra = $conn->query($nb_sport);

                $sp = mysqli_fetch_array($ra);
                if($sport == "Vélo"){
                    $tab['Velo'] = $sp['count(*)'];
                } else {
                    $tab[$sport] = $sp['count(*)'];
                }
            }
            $tab_final[$res] = $tab;
        }
        echo json_encode($tab_final);
    }
}
?>
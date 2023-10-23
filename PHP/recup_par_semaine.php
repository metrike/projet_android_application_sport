<?php 
if(isset($_POST['login']) && isset($_POST['donne'])){  
         
    require_once "conn.php";
    require_once "validate.php";

    $login = validate($_POST['login']);
    $trie = validate($_POST['donne']);    

    $datedeb = "SELECT date FROM `activity` where login = '$login' ORDER BY `activity`.`date` ASC limit 1";

    $resdatedeb = $conn->query($datedeb);
        
    while($row = mysqli_fetch_array($resdatedeb)){
        $date = $row["date"];
                
    }
    $datedebut = $date;

    /* Date de fin */
    $datefi = "SELECT date FROM `activity` where login = '$login' ORDER BY `activity`.`date` DESC limit 1";

    $resdatefin = $conn->query($datefi);
        
    while($row = mysqli_fetch_array($resdatefin)){
        $datef = $row["date"];
                
    }

    $datefin = $datef;


            
    date_default_timezone_set('UTC');

    $datetmp = $datedebut;
    $datetmp2 = $datedebut;

    $tabretour = array();

            
    while($datetmp<= $datefin){
        $resfin = 0;
        $datetmp2 = date('Y-m-d', strtotime($datetmp2. ' + 7 days'));
        $valeur = "SELECT * FROM `activity` where login = '$login' and date BETWEEN '$datetmp' and '$datetmp2' ";
        $taille = "SELECT count(*) FROM `activity` where login = '$login' and date BETWEEN '$datetmp' and '$datetmp2' ";

        $res = $conn->query($valeur);
        $res_taille = $conn->query($taille);

        $resultat = array();

        while($row = mysqli_fetch_array($res_taille)){
            $taillefin = $row['count(*)'];
        }
            
        while($sport = mysqli_fetch_array($res)){
            array_push($resultat,
            $sport[$trie]
                
            );
        }

        $list_sport = $resultat;
        foreach($list_sport as $list){
            if($trie == 'vitesse'){
                $list = str_replace(" KM/H","",$list);
            }
            else if($trie == 'distance'){
                $list = str_replace(" KM","",$list);
            }
            
            $result = doubleval($list);
            $resfin += $result;
        }

        if($taillefin == 0){
            $resfin = $resfin;
        }
        else{
            $resfin*=pow(10,2);
            $result =(int)($resfin / $taillefin);
            if(strlen($result)==2){
                $resfin = '0.'.$result;
            }
            else{
                $resfin = preg_replace('/(\d{'. 2 .'})$/','.\1',$result);
            }

            //$resfin = divideFloat($resfin, $taillefin,2);
        }

        array_push($tabretour,$resfin,$datetmp,$datetmp2);
        
        //echo($resfin."</br>");
        //echo "</br>un total de ".$resfin." pendant la semaine du ".$datetmp." au ".$datetmp2." et trier par ".$trie;
        //echo "</br>";
        
        $datetmp = date('Y-m-d', strtotime($datetmp. ' + 7 days'));
    }
   
    echo json_encode($tabretour);
}

    
?>
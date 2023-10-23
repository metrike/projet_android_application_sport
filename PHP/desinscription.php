<?php
if(isset($_POST['login'])){
    // Inclusion des fichiers php necessaires
    require_once "conn.php";
    require_once "validate.php";

    //Vérifie le contenu du login
    $login = validate($_POST['login']);

    //Supprime les activités de l'utilisateur
    $sql = "DELETE FROM activity WHERE login = '$login'";

    if(!$conn->query($sql)){
        echo "failure".$conn->error;
    }else{
        //Supprime l'utilisateur est connecté
        $sql = "DELETE FROM personne_connecter WHERE login = '$login'";

        if(!$conn->query($sql)){
            echo "failure".$conn->error;
        }else{
            //Supprime la photo de profil de l'utilisateur
            $sql = "DELETE FROM photo_de_profil WHERE login = '$login'";

            if(!$conn->query($sql)){
                echo "failure".$conn->error;
            }else{
                //Supprime l'utilisateur
                $sql = "DELETE FROM utilisateur WHERE id = '$login'";

                if(!$conn->query($sql)){
                    echo "failure".$conn->error;
                }else{
                   echo "successs";
                }                
            }
        }
    }
}
?>
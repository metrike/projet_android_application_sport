<!DOCTYPE html>
<html>
<head>
    <title>Parametre</title>
    <meta charset="utf-8">
    <script src="parametre.js" type="text/javascript"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="icon" type="image/png" sizes="16x16" href="./image/logo.png">
</head>
<body onload="showinfoperso('<?php echo $_POST['login'] ?>')">
 
    <div class="w3-bar w3-black">
        <form method="post" action="page.php">
            <input type="text"  name="login" class="login" value="<?php echo $_POST['login'] ?>" hidden>
            <button class="w3-button w3-left"><i class="fa fa-home"></i></button>
        </form>

        <a class="w3-bar-item w3-mobile"><?php echo $_POST['login'] ?></a>
        <a href="index.php" class="w3-bar-item w3-button w3-mobile w3-right">Déconnexion</a>
        <a class="w3-bar-item  w3-mobile w3-right w3-white" >Parametre</a>
    </div>  

    <form method="post" class="w3-container w3-card-4" action="javascript:update('<?php echo $_POST['login'] ?>')">
        <input type="text"  name="login" class="login" value="<?php echo $_POST['login'] ?>" hidden>
        <p>      
        <label><b>Age</b></label>
        <input class="w3-input w3-border" name="age" id="age" type="text" placeholder="age" ></p>
        <p>      
        <label><b>Poids</b></label>
        <input class="w3-input w3-border" name="poids" id="poids" type="text" placeholder="poids"></p>
        <p>  
        <label><b>Taille</b></label>
        <input class="w3-input w3-border" name="taille" id="taille" type="text" placeholder="taille"></p>
        <p>
        <input class="w3-radio" type="radio" name="sexe" value="homme" id="homme">
        <label>Homme</label></p>
        <p>
        <input class="w3-radio" type="radio" name="sexe" value="femme" id="femme">
        <label> Femme</label></p>     
        <button type="submit" class="w3-btn w3-black">Sauvegarder</button></p>
    </form>

    <div id="txtHint"></div>

</body>
</html>
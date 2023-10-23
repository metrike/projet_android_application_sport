<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    <title>My trainer</title>

    <script  src="scriptKm.js" type="text/javascript"></script>
    <script  src="scriptSport.js" type="text/javascript"></script>
    <script type="text/javascript" src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="https://canvasjs.com/assets/script/canvasjs.stock.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="icon" type="image/png" sizes="16x16" href="./image/logo.png">
</head>
<body onload="showinfokm('<?php echo $_POST['login'] ?>'); showinfosport('<?php echo $_POST['login'] ?>')">

    <div class="w3-bar w3-black">
        <button class="w3-button w3-left w3-white"><i class="fa fa-home"></i></button>
        <a class="w3-bar-item w3-mobile" ><?php echo $_POST['login'] ?></a>
        <a class="w3-bar-item w3-mobile">My trainer (a centrer)</a>
        <a href="index.php" class="w3-bar-item w3-button w3-mobile w3-right">Déconnexion</a>
        <form method="post" action="parametre.php">
            <input type="text"  name="login" class="login" value="<?php echo $_POST['login'] ?>" hidden>
            <input type="submit" value="Paramètre" class="w3-bar-item w3-button w3-mobile w3-right">
        </form>
    </div>
 
    <div id="chartContainer1" style="height: 400px; width: 100%;"></div>
    <div id="chartContainer2" style="height: 400px; width: 100%;"></div>
</body>
</html>
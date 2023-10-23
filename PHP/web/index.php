<!DOCTYPE html>
<html lang="fr">
<head>
        <title>My trainer</title>
        <link rel="icon" type="image/png" sizes="16x16" href="./image/logo.png">
        <script  src="getlogin.js" type="text/javascript"></script>
</head>
<body>
<img src="./image/logo.png"  alt="Logo my trainer">

    <form method="post" action="page.php">
        <div class="formulaire">
            <input type="text" id="nom" name="login" class="login" placeholder="Login" required><br>
            <input type="password" id="password" name="password" class="password" placeholder="Mot de passe" required><br>
            <br>
            <input type="submit" value="Connexion" class="bouton">
        </div>
    </form>
    
    <?php
    if(isset($_GET['out'])){
        ?>
            <p>login ou mot de passe incorrect</p>
        <?php
    }
    ?>
</body>
</html>

<?php
// Create 4 variables to store these information
$server="185.142.53.22";
$username="lp_iot";
$password="aucun";
$database = "lp_iot";
// Create connection
$conn = new mysqli($server, $username, $password, $database);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}
?>
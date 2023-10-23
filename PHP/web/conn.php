<?php
// Create 4 variables to store these information
$server="localhost";
$username="metri";
$password="kevin1503";
$database = "metri";
// Create connection
$conn = new mysqli($server, $username, $password, $database);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}
?>
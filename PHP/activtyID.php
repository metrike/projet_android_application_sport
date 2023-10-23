<?php
		require_once "conn.php";

        $sql = "SELECT COUNT(*) from activity";

        $result=$conn->query($sql);

        foreach($result as $row){
            $nb = $row["COUNT(*)"];
        }

		echo $nb;;




?>

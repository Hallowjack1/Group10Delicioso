<?php
    require("config.php");
    $code = $_REQUEST['code'];
    $sql = "SELECT OrderPrice FROM customerorder WHERE CustomerName LIKE '%$code%'";

//count how many people
//$sql = "SELECT COUNT(*) FROM tbl_people WHERE Fullname LIKE '%$code\"";

    try{
        $dbrecords = mysqli_query($connect, $sql);
    }
    catch (Exception $e) {
        $response["success"] = 0;
        $response["message"] = "Database Error #1. Please Try Again!";
        die(json_encode($response));
    }

    if(mysqli_num_rows($dbrecords) >= 1) { 
        $dbrecords = mysqli_query($connect, $sql);
        $cData = "";
        $cItemCodes = "";
        while($row = mysqli_fetch_assoc($dbrecords)) {
        //bag o ni

            foreach($row as $fieldname => $value){
                $cData.= $value. "-";
            }
        }
        $response["success"] = 1;
        $response["message"] = $cData; 
        die(json_encode($response));
    } else {
        $response["success"] = 0;
        $response["message"] = "Item not found.";
        die(json_encode($response));
    }
?>
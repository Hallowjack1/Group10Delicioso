<?php
    require("config.php");
    $sql = "UPDATE customerorder SET CustomerName = ".$_REQUEST["CustomerName"]. ", Quantity = ".$_REQUEST["Quantity"]. ", OrderName = ". $_REQUEST["OrderName"]. " ,
    OrderPrice = ". $_REQUEST["OrderPrice"] . " WHERE ID = '" . $_REQUEST["id"] . "'";

    try{
        $dbrecords = mysqli_query($connect, $sql);
    }

    catch (Exception $e) {
        $response["success"] = 0;
        $response["message"] = "Database Error #1. Please Try Again!";
        die(json_encode($response));
    }
        $response["success"] = 0;
        $response["message"] = "Record Updated";
        die(json_encode($response));
?>

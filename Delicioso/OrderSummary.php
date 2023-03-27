<!DOCTYPE html>
<html>
<head>
	<title>Order Summary</title>
	<style>
		body {
			font-family: Arial, sans-serif;
      background-color:#F4D19B;
		}
		h2 {
      width: 71%;
			text-align: center;
      margin: auto;
      background-color: #ff8732;
      padding: 20px;
      color: #502314;
		}
		table {
      padding-left: 200px;
			border-collapse: fill;
			width: 87%;
			margin-bottom: 20px;
		}
		th, td {
      margin: auto;
			padding: 15px;
			text-align: left;
			border-bottom: 1px solid #ddd;
		}
		th {
			background-color: #d93611;
      color: #582616;
		}
    tr {
			background-color: #f2f2f2;
      color: #502314;
		}
	</style>
</head>
<body>
	<?php
	$username = "root";
	$password = "";
	$host= "localhost";
	$dbname = "delicioso";

	// Create database connection
	$conn = mysqli_connect($host, $username, $password, $dbname);

	// Check connection
	if (!$conn) {
		die("Connection failed: " . mysqli_connect_error());
	}

	// Define the date range
	$start_date = "2023-03-01";
	$end_date = "2024-03-31";

	// Execute the daily summary report SQL query
	$daily_sql = "SELECT 
						DateOfPurchase, 
						COUNT(ID) AS total_orders, 
						SUM(Total) AS total_revenue, 
						AVG(Total) AS avg_order_value 
					FROM 
						customerorder 
					WHERE 
						DateOfPurchase BETWEEN '$start_date' AND '$end_date'
					GROUP BY 
            DateOfPurchase";
	$daily_result = mysqli_query($conn, $daily_sql);

	// Execute the monthly summary report SQL query
	$monthly_sql = "SELECT 
						MONTH(DateOfPurchase) AS month, 
						COUNT(ID) AS total_orders, 
						SUM(Total) AS total_revenue, 
						AVG(Total) AS avg_order_value 
					FROM 
            customerorder 
					WHERE 
            DateOfPurchase BETWEEN '$start_date' AND '$end_date'
					GROUP BY 
						MONTH(DateOfPurchase)";
	$monthly_result = mysqli_query($conn, $monthly_sql);

echo '<img src="deliciosologo2.png" alt="logo" style = "width: 20%; margin-left: auto; margin-right: auto; display: block;">';

	// Display the daily summary report in an HTML table
	if ($daily_result) {
		echo "<h2>Daily Summary Report</h2>";
		echo "<table>";
		echo "<thead>
            <tr>
              <th>Date</th>
              <th>Total Orders</th>
              <th>Total Income</th>
            </tr>
          </thead>";
		echo "<tbody>";
		while ($row = mysqli_fetch_assoc($daily_result)) {
			echo "<tr>";
			echo "<td>" . $row['DateOfPurchase'] . "</td>";
			echo "<td>" . $row['total_orders'] . "</td>";
			echo "<td>" . number_format($row['total_revenue'], 2) . "</td>";
			echo "</tr>";
		}
		echo "</tbody>";
		echo "</table>";
	} else {
		echo "<p>Error executing daily summary report query: " . mysqli_error($conn) . "</p>";
	}

// Display the monthly summary report in an HTML table
if ($monthly_result) {
  echo "<h2>Monthly Summary Report</h2>";
  echo "<table>";
  echo "<thead>
          <tr>
            <th>Month &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
            <th>Total Orders</th>
            <th>Total Income</th>
          </tr>
        </thead>";
  echo "<tbody>";
  while ($row = mysqli_fetch_assoc($monthly_result)) {
      $month = date("F", mktime(0, 0, 0, $row['month'], 1));
      echo "<tr><td>".$month."</td><td>".$row["total_orders"]."</td><td>".$row["total_revenue"]."</td>";
  }
  echo "</tbody></table>";
} else {
  echo "<p>No transactions this month.</p>";
}

// Close the database connection
mysqli_close($conn);
?>

</body>
</html>
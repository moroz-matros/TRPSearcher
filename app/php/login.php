<?php
    $db_name = 'trpsearcher';
    $username = 'root';
    $password = '';
    $servername = 'localhost';
    $con = mysqli_connect($servername, $username, $password, $db_name);
    
    $login = $_POST["login"];
    $password = $_POST["password"];
    
    $statement = "SELECT * FROM users WHERE login = '$login'";
    $find = mysqli_query($con, $statement);
    if (mysqli_num_rows($find) == 0){
        $response["success"] = false;
        $response["response"] = "Неверный логин или пароль";
    } else {
        $result = mysqli_fetch_array($find);
        if ($result['password'] == $password){
            $response["success"] = true;  
            $response["id"] = $result['id'];
        } else {
            $response["success"] = false;
            $response["response"] = "Неверный логин или пароль";
        }
    }
    
    echo json_encode($response);
?>
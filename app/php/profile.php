<?php
    $db_name = 'trpsearcher';
    $username = 'root';
    $password = '';
    $servername = 'localhost';
    $con = mysqli_connect($servername, $username, $password, $db_name);
    
    $id = $_POST["user_id"];
    $id = (int)$id;

    $statement = "SELECT * FROM profiles WHERE user_id = '$id'";
    if ($find = mysqli_query($con, $statement)){
        if (mysqli_num_rows($find) > 0){
            $result = mysqli_fetch_array($find);

            $statement_login = "SELECT * FROM users WHERE id = '$id'";
            if ($find_login = mysqli_query($con, $statement_login)){
                $result_login = mysqli_fetch_array($find_login);
                $response['login'] = $result_login['login'];
            } else {
                $response['success'] = false;
                $response['response'] = "Невозможно получить данные";
                exit;
            }

            $response['success'] = true;  
            $response['id'] = $result['id'];
            $response['name'] = $result['name'];
            $response['email'] = $result['email'];
            $response['birthdate'] = $result['birthdate'];
            $response['about'] = $result['about'];
            $response['rate'] = $result['rate'];
        } else {
            $response['success'] = false;
            $response['response'] = "Невозможно получить данные";
        } 
    } else {
        $response['success'] = false;
        $response['response'] = "Невозможно получить данные";
    }
    echo json_encode($response);
?>
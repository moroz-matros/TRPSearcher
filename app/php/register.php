<?php
    $db_name = "trpsearcher";
    $username = "root";
    $password = "";
    $servername = "localhost";
    $con = mysqli_connect($servername, $username, $password, $db_name);
    
    $login = $_POST['login'];
    $password = $_POST['password'];
    $email = $_POST['email'];
    $birthdate = $_POST['birthdate'];

    //check for existing login and email

    $statement_check_login = ("SELECT * FROM users WHERE login = '$login'");
    $res_check_login = mysqli_query($con, $statement_check_login);
    $statement_check_email = ("SELECT * FROM profiles WHERE email = '$email'");
    $res_check_email = mysqli_query($con, $statement_check_email);

    if (mysqli_num_rows($res_check_login) > 0){
        $response['success'] = false;
        $response['response'] = "Пользователь с данным логином уже существует";
    }
    else if (mysqli_num_rows($res_check_email) > 0){
        $response['success'] = false;
        $response['response'] = "Пользователь с данным адресом электронной почты уже существует";
    } else {
        //create user
        $statement_reg = ("INSERT INTO users (login, password) VALUES ('$login', '$password')");
        mysqli_query($con, $statement_reg);

        //find id
        $statement_find = ("SELECT * FROM users WHERE login = '$login'");
        $find = mysqli_query($con, $statement_find);
        $id = mysqli_fetch_array($find)['id'];
        
        //add to profile
        $response['id'] = $id;
        $rate = 0;
        $statement_prof = ("INSERT INTO profiles (user_id, email, birthdate) 
        VALUES ('$id', '$email', '$birthdate')");
        mysqli_query($con, $statement_prof);
        
        $response['success'] = true;
    }
    echo json_encode($response);

?>
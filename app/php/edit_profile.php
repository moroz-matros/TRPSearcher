<?php
    $db_name = 'trpsearcher';
    $username = 'root';
    $password = '';
    $servername = 'localhost';
    $con = mysqli_connect($servername, $username, $password, $db_name);

    $name = $_POST['name'];
    $about = $_POST['about'];
    $email = $_POST['email'];
    $id = $_POST['profile_id'];
    $id = (int)$id;

    $statement = "UPDATE profiles 
                SET name = '$name', about = '$about', email = '$email' 
                WHERE id = '$id'";

    if (mysqli_query($con, $statement)) {
        $response['success'] = true;
        $response['response'] = "Профиль успешно отредактирован";
    }
    else {
        $response['success'] = false;
        $response['response'] = "Не удалось отредактировать профиль";
    }
    echo json_encode($response);

?>
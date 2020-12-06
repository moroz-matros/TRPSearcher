<?php
    $db_name = 'trpsearcher';
    $username = 'root';
    $password = '';
    $servername = 'localhost';
    $con = mysqli_connect($servername, $username, $password, $db_name);
        
    $title = $_POST['title'];
    $text = $_POST['text'];
    $id = $_POST['id'];
    $id = (int)$id;


    $statement ="INSERT INTO forms (title, text, id_owner) VALUES ('$title', '$text', '$id')";

    $result = mysqli_query($con, $statement);

    if (!$result) {
        $response['success'] = false;
        $response['response'] = "Не удалось разместить объявление";
        
    } else {
        $response['success'] = true;
        $response['response'] = "Объявление успешно размещено";
    }

    echo json_encode($response);
?>
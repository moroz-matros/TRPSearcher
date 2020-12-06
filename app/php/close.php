<?php
    $db_name = 'trpsearcher';
    $username = 'root';
    $password = '';
    $servername = 'localhost';
    $con = mysqli_connect($servername, $username, $password, $db_name);

    $id = $_POST['id'];
    $id = (int)$id;
    $type = $_POST['type'];
    $type = (int)$type;
    $true = TRUE;

    if ($type == 0){
        $statement = "UPDATE forms SET closed = '$true' WHERE id = '$id'";

        if (mysqli_query($con, $statement)) {
            $response['success'] = true;
            $response['response'] = "Объявление успешно закрыто.\nОбновите страницу для получения актуальной информации";
        }
        else {
            $response['success'] = false;
            $response['response'] = "Не удалось закрыть объявление";
        }
    } else {
        $statement = "UPDATE games SET closed = '$true' WHERE id = '$id'";
        if (mysqli_query($con, $statement)) {
            $response['success'] = true;
            $response['response'] = "Игра успешно завершена.\nОбновите страницу для получения актуальной информации";
        }
        else {
            $response['success'] = false;
            $response['response'] = "Не удалось завершить игру";
        }
    }

    echo json_encode($response);
?>
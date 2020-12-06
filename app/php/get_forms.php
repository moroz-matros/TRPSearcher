<?php
    $db_name = 'trpsearcher';
    $username = 'root';
    $password = '';
    $servername = 'localhost';
    $con = mysqli_connect($servername, $username, $password, $db_name);

    $key = 1;
    $user_id = $_POST['user_id'];
    $user_id = (int)$user_id;

    $statement = "SELECT * FROM forms WHERE (closed <> '$key') AND (id_owner = '$user_id')";
    $result = mysqli_query($con, $statement);
    if (!$result) {
        $response['success'] = false;
        $response['response'] = "Невозможно получить данные";
        echo json_encode($response);
        exit;
    } 
    
    $rows = [];

    while ($row = mysqli_fetch_assoc($result)) {

        $rows[] = [
            'id' => $row['id'],
            'user2_id' => $row['id_owner'],
            'title' => $row['title'],
            'text' => $row['text'],
        ];
    }

    $statement = "SELECT * FROM forms WHERE (closed <> '$key') AND (id_owner <> '$user_id')";
    $result = mysqli_query($con, $statement);
    if (!$result) {
        $response['success'] = false;
        $response['response'] = "Невозможно получить данные";
        echo json_encode($response);
        exit;
    } 

    while ($row = mysqli_fetch_assoc($result)) {

        $rows[] = [
            'id' => $row['id'],
            'user2_id' => $row['id_owner'],
            'title' => $row['title'],
            'text' => $row['text'],
        ];
    }

    if (sizeof($rows) == 0){
        $response['success'] = false;
        $response['response'] = "Нет заявок";
        echo json_encode($response);
        exit;
    } 

    $response['success'] = true;
    $response['response'] = $rows;

    echo json_encode($response);
?>
<?php
    $db_name = 'trpsearcher';
    $username = 'root';
    $password = '';
    $servername = 'localhost';
    $con = mysqli_connect($servername, $username, $password, $db_name);

    $user_id = $_POST['user_id'];
    $user_id = (int)$user_id;


    $statement = "SELECT * FROM chats WHERE id1 = '$user_id' OR id2 = '$user_id'";
    $result = mysqli_query($con, $statement);
    if (!$result) {
        $response['success'] = false;
        $response['response'] = "Невозможно получить данные";
        echo json_encode($response);
        exit;
    } 

    if (mysqli_num_rows($result) == 0){
        $response['success'] = false;
        $response['response'] = "Нет активных чатов";
        echo json_encode($response);
        exit;
    } 

    $rows = [];

    while ($row = mysqli_fetch_assoc($result)) {
        $id1 = $row['id1'];
        $id2 = $row['id2'];
        if ($id1 != $user_id){
            $statement_login = "SELECT * FROM users WHERE id = '$id1'";
            $id_have_new = $row['id2_have_new'];
        } else {
            $statement_login = "SELECT * FROM users WHERE id = '$id2'";
            $id_have_new = $row['id1_have_new'];
        }

        $result_login = mysqli_query($con, $statement_login);
        $row_login = mysqli_fetch_array($result_login);
        $user2_login = $row_login['login'];
        $user2_id = $row_login['id'];

        $messages = $row['messages'];
        $messages = json_decode($messages);

        $rows[] = [
            'id' => $user2_id,
            'login' => $user2_login,
            'messages' => $messages,
            'id_have_new' => $id_have_new
        ];
    }
    
    $response['success'] = true;
    $response['response'] = $rows;

    echo json_encode($response);
?>
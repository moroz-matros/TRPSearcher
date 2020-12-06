<?php
    $db_name = 'trpsearcher';
    $username = 'root';
    $password = '';
    $servername = 'localhost';
    $con = mysqli_connect($servername, $username, $password, $db_name);

    $user_id = $_POST['user_id'];
    $user_id = (int)$user_id;

    $statement = "SELECT * FROM games WHERE id1 = '$user_id' OR id2 = '$user_id' ORDER BY closed";
    $result = mysqli_query($con, $statement);
    if (!$result) {
        $response['success'] = false;
        $response['response'] = "Невозможно получить данные";
        echo json_encode($response);
        exit;
    } 

    if (mysqli_num_rows($result) == 0){
        $response['success'] = false;
        $response['response'] = "Нет созданных игр";
        echo json_encode($response);
        exit;
    } 

    $rows = [];

    while ($row = mysqli_fetch_assoc($result)) {
        $id1 = $row['id1'];
        $id2 = $row['id2'];
        if ($id1 != $user_id){
            $statement_login = "SELECT login FROM users WHERE id = '$id1'";
            $user2_id = $id1;
        } else {
            $statement_login = "SELECT login FROM users WHERE id = '$id2'";
            $user2_id = $id2;
        }

    
        $result_login = mysqli_query($con, $statement_login);
        $row_login = mysqli_fetch_array($result_login);
        $user2_login = $row_login['login'];

        $game = $row['game'];
        $game = json_decode($game);

        $rows[] = [
            'id' => $row['id'],
            'user2_id' => $user2_id,
            'user2_login' => $user2_login,
            'title' => $row['title'],
            'description' => $row['description'],
            'game' => $game,
            'closed' => $row['closed'],
        ];
    }
    
    $response['success'] = true;
    $response['response'] = $rows;

    echo json_encode($response);

?>
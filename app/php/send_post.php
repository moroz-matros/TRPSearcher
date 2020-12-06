<?php
    $db_name = 'trpsearcher';
    $username = 'root';
    $password = '';
    $servername = 'localhost';
    $con = mysqli_connect($servername, $username, $password, $db_name);
        
    $id = $_POST['id'];
    $id = (int)$id;
    $user_id = $_POST['user_id'];
    $user_id = (int)$user_id;
    $user2_id = $_POST['user2_id'];
    $user2_id = (int)$user2_id;
    $text = $_POST['text'];

    $cur_post = array();
    $cur_post['sender'] = $user_id;
    $cur_post['text'] = $text;

    $result = mysqli_query($con, "SELECT game FROM games WHERE id = '$id'");
    if (!$result) {
        $response['success'] = false;
        $response['response'] = "Невозможно получить данные";
        echo json_encode($response);
        exit;
    } 

    if (mysqli_num_rows($result) == 0){
        $response['success'] = false;
        $response['response'] = "Не удалость найти игру";
        echo json_encode($response);
        exit;
    } 

    $game = mysqli_fetch_assoc($result)['game'];

    $game = json_decode($game);

    array_push($game, $cur_post);

    $game = json_encode($game, JSON_UNESCAPED_UNICODE);

    if(mysqli_query($con, "UPDATE games SET game = '$game' WHERE id = '$id'")){
        $response['success'] = true;
        $response['response'] = "Пост успешно отправлен";
    } else {
        $response['success'] = false;
        $response['response'] = "Не удалось отправить пост";
    }
    
    echo json_encode($response);
?>
<?php
    $db_name = 'trpsearcher';
    $username = 'root';
    $password = '';
    $servername = 'localhost';
    $con = mysqli_connect($servername, $username, $password, $db_name);
        
    $user_id = $_POST['user_id'];
    $user_id = (int)$user_id;
    $user2_id = $_POST['user2_id'];
    $user2_id = (int)$user2_id;
    $title = $_POST['title'];
    $description = $_POST['description'];

    $game= array();
    $game = json_encode($game);


    $statement ="INSERT INTO games
                (id1, id2, title, description, game)
                VALUES ('$user_id', '$user2_id', '$title', '$description', '$game')";

    $result = mysqli_query($con, $statement);

    if (!$result) {
        $response['success'] = false;
        $response['response'] = "Не удалось создать игру";
        
    } else {
        $response['success'] = true;
        $response['response'] = "Игра успешно создана";
    }

    echo json_encode($response);
?>
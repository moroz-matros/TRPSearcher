<?php
    $db_name = 'trpsearcher';
    $username = 'root';
    $password = '';
    $servername = 'localhost';
    $con = mysqli_connect($servername, $username, $password, $db_name);

    $user_id = $_POST['user_id'];
    $user_id = (int)$user_id;
    $cur_id = $_POST['cur_id'];
    $cur_id = (int)$cur_id;

    $rate = $_POST['rate'];
    $rate = (int)$rate;
    if ($rate == 1) $isgood = true;
    else $isgood = false;

    $response = array();

    $statement = mysqli_prepare($con, "SELECT * FROM ratings WHERE from_id = ? AND to_id = ?");
    mysqli_stmt_bind_param($statement, "ii", $user_id, $cur_id);
    mysqli_stmt_execute($statement);

    if(mysqli_stmt_fetch($statement)){
        $response["success"] = false;   
        $response["response"] = "Вы уже оставляли оценку на этого пользователя";   
    }
    else{
        mysqli_query($con, "INSERT INTO ratings  (from_id, to_id, is_good) VALUES ('$user_id', '$cur_id', '$isgood')");
        if ($isgood){
            mysqli_query($con, "UPDATE profiles SET rate = rate + 1 WHERE user_id = '$cur_id'");
        }  
        else {
            mysqli_query($con, "UPDATE profiles SET rate = rate - 1 WHERE user_id = '$cur_id'");
        }
        $response["success"] = true;
        $response["response"] = "Оценка успешно оставлена"; 
    }
    echo json_encode($response);
?>
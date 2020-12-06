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
    $message = $_POST['message'];

    $true = TRUE;
    $false = FALSE;

    $statement ="SELECT * FROM chats 
                WHERE (id1 = '$user_id' AND id2 = '$user2_id') 
                OR (id1 = '$user2_id' AND id2 = '$user_id')";

    $result = mysqli_query($con, $statement);

    if (!$result) {
        $response['success'] = false;
        $response['response'] = "Невозможно получить данные";
        echo json_encode($response);
        exit;
    } 


    $cur_message = array();
    $cur_message['sender'] = $user_id;
    $cur_message['title'] = $title;
    $cur_message['message'] = $message;
    
    if (mysqli_num_rows($result) == 0){
        $messages = array();
        array_push($messages, $cur_message);
        
        $messages = json_encode($messages, JSON_UNESCAPED_UNICODE);

        if(mysqli_query($con, "INSERT INTO chats (id1, id2, messages, 
        id1_have_new, id2_have_new) VALUES ('$user_id', '$user2_id', '$messages', '$false', '$true')")){
            $response["success"] = true;
            $response['response'] = "Сообщение успешно отправлено";
        } else{
            $response['success'] = false;
            $response['response'] = "Невозможно отправить сообщение";
            echo json_encode($response);
            exit;
        }
    } else {

        $row = mysqli_fetch_assoc($result);
        $id = $row['id'];
        $id1 = $row['id1'];
        $id2 = $row['id2'];
        $messages = $row['messages'];
        $id1_have_new = $row['id1_have_new'];
        $id2_have_new = $row['id2_have_new'];

        $messages = json_decode($messages);

        array_push($messages, $cur_message);

        $messages = json_encode($messages, JSON_UNESCAPED_UNICODE);

        if ($id1 == $user_id){
            if(mysqli_query($con, "UPDATE chats SET messages = '$messages', id2_have_new = '$true' WHERE id = '$id'")){
                $response["success"] = true;
                $response['response'] = "Сообщение успешно отправлено";
            }
            }else{
                if(mysqli_query($con, "UPDATE chats SET messages = '$messages', id1_have_new = '$true' WHERE id = '$id'")){
                    $response["success"] = true;
                    $response['response'] = "Сообщение успешно отправлено";
                }
            }
        }
    
        echo json_encode($response);
?>
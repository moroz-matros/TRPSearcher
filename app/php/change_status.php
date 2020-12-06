<?php
$db_name = "trpsearcher";
$username = "root";
$password = "";
$servername = "localhost";
$con = mysqli_connect($servername ,$username ,$password ,$db_name );
    $response = array();
    $response["success"] = false;
        
        $id = $_POST['id'];
        $id = (int)$id;
        $id_to = $_POST['id2'];
        $id_to = (int)$id_to;
        $statement = mysqli_prepare($con, "SELECT * FROM chats WHERE (id1 = ? AND id2 = ?) OR (id1 = ? AND id2 = ?)");
        mysqli_stmt_bind_param($statement, "iiii", $id, $id_to, $id_to, $id);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $chat_id, $id1, $id2, $messages, $id1_have_new, $id2_have_new);
        mysqli_stmt_fetch($statement);
        $false = FALSE;
        if ($id == $id1){
            if(mysqli_query($con, "UPDATE chats SET id1_have_new = '$false' WHERE id = '$chat_id'")){
                $response["success"] = true;
                $response["ok"] = "ok";
            } 
        } else{
            if(mysqli_query($con, "UPDATE chats SET id2_have_new = '$false' WHERE id = '$chat_id'")){
                $response["success"] = true;
            } 
        }
    
echo json_encode($response);

?>
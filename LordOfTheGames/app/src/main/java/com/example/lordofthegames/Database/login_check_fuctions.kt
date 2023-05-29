package com.example.lordofthegames.Database

import java.security.MessageDigest
import java.security.SecureRandom

/*
*

private function getHashedPassword(){
    $sql = "SELECT password, salt FROM utente WHERE Email = ? AND UserID = ? ";
    $stmt = $this->db->prepare($sql);
    $stmt->bind_param('ss', $_COOKIE['mail'], $_COOKIE['id']);
    $stmt->execute();
    $result = $stmt->get_result();
    $ven = $result->fetch_all(MYSQLI_ASSOC);
    return $ven[0];
}
public function logUser($mail, $password){

    //echo("<br><br>");
    //UserID, Email, password, salt
    $info = $this->getUserLogInfo($mail);
    //echo("<br><br>");
    // Crea una password usando la chiave appena creata.
    $password = hash('sha512', $password.$info[0]['salt']);
    /*
    echo($password);

    echo("<br><br>");
    echo($info[0]['password']);*/

    if($password == $info[0]["password"]){
        $inTwoMonths = 60 * 60 * 24 * 60 + time();
        setcookie("id", $info[0]["UserID"], $inTwoMonths);
        setcookie("mail", $mail, $inTwoMonths);
        setcookie("logged", true, $inTwoMonths);
        setcookie("vendors", $info[0]["vendors"], $inTwoMonths);
        return $info;
    }
}
    private function getUserLogInfo($mail){
    $query = "SELECT UserID, Email, vendors, password, salt FROM utente WHERE Email = ?";
    $stmt = $this->db->prepare($query);
    $stmt->bind_param('s', $mail);
    $stmt->execute();
    $result = $stmt->get_result();
    return $result->fetch_all(MYSQLI_ASSOC);
    }

 */



//fun addUser(nick: String, mail: String, password: String) {
//    val salt = generateSalt()
//    val hashedPassword = hashPassword(password, salt)
//
//    $sql = "INSERT INTO `User`(`Nome`, `Cognome`, `Email`, `password`, `salt`, `vendors`)
//    VALUES (?, ?, ?, ?, ?, 0)";
//    $stmt = $this->db->prepare($sql);
//    $stmt->bind_param('sssss',$nome, $cognome, $mail, $password, $random_salt);
//    $stmt->execute();
//
//    /*controllo se la query è andata a buon fine poichè nel db la mail è un valore unico per utente e
//    quindi non serve un controllo sull'esistenza dell'utente*/
//    if($this->db->error){
//        return array(false, "$mail esiste già");
//    }
//
//    //se non entra nell'if aggiungerà l'utente anche sulla tabella compratore
//
//    //prendiamo l'id dell'utente appena creato...
//    $utente = $this->getUser($mail); //qui va sistemato
//    $id = $utente[0]["UserID"];
//
//    //...e lo inseriamo su compratore
//    $sql="INSERT INTO `compratore`(`sesso`, `userID`) VALUES (?, ?)";
//    $stmt = $this->db->prepare($sql);
//    $stmt->bind_param('ss',$sesso, $id);
//    $stmt->execute();
//
//    //infine setto i cookie per i dati che servono per mantenere l'accesso
//    if (isset($_COOKIE['id']) && isset($_COOKIE['mail']) && isset($_COOKIE['logged']) && isset($_COOKIE["vendors"]) ) {
//        unset($_COOKIE['id']);
//        unset($_COOKIE['mail']);
//        unset($_COOKIE['logged']);
//        unset($_COOKIE['vendors']);
//    }
//    setcookie("id", $id);
//    setcookie("mail", $mail);
//    setcookie("logged", true);
//    setcookie("vendors", 0);
//
//    return array(true, "Registrazione avvenuta con successo");
//}
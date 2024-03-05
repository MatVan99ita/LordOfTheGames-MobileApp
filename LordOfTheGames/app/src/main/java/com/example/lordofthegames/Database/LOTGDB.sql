-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Set 20, 2023 alle 16:02
-- Versione del server: 10.4.24-MariaDB
-- Versione PHP: 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_per_android`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `achievement`
--

CREATE TABLE `achievement` (
  `achievement_id` int(11) NOT NULL,
  `name` tinytext NOT NULL,
  `description` text NOT NULL,
  `img` text DEFAULT NULL,
  `actual_count` int(11) NOT NULL DEFAULT 0,
  `total_count` int(11) NOT NULL DEFAULT 1,
  `status` tinyint(1) NOT NULL DEFAULT 0,
  `game_ref` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `achievement`
--

INSERT INTO `achievement` (`achievement_id`, `name`, `description`, `img`, `actual_count`, `total_count`, `status`, `game_ref`) VALUES
(20, 'Gli amici sono sempre qui per aiutarti', 'Utilizza il parcheggio automatico', '0', 0, 1, 0, 18),
(21, 'Diesel, non benzina!', 'Utilizza una stazione di rifornimento', '0', 1, 1, 0, 18),
(22, 'Insegnamenti di Rost seguiti', '', '0', 1, 1, 0, 6),
(23, 'Secodonte sconfitto\r\n', '', '0', 0, 1, 0, 6),
(26, 'Livello 10 raggiunto', 'Hai raggiunto il livello giocatore 10.', NULL, 3, 10, 0, 6);

-- --------------------------------------------------------

--
-- Struttura della tabella `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `category_name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `categories`
--

INSERT INTO `categories` (`category_id`, `category_name`) VALUES
(1, 'Story-driven'),
(2, 'Souls-like'),
(3, 'Strategico'),
(4, 'GDR'),
(5, 'Azione'),
(6, 'Avventura'),
(7, 'Open world'),
(8, 'Simulativo'),
(9, 'Hack&Slash'),
(10, 'Fantasy'),
(11, 'Third person'),
(12, 'TPS'),
(13, 'FPS'),
(14, 'Dark fantasy'),
(15, 'Horror');

-- --------------------------------------------------------

--
-- Struttura della tabella `comment`
--

CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL,
  `discussion_ref` int(11) NOT NULL,
  `content` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `comment`
--

INSERT INTO `comment` (`comment_id`, `discussion_ref`, `content`) VALUES
(1, 1, 'Perchè è lo stormo che i approccia');

-- --------------------------------------------------------

--
-- Struttura della tabella `discussion`
--

CREATE TABLE `discussion` (
  `discussion_id` int(11) NOT NULL,
  `title` text NOT NULL,
  `content` longtext NOT NULL,
  `game_ref` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `discussion`
--

INSERT INTO `discussion` (`discussion_id`, `title`, `content`, `game_ref`) VALUES
(1, 'Vergil è più bello di Dante?', 'Si perchè you should motivate yourself NOW', 11);

-- --------------------------------------------------------

--
-- Struttura della tabella `game`
--

CREATE TABLE `game` (
  `game_id` int(11) NOT NULL,
  `game_title` mediumtext NOT NULL,
  `game_description` longtext NOT NULL,
  `game_cover` text NOT NULL DEFAULT '\'img\'',
  `game_status` varchar(11) NOT NULL DEFAULT 'Not played'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `game`
--

INSERT INTO `game` (`game_id`, `game_title`, `game_description`, `game_cover`, `game_status`) VALUES
(1, 'giuoco test', '', 'img', '0'),
(6, 'Horizon Zero Dawn™', 'Vivi il viaggio leggendario di Aloy e svela i misteri della Terra del futuro dominata dalle Macchine. Usa attacchi strategici e devastanti contro le tue prede ed esplora un maestoso open world in questo pluripremiato GdR d\'azione!', '\'img\'', '0'),
(7, 'METAL GEAR RISING: REVENGEANCE', 'Developed by Kojima Productions and PlatinumGames, METAL GEAR RISING: REVENGEANCE takes the renowned METAL GEAR franchise into exciting new territory with an all-new action experience. The game seamlessly melds pure action and epic story-telling that surrounds Raiden – a child soldier transformed into a half-human, half-cyborg ninja who uses his High Frequency katana blade to cut through any thing that stands in his vengeful path!\r\n\r\nA huge success on both Xbox 360® and PlayStation®3, METAL GEAR RISING: REVENGEANCE comes to PC with all the famed moves and action running within a beautifully-realised HD environment.\r\n\r\nThis new PC version includes all three DLC missions: Blade Wolf, Jetstream, and VR Missions, in addition to all customized body upgrades for Raiden, including: White Armor, Inferno Armor, Commando Armor, Raiden’s MGS4 body, and the ever-popular Cyborg Ninja.\r\n\r\n\"CUTSCENES\" option added to the Main Menu. Play any and all cutscenes.\r\n\r\n\"CODECS\" option added to the Main Menu. Play all and any codec conversation scenes.\r\n\r\nMenu option added to the CHAPTER Menu enabling user to play only the Boss battles.\r\n\r\n\"GRAPHIC OPTIONS\" added to the OPTIONS Menu. Modify resolution, anti-aliasing, etc.\r\nThere is an option reading \"ZANGEKI\" that will modify the amount of cuts you can make.', '\'img\'', '0'),
(8, 'Marvel’s Spider-Man', 'Sviluppato da Insomniac Games in collaborazione con Marvel, e ottimizzato per PC da Nixxes Software, Marvel\'s Spider-Man Remastered per PC racconta la storia di un Peter Parker ormai esperto, impegnato a combattere senza tregua i supercriminali della New York Marvel. Trovare il giusto equilibrio tra la carriera e la vita privata, tuttavia, non è mai semplice, soprattutto quando il destino di una città ricade sulle tue spalle.\r\n\r\nCaratteristiche principali\r\n\r\nEssere migliori\r\nQuando un nuovo criminale minaccia New York, le vite di Peter Parker e Spider-Man entrano in collisione. Per salvare la città e i suoi cari, sarà costretto a lottare per... essere migliore.\r\n\r\nDiventa Spider-Man\r\nDopo otto anni dietro la maschera, Peter Parker è un veterano della lotta al crimine. Sperimenta gli immensi poteri di uno Spider-Man ormai esperto e in grado di improvvisare in combattimento, eseguire acrobazie estreme, muoversi con agilità e interagire con l\'ambiente.\r\n\r\nMondi contrapposti\r\nI mondi di Peter Parker e Spider-Man si scontrano in una storia originale carica di azione. Questo nuovo universo firmato Marvel reinventa i classici protagonisti della saga, assegnando a icone storiche un ruolo totalmente inedito.\r\n\r\nLa New York della Marvel è il tuo palcoscenico\r\nLa Grande Mela prende vita nel mondo più esteso e interattivo mai creato dai talenti di Insomniac. Lanciati attraverso quartieri brulicanti di vita e goditi la vista mozzafiato dei luoghi simbolo di Marvel e Manhattan. Sfrutta lo scenario per sconfiggere i criminali con eliminazioni in puro stile cinematografico.\r\n\r\nScopri i contenuti della serie La città che non dorme mai\r\nConclusi gli eventi di Marvel\'s Spider-Man Remastered, affronta un\'ulteriore storia in tre capitoli in Marvel\'s Spider-Man: La città che non dorme mai, con missioni aggiuntive e sfide tutte da scoprire.', '\'img\'', '0'),
(9, 'Bloodborne', 'Dai la caccia ai tuoi incubi\r\nUn viandante solitario. Una città maledetta. Un mistero letale che annienta tutto ciò con cui entra in contatto. Affronta le tue paure addentrandoti nella desolata città di Yharnam, un luogo dimenticato, flagellato da una terribile piaga. Esplora le sue ombre più cupe, combatti per la tua vita con spade e armi e scopri segreti che ti geleranno il sangue... e ti garantiranno la salvezza...', '\'img\'', '0'),
(10, 'The Last of Us Parte II', 'Cinque anni dopo...\r\n\r\nDopo un pericoloso viaggio attraverso gli Stati Uniti flagellati dalla pandemia, Ellie e Joel si stabiliscono in Wyoming.\r\n\r\nLa vita in comunità garantisce loro una nuova stabilità, nonostante la costante minaccia degli infetti e dei sopravvissuti più disperati.\r\n\r\nMa quando un inatteso evento compromette quegli equilibri, Ellie decide di intraprendere un nuovo viaggio. Questa volta per ottenere giustizia. La caccia ai responsabili, tuttavia, le riserverà strazianti ripercussioni fisiche ed emozionali.\r\n\r\n• Vivi una nuova storia piena di emozioni, che metterà in discussione i tuoi concetti di giusto e sbagliato.\r\n• Avventurati in un mondo magnifico ma pericoloso, attraverso idilliaci scenari montani e città soffocate dalla vegetazione.\r\n• Sperimenta dinamiche di gioco evolute da action-survival, tra combattimenti corpo a corpo, attraversamento di ambienti e fasi stealth dinamiche.', '\'img\'', '0'),
(11, 'Devil May Cry 5', 'Il cacciatore di demoni definitivo torna con stile nell\'azione di gioco che i fan stavano aspettando. Include il contenuto scaricabile Personaggio selezionabile: Vergil (disponibile anche separatamente).\r\n\r\nIl nuovo episodio della leggendaria serie di azione, Devil May Cry 5, fonde la caratteristica azione dinamica con gli originali personaggi dell\'altro mondo e con la nuovissima tecnologia di gioco di Capcom per creare un capolavoro di azione e avventura graficamente rivoluzionario.\r\n', '\'img\'', '0'),
(12, 'DARK SOULS™ III', 'DARK SOULS™ continua il suo percorso di innovazione in questo ultimo, ambizioso capitolo della saga acclamata da pubblico e critica.\r\n\r\nLe fiamme si estinguono e il mondo è in rovina: esplora un universo ricco di nemici colossali e vaste ambientazioni, assaporando un\'atmosfera epica e affrontando l\'oscurità grazie a un sistema di combattimento sempre più frenetico e intenso. Vecchi e nuovi giocatori potranno immergersi nel mondo di DARK SOULS™ grazie alla spettacolare grafica e alle dinamiche di gioco che offrono un\'esperienza unica.\r\nDel fuoco non rimangono che braci... preparati ad affrontare nuovamente l\'oscurità!', '\'img\'', '0'),
(13, 'ELDEN RING', 'UN NUOVO ACTION RPG FANTASY.\r\nAlzati, Senzaluce, e lasciati guidare dalla grazia verso la conquista dell\'Anello ancestrale, il cui potere ti renderà lord dell\'Interregno.\r\n\r\n• Un mondo sconfinato e sorprendente\r\nUn vasto mondo, in cui lande sconfinate e dense di pericoli si intersecano senza soluzione di continuità a dedali sotterranei dalle sontuose architetture tridimensionali. Esplora l\'ignoto e combatti minacce mortali in un mondo in cui la sopravvivenza è una conquista.\r\n\r\n• Dai vita al tuo personaggio\r\nOltre a creare da zero l\'aspetto del tuo eroe, puoi personalizzarne l\'equipaggiamento scegliendo armi, corazze e incantesimi. Sviluppa le sue capacità in base al tuo stile di gioco, favorendo la forza fisica o puntando sulle pratiche magiche.\r\n\r\n• Un dramma epico che nasce dal mito\r\nVivi una storia sfaccettata narrata da frammenti. Un dramma epico in cui le vicende di ciascun personaggio si intrecciano sullo sfondo dell\'Interregno.\r\n\r\n• Una speciale modalità online che collega le esperienze\r\nOltre alla modalità multiplayer classica, che permette di connettersi direttamente agli altri giocatori ed esplorare insieme, il gioco include una funzione asincrona online che rende tangibile la presenza di altri eroi.', '\'img\'', '0'),
(14, 'Diablo III', 'Combatti per gli abitanti di Sanctuarium e viaggia dal Paradiso Celeste agli Inferi Fiammeggianti in questo gioco di ruolo d’azione.', '\'img\'', '0'),
(15, 'Diablo IV', 'Gli inferi si scatenano in questo GDR d\'azione ambientato in un immenso e oscuro mondo aperto.\r\n\r\nLa leggendaria serie di GDR d\'azione torna con Diablo IV. Questa spettacolare evoluzione introduce un nuovo vasto mondo da esplorare, colmo della promessa di avventure senza fine, demoni letali e bottini leggendari.\r\n\r\nScopri la terra corrotta di Sanctuarium mentre cade in una nuova era di oscurità. Lilith, figlia di Mefisto, Signore dell\'Odio, è stata liberata dall\'esilio e ora la sua ripugnante influenza minaccia di consumare il mondo. Solo tu e i tuoi compagni d\'avventura potete ergervi tra Lilith e le rovine di Sanctuarium.\r\n\r\nEsplora Sanctuarium in solitaria o con i tuoi amici man mano che progredisci nell\'avvincente campagna di Diablo IV, affrontando missioni, liberando città e combattendo boss epici. In piena tradizione Diablo, ti attende un avvincente scontro finale; i giocatori possono incontrarsi nel mondo condiviso del gioco per fare scambi, unirsi in squadre o mettere alla prova la loro forza in zone PVP senza lobby. Cross-play, progressi multipiattaforma, e modalità cooperativa locale consentono di proseguire l\'avventura in qualsiasi momento e ovunque. ', '\'img\'', '0'),
(16, 'Cyberpunk 2077', 'Cyberpunk 2077 è un GDR d\'azione a mondo aperto ambientato nella megalopoli Night City, nella quale diventi un mercenario cyberpunk coinvolto in una lotta per la sopravvivenza. Migliorato e con contenuti aggiuntivi gratuiti. Personalizza avatar e stile di gioco, accetta incarichi, fatti un nome e sblocca potenziamenti. Stringerai relazioni e prenderai decisioni che decideranno la tua storia e daranno forma al mondo attorno a te. Le leggende nascono qui. Quale sarà la tua?\r\n\r\n\r\nCREA IL TUO CYBERPUNK\r\nDiventa un fuorilegge urbano dotato di potenziamenti cibernetici, e crea la tua leggenda sulle strade di Night City.\r\n\r\n\r\nESPLORA LA CITTÀ DEL FUTURO\r\nNight City è piena zeppa di cose da fare, luoghi da vedere e persone da incontrare. Sarai tu a scegliere dove, quando e come andare.\r\n\r\n\r\nCREA LA TUA LEGGENDA\r\nAffronta incredibili avventure e stringi relazioni con personaggi indimenticabili, i cui destini saranno plasmati dalle tue scelte.\r\n\r\n\r\nMIGLIORIE DA UN FUTURO OSCURO\r\nProva Cyberpunk 2077 con una serie di cambiamenti e miglioramenti al gioco e all\'economia, alla città, all\'utilizzo della mappa e molto altro ancora.\r\n\r\n\r\nINCLUDE CONTENUTI AGGIUNTIVI GRATUITI\r\nMetti le mani su una scorta di nuovi oggetti come nuove armi da fuoco e da mischia, nuove opzioni di personalizzazione e altro ancora.', '\'img\'', '0'),
(17, 'Europa Universalis IV', 'Porta a termine la dominazione globale\r\nParadox Development Studio ritorna con il quarto episodio della pluripremiata serie Europa Universalis. Questo classico gioco del genere grand strategy ti permette di controllare una nazione che attraversa quattro secoli drammatici. Governa la tua terra e domina il mondo intero con una libertà, una profondità e un\'accuratezza storica senza precedenti. Scrivi una nuova storia del mondo e fonda un impero per i secoli a venire.\r\n\r\nAmbientazione storica dettagliata\r\nInizia prima del Rinascimento su una mappa del mondo com\'era allora. Scegli tra centinaia di nazioni e governa fino all\'Età napoleonica. In alternativa, se lo desideri, puoi iniziare la partita in qualsiasi data di questo arco di tempo, con i monarchi storici e gli altri leader dell\'epoca.\r\n\r\nGoditi la storia mentre prende vita\r\nCentinaia di eventi storici e dinamici sono a tua disposizione, da semplici guerre civili a momenti che cambiano il mondo, come la Riforma protestante. Scopri e colonizza il Nuovo Mondo, oppure resisti alla conquista europea.\r\n\r\nRiempiti le tasche\r\nControlla i flussi di mercato sviluppando una tua potenza commerciale nelle province chiave, e usa le navi e la politica governativa per far giungere le ricchezze del mondo nei tuoi porti.\r\n\r\nGuerra e pace\r\nStringi alleanze e trasformale in legami indissolubili, consolidati da un matrimonio reale, oppure mantieni un atteggiamento vago per tenere aperte tutte le tue opzioni. Colpisci quando i tuoi nemici sono deboli, usando i tuoi eserciti per conquistare nuove terre e nuove potenziali ricchezze.\r\n\r\nUn buon sovrano porta tempi prosperi\r\nIl ritmo di sviluppo della tua nazione sarà fortemente influenzato dalla persona che siede sul trono. Riscontra un rapido sviluppo sotto un abile monarca e assisti a un notevole rallentamento quando la carica passa a un erede meno competente. Pianifica il futuro impiegando saggiamente il potere monarchico.\r\n\r\nUna rivoluzione scientifica\r\nSono disponibili quattrocento anni di ricerche su nuovi metodi di guerra, amministrazione e commercio. Sblocca nuove armi, nuovi edifici e nuovi tipi di navi. Nel corso del tempo, puoi adottare approcci nazionali basati su idee che rappresentano sia la tua eredità storica che le tue ambizioni per il futuro.\r\nDai grandi viaggi di scoperta alle guerre di religione fino ai governi rivoluzionari, l\'intera storia del mondo moderno aspetta che tu la riscriva in Europa Universalis IV.', '\'img\'', '0'),
(18, 'Euro Truck Simulator 2', 'Travel across Europe as king of the road, a trucker who delivers important cargo across impressive distances! With dozens of cities to explore from the UK, Belgium, Germany, Italy, the Netherlands, Poland, and many more, your endurance, skill and speed will all be pushed to their limits. If you’ve got what it takes to be part of an elite trucking force, get behind the wheel and prove it!\r\nKey Features:\r\nTransport a vast variety of cargo across more than 60 European cities.\r\nRun your own business which continues to grow even as you complete your freight deliveries.\r\nBuild your own fleet of trucks, buy garages, hire drivers, manage your company for maximum profits.\r\nA varied amount of truck tuning that range from performance to cosmetic changes.\r\nCustomize your vehicles with optional lights, bars, horns, beacons, smoke exhausts, and more.\r\nThousands of miles of real road networks with hundreds of famous landmarks and structures.\r\nWorld of Trucks\r\nTake advantage of additional features of Euro Truck Simulator 2 by joining our online community on World of Trucks, our center for virtual truckers all around the world interested in Euro Truck Simulator 2 and future SCS Software\'s truck simulators.\r\n\r\nUse in-game Photo Mode to capture the best moments and share them with thousands of people who love trucks.\r\nFavorite the images you like the most and return to them anytime in the future.\r\nDiscuss the screenshots with everyone using World of Trucks.\r\nSee the best images hand-picked by the game creators in Editor\'s Pick updated almost every day. Try to get your own screenshot on this list!\r\nUpload and use your custom avatar and license plate in the game.\r\nMore features coming soon!', '\'img\'', '0');

-- --------------------------------------------------------

--
-- Struttura della tabella `gamecategory`
--

CREATE TABLE `gamecategory` (
  `game_ref` int(11) NOT NULL,
  `category_ref` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `gamecategory`
--

INSERT INTO `gamecategory` (`game_ref`, `category_ref`) VALUES
(7, 9),
(9, 2),
(9, 5),
(9, 15),
(9, 16),
(10, 1),
(11, 9),
(11, 10),
(13, 2),
(13, 7),
(13, 15),
(16, 7),
(16, 13),
(17, 3),
(18, 8);

-- --------------------------------------------------------

--
-- Struttura della tabella `gameplatform`
--

CREATE TABLE `gameplatform` (
  `platform_ref` int(11) NOT NULL,
  `game_ref` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `gameplatform`
--

INSERT INTO `gameplatform` (`platform_ref`, `game_ref`) VALUES
(1, 9),
(1, 16),
(2, 10),
(3, 16),
(4, 16),
(9, 14),
(9, 15)
(1,1),(2,1),(3,1),(5,1),(7,1)
;

-- --------------------------------------------------------

--
-- Struttura della tabella `notes`
--

CREATE TABLE `notes` (
  `id` int(11) NOT NULL,
  `title` tinytext NOT NULL,
  `content` text NOT NULL,
  `last_modified` datetime NOT NULL DEFAULT current_timestamp(),
  `game_ref` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `platform`
--

CREATE TABLE `platform` (
  `platform_id` int(11) NOT NULL,
  `nome` text NOT NULL,
  `icona` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `platform`
--

INSERT INTO `platform` (`platform_id`, `nome`, `icona`) VALUES
(1, 'PlayStation 4', ''),
(2, 'PlayStation 5', ''),
(3, 'Steam', ''),
(4, 'Epic', ''),
(5, 'Xbox One', ''),
(6, 'Xbox 360', ''),
(7, 'Nintendo switch', ''),
(8, 'Nintendo DS', ''),
(9, 'Battle.net', ''),
(10, "Game Pass");

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `achievement`
--
ALTER TABLE `achievement`
  ADD PRIMARY KEY (`achievement_id`),
  ADD KEY `game` (`game_ref`);

--
-- Indici per le tabelle `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indici per le tabelle `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `comment_ibfk_1` (`discussion_ref`);

--
-- Indici per le tabelle `discussion`
--
ALTER TABLE `discussion`
  ADD PRIMARY KEY (`discussion_id`),
  ADD KEY `discussion_ibfk_1` (`game_ref`);

--
-- Indici per le tabelle `game`
--
ALTER TABLE `game`
  ADD PRIMARY KEY (`game_id`);

--
-- Indici per le tabelle `gamecategory`
--
ALTER TABLE `gamecategory`
  ADD PRIMARY KEY (`game_ref`,`category_ref`),
  ADD KEY `categoria` (`category_ref`),
  ADD KEY `gioco` (`game_ref`);

--
-- Indici per le tabelle `gameplatform`
--
ALTER TABLE `gameplatform`
  ADD PRIMARY KEY (`platform_ref`,`game_ref`),
  ADD KEY `game ref` (`game_ref`);

--
-- Indici per le tabelle `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `notes_ibfk_1` (`game_ref`);

--
-- Indici per le tabelle `platform`
--
ALTER TABLE `platform`
  ADD PRIMARY KEY (`platform_id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `achievement`
--
ALTER TABLE `achievement`
  MODIFY `achievement_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT per la tabella `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT per la tabella `comment`
--
ALTER TABLE `comment`
  MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `discussion`
--
ALTER TABLE `discussion`
  MODIFY `discussion_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `game`
--
ALTER TABLE `game`
  MODIFY `game_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT per la tabella `platform`
--
ALTER TABLE `platform`
  MODIFY `platform_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `achievement`
--
ALTER TABLE `achievement`
  ADD CONSTRAINT `game` FOREIGN KEY (`game_ref`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`discussion_ref`) REFERENCES `discussion` (`discussion_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `discussion`
--
ALTER TABLE `discussion`
  ADD CONSTRAINT `discussion_ibfk_1` FOREIGN KEY (`game_ref`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `gamecategory`
--
ALTER TABLE `gamecategory`
  ADD CONSTRAINT `categoria` FOREIGN KEY (`category_ref`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `gioco` FOREIGN KEY (`game_ref`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `gameplatform`
--
ALTER TABLE `gameplatform`
  ADD CONSTRAINT `game ref` FOREIGN KEY (`game_ref`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `platform ref` FOREIGN KEY (`platform_ref`) REFERENCES `platform` (`platform_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`game_ref`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

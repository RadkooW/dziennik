-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 23 Lut 2022, 19:13
-- Wersja serwera: 10.4.21-MariaDB
-- Wersja PHP: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `dziennik`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `klasa`
--

CREATE TABLE `klasa` (
  `id_klasa` int(11) NOT NULL,
  `nazwa` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `klasa`
--

INSERT INTO `klasa` (`id_klasa`, `nazwa`) VALUES
(13, '1A'),
(14, '1B'),
(15, '1C'),
(16, '2A'),
(17, '2B'),
(18, '2C'),
(19, '3A'),
(20, '3B'),
(28, '3C');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `logowanie`
--

CREATE TABLE `logowanie` (
  `id_logowanie` int(11) NOT NULL,
  `login` text NOT NULL,
  `haslo` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `logowanie`
--

INSERT INTO `logowanie` (`id_logowanie`, `login`, `haslo`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `nauczyciel`
--

CREATE TABLE `nauczyciel` (
  `id_nauczyciel` int(11) NOT NULL,
  `imie` text NOT NULL,
  `nazwisko` text NOT NULL,
  `adres` text NOT NULL,
  `telefon` int(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `nauczyciel`
--

INSERT INTO `nauczyciel` (`id_nauczyciel`, `imie`, `nazwisko`, `adres`, `telefon`) VALUES
(8, 'Janina', 'Rutkowska', 'Leśna 12', 325543687),
(9, 'Zdzisław', 'Milewski', 'Widokowa 7', 685473231),
(10, 'Arleta', 'Wierzbicka', 'Grunwaldzka 15', 685743232),
(11, 'Emil', 'Kubiak', 'Zduńska 1', 445323332),
(12, 'Iwona', 'Grzelak', 'Brzeźna 32', 867543885),
(16, 'Bronisław', 'Brzozowski', 'Sosnowa 22', 523552343);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `przedmiot`
--

CREATE TABLE `przedmiot` (
  `id_przedmiot` int(11) NOT NULL,
  `id_klasa` int(11) NOT NULL,
  `nazwa` text NOT NULL,
  `id_nauczyciel` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `przedmiot`
--

INSERT INTO `przedmiot` (`id_przedmiot`, `id_klasa`, `nazwa`, `id_nauczyciel`) VALUES
(5, 13, 'Przyroda', 8),
(6, 14, 'Matematyka', 9),
(7, 15, 'Informatyka', 10),
(8, 16, 'Język Polski', 11),
(9, 19, 'Język Angielski', 12),
(14, 20, 'Biologia', 9),
(15, 17, 'Język Niemiecki', 12);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rodzic`
--

CREATE TABLE `rodzic` (
  `id_rodzic` int(11) NOT NULL,
  `imie` text NOT NULL,
  `nazwisko` text NOT NULL,
  `imie1` text NOT NULL,
  `nazwisko1` text NOT NULL,
  `adres` text NOT NULL,
  `telefon` int(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `rodzic`
--

INSERT INTO `rodzic` (`id_rodzic`, `imie`, `nazwisko`, `imie1`, `nazwisko1`, `adres`, `telefon`) VALUES
(12, 'Marta', 'Lipińska', 'Witold', 'Lipiński', 'Różana 16', 432423421),
(13, 'Alicja', 'Kozak', 'Mieczysław', 'Kozak', 'Bielawska 76', 432412123),
(16, 'Renata', 'Szewczyk', 'Krzysztof', 'Szewczyk', 'Stolarska 4', 765454323),
(17, 'Aneta', 'Czajkowska', 'Dariusz', 'Czajkowski', 'Szkolna 15', 876765456),
(18, 'Marta', 'Kamińska', 'Wojciech', 'Kamiński', 'Kujawska 3 ', 753473453);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `uczen`
--

CREATE TABLE `uczen` (
  `id_uczen` int(11) NOT NULL,
  `imie` text NOT NULL,
  `nazwisko` text NOT NULL,
  `adres` text NOT NULL,
  `telefon` int(9) NOT NULL,
  `id_rodzic` int(11) NOT NULL,
  `id_klasa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `uczen`
--

INSERT INTO `uczen` (`id_uczen`, `imie`, `nazwisko`, `adres`, `telefon`, `id_rodzic`, `id_klasa`) VALUES
(14, 'Marcin', 'Lipiński', 'Różana 16', 124125325, 12, 13),
(15, 'Daniel', 'Kozak', 'Bielawska 76', 324236658, 13, 16),
(16, 'Krystian', 'Szewczyk', 'Stolarska 4', 234237547, 16, 19),
(17, 'Maria', 'Kamińska', 'Kujawska 3', 856856856, 18, 18),
(18, 'Milena', 'Czajkowska', 'Szkolna 15', 764534234, 17, 20);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `klasa`
--
ALTER TABLE `klasa`
  ADD PRIMARY KEY (`id_klasa`);

--
-- Indeksy dla tabeli `logowanie`
--
ALTER TABLE `logowanie`
  ADD PRIMARY KEY (`id_logowanie`);

--
-- Indeksy dla tabeli `nauczyciel`
--
ALTER TABLE `nauczyciel`
  ADD PRIMARY KEY (`id_nauczyciel`);

--
-- Indeksy dla tabeli `przedmiot`
--
ALTER TABLE `przedmiot`
  ADD PRIMARY KEY (`id_przedmiot`),
  ADD KEY `id_klasa` (`id_klasa`),
  ADD KEY `id_nauczyciel` (`id_nauczyciel`);

--
-- Indeksy dla tabeli `rodzic`
--
ALTER TABLE `rodzic`
  ADD PRIMARY KEY (`id_rodzic`);

--
-- Indeksy dla tabeli `uczen`
--
ALTER TABLE `uczen`
  ADD PRIMARY KEY (`id_uczen`),
  ADD KEY `id_rodzic` (`id_rodzic`),
  ADD KEY `id_klasa` (`id_klasa`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `klasa`
--
ALTER TABLE `klasa`
  MODIFY `id_klasa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT dla tabeli `logowanie`
--
ALTER TABLE `logowanie`
  MODIFY `id_logowanie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `nauczyciel`
--
ALTER TABLE `nauczyciel`
  MODIFY `id_nauczyciel` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT dla tabeli `przedmiot`
--
ALTER TABLE `przedmiot`
  MODIFY `id_przedmiot` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT dla tabeli `rodzic`
--
ALTER TABLE `rodzic`
  MODIFY `id_rodzic` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT dla tabeli `uczen`
--
ALTER TABLE `uczen`
  MODIFY `id_uczen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `przedmiot`
--
ALTER TABLE `przedmiot`
  ADD CONSTRAINT `przedmiot_ibfk_1` FOREIGN KEY (`id_klasa`) REFERENCES `klasa` (`id_klasa`),
  ADD CONSTRAINT `przedmiot_ibfk_2` FOREIGN KEY (`id_nauczyciel`) REFERENCES `nauczyciel` (`id_nauczyciel`);

--
-- Ograniczenia dla tabeli `uczen`
--
ALTER TABLE `uczen`
  ADD CONSTRAINT `uczen_ibfk_1` FOREIGN KEY (`id_rodzic`) REFERENCES `rodzic` (`id_rodzic`),
  ADD CONSTRAINT `uczen_ibfk_2` FOREIGN KEY (`id_klasa`) REFERENCES `klasa` (`id_klasa`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

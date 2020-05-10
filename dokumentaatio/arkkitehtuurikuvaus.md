# Arkkitehtuurikuvaus

## Rakenne

<img src="https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/silo-package.png" width="300">

## Käyttöliittymä

Käyttöliittymässä on viisi eri näkymää
- kirjautuminen
- uuden käyttäjän luominen
- uuden siilokartan luominen
- siilokarttanäkymä
- siilon tietojen syöttäminen

## Sovelluslogiikka

### Luokkakaavio

<img src="https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/silo-class.png" width="300">

### Sekvenssikaavio

<img src="https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/silo-sequence.png" width="800">

## Tietojen pysyväistallennus

Käyttäjätiedot tallenetaan userDao-luokan avulla users-tietokantaan, asiakastiedot clientDao-luokan avulla clients.tietokantaan ja siilovaraston tiedot siloDao-luokan avulla silos-tietokantaan.

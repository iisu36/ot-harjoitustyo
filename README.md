# **Ohjemistotekniikka**

# **Harjoitustyö - _Silo_**

## Dokumentaatio
[Käyttöohje](https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuurikuvaus.md)
<!--[Testausdokumentti](https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/testausdokumentti.md)-->
[Työaikakirjanpito](https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

## Releaset

[Viikko 4](https://github.com/iisu36/ot-harjoitustyo/tree/master/Silo)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _Silo-1.0-SNAPSHOT.jar_

<!--### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_-->

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/iisu36/ot-harjoitustyo/blob/master/Silo/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_

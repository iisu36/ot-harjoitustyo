# **Ohjemistotekniikka**

## **Tehtavat**

### Viikko 3

[1 Monopoli](https://github.com/iisu36/ot-harjoitustyo/blob/master/laskarit/viikko3/1monopoli.jpg)

[2 Monopoli](https://github.com/iisu36/ot-harjoitustyo/blob/master/laskarit/viikko3/2monopoli.jpg)

[3 Bensatankki ja moottori](https://github.com/iisu36/ot-harjoitustyo/blob/master/laskarit/viikko3/3bensamoottori.png)

[4 HSL](https://github.com/iisu36/ot-harjoitustyo/blob/master/laskarit/viikko3/4hsl.png)

# **Harjoitustyö**

## Dokumentaatio
<!--[Käyttöohje](https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)-->

[Vaatimusmäärittely](https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)
<!--[Arkkitehtuurikuvaus](https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuurikuvaus.md)
[Testausdokumentti](https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/testausdokumentti.md)-->
[Työaikakirjanpito](https://github.com/iisu36/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

## Releaset

[Viikko 3](https://github.com/iisu36/ot-harjoitustyo/tree/master/Silo)

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

generoi hakemistoon _target_ suoritettavan jar-tiedoston _OtmTodoApp-1.0-SNAPSHOT.jar_

<!--### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/mluukkai/OtmTodoApp/blob/master/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_-->



# Tema POO  - GwentStone
#### _Gorin Natalia-Stefania, grupa 343C2_

## Descriera proiectului
GwentStoneLite reprezinta o implementare a unei versiuni simplificate a jocului Gwent cu elemente din Hearthstone. Scopul principal al proiectului este de a crea un cadru de simulare a unui joc de carti intre doi jucatori.

## Structura proiectului
* gwentstonelite/
  * 
    * Card 
    * heroes/
      * EmpressThorina
      * GeneralKocioraw
      * KingMudface
      * LordRoyce
    * minions/ 
      * Berserker
      * Goliath
      * Sentinel
      * Warden
    * special_cards/
      * Disciple
      * Miraj
      * TheCursedOne
      * TheRipper
  * Action
  * GameCommand
  * GameSession
  * GwentStoneLite
  * OutputBuilder
  * Player


### Card 
Clasa publica(,) care defineste functionalitatile si atributele unei carti de GwentStoneLite. Aceasta clasa urmeaza sa fie extinsa de clasele care definesc tipurile de carti: eroi, minioni si carti cu abilitati speciale.

## heroes/
  ### EmpressThorina, GeneralKocioraw, KingMudface, LordRoyce 
Clase care definesc eroul de tip Empress Thorina, General Kocioraw, King Mudface si Lord Royce. Aceste clase extind clasa Card si mostenesc atributele si functionalitatile acesteia, adaugand metode pentru indeplinirea abilitatilor specifice fiecarui erou.

## minions/
  ### Berserker, Goliath, Sentinel, Warden
Clase ce definesc cartile de tip minion: Berserker, Goliath, Sentinel si Warden. Aceste clase mostenesc atributele si functionalitatile clasei Card si ajuta la instantierea cartilor de tip minion prin intermediul constructorului.

## special_cards/
  ### Disciple, Miraj, TheCursedOne, TheRipper
Clase ce definesc cartile cu abilitati speciale: Disciple, Miraj, TheCursedOne si The Ripper. Aceste clase extind clasa Card si in functie de numele cartii, se suprascrie metoda useAbility() pentru a implementa abilitatea specifica fiecarei carti.

### Action
Aceasta clasa defineste o serie de metode cu ajutorul carora sunt gestionate actiunile jucatorilor asupra cartilor din joc. Sunt implementate metode pentru adaugarea cartilor, resetarea starii cartilor si determinarea caracteristiiclor.

### GameCommand
Clasa se comporta ca un controller pentru gestionarea comenzilor jocului, astfel fiind centralzate. Sunt implementate comenzi ce se axeaza pe gestionarea cartilor, comenzi pentru debug si comenzi pentru a vedea statistici legate de joc.

### GameSession
Aceasta clasa izoleaza toate detaliile legate de logica jocului, fiind astfel un punct central pentru gestionarea jocului. Sunt implementate metode pentru pregatire jocului, inceperea acestuia si chiar trecerea la runda urmatoare. Aceasta clasa este esentiala pentru ca asigura o logica eficienta pentru fluxul general al jocului. 

### GwentStoneLite
Clasa principală care gestionează întreaga logică a jocului, inclusiv inițializarea jucătorilor, configurarea și pornirea sesiunilor de joc. Aceasta interacționează cu celelalte clase pentru a asigura desfășurarea corectă a jocului.
### OutputBuilder
Clasa ce se ocupa cu generarea outputului in format JSON, in functie de actiunile jucatorilor. 

### Player
Clasa ce defineste un jucator cu atributele sale. Aceasta clasa este utilizata pe parcursul jocului pentru a manipula starea jucatorului in functie de stadiul in care se afla partida de joc.





